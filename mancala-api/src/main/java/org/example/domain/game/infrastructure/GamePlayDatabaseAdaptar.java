package org.example.domain.game.infrastructure;

import lombok.AllArgsConstructor;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;

@AllArgsConstructor
public class GamePlayDatabaseAdaptar implements GamePlayDatabase {

    private final GamePlayRepository gamePlayRepository;

    @Override
    public GameState save(GameState gameState) {
        return gamePlayRepository.save(gameState);
    }
}
