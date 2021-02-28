package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    public static GameBoard gameboard = null;

    @BeforeAll
    public static void init() {
        gameboard = GameBoard.initializeGame(6);
    }

    @Test
    public void testGetOppositePit() {
        assertEquals("B3", gameboard.getOppositePit("A4").getPitPosition());
    }

    @Test
    public void testGetOppPitforInvalidposition(){
        assertThrows(IllegalArgumentException.class, ()-> gameboard.getOppositePit("D3").getPitPosition());
    }

    @Test
    public void testGetOppPitforBigPit(){
        assertThrows(IllegalArgumentException.class, ()-> gameboard.getOppositePit("AL").getPitPosition());
    }

    @Test
    public void testGetNextPit(){
       assertEquals("B4",gameboard.getNextPit(PlayerTurn.PLAYER_A,"B3").getPitPosition());
    }

    @Test
    public void testGetNextPitforSameBigPit(){
        assertEquals("AL",gameboard.getNextPit(PlayerTurn.PLAYER_A,"A6").getPitPosition());
    }

    @Test
    public void testGetNextPitforOppBigPit(){
        assertEquals("A1",gameboard.getNextPit(PlayerTurn.PLAYER_A,"B6").getPitPosition());
    }

    @Test
    public void testGetNextPitforInvalidposition(){
        assertThrows(IllegalArgumentException.class,() -> gameboard.getNextPit(PlayerTurn.PLAYER_A,"D3"));
    }

}