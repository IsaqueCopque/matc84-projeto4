package com.matc84demo.view;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.matc84demo.entities.Game;
import com.matc84demo.entities.GameCollection;
import com.matc84demo.entities.User;
import com.matc84demo.services.GameCollectionService;
import com.matc84demo.services.GameService;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;

@Component
@SessionScoped
public class HomeMBean extends FatherBean {
	
	@Autowired
	GameCollectionService service;
	
	@Autowired
	GameService gameService;

	private Game newGame;
	
	private boolean erroValidacao = false;
	
	private List<String> erros;
	
	private String successMsg;
	
	private List<GameCollection> colecoes = null;
	
	private GameCollection selecionado;

	public GameCollection getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(GameCollection selecionado) {
		this.selecionado = selecionado;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		setColecoes((List<GameCollection>) getAtributoEmSessao("colecoes"));
		selecionado = new GameCollection();
	}
	
	public String selecionarColecao(GameCollection selecionado) {
		this.selecionado = selecionado;
		return null;
	}
	
	public String visualizarColecao(GameCollection selecionado) {
		this.selecionado = selecionado;
		//selecionado.getGames().get(0);
		return collectionPage + "?faces-redirect=true";
	}
	
	public String voltarHome() {
		selecionado = new GameCollection();
		return homePage + "?faces-redirect=true";
	}
	
	public String criarCollec() {
		erros = new ArrayList<String>();
		if(selecionado.getName() == null || selecionado.getName().length() == 0) {
			erroValidacao = true;
			erros.add("Nome da coleção é obrigatório.");
			return null;
		}
		for(GameCollection collec : colecoes) {
			if(collec.getName() == selecionado.getName()) {
				erroValidacao = true;
				erros.add("Você já possui uma coleção com este nome.");
				return null;
			}
		}
		
		User user = (User)getAtributoEmSessao("user");
		selecionado.setCreator(user);
		service.save(selecionado);
		//successMsg = "Coleção criada";
		colecoes = service.findMyCollections(user);
		
		//return null;
		return homePage + "?faces-redirect=true";
	}
	
	public String editCollec() {
		erros = new ArrayList<String>();
		if(selecionado.getName() == null || selecionado.getName().length() == 0) {
			erroValidacao = true;
			erros.add("Nome da coleção é obrigatório.");
			return null;
		}
		for(GameCollection collec : colecoes) {
			if(collec.getName() == selecionado.getName() && collec.getId() != selecionado.getId()) {
				erroValidacao = true;
				erros.add("Você já possui uma coleção com este nome.");
				return null;
			}
		}
		
		User user = (User)getAtributoEmSessao("user");
		selecionado.setCreator(user);
		service.save(selecionado);
		successMsg = "Coleção Editada";
		colecoes = service.findMyCollections(user);
		
		return null;
	}
	
	public String deletarCollec(GameCollection selecionado) {
		service.deleteGameCollection(selecionado.getId());
		User user = (User)getAtributoEmSessao("user");
		colecoes = service.findMyCollections(user);
		return voltarHome();
	}
	
	public List<Game> completeGames(String query){
		query = query.toLowerCase();
		List<Game> games = gameService.findByName(query);
		for(Game game  : games) {
			if(selecionado.getGames().contains(game)) 
				games.remove(game);
		}
		return games;
	}
	
	public String addGame() {
		if(newGame != null) {
			selecionado.getGames().add(newGame);
			service.save(selecionado);
		}
		return collectionPage + "?faces-redirect=true";
	}
	
	public String removerGame(Game game) {
		if(game != null) {
			selecionado.getGames().remove(game);
			service.save(selecionado);
		}
		return collectionPage + "?faces-redirect=true";
	}
	
	public List<GameCollection> getColecoes() {
		return colecoes;
	}

	public void setColecoes(List<GameCollection> colecoes) {
		this.colecoes = colecoes;
	}

	public boolean getErroValidacao() {
		return erroValidacao;
	}

	public void setErroValidacao(boolean erroValidacao) {
		this.erroValidacao = erroValidacao;
	}

	public List<String> getErros() {
		return erros;
	}

	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
	public boolean getHasSuccessMessage() {
		return successMsg != null && successMsg.length() > 0;
	}

	public Game getNewGame() {
		return newGame;
	}

	public void setNewGame(Game newGame) {
		this.newGame = newGame;
	}
	
}
