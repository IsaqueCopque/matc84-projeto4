package com.matc84demo.view;

import com.matc84demo.entities.Game;
import com.matc84demo.services.GameService;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@FacesConverter(value = "gameConverter", managed = true)
public class GameConverter implements Converter<Game> {

    @Inject
    private GameService gameService;

    @Override
    public Game getAsObject(FacesContext context, UIComponent component, String value) {
        if (value != null && value.trim().length() > 0) {
            try {
                return gameService.findById(Long.parseLong(value));
            }
            catch (NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid country."));
            }
        }
        else {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Game value) {
        if (value != null) {
            return String.valueOf(value.getId());
        }
        else {
            return null;
        }
    }
}
