package com.cm6123.monopoly.game;

import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.spaces.Property;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class PropertyManager {
    /**
     * A class for property space to be managed by the player.
     *
     * @param player the current player
     * @param property the porperty they landed on.
     * @param players the players
     */
    public void processSpaceForPlayer(final Player player, final Property property, final Players players) {
        BigDecimal rent = BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP);
        if (!property.isOwned()) {
            // check to see if player has enough money to buy property.
            boolean validInput = false;
            if (player.getBalance().compareTo(property.getCharge()) > 0) {
                while (!validInput) {

                    // ask if they want to buy it
                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Player " + player.getPlayerId() + " has landed on " + property.getName()
                            + ". Would you like to buy this property for £" + property.getCharge() + "? (Y/N)");
                    String buyOrNot = scanner.nextLine().trim().toUpperCase();

                    if (buyOrNot.equals("Y")) {
                        // if they want to buy, we set owned to true, owned by the player,
                        // charge the player the cost of the property.
                        property.setOwned(true);
                        property.setOwner(player);
                        player.getOwnedProperties().add(property);
                        player.setBalance(player.getBalance().subtract(property.getCharge()));
                        validInput = true;

                    } else if (buyOrNot.equals("N")) {
                        // we do not need ot do anything here.
                        System.out.println("Ok");
                        validInput = true;
                    } else {
                        System.out.println("Invalid input. Please try again.");
                        validInput = false;
                    }
                }
            } else {
                System.out.println("Player " + player.getPlayerId() + " doesn't have enough money to buy this property.");
            }
            // else the property is owned by someone so the player must pay rent
        } else {
            rent = (property.getCharge().multiply(BigDecimal.valueOf(0.1)).setScale(2, RoundingMode.HALF_UP));


            //  player does not have enough money to pay the rent
            if (player.getBalance().compareTo(rent) < 0) {

                System.out.println("Player " + player.getPlayerId() + " landed on " + property.getName() + " and cannot afford rent of £" + rent);

                // player must sell properties to cover the debt
                player.sellPropertiesToCoverDebt(rent);

                // check again if player has enough money after selling
                if (player.getBalance().compareTo(rent) < 0) {
                    BigDecimal remainingBalance = player.getBalance();
                    int propertyOwnerId = property.getOwner().getPlayerId();
                    Player owningPlayer = players.getPlayerList().get(propertyOwnerId - 1);
                    owningPlayer.setBalance(owningPlayer.getBalance().add(remainingBalance));

                    // set player to bankrupt!
                    player.setBankrupt(true);
                    System.out.println("Player " + player.getPlayerId() + " is bankrupt! Out of the game");

                } else {
                    // pay rent after selling their cheapest property for 50% of the charge.
                    player.setBalance(player.getBalance().subtract(rent));
                    int propertyOwnerId = property.getOwner().getPlayerId();
                    Player owningPlayer = players.getPlayerList().get(propertyOwnerId - 1);
                    owningPlayer.setBalance(owningPlayer.getBalance().add(rent));
                    System.out.println("Rent of £" + rent + " paid to Player " + propertyOwnerId + " after selling properties.");
                }

                // player has enough money to pay rent - no need to sell
            } else {
                player.setBalance(player.getBalance().subtract(rent));
                int propertyOwnerId = property.getOwner().getPlayerId();
                Player owningPlayer = players.getPlayerList().get(propertyOwnerId - 1);
                owningPlayer.setBalance(owningPlayer.getBalance().add(rent));
                System.out.println("Player " + property.getOwner().getPlayerId() + " ownes "
                        + property.getName() + ". Player " + player.getPlayerId() + " must pay rent of £" + rent);
            }
        }
    }
}
