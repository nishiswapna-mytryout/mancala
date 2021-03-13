package org.example.domain.player.core.ports.outgoing;

import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;

import java.util.Optional;


public interface PlayerDatabase {

    PlayerIdentifier save(final Player player);
    Optional<PlayerResponse> getExistingPlayer(final String playerId);


}
