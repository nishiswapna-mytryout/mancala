package org.example.domain.player.infrastructure;

import lombok.AllArgsConstructor;
import org.example.domain.player.core.model.Player;
import org.example.domain.player.core.model.output.PlayerIdentifier;
import org.example.domain.player.core.model.output.PlayerResponse;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;

@AllArgsConstructor
public class PlayerDatabaseAdaptar implements PlayerDatabase {

    private final PlayerRepository playerRepository;

    @Override
    public PlayerIdentifier save(final Player player) {
        Player savedData = playerRepository.save(player);
        return new PlayerIdentifier(savedData.getPlayerId());
    }

    @Override
    public PlayerResponse get(final String id) {
        Player player = playerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id not Found"));
        return new PlayerResponse(player.getFirstName(), player.getLastName());
    }
}
