package com.matc84demo.controllers;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matc84demo.entities.Game;
import com.matc84demo.services.GameService;

@RestController
@RequestMapping("game")
public class GameController {
	
	@Autowired
	GameService service;
	
	/*
	 * Retorna game pelo id passado na url
	 */
	@GetMapping("/id/{id}")
	public ResponseEntity<Game> findById(@PathVariable Long id){
		return ResponseEntity.ok().body(service.findById(id));
	}
	
	/*
	 * Retorna game pelo nome passado na url
	 * Parâmetro: exact -> true: exatamente pelo nome, false: contendo nome
	 */
	@GetMapping("/name/{name}")
	public ResponseEntity<List<Game>> findByName(@PathVariable String name, @RequestParam boolean exact){
		if(exact) {
			List<Game> game = new ArrayList<Game>();
			game.add(service.findByExactName(name));
			return ResponseEntity.ok().body(game);
		}
		return ResponseEntity.ok().body(service.findByName(name));
	}
	
	
	@PostMapping("/")
	public ResponseEntity<Game> createGame(@RequestBody Game obj){
		obj = service.saveGame(obj);
		//Para devolver no cabeçalho 'Location' de resposta 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
				path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Game> updateGame(@PathVariable Long id){
		Game obj = service.findById(id);
		if(obj == null)
			return ResponseEntity.badRequest().build();
		obj = service.saveGame(obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteGame(@PathVariable Long id){
		if(service.deleteGame(id))
			return ResponseEntity.ok().build();
		return ResponseEntity.badRequest().build();
	}
	
}
