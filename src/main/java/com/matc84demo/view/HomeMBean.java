package com.matc84demo.view;

import java.util.List;

import org.springframework.stereotype.Component;

import com.matc84demo.entities.GameCollection;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;

@Component
@SessionScoped
public class HomeMBean extends FatherBean {
	
	
	private List<GameCollection> colecoes = null;

	@PostConstruct
	private void init() {
		setColecoes((List<GameCollection>) getAtributoEmSessao("colecoes"));
	}
	
	public List<GameCollection> getColecoes() {
		return colecoes;
	}

	public void setColecoes(List<GameCollection> colecoes) {
		this.colecoes = colecoes;
	}
	
}
