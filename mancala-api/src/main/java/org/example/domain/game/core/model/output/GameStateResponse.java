package org.example.domain.game.core.model.output;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.example.domain.game.core.model.GameState;
import org.example.domain.game.core.model.Pit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GameStateResponse {

    private final String gameId;
    private final List<Pit> pitState;
    private final boolean hasGameEnded;
    private final List<PlayerScore> playerScores;

    public static GameStateResponse from(@NonNull final GameState gameState) {

        final List<PlayerScore> playerScores = gameState.getPlayerStates()
                .stream()
                .map(playerState -> new PlayerScore(playerState.getPlayerId(),
                        gameState.getBigPit(playerState.getPlayerSide()).getCurrentStoneCount()))
                .collect(Collectors.toList());

        return new GameStateResponse(gameState.getGameId()
                , new ArrayList<>(gameState.getAllPits().values())
                , gameState.isFinished()
                , playerScores);

    }

}
