package com.cm6123.monopoly.s2congestion;

import com.cm6123.monopoly.players.Player;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlayerTests {

    @Test
    void testSetAndGetRoundsToMiss() {
        Player player = new Player(1, 1, BigDecimal.valueOf(1500.00), 6); // example values
        player.setRoundsToMiss(3);
        assertEquals(3, player.getRoundsToMiss(), "Rounds to miss should be set to 3");
    }

    @Test
    void testDecrementRoundsToMiss() {
        Player player = new Player(1, 2, BigDecimal.valueOf(1500.00), 4);
        player.setRoundsToMiss(2);

        player.decrementRoundsToMiss();
        assertEquals(1, player.getRoundsToMiss(), "Rounds to miss should decrement to 1");

        player.decrementRoundsToMiss();
        assertEquals(0, player.getRoundsToMiss(), "Rounds to miss should decrement to 0");

        player.decrementRoundsToMiss();
        assertEquals(0, player.getRoundsToMiss(), "Rounds to miss should not go below 0");
    }

    @Test
    void testRoundsToMissInitialValue() {
        Player player = new Player(5, 3, BigDecimal.valueOf(1500.00), 2);
        assertEquals(0, player.getRoundsToMiss(), "Initial rounds to miss should be 0");
    }
}
