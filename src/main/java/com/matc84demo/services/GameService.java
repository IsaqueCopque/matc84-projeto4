package com.matc84demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matc84demo.entities.Game;
import com.matc84demo.repositories.GameRepository;

@Service
public class GameService {
	@Autowired
	GameRepository repository;
	
	public List<Game> findAll(){
		return repository.findAll();
	}
	
	public Game findByExactName(String name){
		return repository.findByName(name);
	}
	
	public List<Game> findByName(String name){
		return repository.findByNameContainsIgnoreCase(name);
	}
	
	public Game findById(Long id) {
		Optional<Game> obj = repository.findById(id);
		try {
			return obj.get();
		}catch(NoSuchElementException e) { return null; }
	}
	
	public Game saveGame(Game obj) {
		return repository.save(obj);
	}
	
	public boolean deleteGame(Long id) {
		if(id == null) return false;
		Game dbGame = findById(id);
		if(dbGame == null) return false;
		repository.deleteById(dbGame.getId());
		return true;
	}
	
}
