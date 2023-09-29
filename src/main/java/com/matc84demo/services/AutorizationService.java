package com.matc84demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.matc84demo.entities.User;
import com.matc84demo.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AutorizationService implements UserDetailsService {
	
	@Autowired
	UserRepository repository;
	
	@Autowired
	TokenService tokenService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return repository.findByEmail(username);
	}
	
	
	/*
	 * Método para retornarusuário do token passado na requisição
	 */
	public User getUserFromToken(HttpServletRequest request) {
		String token = TokenService.recoverToken(request);
		if(token == null)
			throw new SecurityException("A user not logged in acessed a protected route");
		String email = tokenService.validateToken(token);
		return (User) repository.findByEmail(email);
	}

}
