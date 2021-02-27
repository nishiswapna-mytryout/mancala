package org.example.domain;

import lombok.AllArgsConstructor;
import org.example.domain.exceptions.GameDrawException;
import org.example.domain.exceptions.GameNotOverException;

import java.util.Arrays;
import java.util.TreeSet;
import java.util.stream.Stream;


public class TheGame {

    private final Player playerA;
    private final Player playerB;

    public TheGame(final String playerAName, final String playerBName) {

        int PIT_STONE_COUNT = 6;
        TreeSet<Pit> pitTreeSetA = new TreeSet<>(Arrays.asList(new Pit("A1", PIT_STONE_COUNT)
                , new Pit("A2", PIT_STONE_COUNT)
                , new Pit("A3", PIT_STONE_COUNT)
                , new Pit("A4", PIT_STONE_COUNT)
                , new Pit("A5", PIT_STONE_COUNT)
                , new Pit("A6", PIT_STONE_COUNT)));

        TreeSet<Pit> pitTreeSetB = new TreeSet<>(Arrays.asList(new Pit("B1", PIT_STONE_COUNT)
                , new Pit("B2", PIT_STONE_COUNT)
                , new Pit("B3", PIT_STONE_COUNT)
                , new Pit("B4", PIT_STONE_COUNT)
                , new Pit("B5", PIT_STONE_COUNT)
                , new Pit("B6", PIT_STONE_COUNT)));


        this.playerA = new Player(playerAName, new BigPit("AL"), pitTreeSetA);
        this.playerB = new Player(playerBName, new BigPit("BL"), pitTreeSetB);
    }


    public Player sow(int pickPosition, PlayerTurn playerTurn) throws GameNotOverException, GameDrawException {

        if(isGameComingToEnd()){
            return getWinner();
        }else{

            if(PlayerTurn.PLAYER_A.equals(playerTurn)){
                
                Pit pickupPit = playerA.getPit("A"+pickPosition);
                int pickedStones = pickupPit.pick();
                sowRight(pickedStones-1,pickPosition+1);

            }else if(PlayerTurn.PLAYER_B.equals(playerTurn)){

            }

        }

    }

    private void sowRight(int i, int i1) {
    }

    public boolean isGameComingToEnd() {

        return playerA.areAllPitsEmpty() || playerB.areAllPitsEmpty();

    }

    public Player getWinner() throws GameDrawException, GameNotOverException {

        if (playerA.areAllPitsEmpty() && playerB.areAllPitsEmpty()) {
            return getWinningPlayer();

        } else if (playerA.areAllPitsEmpty() && !playerB.areAllPitsEmpty()) {

            playerB.getBigPit().put(playerB.emptyAllPits());
            return getWinningPlayer();

        } else if (!playerA.areAllPitsEmpty() && playerB.areAllPitsEmpty()) {

            playerA.getBigPit().put(playerA.emptyAllPits());
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
