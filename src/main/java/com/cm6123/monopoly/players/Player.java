package com.cm6123.monopoly.players;

import com.cm6123.monopoly.spaces.Property;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that creates a single player.
 */

public class Player {
    /**
     * The player position.
     */
    private Integer position;
    /**
     * The player id.
     */
    private final Integer playerId;
    /**
     * The player balance.
     */
    private BigDecimal balance;

    /**
     * The players last roll value.
     */
    private Integer lastRoll;

    /**
     * The boolean which tells us if player has passed position 1.
     */
    private boolean passedPosition1 = false;

    /**
     * The number of rounds to miss.
     */
    private Integer roundsToMiss = 0;
    /**
     * List of owned properties the player has.
     */
    private final List<Property> ownedProperties;

    /**
     * Is the player bankrupt?
     */
    private boolean isBankrupt = false;


    /**
     * Constructs a Player object with the specified position and player ID.
     *
     * @param playerPos The position of the player.
     * @param playerIdentity The unique ID of the player.
     * @param theBalance The balance of the player.
     * @param theLastRoll The last roll number
     */
    public Player(final Integer playerPos, final Integer playerIdentity, final BigDecimal theBalance, final Integer theLastRoll) {
        this.position = playerPos;
        this.playerId = playerIdentity;
        this.balance = theBalance;
        this.lastRoll = theLastRoll;
        this.ownedProperties = new ArrayList<>();
    }
    /**
     * A method that returns the player id.
     *
     * @return the player id.
     */
    public Integer getPlayerId() {
        return playerId;
    }
    /**
     * A method that states if the player is bankrupt.
     *
     * @return boolean of true or false.
     */
    public boolean isBankrupt() {
        return isBankrupt;
    }

    /**
     * A method that sets the players bankruptcy boolean.
     *
     * @param theBankrupt new player position.
     */
    public void setBankrupt(final boolean theBankrupt) {
        isBankrupt = theBankrupt;
    }
    /**
     * A method that returns the player position.
     *
     * @return the player position.
     */
    public Integer getPosition() {
        return position;
    }

    /**
     * A method that sets a new position for a player.
     *
     * @param newPosition the new player position.
     */
    public void setPosition(final int newPosition) {
        this.position = newPosition;
    }

    /**
     * A method that returns the player balance.
     *
     * @return the player balance.
     */
    public BigDecimal getBalance() {
        return balance.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * A method that sets a new balance for the player.
     *
     * @param newBalance the new player balance.
     */
    public void setBalance(final BigDecimal newBalance) {
        this.balance = newBalance;
    }

    /**
     * A method that sets the last roll of the player.
     *
     * @param newLastRoll the last roll value.
     */
    public void setLastRoll(final int newLastRoll) {
        this.lastRoll = newLastRoll;
    }

    /**
     * A method that returns the players last roll value.
     *
     * @return the players last roll value.
     */
    public Integer getLastRoll() {
        return lastRoll;
    }

    /**
     * A method that returns the result of if player has passed position 1.
     *
     * @return if the player has passed position 1.
     */
    public boolean hasPassedPosition1() {
        return passedPosition1;
    }

    /**
     * A method that sets if the player has passed position 1.
     *
     * @param thePassedPosition1 players passed position 1 or not.
     */
    public void setPassedPosition1(final boolean thePassedPosition1) {
        this.passedPosition1 = thePassedPosition1;
    }

    /**
     * A method gets the rounds the player will miss - frozen.
     * @return roundsToMiss the rounds the miss.
     */
    public int getRoundsToMiss() {
        return roundsToMiss;
    }

    /**
     * A method that sets the rounds to miss to a number.
     * @param rounds the number of rounds to miss.
     */
    public void setRoundsToMiss(final int rounds) {
        this.roundsToMiss = rounds;
    }

    /**
     * A method that decreases the number of rounds to miss after a
     * round has been missed.
     */
    public void decrementRoundsToMiss() {
        if (roundsToMiss > 0) {
            roundsToMiss--;
        }
    }

    /**
     * A method that gets the owned properties.
     * @return owned properties.
     */
    public List<Property> getOwnedProperties() {
        return ownedProperties;
    }


    /**
     * A method that calculates the total rental income from all properties owned by the player.
     * @param theOwnedProperties
     * @return The total rental income.
     */
    public BigDecimal getTotalRentalIncome(final List<Property> theOwnedProperties) {
        BigDecimal totalRent = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);

        for (Property property : ownedProperties) {
            totalRent = totalRent.add(property.getCharge().multiply(BigDecimal.valueOf(0.1).setScale(2, RoundingMode.HALF_UP)));
        }

        return totalRent.setScale(2, RoundingMode.HALF_UP);
    }
    /**
     * A method that gets the cheapest property a player ownes and then
     * halves the price and removes the property from the players list of properties.
     * @param amount (this is the rent cost)
     */
    public void sellPropertiesToCoverDebt(final BigDecimal amount) {
        // while the balance is less than amount (rent)  AND owned properties list is not empty
        while (balance.compareTo(amount) < 0 && !ownedProperties.isEmpty()) {
            // find the property with the lowest purchase price
            Property cheapestProperty = ownedProperties.get(0);
            for (Property prop : ownedProperties) {
                if (prop.getCharge().compareTo(cheapestProperty.getCharge()) < 0) {
                    cheapestProperty = prop;
                }
            }

            // sell it for 50% of its purchase price
            BigDecimal salePrice = cheapestProperty.getCharge().multiply(BigDecimal.valueOf(0.5)).setScale(2, RoundingMode.HALF_UP);
            balance = balance.add(salePrice);
            // removing that property from their owned porperties list
            ownedProperties.remove(cheapestProperty);

            System.out.println("Player " + playerId + " sold their property " + cheapestProperty.getName()
                    + " for £" + salePrice + " (50% of £" + cheapestProperty.getCharge() + ")");

        }

        if (balance.compareTo(amount) < 0) {
            System.out.println("Player " + playerId + " still cannot afford the debt and may go bankrupt.");
        } else {
            System.out.println("Player " + playerId + " now has enough balance to cover the debt.");
        }
    }
}
