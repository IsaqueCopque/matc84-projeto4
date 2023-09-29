package com.matc84demo.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matc84demo.entities.Game;
import com.matc84demo.entities.GameCollection;
import com.matc84demo.entities.User;
import com.matc84demo.repositories.GameCollectionRepository;

@Service
public class GameCollectionService {
	@Autowired
	GameCollectionRepository repository;
	
	
	public GameCollection findById(Long collecId) {
		Optional<GameCollection> obj = repository.findById(collecId);
		try {
			return obj.get();
		}catch(NoSuchElementException e) { return null; }
	}
	
	public GameCollection findByIds(Long userId, Long collecId) {
		return repository.findByIds(userId, collecId);
	}
	
	public GameCollection save(GameCollection obj) {
		return repository.save(obj);
	}
	
	public List<GameCollection> findMyCollections(User user){
		return repository.findByCreator(user);
	}
	
	public boolean deleteGameCollection(Long id) {
		if(id == null) return false;
		GameCollection dbCollection = findById(id);
		if(dbCollection == null) return false;
		repository.deleteById(dbCollection.getId());
		return true;
	}
	
}
