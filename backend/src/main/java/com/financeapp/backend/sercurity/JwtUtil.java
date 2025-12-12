package com.financeapp.backend.sercurity;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.crypto.SecretKey;
import java.util.Date;


@Component
public class JwtUtil {


private final SecretKey key;
private final long expirationMs;


 public JwtUtil(
  @Value("${app.jwt.secret}") String secret,
  @Value("${app.jwt.expiration-ms}") long expirationMs
  ) {
  this.key = Keys.hmacShaKeyFor(secret.getBytes());
  this.expirationMs = expirationMs;
  }


  public String generateToken(String subject) {
  return Jwts.builder()
  .setSubject(subject)
  .setIssuedAt(new Date())
  .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
  .signWith(key, SignatureAlgorithm.HS256)
  .compact();
  }


  public String getSubject(String token) {
  return Jwts.parserBuilder()
  .setSigningKey(key)
  .build()
  .parseClaimsJws(token)
  .getBody()
  .getSubject();
  }
}