package com.cm6123.monopoly.s4purchase;

import com.cm6123.monopoly.game.PropertyManager;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.spaces.Property;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;


public class PropertyManagerTests {

    @Test
    void testProcessSpaceForPlayerPlayerCannotAffordProperty() {

        Player player = new Player(5, 1, BigDecimal.valueOf(50.00), 0);

        Property property = new Property(BigDecimal.valueOf(400), 5, false, "Park Lane");

        Players players = new Players(1);

        PropertyManager propertyManager = new PropertyManager();
        propertyManager.processSpaceForPlayer(player, property, players);

        assertFalse(property.isOwned(), "Property should not be owned if player can't afford it.");
        assertEquals(BigDecimal.valueOf(50.00).setScale(2, RoundingMode.HALF_UP), player.getBalance(), "Player's balance should stay the same if they can't buy.");
        assertTrue(player.getOwnedProperties().isEmpty(), "Player should not gain a property they couldn't afford.");
    }

    @Test
    void testProcessSpaceForPlayerDoesntBuyProperty() {

        Player player = new Player(5, 1, BigDecimal.valueOf(500.00), 0);

        Property property = new Property(BigDecimal.valueOf(400.00), 5, false, "Park Lane");
        Players players = new Players(1);

        String mockInput = "N";
        System.setIn(new ByteArrayInputStream(mockInput.getBytes()));

        PropertyManager propertyManager = new PropertyManager();
        propertyManager.processSpaceForPlayer(player, property, players);


        assertFalse(property.isOwned(), "Property should not be owned.");
        assertEquals(BigDecimal.valueOf(500.00).setScale(2, RoundingMode.HALF_UP), player.getBalance(), "Player's balance should stay the same since they didn't buy it.");
        assertTrue(player.getOwnedProperties().isEmpty(), "Player should not gain a property.");
    }

    @Test
    void testProcessSpaceForPlayerDoesBuyProperty() {

        Player player = new Player(5, 1, BigDecimal.valueOf(500.00), 0);

        Property property = new Property(BigDecimal.valueOf(400.00), 5, false, "Park Lane");
        Players players = new Players(1);

        String mockInput = "Y";
        System.setIn(new ByteArrayInputStream(mockInput.getBytes()));

        PropertyManager propertyManager = new PropertyManager();
        propertyManager.processSpaceForPlayer(player, property, players);


        assertTrue(property.isOwned(), "Property should be owned.");
        assertEquals(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP), player.getBalance(), "Player's balance should decrease to 100.00");
        assertFalse(player.getOwnedProperties().isEmpty(), "Player should gain one property.");
    }

}
