package org.example.domain.player.infrastructure;

import lombok.AllArgsConstructor;
import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;

@AllArgsConstructor
public class PlayerDatabaseAdaptar implements PlayerDatabase {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerIdentifier save(final Player player) {
        Player savedData = playerRepository.save(player);
        return new PlayerIdentifier(savedData.getPlayerId());
    }
}
