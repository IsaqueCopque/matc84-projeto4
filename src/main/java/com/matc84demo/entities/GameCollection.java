package com.matc84demo.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="game_collection")
public class GameCollection  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column
	private String description;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User creator;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.REMOVE})
	@JoinTable(name="collection_game", 
	joinColumns = @JoinColumn(name="game_collection_id"),
	inverseJoinColumns = @JoinColumn(name="game_id"))
	private List<Game> games = new ArrayList<Game>();
	
	public GameCollection() {}
	
	public GameCollection(Long id, String name, String description, User creator) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.creator = creator;
	}

	public GameCollection(Long id, String name, String description, User creator, List<Game> games) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.creator = creator;
		this.games = games;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public List<Game> getGames() {
		return games;
	}

	public void setGames(List<Game> games) {
		this.games = games;
	}
	
}
