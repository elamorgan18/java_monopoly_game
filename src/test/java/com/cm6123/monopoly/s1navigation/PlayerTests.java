package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.players.Player;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTests {

    @Test
    void testSetAndGetPosition() {
        Player player = new Player(1, 1, BigDecimal.valueOf(1000.00), 0);
        player.setPosition(5);
        assertEquals(5, player.getPosition(), "Player position should be updated to 5");
    }

    @Test
    void testSetAndGetBalance() {
        Player player = new Player(1, 1, BigDecimal.valueOf(1000.00), 0);
        player.setBalance(BigDecimal.valueOf(1500.00));
        assertEquals(BigDecimal.valueOf(1500.00).setScale(2, RoundingMode.HALF_UP), player.getBalance(), "Player balance should be updated to 1500.00");
    }

    @Test
    void testSetAndGetLastRoll() {
        Player player = new Player(1, 1, BigDecimal.valueOf(1000.00), 0);
        player.setLastRoll(6);
        assertEquals(6, player.getLastRoll(), "Player last roll should be updated to 6");
    }

    @Test
    void testGetPlayerId() {
        Player player = new Player(1, 42, BigDecimal.valueOf(1000.00), 0);
        assertEquals(42, player.getPlayerId(), "Player ID should be 42");
    }

    @Test
    void testSetAndHasPassedPosition1() {
        Player player = new Player(1, 1, BigDecimal.valueOf(1000.00), 0);
        assertFalse(player.hasPassedPosition1(), "Player should not have passed position 1 initially");

        player.setPassedPosition1(true);
        assertTrue(player.hasPassedPosition1(), "Player should have passed position 1 after setting to true");
    }


}
