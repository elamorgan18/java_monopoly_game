package com.cm6123.monopoly.game;

import com.cm6123.monopoly.players.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CongestionManager {

    /**
     * An instance for the charge that is applied.
     */
    private static final BigDecimal CHARGE = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);

    /**
     * A method that processes the space for the player and applies a charge to the
     * player who lands on that space.
     * If the player cannot afford the charge, then the player is bankrupt!
     * @param player the current player
     */
    public void processSpaceForPlayer(final Player player) {
        // congestion is fine of £10 x last roll value
        // checking if they can afford the charge.
        BigDecimal playerBalance = player.getBalance();
        if (playerBalance.compareTo(CHARGE) < 0) {
            player.setBankrupt(true);
            System.out.println("Player Cannot Afford Congestion Charge! Bankrupt");
        } else {
            BigDecimal charge = CHARGE.multiply(BigDecimal.valueOf(player.getLastRoll()));
            player.setBalance(player.getBalance().subtract(charge).setScale(2, RoundingMode.HALF_UP));
            System.out.println("Congestion fine has been charged to Player " + player.getPlayerId()
                    + ". Charge of £" + charge);
        }
    }

}
