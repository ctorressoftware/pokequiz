package com.ctorres.pokequiz.application.usecase.auth;

import com.ctorres.pokequiz.application.port.in.auth.login.LoginCommand;
import com.ctorres.pokequiz.application.port.in.auth.login.LoginResult;
import com.ctorres.pokequiz.application.port.in.auth.login.LoginUseCase;
import com.ctorres.pokequiz.application.port.in.auth.logout.LogoutCommand;
import com.ctorres.pokequiz.application.port.in.auth.logout.LogoutResult;
import com.ctorres.pokequiz.application.port.in.auth.logout.LogoutUseCase;
import com.ctorres.pokequiz.application.port.in.auth.refresh.RefreshCommand;
import com.ctorres.pokequiz.application.port.in.auth.refresh.RefreshResult;
import com.ctorres.pokequiz.application.port.in.auth.refresh.RefreshUseCase;
import com.ctorres.pokequiz.application.port.in.auth.register.RegisterCommand;
import com.ctorres.pokequiz.application.port.in.auth.register.RegisterResult;
import com.ctorres.pokequiz.application.port.in.auth.register.RegisterUseCase;
import com.ctorres.pokequiz.infrastructure.persistence.entity.UserEntity;
import com.ctorres.pokequiz.infrastructure.security.config.JwtProperties;
import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;
import com.ctorres.pokequiz.infrastructure.persistence.entity.RefreshTokenEntity;
import com.ctorres.pokequiz.application.usecase.auth.exception.DuplicatedUsernameException;
import com.ctorres.pokequiz.application.usecase.auth.exception.InactiveUserException;
import com.ctorres.pokequiz.application.usecase.auth.exception.InvalidRefreshTokenException;
import com.ctorres.pokequiz.application.usecase.auth.exception.WeakPasswordException;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataRefreshTokenRepository;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataRoleRepository;
import com.ctorres.pokequiz.infrastructure.persistence.repository.SpringDataUserRepository;
import com.ctorres.pokequiz.infrastructure.security.AuthUser;
import com.ctorres.pokequiz.infrastructure.security.JwtService;
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
public class AuthService implements LoginUseCase, RegisterUseCase, RefreshUseCase, LogoutUseCase {
    private final AuthenticationManager authenticationManager;
    private final Clock clock;
    private final JwtService jwt;
    private final JwtProperties jwtProperties;
    private final PasswordEncoder encoder;
    private final SpringDataRoleRepository roles;
    private final SpringDataRefreshTokenRepository refreshTokens;
    private final SpringDataUserRepository users;

    public AuthService(SpringDataUserRepository users,
                       SpringDataRoleRepository roles,
                       SpringDataRefreshTokenRepository refreshTokens,
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

    private String sha256Hex(String s) {
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

    private String issueAccess(UUID id, String username, Collection<String> roles) {
        var claims = new HashMap<String, Object>();
        claims.put("uid", id);
        claims.put("roles", roles);
        return jwt.generateAccessToken(username, claims);
    }

    private String[] issueRefresh(UserEntity u, String ip, String userAgent) {
        var value = newRefreshTokenValue();
        var now = OffsetDateTime.now(clock).truncatedTo(ChronoUnit.SECONDS);
        var rt = new RefreshTokenEntity();
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
    public RegisterResult register(RegisterCommand command) {
        if (!isStrongPassword(command.password())) throw new WeakPasswordException();
        var user = new UserEntity(command.username(), encoder.encode(command.password()), true);
        var roleUser = roles.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER not seeded"));
        user.getRoles().add(roleUser);

        try {
            users.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatedUsernameException(command.username());
        }

        var access = issueAccess(user.getId(), user.getUsername(), user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.toList()));
        var pair = issueRefresh(user, command.ip(), command.userAgent());
        return new RegisterResult(TokenResponse.builder()
                .accessToken(access)
                .refreshToken(pair[0])
                .build());
    }

    @Transactional
    public LoginResult login(LoginCommand command) {

        var token = new UsernamePasswordAuthenticationToken(
                command.username(),
                command.rawPassword()
        );

        var auth = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(auth);
        var user = (AuthUser) auth.getPrincipal();
        var access = issueAccess(user.getId(), user.getUsername(), user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        var domainUser = users.getReferenceById(user.getId());
        var pair = issueRefresh(domainUser, command.ip(), command.userAgent());
        return new LoginResult(TokenResponse.builder()
                .accessToken(access)
                .refreshToken(pair[0])
                .build()
        );
    }

    @Transactional
    public RefreshResult refresh(RefreshCommand command) {
        var hash = sha256Hex(command.refreshTokenValue());
        var now = OffsetDateTime.now(clock);
        var stored = refreshTokens.findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(hash, now)
                .orElseThrow(InvalidRefreshTokenException::new);
        stored.setRevokedAt(now);
        var user = stored.getUser();
        if (!user.isActive()) throw new InactiveUserException();
        var access = issueAccess(user.getId(), user.getUsername(), user.getRoles().stream()
                .map(role -> "ROLE_" + role.getName())
                .collect(Collectors.toList()));
        var pair = issueRefresh(user, command.ip(), command.userAgent());
        stored.setReplacedByHash(pair[1]);
        return new RefreshResult(TokenResponse.builder()
                .accessToken(access)
                .refreshToken(pair[0])
                .build());
    }

    @Transactional
    public LogoutResult logout(LogoutCommand command) {
        var hash = sha256Hex(command.refreshTokenValue());
        boolean revoked = refreshTokens.revokeByHash(hash) > 0;
        return new LogoutResult(revoked);
    }
}