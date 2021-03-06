package org.example.domain.game.infrastructure;

import lombok.AllArgsConstructor;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;
import org.example.domain.game.infrastructure.exceptions.StorageNotFoundException;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

@AllArgsConstructor
public class GamePlayDatabaseAdaptar implements GamePlayDatabase {

    private final GamePlayRepository gamePlayRepository;

    @Override
    //@CachePut(value = "mancala", key = "#gameState.gameId")
    public GameState save(GameState gameState) {
        return gamePlayRepository.save(gameState);
    }

    @Override
    //@Cacheable(value = "mancala", key = "#gameId" , unless = "#result  == null")
    public GameState load(final String gameId) {
        return gamePlayRepository.findById(gameId).orElseThrow(() -> new StorageNotFoundException("Unknown Game"));
    }

    @Override
    //@CachePut(value = "mancala", key = "#updatedGameState.gameId" , unless = "#result  == null")
    public GameState update(GameState updatedGameState) {
        return gamePlayRepository.save(updatedGameState);
    }
}
