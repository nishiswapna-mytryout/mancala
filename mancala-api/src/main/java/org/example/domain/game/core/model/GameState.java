package org.example.domain.game.core.model;

import lombok.*;
import org.example.domain.BigPit;
import org.example.domain.GameBoardFeatures;
import org.example.domain.Pit;
import org.example.domain.SmallPit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    /**
     * The mapping between pit position and Actual Pits
     **/
    @NonNull
    @Getter
    private final Map<String, Pit> allPits;

    private static GameState gameState;

    public static GameState initialize(final String playerIdA, final String playerIdB, final GameBoardFeatures gameBoardFeatures) {

        PlayerState playerStateA = new PlayerState(playerIdA, true, PlayerSide.SideA);
        PlayerState playerStateB = new PlayerState(playerIdB, false, PlayerSide.SideA);

        int gameStoneCount = gameBoardFeatures.getStoneCountPerPit();

        HashMap<String, Pit> pitSetUp = new HashMap<String, Pit>() {{

            put("A1", new SmallPit("A1", gameStoneCount));
            put("A2", new SmallPit("A2", gameStoneCount));
            put("A3", new SmallPit("A3", gameStoneCount));
            put("A4", new SmallPit("A4", gameStoneCount));
            put("A5", new SmallPit("A5", gameStoneCount));
            put("A6", new SmallPit("A6", gameStoneCount));
            put("AL", new BigPit("AL"));
            put("B1", new SmallPit("B1", gameStoneCount));
            put("B2", new SmallPit("B2", gameStoneCount));
            put("B3", new SmallPit("B3", gameStoneCount));
            put("B4", new SmallPit("B4", gameStoneCount));
            put("B5", new SmallPit("B5", gameStoneCount));
            put("B6", new SmallPit("B6", gameStoneCount));
            put("BL", new BigPit("BL"));
        }};

        gameState = new GameState(Arrays.asList(playerStateA, playerStateB), pitSetUp);
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

        Pit pit = this.getAllPits().get(pickPosition);

        PlayerSide movingPlayerSide = this.playerStates.stream()
                .filter(playerState -> playerState.getPlayerId().equals(movingPlayerId))
                .findFirst().map(PlayerState::getPlayerSide)
                .orElseThrow(() -> new IllegalStateException("Invalid player side"));

        if (pit instanceof SmallPit) {
            int pickedStones = ((SmallPit) pit).pick();
            String startPosition = pickPosition;
            for (int i = 0; i < pickedStones; i++) {
                Pit nextPit = this.getAllPits().get(gameBoardFeatures.getNextPit(startPosition, movingPlayerSide));
                nextPit.sow(1);
                startPosition = nextPit.getPitPosition();
            }

            Pit lastPit = this.getAllPits().get(startPosition);

            if (lastPit instanceof SmallPit
                    && gameBoardFeatures.belongsToPlayingSide(lastPit, movingPlayerSide)) {
                if (lastPit.getCurrentStoneCount() == 1) {
                    int lastStone = ((SmallPit) lastPit).pick();
                    SmallPit oppositePit = (SmallPit) this.getAllPits().get(gameBoardFeatures.getOppositePitPosition(lastPit.getPitPosition()));
                    int capturedStones = oppositePit.pick();
                    BigPit movingSideBigPit = (BigPit) this.getAllPits().get(gameBoardFeatures.getBigPitForPlayingSide(movingPlayerSide));
                    movingSideBigPit.sow(lastStone + capturedStones);
                } else {
                    this.switchPlayerTurn(movingPlayerSide);
                }
            } else if (lastPit instanceof BigPit && !gameBoardFeatures.belongsToPlayingSide(lastPit, movingPlayerSide)) {
                this.switchPlayerTurn(movingPlayerSide);
            }

        }


    }

    private void switchPlayerTurn(PlayerSide movingPlayerSide) {
        final PlayerState opponentSide = this.playerStates.stream()
                .filter(playerState -> !playerState.getPlayerSide().equals(movingPlayerSide))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There has to be one player in oppoenet"));

        final PlayerState playingSide = this.playerStates.stream()
                .filter(playerState -> !playerState.getPlayerSide().equals(movingPlayerSide))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("There has to be one player in oppoenet"));
        playingSide.setPlayerTurn(false);
        opponentSide.setPlayerTurn(true);
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

}
