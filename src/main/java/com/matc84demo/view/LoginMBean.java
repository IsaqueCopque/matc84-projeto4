package com.matc84demo.view;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.matc84demo.entities.GameCollection;
import com.matc84demo.entities.User;
import com.matc84demo.services.AutorizationService;
import com.matc84demo.services.GameCollectionService;
import com.matc84demo.services.TokenService;
import com.matc84demo.services.UserService;

import jakarta.enterprise.context.SessionScoped;

@Component
@SessionScoped
public class LoginMBean extends FatherBean  {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	GameCollectionService gameCollectionService;
	
	@Autowired
	AutorizationService authService;
	
	private String senha;
	
	private String name;
	
	private String email;
	
	private boolean erroValidacao = false;
	
	private String errorMsg;
	
	public String logar() {
		UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(email, senha);
		
		try {
			Authentication auth = this.authenticationManager.authenticate(userNamePassword);
			String token = tokenService.generateToken((User) auth.getPrincipal());
			if(token != null) {
				User user = authService.getUserFromToken(token);
				List<GameCollection> colecoes = gameCollectionService.findMyCollections(user);
				
				guardaAtributoEmSessao("user",user);
				guardaAtributoEmSessao("colecoes",colecoes);
				
				return homePage + "?faces-redirect=true";
			}
			return loginPage + "?error=true";
		}catch(AuthenticationException e) {
			return loginPage + "?error=true";
		}
	}
	
	public String registrar() {
		if(userService.findByEmail(email) != null) {
			erroValidacao = true;
			errorMsg = "E-mail já registrado.";
			return registerPage + "?faces-redirect=true";
		}
		if(senha == null  || email == null || name == null) {
			erroValidacao = true;
			errorMsg = "E-mail ou senha inválidos.";
			return registerPage + "?faces-redirect=true";
		}
		
		String senhaEncrypted = new BCryptPasswordEncoder().encode(senha);
		User novoUser = new User(email,senhaEncrypted,name);
		novoUser = userService.insert(novoUser);
		return logar();
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isErroValidacao() {
		return erroValidacao;
	}

	public void setErroValidacao(boolean erroValidacao) {
		this.erroValidacao = erroValidacao;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
}
