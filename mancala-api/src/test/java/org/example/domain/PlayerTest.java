package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private static Player player = null;

    @BeforeAll
    public static void setUp(){
        player = new Player("Random Name",
                new BigPit("AL"),
                Arrays.asList(new SmallPit("A1",6), new SmallPit("A2",6)));
    }

    @Test
    public void testPickStonesWhenPitHasStones(){

        SmallPit pit = new SmallPit("A3",7);
        assertEquals(pit.getCurrentStoneCount(),player.pickStones(pit));
    }

    @Test
    public void testPickStonesWhenPitHasNoStones(){

        SmallPit pit = new SmallPit("A3",0);
        assertThrows(IllegalArgumentException.class,()->player.pickStones(pit));
    }

    @Test
    public void testPickStonesWhenPitisNull(){

        assertThrows(IllegalArgumentException.class,()->player.pickStones(null));
    }



}
