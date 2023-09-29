package com.matc84demo.services;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.matc84demo.entities.User;
import com.matc84demo.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	public User insert(User obj) {
		return repository.save(obj);
	}
	
	public void delete(Long id) {
		//to-do: delete game lists
		repository.deleteById(id);
	}
	
	public User update(Long id, User obj) {
		//to-do: hash password
		User objDb = repository.getReferenceById(id);
		objDb.setEmail(obj.getEmail());
		objDb.setName(obj.getName());
		return repository.save(objDb);
	}
	
	public UserDetails findByEmail(String email) {
		return repository.findByEmail(email);
	}
	
	private String hashPassword(String password) {
		SecureRandom random = new SecureRandom();
		byte[] salt = new byte[16];
		random.nextBytes(salt);
		return null;
	}
}
