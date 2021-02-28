package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TheGameTest {

    public static TheGame activegame = null;

    @BeforeAll
    public static void init(){
        activegame = new TheGame("Tom","Harry");
    }

    @Test
    public void testSow(){

        Player player = activegame.sow("A1", PlayerTurn.PLAYER_A);
        assertEquals(player.getPlayerName(),"Tom");

        player = activegame.sow("B2", PlayerTurn.PLAYER_B);
        assertEquals(player.getPlayerName(),"Tom");
    }

    @Test
    public void testInvalidSow(){
        assertThrows(IllegalArgumentException.class, ()-> activegame.sow("B2", PlayerTurn.PLAYER_A));
    }
}