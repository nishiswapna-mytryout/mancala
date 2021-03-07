package org.example.domain.game.core.model.exceptions;

public class GameIllegalStateException extends RuntimeException {
    public GameIllegalStateException(final String message) {
        super(message);
    }
}
