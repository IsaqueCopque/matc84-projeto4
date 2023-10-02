package com.matc84demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.matc84demo.entities.AuthenticationDTO;
import com.matc84demo.entities.LoginResponseDTO;
import com.matc84demo.entities.User;
import com.matc84demo.repositories.UserRepository;
import com.matc84demo.services.TokenService;

import ch.qos.logback.core.model.Model;

@Controller
@RequestMapping("auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO data) {
		System.out.println("---> Bateu login");
		UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
		Authentication auth = this.authenticationManager.authenticate(userNamePassword);
		
		String token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));
	}
	
	/*
	 * Formulário de cadastro do usuário
	 */
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		System.out.println("--> Bateu regsiter get");
		return "register";
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthenticationDTO data) {
		System.out.println("---> Bateu Register");
		if(this.repository.findByEmail(data.login()) != null) {
			return ResponseEntity.badRequest().build();
		}
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.login(), encryptedPassword, data.name());
		
		this.repository.save(newUser);
		return ResponseEntity.ok().build();
	}
	
}