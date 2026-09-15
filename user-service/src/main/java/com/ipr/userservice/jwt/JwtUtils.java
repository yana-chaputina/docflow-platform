package com.ipr.userservice.jwt;

import com.ipr.userservice.security.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.secret.key}")
    private String secretKey;

    public SecretKey decodeSecretKey(String jwtSecret) {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", userDetails.getUserId())
                .claim("role", userDetails.getUserRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(decodeSecretKey(secretKey))
                .compact();
    }

    public String getJwtFromHeader (HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        logger.debug("Authorization Header: {}", header);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length());
        }
        return null;
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser().verifyWith(decodeSecretKey(secretKey)).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public Long getUserIdFromJwtToken(String token) {
        return Jwts.parser().verifyWith(decodeSecretKey(secretKey)).build()
                .parseSignedClaims(token).getPayload().get("userId",Long.class);
    }

    public Boolean verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(decodeSecretKey(secretKey)).build()
                    .parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT Token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT Token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}