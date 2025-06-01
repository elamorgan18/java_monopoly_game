package com.cm6123.monopoly.s6bankruptcy;

import com.cm6123.monopoly.game.CongestionManager;
import com.cm6123.monopoly.game.SpaceManager;
import com.cm6123.monopoly.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class CongestionManagerTests {

    private CongestionManager congestionManager;
    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        congestionManager = new CongestionManager();
        testPlayer = new Player(8, 3, BigDecimal.valueOf(100.00), 2); // optional: you override this in the test
    }

    @Test
    public void testProcessSpaceForPlayer_PlayerCannotAffordAndGoesBankrupt() {
        // given player with very low balance, less than the base £10 charge
        testPlayer = new Player(8, 3, BigDecimal.valueOf(5.00), 2);
        // even 2 × £10 = £20 is too much for £5 balance
        testPlayer.setLastRoll(2);

        // when
        congestionManager.processSpaceForPlayer(testPlayer);

        // then
        assertTrue(testPlayer.isBankrupt(), "Player should be marked as bankrupt if they cannot afford the charge.");
        assertEquals(BigDecimal.valueOf(5.00).setScale(2), testPlayer.getBalance(), "Balance should remain unchanged.");
    }

    @Test
    public void testProcessSpaceForPlayer_PlayerCanAffordCharge() {
        // given -  a player with sufficient balance
        testPlayer = new Player(8, 3, BigDecimal.valueOf(50.00), 2);
        // charge will be 3 × £10 = £30
        testPlayer.setLastRoll(3);

        // when
        congestionManager.processSpaceForPlayer(testPlayer);

        // then - balance should be reduced, player not bankrupt
        BigDecimal expectedBalance = BigDecimal.valueOf(50.00)
                .subtract(BigDecimal.valueOf(30.00)).setScale(2, RoundingMode.HALF_UP);

        assertFalse(testPlayer.isBankrupt(), "Player should not be marked as bankrupt.");
        assertEquals(expectedBalance, testPlayer.getBalance(), "Balance should be reduced correctly.");
    }

}
