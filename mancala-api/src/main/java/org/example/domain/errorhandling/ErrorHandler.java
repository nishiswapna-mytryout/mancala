package org.example.domain.errorhandling;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.game.core.model.exceptions.GameIllegalMoveException;
import org.example.domain.game.core.model.exceptions.GameIllegalStateException;
import org.example.domain.game.core.model.exceptions.GameNotFoundException;
import org.example.domain.player.core.model.exceptions.PlayerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.OffsetTime;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler(GameIllegalMoveException.class)
    public ResponseEntity<ErrorResponse>
    handleGameIllegalMoveException(final GameIllegalMoveException gameIllegalMoveException, final WebRequest request) {
        log.error(String.format("Exception happened %s", gameIllegalMoveException));
        return new ResponseEntity<>(new ErrorResponse(gameIllegalMoveException.getMessage(), OffsetTime.now()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(GameIllegalStateException.class)
    public ResponseEntity<ErrorResponse>
    handleGameIllegalStateException(final GameIllegalStateException gameIllegalStateException, final WebRequest request) {
        log.error(String.format("Exception happened %s", gameIllegalStateException));
        return new ResponseEntity<>(new ErrorResponse(gameIllegalStateException.getMessage(), OffsetTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GameNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleGameNotFoundException(final GameNotFoundException gameNotFoundException, final WebRequest request) {
        log.error(String.format("Exception happened %s", gameNotFoundException));
        return new ResponseEntity<>(new ErrorResponse(gameNotFoundException.getMessage(), OffsetTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handlePlayerNotFoundException(final PlayerNotFoundException playerNotFoundException, final WebRequest request) {
        log.error(String.format("Exception happened %s", playerNotFoundException));
        return new ResponseEntity<>(new ErrorResponse(playerNotFoundException.getMessage(), OffsetTime.now()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handlePlayerNotFoundException(final Exception unknownException, final WebRequest request) {
        log.error(String.format("Unknown Exception happened %s", unknownException));
        return new ResponseEntity<>(new ErrorResponse(unknownException.getMessage(), OffsetTime.now()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
