package org.example.domain.game.core.model.exceptions;

public class GameIllegalMoveException extends RuntimeException {
    public GameIllegalMoveException(String message) {
        super(message);
    }
}
