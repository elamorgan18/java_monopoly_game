package com.cm6123.monopoly.board;

import com.cm6123.monopoly.validator.InputValidator;


/**
 * A class that validates the board size input.
 */
public class BoardValidator implements InputValidator {

    /**
     * A method to check that the board size is an integer and is between 10 and 50.
     *
     * @param sizeOfBoard - The size of the board that the user wants to play with.
     * @return a boolean to see if number is valid.
     */
    public boolean validate(final String sizeOfBoard) {
        boolean isValid = false;
        int boardSize = 0;

        // Converting the input to an int.

        try {
            if (sizeOfBoard != null) {
                boardSize = Integer.parseInt(sizeOfBoard.trim());
                isValid = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Board size must be an integer. Please try again.");
            isValid = false;
        }

        // Checking if the input is between 10 and 50.
        if (isValid) {
            if (boardSize < 10 || boardSize > 50) {
                isValid = false;
                System.out.println("Size of board must be between 10 and 50. Please try again.");
            }
        }
        return isValid;
    }

}
