package com.hexaware.HotPot.security;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  @Value("${app.jwt.secret:change-this-32+chars-secret-key-change-me}")
  private String secret;

  @Value("${app.jwt.expiration-ms:86400000}") 
  private long jwtExpiryMs;

  private byte[] key() {
    return secret.getBytes();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> resolver) {
    Claims claims = Jwts.parserBuilder()
        .setSigningKey(Keys.hmacShaKeyFor(key()))
        .build()
        .parseClaimsJws(token)
        .getBody();
    return resolver.apply(claims);
  }

  public String generateToken(String username, Map<String, Object> extraClaims) {
    Date now = new Date();
    Date exp = new Date(now.getTime() + jwtExpiryMs);
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(username)
        .setIssuedAt(now)
        .setExpiration(exp)
        .signWith(Keys.hmacShaKeyFor(key()))
        .compact();
  }

  public boolean isTokenValid(String token, String username) {
    String subject = extractUsername(token);
    return subject != null && subject.equals(username) && !isExpired(token);
  }

  private boolean isExpired(String token) {
    Date exp = extractClaim(token, Claims::getExpiration);
    return exp.before(new Date());
  }
}
