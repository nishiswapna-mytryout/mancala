package org.example.domain.player.application;

import lombok.AllArgsConstructor;
import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.command.GetPlayerCommand;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.incoming.AddNewPlayer;
import org.example.domain.player.core.ports.incoming.GetPlayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class PlayerController {

    private AddNewPlayer addNewPlayer;
    private GetPlayer getPlayer;


    @GetMapping("player/{id}")
    public ResponseEntity<PlayerResponse> getPlayer(@PathVariable("id") final String playerId) {

        PlayerResponse playerResponse = getPlayer.get(new GetPlayerCommand(playerId));
        return playerResponse != null ? ResponseEntity.ok(playerResponse) : ResponseEntity.notFound().build();
    }

    @PostMapping("player")
    public ResponseEntity<PlayerIdentifier> addPlayer(@RequestBody final AddPlayerCommand addPlayerCommand) {
        PlayerIdentifier playerIdentifier = addNewPlayer.add(addPlayerCommand);

        return playerIdentifier != null ?
                ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(playerIdentifier) :
                ResponseEntity.badRequest().build();

    }

}
