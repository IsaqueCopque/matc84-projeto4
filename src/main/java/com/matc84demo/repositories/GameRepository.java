package com.matc84demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matc84demo.entities.Game;

public interface GameRepository extends JpaRepository<Game,Long> {
	
	Game findByName(String name);
	List<Game> findByNameContainsIgnoreCase(String name);
}
