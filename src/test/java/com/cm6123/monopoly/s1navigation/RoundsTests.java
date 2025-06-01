package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.rounds.Rounds;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RoundsTests {

    // Test the number of rounds constructor.
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 4, 10, 15, 20, 25, 47})
    public void shouldHaveValidRounds(int inputRounds) {
        Rounds rounds = new Rounds(inputRounds);
        assertEquals(inputRounds, rounds.getNumberOfRounds());
    }
}
