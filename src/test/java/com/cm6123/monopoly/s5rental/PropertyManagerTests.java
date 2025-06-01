package com.cm6123.monopoly.s5rental;

import com.cm6123.monopoly.game.PropertyManager;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.spaces.Property;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyManagerTests {

    @Test
    void testProcessSpaceForPlayerPaysRentToOwner() {
        // make an owner
        Player owner = new Player(5, 1, BigDecimal.valueOf(1000.00), 0);
        // make a property at position 5
        Property property = new Property(BigDecimal.valueOf(400.00), 5, true, "Park Lane");
        // make that property belong to owner and add it to their properties list
        property.setOwner(owner);
        owner.getOwnedProperties().add(property);

        // creating another player
        Player player = new Player(5, 2, BigDecimal.valueOf(500.00), 0);

        // make list of players
        Players players = new Players(0);
        players.getPlayerList().add(owner);
        players.getPlayerList().add(player);

        PropertyManager propertyManager = new PropertyManager();
        propertyManager.processSpaceForPlayer(player, property, players);

        BigDecimal expectedRent = property.getCharge().multiply(BigDecimal.valueOf(0.1));

        assertEquals(BigDecimal.valueOf(500.00).subtract(expectedRent).setScale(2, RoundingMode.HALF_UP), player.getBalance(), "Player's balance should decrease by rent amount.");
        assertEquals(BigDecimal.valueOf(1000.00).add(expectedRent).setScale(2, RoundingMode.HALF_UP), owner.getBalance(), "Owner's balance should increase by rent amount.");
    }

}
