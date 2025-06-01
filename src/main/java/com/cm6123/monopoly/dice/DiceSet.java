package com.cm6123.monopoly.dice;

public class DiceSet {
    /**
     * The first dice instance.
     */
    private final Dice die1;
    /**
     * The second dice instance.
     */
    private final Dice die2;

    /**
     * Method that constructs a set with exactly two dice, each having the given number of faces.
     *
     * @param numberOfFaces the number of faces on each die.
     */
    public DiceSet(final Integer numberOfFaces) {
        this.die1 = new Dice(numberOfFaces);
        this.die2 = new Dice(numberOfFaces);
    }

    /**
     * Method that constructs each die.
     *
     * @param dice1 the die 1 value.
     * @param dice2 the die 2 value.
     */
    public DiceSet(final Dice dice1, final Dice dice2) {
        this.die1 = dice1;
        this.die2 = dice2;
    }
    /**
     * A method that rolls both dice and adds their results together.
     *
     * @return the sum of both dice rolls.
     */
    public int addTogether() {
        int totalRoll =  die1.roll() + die2.roll();
        return totalRoll;
    }

    /**
     * A method that gets the die 1 value.
     *
     * @return the die 1 value.
     */
    public Dice getDie1() {
        return die1;
    }
    /**
     * A method that gets the die 2 value.
     *
     * @return the die 2 value.
     */
    public Dice getDie2() {
        return die2;
    }

}
