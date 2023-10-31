package com.matc84demo.view;

import jakarta.faces.context.FacesContext;
import jakarta.servlet.http.HttpServletRequest;

public class FatherBean {
	protected HttpServletRequest getRequest() {
		return ((HttpServletRequest) (FacesContext.getCurrentInstance().getExternalContext().getRequest()));
	}
	protected void guardaAtributoEmSessao(String atrNome, Object atrObj) {
		getRequest().getSession().setAttribute(atrNome, atrObj);
	}
	protected Object getAtributoEmSessao(String atrNome) {
		return getRequest().getSession().getAttribute(atrNome);
	}
}
