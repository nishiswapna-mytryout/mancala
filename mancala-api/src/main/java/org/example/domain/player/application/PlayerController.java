package org.example.domain.player.application;

import lombok.AllArgsConstructor;
import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.ports.incoming.AddNewPlayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PlayerController {

    private AddNewPlayer addNewPlayer;


    @GetMapping("player/{id}")
    public ResponseEntity<String> getPlayer(@PathVariable("id") final String playerId) {

        return null;
    }

    @PostMapping("player")
    public ResponseEntity<String> addPlayer(@RequestBody final AddPlayerCommand addPlayerCommand) {
        PlayerIdentifier playerIdentifier = addNewPlayer.handleCommand(addPlayerCommand);

        return playerIdentifier!=null ?
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(playerIdentifier.getPlayerId()):
                ResponseEntity.badRequest().body("someting went wrong");

    }

}
