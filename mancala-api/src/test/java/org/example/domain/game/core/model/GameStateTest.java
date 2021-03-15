package org.example.domain.game.core.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameStateTest {

    GameState gameState = null;


    @Before
    public void setUp() {
        gameState = GameState.initialize("SomeThingA", "SomethingB", 6);
    }

    @Test
    public void getSmallPitsTest() {
        assertTrue(gameState.getSmallPits(PlayerSide.SideA).size() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSmallPitsTestWhenPlayerSideNull() {
        gameState.getSmallPits(null);
    }

}