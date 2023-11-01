package com.matc84demo.view;

import java.util.List;

import org.springframework.stereotype.Component;

import com.matc84demo.entities.GameCollection;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;

@Component
@SessionScoped
public class HomeMBean extends FatherBean {
	
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
	
	public String visualizarColecao(GameCollection selecionado) {
		this.selecionado = selecionado;
		selecionado.getGames().get(0);
		return collectionPage + "?faces-redirect=true";
	}
	
	public String voltarHome() {
		selecionado = new GameCollection();
		return homePage + "?faces-redirect=true";
	}
	
	public String criarCollec() {
		System.out.println("HIT CRIAR COLLEC");
		return null;
		
	}
	
	public void addGame() {
		
	}
	
	public List<GameCollection> getColecoes() {
		return colecoes;
	}

	public void setColecoes(List<GameCollection> colecoes) {
		this.colecoes = colecoes;
	}
	
}
