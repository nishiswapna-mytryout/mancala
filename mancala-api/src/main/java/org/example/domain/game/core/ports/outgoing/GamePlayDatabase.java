package org.example.domain.game.core.ports.outgoing;


import org.example.domain.game.core.model.GameState;

public interface GamePlayDatabase {
    GameState save(final GameState gameState);

    GameState load(final String gameId);

    GameState update(final GameState updatedGameState);
}
