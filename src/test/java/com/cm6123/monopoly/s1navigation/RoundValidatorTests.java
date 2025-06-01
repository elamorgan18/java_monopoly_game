package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.board.BoardValidator;
import com.cm6123.monopoly.rounds.RoundValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class to test the RoundValidator class.
 */
public class RoundValidatorTests {

    //    Tests for validateNumberOfRounds method.

    private final RoundValidator roundValidator = new RoundValidator();

    // asserting valid inputs.
    private void assertValid(String input) {
        assertTrue(roundValidator.validate(input));
    }

    // asserting invalid inputs.
    private void assertInvalid(String input) {
        assertFalse(roundValidator.validate(input));
    }

    @Test
    public void testValidInputs() {
        assertValid("1");
        assertValid("20");
        assertValid("10");
        assertValid("15 "); // space after integer
        assertValid(" 15"); // space before integer
    }

    @Test
    public void testInvalidInputs() {
        assertInvalid("hello");
        assertInvalid("11.5");
        assertInvalid(" ");
        assertInvalid("0");
        assertInvalid("21");
        assertInvalid("");
        assertInvalid(null);
    }
}
