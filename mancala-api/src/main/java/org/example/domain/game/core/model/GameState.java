package org.example.domain.game.core.model;

import lombok.*;
import org.example.domain.GameBoardFeatures;
import org.example.domain.Pit;
import org.example.domain.PlayerTurn;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Document(collection = "game")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class GameState implements Serializable {

    @Id
    private String gameId;
    @NonNull
    private final List<PlayerState> playerStates;

    private static GameState gameState;

    public static GameState initialize(final String playerIdA, final String playerIdB, final GameBoardFeatures gameBoardFeatures) {

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
            return gameState;

    }

    public List<Pit> getPitState() {
        return this.getPlayerStates().stream()
                .flatMap(playerState -> playerState.getPits().stream()).collect(Collectors.toList());
    }

    public String getPlayerIdTurn() {
        return this
                .getPlayerStates().stream()
                .filter(PlayerState::isPlayerTurn).findFirst()
                .map(PlayerState::getPlayerId).orElseThrow(() -> new IllegalStateException("There has to be one player with valid turn"));
    }

    public String getPlayerIdOpponent() {
        return this
                .getPlayerStates().stream()
                .filter(playerState -> !playerState.isPlayerTurn()).findFirst()
                .map(PlayerState::getPlayerId).orElseThrow(() -> new IllegalStateException("There has to be one player as opponent turn"));

    }

    public Pit getPitForPlayer(final String playerId, final String pitPosition) {
        PlayerState playingPlayerState = this.playerStates.stream()
                .filter(playerState -> playerState.getPlayerId().equals(playerId) && playerState.isPlayerTurn())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Player Turn not allowed"));

        return playingPlayerState.getPits().stream().filter(pit -> pit.getPitPosition().equals(pitPosition))
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Wrong Pit Position"));

    }


//    public Pit sowRightAllStonesInHandAndGiveLastPosition(final String startPitPosition, final PlayerTurn playerTurn, final int stones){
//
//        String currentPitPosition = startPitPosition;
//        for (int i = 0; i < stones; i++) {
//            Pit nextPit = this.getNextPit(playerTurn, currentPitPosition);
//            nextPit.sow(1);
//            currentPitPosition = nextPit.getPitPosition();
//        }
//
//        return this.getAllPits().get(currentPitPosition);
//
//    }
}
