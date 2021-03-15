package org.example.domain.game.core.model;

import org.example.domain.GameBoardFeatures;
import org.example.domain.game.core.model.exceptions.GameIllegalMoveException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameStateTest {

    private GameState gameState = null;
    private GameBoardFeatures gameBoardFeatures = null;
    private final String playerIdA = "SomeThingA";
    private final String playerIdB = "SomethingB";


    @Before
    public void setUp() {
        int stoneCount = 6;
        gameState = GameState.initialize(playerIdA, playerIdB, stoneCount);
        gameBoardFeatures = new GameBoardFeatures(stoneCount);
    }

    @Test
    public void getSmallPitsTest() {
        assertTrue(gameState.getSmallPits(PlayerSide.SideA).size() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSmallPitsTestWhenPlayerSideNull() {
        gameState.getSmallPits(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sowTestWithNullGameFeatures() {
        gameState.sow("", "", null);
    }

    @Test(expected = GameIllegalMoveException.class)
    public void sowFromOpponentBigPitTest() {
        gameState.sow("SomeThingA", "BL", gameBoardFeatures);
    }

    @Test(expected = GameIllegalMoveException.class)
    public void sowFromSelfBigPitTest() {
        gameState.sow("SomeThingA", "AL", gameBoardFeatures);
    }

    @Test(expected = GameIllegalMoveException.class)
    public void sowFromUnknownPitTest() {
        gameState.sow("SomeThingA", "A", gameBoardFeatures);
    }

    @Test
    public void sowFromSelfSmallPitTest() {
        GameState updatedGameState = gameState.sow("SomeThingA", "A2", gameBoardFeatures);
        assertTrue(updatedGameState.getAllPits().get("A2").isEmpty());
        assertFalse(updatedGameState.isFinished());
        assertEquals(updatedGameState.getMovingPlayerId(),playerIdB);
        assertEquals(updatedGameState.getOpponentPlayerId(),playerIdA);
    }

    @Test
    public void sowFromSelfSmallPitAndRepeatTurnTest() {
        GameState updatedGameState = gameState.sow("SomeThingA", "A1", gameBoardFeatures);
        assertTrue(updatedGameState.getAllPits().get("A1").isEmpty());
        assertFalse(updatedGameState.isFinished());
        assertEquals(updatedGameState.getMovingPlayerId(),playerIdA);
        assertEquals(updatedGameState.getOpponentPlayerId(),playerIdB);
    }

    @Test(expected = GameIllegalMoveException.class)
    public void sowFromSelfSmallPitAndForceNextTurnTest() {
        gameState.sow("SomeThingA", "A2", gameBoardFeatures)
        .sow("SomeThingA", "A4", gameBoardFeatures);
    }

    @Test(expected = IllegalArgumentException.class)
    public void sowFromSelfSmallPitAndNextTurnTryPickingEmptyPit() {
        gameState.sow("SomeThingA", "A1", gameBoardFeatures)
                .sow("SomeThingA", "A1", gameBoardFeatures);
    }

}