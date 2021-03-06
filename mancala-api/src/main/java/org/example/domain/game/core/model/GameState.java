package org.example.domain.game.core.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.domain.GameBoardFeatures;
import org.example.domain.Pit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Document(collection = "game")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class GameState {

    @Id
    private String gameId;
    @NonNull
    private final List<PlayerState> playerStates;

    private static GameState gameState;

    public static GameState initialize(String playerIdA, String playerIdB) {
        if (gameState == null) {

            GameBoardFeatures gameBoardFeatures = GameBoardFeatures.initializeGame(6);
            List<Pit> sideA = gameBoardFeatures.getAllPits().entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().contains("A"))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());
            List<Pit> sideB = gameBoardFeatures.getAllPits().entrySet()
                    .stream()
                    .filter(entry -> entry.getKey().contains("B"))
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            PlayerState playerStateA = new PlayerState(playerIdA, true, sideA);
            PlayerState playerStateB = new PlayerState(playerIdB, false, sideB);
            gameState = new GameState(Arrays.asList(playerStateA, playerStateB));
        }
        return gameState;
    }

    public List<Pit> getPitState(){
        return this.getPlayerStates().stream()
                .flatMap(playerState -> playerState.getPits().stream()).collect(Collectors.toList());
    }

    public String getPlayerIdTurn(){
        return this
                .getPlayerStates().stream()
                .filter(PlayerState::isPlayerTurn).findFirst()
                .map(PlayerState::getPlayerId).orElseThrow(() -> new IllegalStateException("There has to be one player with valid turn"));
    }

    public String getPlayerIdOpponent(){
        return this
                .getPlayerStates().stream()
                .filter(playerState -> !playerState.isPlayerTurn()).findFirst()
                .map(PlayerState::getPlayerId).orElseThrow(() -> new IllegalStateException("There has to be one player as opponent turn"));

    }

}
