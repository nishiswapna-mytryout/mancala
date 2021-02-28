package org.example.domain;

import org.example.domain.exceptions.GameDrawException;
import org.example.domain.exceptions.GameNotOverException;


public class TheGame {

    private final Player playerA;
    private final Player playerB;
    private final GameBoard gameBoard;


    public TheGame(final String playerAName, final String playerBName) {




        int PIT_STONE_COUNT = 6;
        this.gameBoard = new GameBoard(PIT_STONE_COUNT);


        this.playerA = new Player(playerAName, this.gameBoard.getBigPitA(), this.gameBoard.getSideA());
        this.playerB = new Player(playerBName, this.gameBoard.getBigPitB(), this.gameBoard.getSideB());
    }


    public Player sow(int pickPosition, PlayerTurn playerTurn) throws GameNotOverException, GameDrawException {

        return null;

    }



    public boolean isGameComingToEnd() {

        return playerA.areAllPitsEmpty() || playerB.areAllPitsEmpty();

    }

    public Player getWinner() throws GameDrawException, GameNotOverException {

        if (playerA.areAllPitsEmpty() && playerB.areAllPitsEmpty()) {
            return getWinningPlayer();

        } else if (playerA.areAllPitsEmpty() && !playerB.areAllPitsEmpty()) {

            playerB.getBigPit().sow(playerB.emptyAllPits());
            return getWinningPlayer();

        } else if (!playerA.areAllPitsEmpty() && playerB.areAllPitsEmpty()) {

            playerA.getBigPit().sow(playerA.emptyAllPits());
            return getWinningPlayer();

        } else{
            throw new GameNotOverException("Game is not over, Please continue");
        }

    }

    private Player getWinningPlayer() throws GameDrawException {
        if(playerB.getBigPit().getCurrentStoneCount() == playerA.getBigPit().getCurrentStoneCount()){
            throw new GameDrawException("Game is Draw, No winner");
        }else if(playerB.getBigPit().getCurrentStoneCount()>playerA.getBigPit().getCurrentStoneCount()){
            return playerA;
        }else{
            return playerB;
        }
    }


}
