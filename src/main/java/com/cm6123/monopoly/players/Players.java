package com.cm6123.monopoly.players;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that stores the players.
 */
public class Players {

    /**
     * A list containing all the players in the game.
     * This list is final and initialised once to ensure immutability.
     */
    private final List<Player> playerList;

    /**
     * A method that constructs a Players object and initializes the player list with the specified number of players.
     * Each player is created with an initial position of 1 and a unique player ID.
     *
     * @param numberOfPlayers The number of players to be added to the game.
     */
    public Players(final int numberOfPlayers) {
        playerList = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            playerList.add(new Player(1, i, BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_UP), 1));
        }
    }

    /**
     * A method that eturns the list of players in the game.
     *
     * @return A list containing all the players.
     */
    public List<Player> getPlayerList() {
        return playerList;
    }

    /**
     * A method that prints the details of all players in the game, including
     * their player ID, last roll, position and balance.
     */
    public void printPlayers() {
        System.out.println("---------------------------------------------------------------");
        for (Player player : playerList) {
            System.out.println("Player " + player.getPlayerId()
                    + " rolled a " + player.getLastRoll()
                    + ", has position: " + player.getPosition()
                     + " and balance: " + player.getBalance()
                    + " and owned properties are: " + player.getOwnedProperties());
        }
        System.out.println("---------------------------------------------------------------");
    }
}
