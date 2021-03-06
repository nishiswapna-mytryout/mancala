package org.example.domain.game.application;

import lombok.AllArgsConstructor;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController

@AllArgsConstructor
public class GameController {


    private final GamePlay gamePlay;

    @GetMapping(path = "game", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStateResponse> startGame(@RequestParam("playerIdA") final String playerIdA, @RequestParam("playerIdB") final String playerIdB) {
        GameStateResponse gameStateResponse = gamePlay.initialize(playerIdA, playerIdB);
        return gameStateResponse != null ? ResponseEntity.ok().body(gameStateResponse) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping(path = "game/{id}/pits/{pitlocation}/player/{playerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GameStateResponse> sow(@PathVariable("id") final String gameId,@PathVariable("pitlocation") final String pitPosition,@PathVariable("playerId") final String movingPlayer){

        GameStateResponse gameStateResponse = gamePlay.sow(gameId, pitPosition,movingPlayer);
        return gameStateResponse != null ? ResponseEntity.ok().body(gameStateResponse) :
                ResponseEntity.badRequest().build();

    }
//
//    @PostMapping("game/status/{id}")
//    public ResponseEntity<GameStateResponse> gameStatus(@PathVariable("id") final String gameId) {
//        return ResponseEntity.ok().body(null);
//    }

}
