package org.example.domain.player.core.model.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(final String message) {
        super(message);
    }
}
