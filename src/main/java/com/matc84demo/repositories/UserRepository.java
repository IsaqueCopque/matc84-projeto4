package com.matc84demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.matc84demo.entities.User;

public interface UserRepository extends JpaRepository<User,Long>{
	UserDetails findByEmail(String email);
}
