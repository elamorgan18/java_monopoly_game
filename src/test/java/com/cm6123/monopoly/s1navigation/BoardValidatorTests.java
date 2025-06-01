package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.board.Board ;
import com.cm6123.monopoly.board.BoardValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A class to test the BoardValidator class.
 */
public class BoardValidatorTests {

    //    Tests for validateBoardSize method.

    private final BoardValidator boardValidator = new BoardValidator();

    // asserting valid inputs.
    private void assertValid(String input) {
        assertTrue(boardValidator.validate(input));
    }

    // asserting invalid inputs.
    private void assertInvalid(String input) {
        assertFalse(boardValidator.validate(input));
    }

    @Test
    public void testValidInputs() {
        assertValid("10");
        assertValid("50");
        assertValid("25");
        assertValid("34 "); // space after integer
        assertValid(" 34"); // space before integer
    }

    @Test
    public void testInvalidInputs() {
        assertInvalid("hello");
        assertInvalid("11.5");
        assertInvalid(" ");
        assertInvalid("9");
        assertInvalid("51");
        assertInvalid("");
        assertInvalid(null);
    }
}
