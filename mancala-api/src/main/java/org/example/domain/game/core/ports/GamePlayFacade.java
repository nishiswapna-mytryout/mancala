package org.example.domain.game.core.ports;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.GameBoardFeatures;
import org.example.domain.Pit;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;

import java.util.ArrayList;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class GamePlayFacade implements GamePlay {

    private final GamePlayDatabase gamePlayDatabase;
    private final GameBoardFeatures gameBoardFeatures;
    private final PlayerDatabase playerDatabase;

    @Override
    public GameStateResponse initialize(final NewGameCommand newGameCommand) {

        playerDatabase.getExistingPlayer(newGameCommand.getPlayerIdA())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Player id %s unknown ", newGameCommand.getPlayerIdA())));

        playerDatabase.getExistingPlayer(newGameCommand.getPlayerIdB())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Player id %s unknown ", newGameCommand.getPlayerIdB())));


        final GameState gameState = gamePlayDatabase.save(GameState.initialize(newGameCommand.getPlayerIdA(), newGameCommand.getPlayerIdB(), gameBoardFeatures));

        log.warn(gameState.toString());
        if (gameState == null) {
            throw new IllegalStateException("Something went wrong with storage");
        }

        return new GameStateResponse(gameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , gameState.getPlayerIdTurn()
                , gameState.getPlayerIdOpponent()
                , false);

    }

    @Override
    public GameStateResponse sow(final SowCommand sowCommand) {

        playerDatabase.getExistingPlayer(sowCommand.getMovingPlayer())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Player id %s unknown for the active game", sowCommand.getMovingPlayer())));

        final GameState gameState = gamePlayDatabase.load(sowCommand.getGameId())
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unknown active game %s", sowCommand.getGameId())));

        gameState.isPlayerMoveAllowed(sowCommand.getMovingPlayer(),
                ()->new IllegalStateException(String.format("Player %s move not allowed", sowCommand.getMovingPlayer())));

        gameState.isPickPositionValid(sowCommand.getPickPosition(),sowCommand.getMovingPlayer(),
                ()->new IllegalStateException(String.format("Player cannot pick from this position %s", sowCommand.getPickPosition())));

        gameState.sowByPlayer(sowCommand.getMovingPlayer(), sowCommand.getPickPosition(), gameBoardFeatures);

        log.warn(gameState.toString());

        final GameState newGameState = gamePlayDatabase.update(gameState);

        if (newGameState == null) {
            throw new IllegalStateException("Something went wrong with storage");
        }

        return new GameStateResponse(newGameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , newGameState.getPlayerIdTurn()
                , newGameState.getPlayerIdOpponent()
                , false);

    }
}
