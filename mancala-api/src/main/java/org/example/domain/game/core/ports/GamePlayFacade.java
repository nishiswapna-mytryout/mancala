package org.example.domain.game.core.ports;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.domain.GameBoardFeatures;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.command.GetGameStatusCommand;
import org.example.domain.game.core.model.command.NewGameCommand;
import org.example.domain.game.core.model.command.SowCommand;
import org.example.domain.game.core.model.exceptions.GameIllegalMoveException;
import org.example.domain.game.core.model.exceptions.GameNotFoundException;
import org.example.domain.game.core.model.output.ActiveGameStateResponse;
import org.example.domain.game.core.model.output.GameStateResponse;
import org.example.domain.game.core.model.output.PlayerScore;
import org.example.domain.game.core.ports.incoming.GamePlay;
import org.example.domain.game.core.ports.outgoing.GamePlayDatabase;
import org.example.domain.player.core.model.exceptions.PlayerNotFoundException;
import org.example.domain.player.core.ports.outgoing.PlayerDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
public class GamePlayFacade implements GamePlay {

    private final GamePlayDatabase gamePlayDatabase;
    private final GameBoardFeatures gameBoardFeatures;
    private final PlayerDatabase playerDatabase;

    @Override
    public ActiveGameStateResponse initialize(final NewGameCommand newGameCommand) {

        playerDatabase.getExistingPlayer(newGameCommand.getPlayerIdA())
                .orElseThrow(() -> new PlayerNotFoundException(String.format("Player id %s unknown ", newGameCommand.getPlayerIdA())));

        playerDatabase.getExistingPlayer(newGameCommand.getPlayerIdB())
                .orElseThrow(() -> new PlayerNotFoundException(String.format("Player id %s unknown ", newGameCommand.getPlayerIdB())));


        final GameState gameState = gamePlayDatabase.save(GameState.initialize(newGameCommand.getPlayerIdA(), newGameCommand.getPlayerIdB(), gameBoardFeatures));

        log.warn(gameState.toString());

        return new ActiveGameStateResponse(gameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , gameState.getMovingPlayerId()
                , gameState.getOpponentPlayerId());

    }

    @Override
    public ActiveGameStateResponse sow(final SowCommand sowCommand) {

        playerDatabase.getExistingPlayer(sowCommand.getMovingPlayerId())
                .orElseThrow(() -> new PlayerNotFoundException(String.format("Player id %s unknown for the active game", sowCommand.getMovingPlayerId())));

        final GameState gameState = gamePlayDatabase.load(sowCommand.getGameId())
                .orElseThrow(() -> new GameNotFoundException(String.format("Unknown active game %s", sowCommand.getGameId())));

        gameState.isPlayerMoveAllowed(sowCommand.getMovingPlayerId(),
                () -> new GameIllegalMoveException(String.format("Player %s move not allowed", sowCommand.getMovingPlayerId())));

        gameState.isPickPositionValid(sowCommand.getPickPosition(), sowCommand.getMovingPlayerId(),
                () -> new GameIllegalMoveException(String.format("Player cannot pick from this position %s", sowCommand.getPickPosition())));

        final GameState newGameState = gameState.sow(sowCommand.getMovingPlayerId(), sowCommand.getPickPosition(), gameBoardFeatures);

        log.warn(gameState.toString());

        final GameState updatedGameState = gamePlayDatabase.update(newGameState);

        return new ActiveGameStateResponse(updatedGameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , updatedGameState.getMovingPlayerId()
                , updatedGameState.getOpponentPlayerId());

    }

    @Override
    public GameStateResponse getStatus(GetGameStatusCommand getGameStatusCommand) {
        final GameState gameState = gamePlayDatabase.load(getGameStatusCommand.getGameId())
                .orElseThrow(() -> new GameNotFoundException(String.format("Unknown active game %s", getGameStatusCommand.getGameId())));

        GameState newGameState = gameState.checkStatus();

        final GameState updatedGameState = gamePlayDatabase.update(newGameState);

        final List<PlayerScore> playerScores = updatedGameState.getPlayerStates()
                .stream()
                .map(playerState -> new PlayerScore(playerState.getPlayerId(),
                        updatedGameState.getBigPit(playerState.getPlayerSide()).getCurrentStoneCount()))
                .collect(Collectors.toList());
        return new GameStateResponse(updatedGameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , updatedGameState.isFinished()
                , playerScores);

    }

}
