package org.example.domain.game.core.model.exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(final String message) {
        super(message);
    }
}
