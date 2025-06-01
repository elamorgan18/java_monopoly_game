package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.players.Players;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class to test the Player class.
 */
public class PlayersTests {

    // Test to see if all players are set to correct id and initial position (1).
    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10})
    void testPlayerInitialisation(int numberOfPlayers) {
        Players players = new Players(numberOfPlayers);

        // Check that each player has a unique ID and position is initialised to 1.
        for (int i = 0; i < numberOfPlayers; i++) {
            assertEquals(i + 1, players.getPlayerList().get(i).getPlayerId(),
                    "Player " + (i + 1) + " should have ID " + (i + 1));

            assertEquals(1, players.getPlayerList().get(i).getPosition(),
                    "Player " + (i + 1) + "'s position should be 1");

            assertEquals(BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_UP), players.getPlayerList().get(i).getBalance(),
                    "Player " + (i + 1) + "'s balance should be 1000.00");
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10})
    void testPrintPlayers(int numberOfPlayers) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out));

        Players players = new Players(numberOfPlayers);
        players.printPlayers();

        String output = out.toString();

        for (int i = 1; i <= numberOfPlayers; i++) {
            assertTrue(output.contains("Player " + i + " rolled a 1, has position: 1 and balance: 1000"));

        }

    }
}

