package org.example.domain.player.core.ports;

import lombok.AllArgsConstructor;
import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.command.AddPlayerCommand;
import org.example.domain.player.core.model.command.GetPlayerCommand;
import org.example.domain.player.core.model.exceptions.PlayerNotFoundException;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.incoming.AddNewPlayer;
import org.example.domain.player.core.ports.incoming.GetPlayer;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;

@AllArgsConstructor
public class PlayerFacade implements AddNewPlayer, GetPlayer {

    final PlayerDatabase playerDatabase;

    @Override
    public PlayerIdentifier add(AddPlayerCommand addPlayerCommand) {
        final Player player = new Player(addPlayerCommand.getFirstName(), addPlayerCommand.getLastName());
        player.validatePlayer(()->new IllegalArgumentException("Player contains invalid data, cannot be saved"));
        return playerDatabase.save(player);
    }

    @Override
    public PlayerResponse get(GetPlayerCommand getPlayerCommand) {
        return playerDatabase.getExistingPlayer(getPlayerCommand.getPlayerId())
                .orElseThrow(() -> new PlayerNotFoundException(String.format("Player not found for id %s", getPlayerCommand.getPlayerId())));
    }
}
