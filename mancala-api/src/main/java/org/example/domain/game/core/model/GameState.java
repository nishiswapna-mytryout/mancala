package org.example.domain.game.core.model;

import lombok.*;
import org.example.domain.GameBoardFeatures;
import org.example.domain.Pit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Document(collection = "game")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class GameState implements Serializable {

    @Id
    private String gameId;
    @NonNull
    private final List<PlayerState> playerStates;

    @NonNull
    @Getter
    private final List<Pit> pitState;

    private static GameState gameState;

    public static GameState initialize(final String playerIdA, final String playerIdB, final GameBoardFeatures gameBoardFeatures) {

        PlayerState playerStateA = new PlayerState(playerIdA, true, PlayerSide.SideA);
        PlayerState playerStateB = new PlayerState(playerIdB, false, PlayerSide.SideA);

        gameState = new GameState(Arrays.asList(playerStateA, playerStateB), new ArrayList<>(gameBoardFeatures.getAllPits().values()));
        return gameState;

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

    public void sowByPlayer(String movingPlayerId, String pickPosition, GameBoardFeatures gameBoardFeatures) {


    }


    public void isPlayerMoveAllowed(final String movingPlayer, final Supplier<? extends RuntimeException> exceptionSupplier) {
        this.playerStates.stream()
                .filter(playerState -> playerState.getPlayerId().equals(movingPlayer) && playerState.isPlayerTurn())
                .findFirst()
                .orElseThrow(exceptionSupplier);
    }

    public void isPickPositionValid(final String pickPosition, final String movingPlayer, final Supplier<? extends RuntimeException> exceptionSupplier) {
        this.playerStates.stream()
                .filter(playerState -> playerState.getPlayerId().equals(movingPlayer) && pickPosition.indexOf(playerState.getPlayerSide().getGameSide()) == 0)
                .findFirst().orElseThrow(exceptionSupplier);

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
