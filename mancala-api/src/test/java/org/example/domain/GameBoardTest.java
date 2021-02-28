package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    public static GameBoard gameboard = null;

    @BeforeAll
    public static void init(){
        gameboard = new GameBoard(6);
    }

    @Test
    public void testGetOppositePit(){

        assertEquals("B3",gameboard.getOppositePit("A4").getPitPosition());
    }

}