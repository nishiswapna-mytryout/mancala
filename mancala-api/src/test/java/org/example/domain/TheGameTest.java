package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TheGameTest {

    @Test
    public void testSow(){
        TheGame theGame = new TheGame("Tom","Harry");
        Player player = theGame.sow("A1", PlayerTurn.PLAYER_A);
        assertEquals(player.getPlayerName(),"Harry");

        player = theGame.sow("B2", PlayerTurn.PLAYER_B);
        assertEquals(player.getPlayerName(),"Tom");
    }

}