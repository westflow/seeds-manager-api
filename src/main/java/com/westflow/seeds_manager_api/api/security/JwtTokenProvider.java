package com.westflow.seeds_manager_api.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration}")
    private long jwtExpirationInMs;

    public String generateToken(UserDetails userDetails) {
        return generateToken(userDetails, null, null);
    }

    public String generateToken(UserDetails userDetails, Long tenantId, String tenantCode) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtExpirationInMs);

        io.jsonwebtoken.JwtBuilder builder = Jwts.builder()
                .setSubject(userDetails.getUsername()) // geralmente o e-mail
                .setIssuedAt(now)
                .setExpiration(expiry);

        if (tenantId != null) {
            builder.claim("tenantId", tenantId);
        }

        if (tenantCode != null && !tenantCode.isBlank()) {
            builder.claim("tenantCode", tenantCode);
        }

        return builder
                .signWith(getSigningKey())
                .compact();
    }

    public String generateResetToken(String email) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + 900_000L);

        return Jwts.builder()
                .setSubject(email)
                .claim("purpose", "reset_password")
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(getSigningKey())
                .compact();
    }

    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Long getTenantIdFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object tenant = claims.get("tenantId");
        if (tenant == null) return null;
        if (tenant instanceof Number) return ((Number) tenant).longValue();
        try {
            return Long.valueOf(tenant.toString());
        } catch (Exception ex) {
            return null;
        }
    }

    public String getResetEmailFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String purpose = claims.get("purpose", String.class);
        if (!"reset_password".equals(purpose)) {
            throw new IllegalArgumentException("Invalid token purpose");
        }

        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();

        return expiration.before(new Date());
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }
}
