package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.players.PlayerValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class to test the PlayerValidator class.
 */
public class PlayerValidatorTests {

//    Tests for validateNumberOfPlayers method.

    private final PlayerValidator playerValidator = new PlayerValidator();

    // asserting valid inputs.
    private void assertValid(String input) {
        assertTrue(playerValidator.validate(input));
    }

    // asserting invalid inputs.
    private void assertInvalid(String input) {
        assertFalse(playerValidator.validate(input));
    }

    @Test
    public void testValidInputs() {
        assertValid("2");
        assertValid("6");
        assertValid("10");
        assertValid("4 "); // space after integer
        assertValid(" 4"); // space before integer
    }

    @Test
    public void testInvalidInputs() {
        assertInvalid("hello");
        assertInvalid("4.5");
        assertInvalid(" ");
        assertInvalid("1");
        assertInvalid("11");
        assertInvalid("");
        assertInvalid(null);
    }

}
