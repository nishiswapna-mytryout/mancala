package org.example.domain.game.application;

import lombok.AllArgsConstructor;
import org.example.domain.game.core.model.command.GetGameStatusCommand;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.output.ActiveGameStateResponse;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.example.domain.game.core.model.request.SowRequest;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PATCH})
@AllArgsConstructor
public class GameController {

    private final GamePlay gamePlay;


    @PostMapping(path = "game", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActiveGameStateResponse> initializeGame(@Valid @RequestBody final NewGameCommand newGameCommand) {
        ActiveGameStateResponse activeGameStateResponse = gamePlay.initialize(newGameCommand);
        return activeGameStateResponse != null ? ResponseEntity.ok().body(activeGameStateResponse) :
                ResponseEntity.badRequest().build();
    }

    @PatchMapping(path = "game/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ActiveGameStateResponse> sow(@PathVariable("id") final String gameId, @RequestBody final SowRequest sowRequest) {

        ActiveGameStateResponse activeGameStateResponse = gamePlay.sow(new SowCommand(gameId, sowRequest.getPickPosition(), sowRequest.getMovingPlayerId()));
        return activeGameStateResponse != null ? ResponseEntity.ok().body(activeGameStateResponse) :
                ResponseEntity.badRequest().build();

    }

    @GetMapping("game/{id}/status")
    public ResponseEntity<GameStateResponse> gameStatus(@PathVariable("id") final String gameId) {
        GameStateResponse gameStateResponse = gamePlay.getStatus(new GetGameStatusCommand(gameId));
        return gameStateResponse != null ? ResponseEntity.ok().body(gameStateResponse) :
                ResponseEntity.badRequest().build();
    }

}
