package com.matc84demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matc84demo.entities.AuthenticationDTO;
import com.matc84demo.entities.Game;
import com.matc84demo.entities.User;
import com.matc84demo.services.AutorizationService;
import com.matc84demo.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	AutorizationService authService;
	
	/*
	 * Modifica senha ou nome do usuário do token
	 */
	@PutMapping("/update_profile")
	public ResponseEntity<User> updateUser(@RequestBody AuthenticationDTO auth, HttpServletRequest request){
		
		String email = authService.getUserEmailFromToken(request);
		
		User user = (User) service.findByEmail(email);
		
		if(auth.name() != null)
			user.setName(auth.name());
		
		if(auth.password() != null) 
			user.setPassword(new BCryptPasswordEncoder().encode(auth.password()));
		
		user = service.insert(user);
		
		return ResponseEntity.ok().body(user);
	}
	
}
