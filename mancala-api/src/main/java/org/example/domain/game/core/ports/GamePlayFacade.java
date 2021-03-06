package org.example.domain.game.core.ports;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.BigPit;
import org.example.domain.GameBoardFeatures;
import org.example.domain.Pit;
import org.example.domain.SmallPit;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;

@AllArgsConstructor
@Slf4j
public class GamePlayFacade implements GamePlay {

    private final GamePlayDatabase gamePlayDatabase;
    private final GameBoardFeatures gameBoardFeatures;

    @Override
    public GameStateResponse initialize(final NewGameCommand newGameCommand) {
        final GameState gameState = gamePlayDatabase.save(GameState.initialize(newGameCommand.getPlayerIdA(), newGameCommand.getPlayerIdB(), gameBoardFeatures));

        log.warn(gameState.toString());
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
    public GameStateResponse sow(final SowCommand sowCommand) {
        final GameState previousGameState = gamePlayDatabase.load(sowCommand.getGameId());

        final Pit pit = previousGameState.getPitForPlayer(sowCommand.getMovingPlayer(), sowCommand.getPitPosition());

        if (pit instanceof BigPit) {
            throw new IllegalStateException("Not allowed to pick stones from big pit");
        } else {
            SmallPit smallPit = (SmallPit) pit;

            if (smallPit.isEmpty()) {
                throw new IllegalStateException("Cannot pick from empty pit");
            }

            final int pickedStones = smallPit.pick();

            //gameBoardFeatures.getNextPit()


        }


        final GameState newGameState = gamePlayDatabase.update(previousGameState);

        if (newGameState == null) {
            throw new IllegalStateException("Something went wrong with storage");
        }

        return new GameStateResponse(newGameState.getGameId()
                , newGameState.getPitState()
                , newGameState.getPlayerIdTurn()
                , newGameState.getPlayerIdOpponent()
                , false);

    }
}
