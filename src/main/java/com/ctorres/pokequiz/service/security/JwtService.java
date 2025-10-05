package com.ctorres.pokequiz.service.security;

import com.ctorres.pokequiz.config.JwtProperties;
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
import java.util.Date;
import java.util.Map;

@Component
public class JwtService {

  private final SecretKey key;
  private final String issuer;
  private final Duration accessTtl;
  private final Clock clock;

  public JwtService(JwtProperties props, Clock clock) {
    // OJO: la clave debe tener >= 32 bytes para HS256
    this.key = Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8));
    this.issuer = props.getIssuer();
    this.accessTtl = Duration.ofMinutes(props.getAccessMins());
    this.clock = clock;
  }

  public String generateAccessToken(String subject, Map<String, Object> claims) {
    Instant now = Instant.now(clock);
    return Jwts.builder()
        .subject(subject)
        .issuer(issuer)
        .issuedAt(Date.from(now))
        .expiration(Date.from(now.plus(accessTtl)))
        .claims(claims)                  // en 0.12.x este setter acepta Map
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
