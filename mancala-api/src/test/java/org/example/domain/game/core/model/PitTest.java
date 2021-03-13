package org.example.domain.game.core.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PitTest {

    private static SmallPit pit;

    @BeforeAll
    public static void setUp() {
        pit = new SmallPit("A1", 6);
    }

    @Test
    public void testPutForZeroValue() {
        int currentStoneCount = pit.getCurrentStoneCount();
        assertEquals(currentStoneCount, pit.sow(0));
    }

    @Test
    public void testPutForNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> pit.sow(-2), "Incoming stone count cannot be negative");
    }

    @Test
    public void testPutForPositiveValue() {
        int currentStoneCount = pit.getCurrentStoneCount();
        int increment = 2;
        assertEquals(currentStoneCount + increment, pit.sow(increment));
    }

    @Test
    public void testPitIfAlreadyEmpty() {
        SmallPit newPit = new SmallPit("B2", 6);
        newPit.pick();
        assertThrows(IllegalArgumentException.class, newPit::pick, "Pit is already empty, pick another pit");
    }

    @Test
    public void testEmptyPit() {
        int currentStoneCount = pit.getCurrentStoneCount();
        assertEquals(currentStoneCount, pit.pick());
    }


}