package org.example.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PitTest {

    private static Pit pit;

    @BeforeAll
    public static void setUp() {
        pit = new Pit("A1", 6);
    }

    @Test
    public void testPutForZeroValue() {
        int currentStoneCount = pit.getCurrentStoneCount();
        assertEquals(currentStoneCount, pit.put(0));
    }

    @Test
    public void testPutForNegativeValue() {
        assertThrows(IllegalArgumentException.class, () -> pit.put(-2), "Incoming stone count cannot be negative");
    }

    @Test
    public void testPutForPositiveValue() {
        int currentStoneCount = pit.getCurrentStoneCount();
        int increment = 2;
        assertEquals(currentStoneCount + increment, pit.put(increment));
    }

    @Test
    public void testPitIfAlreadyEmpty() {
        Pit newPit = new Pit("B2", 6);
        newPit.emptyPit();
        assertThrows(IllegalArgumentException.class, newPit::emptyPit, "Pit is already empty, pick another pit");
    }

    @Test
    public void testEmptyPit() {
        int currentStoneCount = pit.getCurrentStoneCount();
        assertEquals(currentStoneCount, pit.emptyPit());
    }


}