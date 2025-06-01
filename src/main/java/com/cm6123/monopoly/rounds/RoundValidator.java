package com.cm6123.monopoly.rounds;

import com.cm6123.monopoly.validator.InputValidator;

/**
 * A class that validates the number of rounds input.
 */
public class RoundValidator implements InputValidator {
    /**
     * A method to check that the number of rounds is an integer and is between 1 and 20.
     *
     * @param numberOfRounds - The number of rounds the user wants to play for.
     * @return a boolean to see if number is valid.
     */
    public boolean validate(final String numberOfRounds) {
        boolean isValid = false;
        int numRounds = 0;

        // Converting the input to an int.
        try {
            if (numberOfRounds != null) {
                numRounds = Integer.parseInt(numberOfRounds.trim());
                isValid = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Number of rounds must be an integer. Please try again.");
            isValid = false;
        }

        // Checking if the input is between 1 and 20.
        if (isValid) {
            if (numRounds > 20 || numRounds < 1) {
                isValid = false;
                System.out.println("Number of rounds must be between 1 and 20. Please try again.");
            }
        }
        return isValid;
    }

}
