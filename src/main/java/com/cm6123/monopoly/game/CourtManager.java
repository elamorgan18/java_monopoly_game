package com.cm6123.monopoly.game;

import com.cm6123.monopoly.players.Player;

import java.math.BigDecimal;

public class CourtManager {
    /**
     * An instance for the bonus that is applied.
     */
    private static final BigDecimal BONUS = BigDecimal.valueOf(100);
    /**
     * Amethod that processes the space for the player and applies a bonus to the
     * player who lands on that space.
     * @param player the current player
     */
    public void processSpaceForPlayer(final Player player) {
        // court has a conus of £100, but iss two turns.
        player.setBalance(player.getBalance().add(BONUS));
        // rounds to miss
        player.setRoundsToMiss(2);
        System.out.println("Player " + player.getPlayerId()
                + " has gone to Court. £100 bonus added, but miss 2 turns!");
    }
}
