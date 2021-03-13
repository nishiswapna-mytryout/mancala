package org.example.domain.player.infrastructure;

import lombok.AllArgsConstructor;
import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;

import java.util.Optional;

@AllArgsConstructor
public class PlayerDatabaseAdaptar implements PlayerDatabase {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerIdentifier save(final Player player) {
        Player savedData = playerRepository.save(player);
        return new PlayerIdentifier(savedData.getPlayerId());
    }

    @Override
    public Optional<PlayerResponse> getExistingPlayer(final String playerId) {
        return playerRepository.findById(playerId).map(player-> new PlayerResponse(player.getFirstName(),player.getLastName()));
    }


}
