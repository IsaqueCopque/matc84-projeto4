package com.matc84demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	SecurityFilter securityFilter;
	
	//Filtros que serão aplicados às requisições
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception{
		MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector); // Authorization rules can be misconfigured when using multiple servlets
		return http
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(mvcMatcherBuilder.pattern("/**faces")).permitAll()
				.requestMatchers(mvcMatcherBuilder.pattern("/images/**")).permitAll()
				.requestMatchers(mvcMatcherBuilder.pattern("/jakarta.faces.resource/**")).permitAll()
				.requestMatchers(mvcMatcherBuilder.pattern(HttpMethod.POST, "/auth/login")).permitAll()
				.anyRequest().authenticated()
				) 
		.formLogin(form -> form
				.loginPage("/login.faces")
				.loginProcessingUrl("/auth/login")
				.failureUrl("/login.faces?error=true")
				.defaultSuccessUrl("/home.faces")
				.permitAll()
			)
		//.authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll())
		.csrf(csrf -> csrf.disable()) //AbstractHttpConfigurer::disable
		.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class) //Recupera dados do token
		.build();
	}
	/*.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
				.requestMatchers(HttpMethod.POST, "/auth/register").permitAll()*/
	//.anyRequest().authenticated() Requisição para qualquer rota requer apenas que esteja autenticado
	//.requestMatchers(HttpMethod.GET, "/rota admin").hasRole("ADMIN")) Exemplo Rota privada admin
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
