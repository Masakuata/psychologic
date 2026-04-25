package com.xatal.psychologic.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;

import com.xatal.psychologic.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class TokenUtils {
    @Value("${spring.application.password}")
    private static String TOKEN_PASSWORD;

    @Value("${spring.application.tokenLifetime}")
    private static Long TOKEN_LIFETIME_MILLS;

    private static SecretKey key = Keys.hmacShaKeyFor(TOKEN_PASSWORD.getBytes());

    public static String createToken(Usuario usuario) {
        Date expirationDate = new Date(System.currentTimeMillis() + TOKEN_LIFETIME_MILLS);
        Map<String, Object> claims = new HashMap<>();

        claims.put("email", usuario.getEmail());

        return Jwts.builder()
                .subject(usuario.getEmail())
                .expiration(expirationDate)
                .claims(claims)
                .signWith(key)
                .compact();
    }

    public static Claims getTokenClaims(String token) {
        return Jwts.parser()
        .verifyWith(key)
        .build()
        .parseSignedClaims(token)
        .getPayload();
    }

    public static boolean isExpired(String token) {
        return isExpired(getTokenClaims(token));
    }

    public static boolean isExpired(Claims claims) {
        if (claims == null) {
            return true;
        }
        return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }
}
