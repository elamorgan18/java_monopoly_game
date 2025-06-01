package com.cm6123.monopoly.s3court;

import com.cm6123.monopoly.game.CourtManager;
import com.cm6123.monopoly.players.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourtManagerTests {

    private CourtManager courtManager;
    private Player testPlayer;

    @BeforeEach
    public void setUp() {
        courtManager = new CourtManager();
        // will override the last roll value in test
        testPlayer = new Player(8, 3, BigDecimal.valueOf(100.00), 2);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
    public void testProcessSpaceForPlayer_withVariousRolls(int roll) {
        testPlayer.setLastRoll(roll);

        courtManager.processSpaceForPlayer(testPlayer);

        BigDecimal expectedBonus = BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedBalance = BigDecimal.valueOf(100.00).add(expectedBonus).setScale(2, RoundingMode.HALF_UP);

        assertEquals(expectedBalance, testPlayer.getBalance(),
                "Balance should be increased by £" + expectedBonus + " for a roll of " + roll);
    }
}
