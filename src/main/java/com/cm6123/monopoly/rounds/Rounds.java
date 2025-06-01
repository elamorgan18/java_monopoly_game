package com.cm6123.monopoly.rounds;

/**
 * A class that stores the number rounds in a game.
 */
public class Rounds {

    /**
     * An instance variable to store the number of rounds.
     */
    private final int rounds;

    /**
     * A constructor for the number of rounds.
     * @param numberOfRounds the number of rounds.
     */
    public Rounds(final int numberOfRounds) {
        this.rounds = numberOfRounds;
    }

    /**
     * A method to retrieve the number of rounds.
     * @return an integer of the number of rounds.
     */
    public int getNumberOfRounds() {
        return rounds;
    }

}
