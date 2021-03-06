package org.example.domain.game.core.ports;

import lombok.AllArgsConstructor;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;

@AllArgsConstructor
public class GamePlayFacade implements GamePlay {

    private final GamePlayDatabase gamePlayDatabase;

    @Override
    public GameStateResponse initialize(String playerIdA, String playerIdB) {
        final GameState gameState = gamePlayDatabase.save(GameState.initialize(playerIdA, playerIdB));

        if (gameState == null) {
            throw new IllegalStateException("Something went wrong with storage");
        }

        return new GameStateResponse(gameState.getGameId()
                , gameState.getPitState()
                , gameState.getPlayerIdTurn()
                , gameState.getPlayerIdOpponent()
                , false);

    }

    @Override
    public GameStateResponse sow(String gameId, String pitPosition, String movingPlayer) {
        GameState previousGameState = gamePlayDatabase.load(gameId);
        return null;
    }
}
