package com.matc84demo.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.matc84demo.entities.User;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create().withIssuer("matc84")
					.withSubject(user.getEmail())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		}catch(JWTCreationException e) {
			throw new RuntimeException("Erro ao gerar token",e);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm).withIssuer("matc84")
					.build()
					.verify(token)
					.getSubject();
		}catch(JWTVerificationException e) {
			return "";
		}
	}
	
	private Instant genExpirationDate() {
		return LocalDateTime.now().plusHours(5).toInstant(ZoneOffset.of("-03:00"));
	}
	
	public static String recoverToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null)
			return null;
		return	authHeader.replace("Bearer ", ""); //retorna apenas o token sem o nome do header
	}
}
