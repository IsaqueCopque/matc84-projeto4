package com.matc84demo.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.matc84demo.entities.GameCollection;
import com.matc84demo.entities.User;
import com.matc84demo.services.AutorizationService;
import com.matc84demo.services.GameCollectionService;
import com.matc84demo.services.TokenService;

import jakarta.enterprise.context.SessionScoped;

@Component
@SessionScoped
public class LoginMBean extends FatherBean  {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	GameCollectionService gameCollectionService;
	
	@Autowired
	AutorizationService authService;
	
	private String senha;
	
	private String email;
	
	public String logar() {
		System.out.println("---> Bateu login do Bean");
		UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(email, senha);
		
		try {
			Authentication auth = this.authenticationManager.authenticate(userNamePassword);
			String token = tokenService.generateToken((User) auth.getPrincipal());
			if(token != null) {
				User user = authService.getUserFromToken(token);
				List<GameCollection> colecoes = gameCollectionService.findMyCollections(user);
				
				guardaAtributoEmSessao("token",token);
				guardaAtributoEmSessao("colecoes",colecoes);
				
				return homePage + "?faces-redirect=true";
			}
			return loginPage + "?error=true";
		}catch(AuthenticationException e) {
			return loginPage + "?error=true";
		}
	}
	
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
