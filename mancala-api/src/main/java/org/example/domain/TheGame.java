package org.example.domain;

import lombok.extern.slf4j.Slf4j;
import org.example.domain.exceptions.GameDrawException;
import org.example.domain.exceptions.GameNotOverException;

import java.util.stream.IntStream;

@Slf4j
public class TheGame {

    private final Player playerA;
    private final Player playerB;
    private final GameBoard gameBoard;


    public TheGame(final String playerAName, final String playerBName) {

        int PIT_STONE_COUNT = 6;
        this.gameBoard = GameBoard.initializeGame(PIT_STONE_COUNT);
        this.playerA = new Player(playerAName, this.gameBoard.getBigPitA(), this.gameBoard.getSideA());
        this.playerB = new Player(playerBName, this.gameBoard.getBigPitB(), this.gameBoard.getSideB());
    }


    public Player sow(String pickPosition, PlayerTurn playerTurn) throws GameNotOverException, GameDrawException {

        String pitPosition = pickPosition;
        if(PlayerTurn.PLAYER_A.equals(playerTurn)){

            int pickedStones = this.playerA.getPit(pitPosition).pick();

            //drop all stones except the last one
            for(int i = 0 ; i < pickedStones-1 ;i++){
                Pit nextPit = this.gameBoard.getNextPit(playerTurn, pitPosition);
                nextPit.sow(1);
                pitPosition = nextPit.getPitPosition();
            }

            int lastStone = 1;

            //handle last stone
            if("AL".equals(pitPosition)){
                this.playerA.getBigPit().sow(lastStone);
                return this.playerA;
            }else{
                Pit pit = this.gameBoard.getAllPits().get(pitPosition);

                if(this.playerA.getPits().contains(pit) && pit.getCurrentStoneCount()==0){
                    int capturedStones = 0 ;

                    try {
                        capturedStones = this.gameBoard.getOppositePit(pitPosition).pick();
                    }catch(IllegalArgumentException exception){
                        log.warn("The opposite pit doesnt have any stones, hence captured stones is 0");
                    }

                    this.playerA.getBigPit().sow(capturedStones+lastStone);
                }else {
                    pit.sow(1);
                }

                return playerB;
            }



        }else if(PlayerTurn.PLAYER_B.equals(playerTurn)){

        }

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

        } else {
            throw new GameNotOverException("Game is not over, Please continue");
        }

    }

    private Player getWinningPlayer() throws GameDrawException {
        if (playerB.getBigPit().getCurrentStoneCount() == playerA.getBigPit().getCurrentStoneCount()) {
            throw new GameDrawException("Game is Draw, No winner");
        } else if (playerB.getBigPit().getCurrentStoneCount() > playerA.getBigPit().getCurrentStoneCount()) {
            return playerA;
        } else {
            return playerB;
        }
    }


}
