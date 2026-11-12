package com.ctorres.pokequiz.infrastructure.security;

import com.ctorres.pokequiz.application.port.out.TokenIssuer;
import com.ctorres.pokequiz.infrastructure.security.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Component
public class JwtTokenIssuer implements TokenIssuer {

  private final SecretKey key;
  private final String issuer;
  private final Duration accessTtl;
  private final Clock clock;

  public JwtTokenIssuer(JwtProperties props, Clock clock) {
    this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    this.issuer = props.getIssuer();
    this.accessTtl = Duration.ofMinutes(props.getAccessMins());
    this.clock = clock;
  }

  @Override
  public String issueAccessToken(
          UUID id,
          String username,
          Collection<String> roles
  ) {
    Instant now = Instant.now(clock);
    return Jwts.builder()
        .subject(username)
        .issuer(issuer)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(accessTtl)))
        .claims(Map.of("uid", id, "roles", roles))
        .signWith(key, Jwts.SIG.HS256)   // MacAlgorithm moderno
        .compact();
  }

  public Jws<Claims> parse(String jwt) {
    return Jwts.parser()
        .verifyWith(key)   // reemplaza al antiguo setSigningKey
        .build()
        .parseSignedClaims(jwt);
  }

  public String getSubject(String jwt) {
    return parse(jwt).getPayload().getSubject();
  }
}
