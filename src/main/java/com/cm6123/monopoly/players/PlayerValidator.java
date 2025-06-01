package com.cm6123.monopoly.players;

import com.cm6123.monopoly.validator.InputValidator;

/**
 * A class that validates the number of players inputted.
 */
public class PlayerValidator implements InputValidator {

    /**
     * A method to check the number of players is an integer and is between 2 and 10.
     *
     * @param numberOfPlayers - The number of players the user wants to play with.
     * @return a boolean to see if number is valid
     */
    public boolean validate(final String numberOfPlayers) {

        boolean isValid = false;
        int playerCount = 0;

        // Converting the input to an int.
        try {
            if (numberOfPlayers != null) {
                playerCount = Integer.parseInt(numberOfPlayers.trim());
                isValid = true;
            }
        } catch (NumberFormatException e) {
            System.out.println("Number of players must be an integer. Please try again.");
            isValid = false;
        }

        // Checking if the input is between 2 and 10.
        if (isValid) {
            if (playerCount < 2 || playerCount > 10) {
                isValid = false;
                System.out.println("Number of players must be between 2 and 10. Please try again.");
            }
        }
        return isValid;

    }

}
