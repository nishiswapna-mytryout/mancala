package org.example.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.GameDrawException;
import org.example.domain.exceptions.GameNotOverException;
import org.example.domain.game.core.model.Pit;
import org.example.domain.game.core.model.SmallPit;

@Slf4j
public class TheGame {

    private final Player1 playerA;
    private final Player1 playerB;
    private final GameBoard gameBoard;


    public TheGame(final String playerAName, final String playerBName) {

        int PIT_STONE_COUNT = 6;
        this.gameBoard = GameBoard.initializeGame(PIT_STONE_COUNT);
        this.playerA = new Player1(playerAName, this.gameBoard.getBigPitA(), this.gameBoard.getSideA());
        this.playerB = new Player1(playerBName, this.gameBoard.getBigPitB(), this.gameBoard.getSideB());
    }


    public Player1 sow(String pickPosition, PlayerTurn playerTurn) {

        String pitPosition = pickPosition;
        log.warn(String.format("Player selected position %s",pickPosition));
        if (PlayerTurn.PLAYER_A.equals(playerTurn)) {
            return playAndNextTurn(this.playerA, this.playerB, playerTurn, pitPosition);
        } else if (PlayerTurn.PLAYER_B.equals(playerTurn)) {
            return playAndNextTurn(this.playerB, this.playerA, playerTurn, pitPosition);
        } else {
            throw new IllegalArgumentException("Unknown Player turn type");
        }

    }

    private Player1 playAndNextTurn(Player1 currentPlayer, Player1 nextPlayer, PlayerTurn playerTurn, String pitPosition) {

        int pickedStones = currentPlayer.getPit(pitPosition).pick();

        Pit lastSownPit = this.gameBoard.sowRightAllStonesInHandAndGiveLastPosition(pitPosition,playerTurn,pickedStones-1);

        int lastStone = 1;
        //bad code we have to come back here
        Pit lastPitToSow = this.gameBoard.getNextPit(playerTurn, lastSownPit.getPitPosition());

        //handle last stone
        if (currentPlayer.getBigPit().equals(lastPitToSow)) {
            currentPlayer.sowStonesInBigPit(lastStone);
            log.warn(this.gameBoard.toString());
            return currentPlayer;
        } else {

            if (lastPitToSow instanceof SmallPit
                    && currentPlayer.smallPitBelongsToMe((SmallPit)lastPitToSow)
                    && lastPitToSow.isEmpty()) {
                int capturedStonesFromOpponent = 0;

                try {
                    capturedStonesFromOpponent = this.gameBoard.getOppositePit(lastPitToSow.getPitPosition()).pick();
                } catch (IllegalArgumentException exception) {
                    log.warn("The opposite pit doesnt have any stones, hence captured stones is 0");
                }

                currentPlayer.sowStonesInBigPit(capturedStonesFromOpponent + lastStone);
            } else {
                lastPitToSow.sow(lastStone);
            }
            log.warn(this.gameBoard.toString());
            return nextPlayer;
        }

    }


    public boolean isGameComingToEnd() {
        return playerA.areAllPitsEmpty() || playerB.areAllPitsEmpty();
    }

    public Player1 getWinner() throws GameDrawException, GameNotOverException {

        if (playerA.areAllPitsEmpty() && playerB.areAllPitsEmpty()) {
            return getWinningPlayer();

        } else if (playerA.areAllPitsEmpty() && !playerB.areAllPitsEmpty()) {

            playerB.getBigPit().sow(playerB.emptyAllPits());
            return getWinningPlayer();

        } else if (!playerA.areAllPitsEmpty() && playerB.areAllPitsEmpty()) {

            playerA.getBigPit().sow(playerA.emptyAllPits());
            return getWinningPlayer();

        } else {
            throw new GameNotOverException("Game is not over, Please continue");
        }

    }

    private Player1 getWinningPlayer() throws GameDrawException {
        if (playerB.getBigPit().getCurrentStoneCount() == playerA.getBigPit().getCurrentStoneCount()) {
            throw new GameDrawException("Game is Draw, No winner");
        } else if (playerB.getBigPit().getCurrentStoneCount() > playerA.getBigPit().getCurrentStoneCount()) {
            return playerA;
        } else {
            return playerB;
        }
    }


}
