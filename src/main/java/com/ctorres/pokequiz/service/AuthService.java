package com.ctorres.pokequiz.service;

import com.ctorres.pokequiz.config.JwtProperties;
import com.ctorres.pokequiz.entity.RefreshToken;
import com.ctorres.pokequiz.entity.User;
import com.ctorres.pokequiz.repository.RefreshTokenRepository;
import com.ctorres.pokequiz.repository.RoleRepository;
import com.ctorres.pokequiz.repository.UserRepository;
import com.ctorres.pokequiz.service.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.UUID;

@Service
public class AuthService {
  private final UserRepository users;
  private final RoleRepository roles;
  private final RefreshTokenRepository refreshTokens;
  private final PasswordEncoder encoder;
  private final JwtService jwt;
  private final Clock clock;
  private JwtProperties jwtProperties;

  public AuthService(UserRepository users,
                     RoleRepository roles,
                     RefreshTokenRepository refreshTokens,
                     PasswordEncoder encoder,
                     JwtService jwt,
                     Clock clock,
                     JwtProperties jwtProperties) {
      this.users = users;
      this.roles = roles;
      this.refreshTokens = refreshTokens;
      this.encoder = encoder;
      this.jwt = jwt;
      this.clock = clock;
      this.jwtProperties = jwtProperties;
  }

  private static String sha256Hex(String s){
    try{
      var md = MessageDigest.getInstance("SHA-256");
      return HexFormat.of().formatHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
    } catch (Exception e){ throw new IllegalStateException(e); }
  }

  private String newRefreshTokenValue(){
      return UUID.randomUUID().toString()+"."+UUID.randomUUID();
  }

  private String issueAccess(User u){
    var claims = new HashMap<String, Object>();
    claims.put("uid", u.getId());
    claims.put("roles", u.getRoles().stream()
        .map(r -> "ROLE_" + r.getName())
        .toList());
  
    return jwt.generateAccessToken(u.getUsername(), claims);
  }

  private String[] issueRefresh(User u, String ip, String userAgent){
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
    return new String[]{ value, rt.getTokenHash() };
  }

  private boolean isStrongPassword(String password) {
      final int MAX_LENGTH = jwtProperties.getUserPasswordMaxLength();
      if (password.length() < MAX_LENGTH) return false;
      // if (password.codePoints().anyMatch(Character::isEmoji)) return false;
      if (password.codePoints().noneMatch(Character::isUpperCase)) return false;
      if (password.codePoints().noneMatch(Character::isLowerCase)) return false;
      if (password.codePoints().noneMatch(Character::isDigit)) return false;
      return password.codePoints().anyMatch(c -> String.valueOf((char) c)
              .matches("[^a-zA-Z0-9\\s]"));
  }

  @Transactional
  public String[] register(String username, String password, String ip, String userAgent){
    if (users.existsByUsername(username)) throw new IllegalArgumentException("username already exists");
    if (!isStrongPassword(password)) throw new IllegalArgumentException("password is weak");
    var user = new User(username, encoder.encode(password), true);
    var roleUser = roles.findByName("USER")
        .orElseThrow(() -> new IllegalStateException("Role USER not seeded"));
    user.getRoles().add(roleUser);
    // users.save(user);
    var access = issueAccess(user);
    var pair = issueRefresh(user, ip, userAgent);
    return new String[]{access, pair[0]};
  }

  @Transactional
  public String[] login(String username, String rawPassword, String ip, String userAgent){
    var user = users.findByUsernameAndActiveTrue(username)
        .orElseThrow(() -> new IllegalArgumentException("bad credentials"));
    if (!encoder.matches(rawPassword, user.getPasswordHash()))
      throw new IllegalArgumentException("bad credentials");
    var access = issueAccess(user);
    var pair = issueRefresh(user, ip, userAgent);
    return new String[]{access, pair[0]};
  }

  @Transactional
  public String[] refresh(String refreshTokenValue, String ip, String userAgent){
    var hash = sha256Hex(refreshTokenValue);
    var now = OffsetDateTime.now(clock);
    var stored = refreshTokens.findByTokenHashAndRevokedAtIsNullAndExpiresAtAfter(hash, now)
        .orElseThrow(() -> new IllegalArgumentException("invalid refresh token"));

    stored.setRevokedAt(now);                    // rotación
    var user = stored.getUser();
    var access = issueAccess(user);
    var pair = issueRefresh(user, ip, userAgent);
    stored.setReplacedByHash(pair[1]);           // vínculo con el nuevo
    return new String[]{access, pair[0]};
  }

  @Transactional
  public void logout(String refreshTokenValue){
    var hash = sha256Hex(refreshTokenValue);
    refreshTokens.revokeByHash(hash);
  }
}