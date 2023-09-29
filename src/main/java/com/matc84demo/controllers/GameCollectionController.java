package com.matc84demo.controllers;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matc84demo.entities.Game;
import com.matc84demo.entities.GameCollection;
import com.matc84demo.entities.User;
import com.matc84demo.services.AutorizationService;
import com.matc84demo.services.GameCollectionService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("collection")
public class GameCollectionController {
	
	@Autowired
	GameCollectionService service;
	
	@Autowired
	AutorizationService authService;
	
	/*
	 * Cria uma coleção
	 */
	@PostMapping("/")
	public ResponseEntity<GameCollection> createGameCollection(@RequestBody GameCollection obj, HttpServletRequest request){
		User user = authService.getUserFromToken(request);
		obj.setCreator(user);
		obj = service.save(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	/*
	 * Insere game em coleção passada por id
	 */
	@PostMapping("/{collecId}")
	public ResponseEntity<GameCollection> insertGame(@PathVariable Long collecId, 
			@RequestBody Game obj, HttpServletRequest request){
		User user = authService.getUserFromToken(request);
		GameCollection collec = service.findByIds(user.getId(), collecId);
		
		if(collec == null)
			return ResponseEntity.badRequest().header("Reason", "User doesn't have a collection with id "+collecId+".")
					.build();
		
		try {
			collec.getGames().add(obj);
			collec = service.save(collec);
			return ResponseEntity.ok().body(collec);
		}catch(NoSuchElementException e) {
			return ResponseEntity.badRequest()
					.header("Reason", "There is not a game collection for id " + collecId + ".")
					.build(); 
		}
	}
	
	/*
	 * Retorna as coleções do usuário logado
	 */
	@GetMapping("/my")
	public ResponseEntity<List<GameCollection>> myCollections(HttpServletRequest request){
		User user = authService.getUserFromToken(request);
		return ResponseEntity.ok().body(service.findMyCollections(user));
	}
	
	/*
	 * Retorna coleção pelo parâmetro passado em URL
	 * {id_do_criador}/{id_coleção}
	 */
	@GetMapping("/{user}/{collec}")
	public ResponseEntity<GameCollection> findById(@PathVariable Long user, @PathVariable Long collec){
		return ResponseEntity.ok().body(service.findByIds(user,collec));
	}
	
	/*
	 * Modifica valores da coleção
	 */
	@PutMapping("/{id}")
	public ResponseEntity<GameCollection> updateGame(@PathVariable Long id,
			@RequestBody GameCollection gameCollection, HttpServletRequest request){
		
		User user = authService.getUserFromToken(request);
		GameCollection obj = service.findByIds(user.getId(), id);
		
		if(obj == null)
			return ResponseEntity.badRequest().header("Reason", "User doesn't have a collection with id "+id+".")
					.build();
		
		if(gameCollection.getName() != null)
			obj.setName(gameCollection.getName());
		
		obj.setDescription(gameCollection.getDescription());
		
		return ResponseEntity.ok().body(service.save(obj));
	}
	
	/*
	 * Remove game de coleção
	 */
	@DeleteMapping("/remove_game/{id}")
	public ResponseEntity<List<Game>> deleteGame(@PathVariable Long id, @RequestBody Game game, HttpServletRequest request){
		
		User user = authService.getUserFromToken(request);
		GameCollection obj = service.findByIds(user.getId(), id);
		
		if(obj == null)
			return ResponseEntity.badRequest().header("Reason", "User doesn't have a collection with id "+id+".")
					.build();
		
		obj.getGames().remove(game);
		return ResponseEntity.ok().body(obj.getGames());
	}
	
	/*
	 * Deleta coleção
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGameCollection(@PathVariable Long id, HttpServletRequest request){
		User user = authService.getUserFromToken(request);
		
		GameCollection obj = service.findByIds(user.getId(), id);
		
		if(obj == null)
			return ResponseEntity.badRequest()
					.header("Reason", "User doesn't have a collection with id "+id+".")
					.build();
		
		if(service.deleteGameCollection(id))
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
	
}
