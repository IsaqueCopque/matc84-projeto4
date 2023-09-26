package com.matc84demo.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.matc84demo.repositories.UserRepository;
import com.matc84demo.services.TokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	TokenService tokenService;
	
	@Autowired
	UserRepository repository;
	
	/*
	 * Recupera usuário do token antes da validação
	 */
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = this.recoverToken(request);
		if(token != null) {
			String login = tokenService.validateToken(token);
			UserDetails user = repository.findByEmail(login);
			UsernamePasswordAuthenticationToken authentication = 
					new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
			//Salva usuário na sessão para o próximo filtro
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		filterChain.doFilter(request, response);
	}
	 
	private String recoverToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null)
			return null;
		return	authHeader.replace("Bearer ", ""); //retorna apenas o token sem o nome do header
	}
	
}
