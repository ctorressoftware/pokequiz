package com.ctorres.pokequiz.service;

import com.ctorres.pokequiz.config.JwtProperties;
import com.ctorres.pokequiz.dto.auth.TokenResponse;
import com.ctorres.pokequiz.entity.RefreshToken;
import com.ctorres.pokequiz.entity.User;
import com.ctorres.pokequiz.exception.DuplicatedUsernameException;
import com.ctorres.pokequiz.exception.InactiveUserException;
import com.ctorres.pokequiz.exception.InvalidRefreshTokenException;
import com.ctorres.pokequiz.exception.WeakPasswordException;
import com.ctorres.pokequiz.repository.RefreshTokenRepository;
import com.ctorres.pokequiz.repository.RoleRepository;
import com.ctorres.pokequiz.repository.UserRepository;
import com.ctorres.pokequiz.service.security.AuthUser;
import com.ctorres.pokequiz.service.security.JwtService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtService jwt;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder encoder;
    private final RoleRepository roles;
    private final RefreshTokenRepository refreshTokens;
    private final UserRepository users;

    public AuthService(UserRepository users,
                       RoleRepository roles,
                       RefreshTokenRepository refreshTokens,
                       PasswordEncoder encoder,
                       JwtService jwt,
                       Clock clock,
                       JwtProperties jwtProperties,
                       AuthenticationManager authenticationManager) {
        this.users = users;
        this.roles = roles;
        this.refreshTokens = refreshTokens;
        this.encoder = encoder;
        this.jwt = jwt;
        this.clock = clock;
        this.jwtProperties = jwtProperties;
        this.authenticationManager = authenticationManager;
    }

    private static String sha256Hex(String s) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private String newRefreshTokenValue() {
        return UUID.randomUUID() + "." + UUID.randomUUID();
    }

    private String issueAccess(Long id, String username, Collection<String> roles) {
        var claims = new HashMap<String, Object>();
        claims.put("uid", id);
        claims.put("roles", roles);
        return jwt.generateAccessToken(username, claims);
    }

    private String[] issueRefresh(User u, String ip, String userAgent) {
        var value = newRefreshTokenValue();
        var now = OffsetDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS);
        var rt = new RefreshToken();
        rt.setUser(u);
        rt.setTokenHash(sha256Hex(value));
        rt.setIssuedAt(now);
        rt.setExpiresAt(now.plusDays(jwtProperties.getRefreshDays()));
        rt.setIp(ip);
        rt.setUserAgent(userAgent);
        refreshTokens.save(rt);
        return new String[]{value, rt.getTokenHash()};
    }

    private boolean isStrongPassword(String password) {
        final int MIN_LENGTH = jwtProperties.getUserPasswordMinLength();
        final String SPECIAL_CHARACTERS_REGEX = "[^a-zA-Z0-9\\s]";

        if (password.length() < MIN_LENGTH) return false;
        if (!hasNoEmojis(password)) return false;
        if (password.codePoints().noneMatch(Character::isUpperCase)) return false;
        if (password.codePoints().noneMatch(Character::isLowerCase)) return false;
        if (password.codePoints().noneMatch(Character::isDigit)) return false;
        return password.codePoints().anyMatch(c ->
                Character.toString(c).matches(SPECIAL_CHARACTERS_REGEX));
    }

    private boolean hasNoEmojis(String text) {
        return text.codePoints().noneMatch(cp ->
                Character.isEmoji(cp) && !Character.isDigit(cp) && !Character.isLetter(cp));
    }

    @Transactional
    public TokenResponse register(String username, String password, String ip, String userAgent) {
        if (!isStrongPassword(password)) throw new WeakPasswordException();
        var user = new User(username, encoder.encode(password), true);
        var roleUser = roles.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER not seeded"));
        user.getRoles().add(roleUser);

        try {
            users.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatedUsernameException(username);
        }

        var access = issueAccess(user.getId(), user.getUsername(), user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.toList()));
        var pair = issueRefresh(user, ip, userAgent);
        return TokenResponse.builder()
                .accessToken(access)
                .refreshToken(pair[0])
                .build();
    }

    @Transactional
    public TokenResponse login(String username, String rawPassword, String ip, String userAgent) {
        var token = new UsernamePasswordAuthenticationToken(username, rawPassword);
        var auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var user = (AuthUser) auth.getPrincipal();
        var access = issueAccess(user.getId(), user.getUsername(), user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        var domainUser = users.getReferenceById(user.getId());
        var pair = issueRefresh(domainUser, ip, userAgent);
        return TokenResponse.builder()
                .accessToken(access)
                .refreshToken(pair[0])
                .build();
    }

    @Transactional
    public TokenResponse refresh(String refreshTokenValue, String ip, String userAgent) {
        var hash = sha256Hex(refreshTokenValue);
        var now = OffsetDateTime.now(clock);
        var stored = refreshTokens.findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(hash, now)
                .orElseThrow(InvalidRefreshTokenException::new);
        stored.setRevokedAt(now);
        var user = stored.getUser();
        if (!user.isActive()) throw new InactiveUserException();
        var access = issueAccess(user.getId(), user.getUsername(), user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.toList()));
        var pair = issueRefresh(user, ip, userAgent);
        stored.setReplacedByHash(pair[1]);
        return TokenResponse.builder()
                .accessToken(access)
                .refreshToken(pair[0])
                .build();
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        var hash = sha256Hex(refreshTokenValue);
        refreshTokens.revokeByHash(hash);
    }
}