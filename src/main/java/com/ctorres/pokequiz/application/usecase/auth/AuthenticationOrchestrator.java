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
import com.ctorres.pokequiz.application.port.out.*;
import com.ctorres.pokequiz.domain.model.auth.User;
import com.ctorres.pokequiz.infrastructure.web.response.auth.TokenResponse;
import com.ctorres.pokequiz.application.usecase.auth.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.HexFormat;
import java.util.stream.Collectors;

@Service
public class AuthenticationOrchestrator
        implements LoginUseCase, RegisterUseCase, RefreshUseCase, LogoutUseCase {
    private final Authenticator authenticationService;
    private final Clock clock;
    private final TokenIssuer tokenIssuer;
    private final RefreshTokenIssuer refreshTokenIssuer;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public AuthenticationOrchestrator(
            UserRepository userRepository,
            RoleRepository roleRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordEncoder passwordEncoder,
            TokenIssuer tokenIssuer,
            RefreshTokenIssuer refreshTokenIssuer,
            Clock clock,
            Authenticator authenticationService
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenIssuer = tokenIssuer;
        this.refreshTokenIssuer = refreshTokenIssuer;
        this.clock = clock;
        this.authenticationService = authenticationService;
    }

    private boolean isStrongPassword(String password) {
        final int MIN_LENGTH = 8;
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
        var user = User.create(command.username(), passwordEncoder.encode(command.password()));
        var roleUser = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("Role USER not seeded"));
        user.roles().add(roleUser);

        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new DuplicatedUsernameException(command.username());
        }

        var access = tokenIssuer.issueAccessToken(
                user.id(),
                user.username(),
                user.roles().stream()
                        .map(role -> "ROLE_" + role.name())
                        .collect(Collectors.toList()));

        var refreshToken = refreshTokenIssuer.issueRefreshToken(user, command.ip(), command.userAgent());
        return new RegisterResult(TokenResponse.builder()
                .accessToken(access)
                .refreshToken(refreshToken.value())
                .build());
    }

    @Transactional
    public LoginResult login(LoginCommand command) {

        AccessToken accessToken = authenticationService
                .authenticate(command.username(), command.rawPassword());
        User user = userRepository.getByUsername(command.username());
        var refreshToken = refreshTokenIssuer.issueRefreshToken(user, command.ip(), command.userAgent());
        return new LoginResult(TokenResponse.builder()
                .accessToken(accessToken.value())
                .refreshToken(refreshToken.value())
                .build()
        );
    }

    @Transactional
    public RefreshResult refresh(RefreshCommand command) {
        var hash = sha256Hex(command.refreshTokenValue());
        OffsetDateTime now = OffsetDateTime.now(clock);
        com.ctorres.pokequiz.domain.model.auth.RefreshToken found = refreshTokenRepository
                .findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(hash, now)
                .orElseThrow(InvalidRefreshTokenException::new);
        found.setRevokedAt(now);
        User user = userRepository.getById(found.getUserId());
        if (!user.active()) throw new InactiveUserException();
        var accessToken = tokenIssuer.issueAccessToken(user.id(), user.username(), user.roles().stream()
                .map(role -> "ROLE_" + role.name())
                .collect(Collectors.toList()));
        RefreshToken refreshToken = refreshTokenIssuer
                .issueRefreshToken(user, command.ip(), command.userAgent());
        found.setReplacedByHash(refreshToken.hash());
        refreshTokenRepository.save(found);
        return new RefreshResult(TokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.value())
                .build());
    }

    @Transactional
    public LogoutResult logout(LogoutCommand command) {
        var hash = sha256Hex(command.refreshTokenValue());
        boolean revoked = refreshTokenRepository.revokeByHash(hash) > 0;
        return new LogoutResult(revoked);
    }

    private String sha256Hex(String s) {
        try {
            var md = MessageDigest.getInstance("SHA-256");
            return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}