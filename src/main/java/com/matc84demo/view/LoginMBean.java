package com.matc84demo.view;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.matc84demo.entities.User;
import com.matc84demo.services.TokenService;

import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ActionEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.servlet.http.HttpServletRequest;

@Component
@ViewScoped
public class LoginMBean   {
	
	static String loginPage = "/login.xhtml";
	
	static String homePage = "/home.xhtml";
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private TokenService tokenService;
	
	private String senha;
	
	private String email;
	
	public String logar() {
		System.out.println("---> Bateu login do Bean");
		UsernamePasswordAuthenticationToken userNamePassword = new UsernamePasswordAuthenticationToken(email, senha);
		
		try {
			Authentication auth = this.authenticationManager.authenticate(userNamePassword);
			String token = tokenService.generateToken((User) auth.getPrincipal());
			if(token != null) {
				((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest()))
						.getSession().setAttribute("token", token);
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
