package com.xatal.psychologic.util;

import com.xatal.psychologic.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtils {
	@Value("${spring.application.password}")
	private String TOKEN_PASSWORD;

	@Value("${spring.application.tokenLifetimeHours}")
	private int TOKEN_LIFETIME_HOURS;

	private SecretKey key;

	@PostConstruct
	private void init() {
		key = Keys.hmacShaKeyFor(TOKEN_PASSWORD.getBytes());
	}

	public String createToken(Usuario usuario) {
		LocalDateTime expiration = LocalDateTime.now().plusHours(TOKEN_LIFETIME_HOURS);
		Date date = Date.from(expiration.atZone(ZoneId.systemDefault()).toInstant());

		Map<String, Object> claims = new HashMap<>();

		claims.put("email", usuario.getEmail());
		claims.put("rol", usuario.getRol());

		return Jwts.builder()
			.subject(usuario.getEmail())
			.expiration(date)
			.claims(claims)
			.signWith(key)
			.compact();
	}

	public Claims getTokenClaims(String token) {
		return Jwts.parser()
			.verifyWith(key)
			.build()
			.parseSignedClaims(token)
			.getPayload();
	}

	public boolean isExpired(String token) {
		return isExpired(getTokenClaims(token));
	}

	public boolean isExpired(Claims claims) {
		if (claims == null) {
			return true;
		}
		return claims.getExpiration().before(new Date(System.currentTimeMillis()));
	}
}
