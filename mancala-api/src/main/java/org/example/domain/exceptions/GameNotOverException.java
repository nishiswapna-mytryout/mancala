package org.example.domain.exceptions;

public class GameNotOverException extends Throwable {
    public GameNotOverException(String msg) {
        super(msg);
    }
}
