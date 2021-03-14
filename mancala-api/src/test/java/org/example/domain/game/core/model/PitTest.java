package org.example.domain.game.core.model;


import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PitTest {

    private static SmallPit pit;

    @BeforeClass
    public static void setUp() {
        pit = new SmallPit("A1", 6);
    }

    @Test
    public void testPutForZeroValue() {
        int currentStoneCount = pit.getCurrentStoneCount();
        assertEquals(currentStoneCount, pit.sow(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutForNegativeValue() {
        pit.sow(-2);
    }

    @Test
    public void testPutForPositiveValue() {
        int currentStoneCount = pit.getCurrentStoneCount();
        int increment = 2;
        assertEquals(currentStoneCount + increment, pit.sow(increment));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPitIfAlreadyEmpty() {
        SmallPit newPit = new SmallPit("B2", 6);
        newPit.pick();
        newPit.pick();

    }

    @Test
    public void testEmptyPit() {
        int currentStoneCount = pit.getCurrentStoneCount();
        assertEquals(currentStoneCount, pit.pick());
    }


}