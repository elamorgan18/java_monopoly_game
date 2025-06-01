package com.cm6123.monopoly.s1navigation;

import static org.junit.jupiter.api.Assertions.*;

import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import org.junit.jupiter.api.Test;

public class DiceSetTests {

    @Test
    public void testValidDiceRange() {
        DiceSet diceSet = new DiceSet(6);

        // test sum to see if in range of 2 and 12, multiple times for coverage.
        for (int i = 0; i < 1000; i++) {
            int totalRoll = diceSet.addTogether();
            assertTrue(totalRoll >= 2 && totalRoll <= 12,
                    "Invalid roll result: " + totalRoll);
        }
    }

    @Test
    public void testConstructorWithFaceCount() {
        DiceSet diceSet = new DiceSet(6);
        assertNotNull(diceSet.getDie1(), "Die 1 shouldn't be null");
        assertNotNull(diceSet.getDie2(), "Die 2 shouldn't be null");
    }

    @Test
    public void testConstructorWithTwoDice() {
        Dice die1 = new Dice(6);
        Dice die2 = new Dice(6);
        DiceSet diceSet = new DiceSet(die1, die2);

        assertEquals(die1, diceSet.getDie1(), "Die 1 should match constructor.");
        assertEquals(die2, diceSet.getDie2(), "Die 2 should match constructor.");
    }

}
