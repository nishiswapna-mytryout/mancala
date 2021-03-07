package org.example.domain.game.core.ports.outgoing;


import org.example.domain.game.core.model.GameState;

import java.util.Optional;

public interface GamePlayDatabase {
    GameState save(final GameState gameState);

    Optional<GameState> load(final String gameId);

    GameState update(final GameState updatedGameState);
}
