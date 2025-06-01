package com.cm6123.monopoly.s6bankruptcy;

import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.spaces.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTests {
    private Player player;

    @BeforeEach
    void setUp() {
        // Player starts at position 0, with ID 1, balance £20.00, last roll 0
        player = new Player(0, 1, new BigDecimal("20.00"), 0);

        // Add properties to the player's owned list
        Property prop1 = new Property(new BigDecimal("100.00"), 1, false, "Park Lane");
        Property prop2 = new Property(new BigDecimal("50.00"), 2, false, "Old Kent Road");
        Property prop3 = new Property(new BigDecimal("200.00"), 3, false, "Mayfair");

        player.getOwnedProperties().add(prop1);
        player.getOwnedProperties().add(prop2);
        player.getOwnedProperties().add(prop3);
    }

    @Test
    void testPlayerHasEnoughBalanceAlready() {
        player.setBalance(new BigDecimal("100.00"));
        BigDecimal debt = new BigDecimal("50.00");

        player.sellPropertiesToCoverDebt(debt);

        // balance is already enough – no properties should be sold
        assertEquals(3, player.getOwnedProperties().size());
        assertEquals(new BigDecimal("100.00").setScale(2, RoundingMode.HALF_UP), player.getBalance());
    }

    @Test
    void testCheapestPropertyIsSoldFirst() {
        BigDecimal debt = new BigDecimal("30.00");
        player.sellPropertiesToCoverDebt(debt);

        // only one property should be sold (Old Kent Road for 25)
        assertEquals(2, player.getOwnedProperties().size());
        assertFalse(player.getOwnedProperties().stream().anyMatch(p -> p.getName().equals("Old Kent Road")));
    }
    @Test
    void testSellPropertiesToCoverDebtSellingTwoProperties() {
        BigDecimal debt = new BigDecimal("60.00");
        player.sellPropertiesToCoverDebt(debt);


        assertTrue(player.getBalance().compareTo(debt) >= 0);
        assertEquals(1, player.getOwnedProperties().size());
        assertFalse(player.getOwnedProperties().stream().anyMatch(p -> p.getName().equals("Old Kent Road")));
        assertFalse(player.getOwnedProperties().stream().anyMatch(p -> p.getName().equals("Park Lane")));
    }

    @Test
    void testSellPropertiesStillNotEnoughAfterSellingThreeProperties() {
        BigDecimal highDebt = new BigDecimal("300.00");
        player.sellPropertiesToCoverDebt(highDebt);

        // 25 (Old Kent) + 50 (Park Lane) + 100 (Mayfair) = 175 + 20 = 195
        // still not enough money

        assertTrue(player.getBalance().compareTo(highDebt) < 0);
        assertEquals(0, player.getOwnedProperties().size());
    }

    @Test
    void testPlayerHasNoProperties() {
        player.getOwnedProperties().clear();
        player.setBalance(new BigDecimal("10.00"));
        BigDecimal debt = new BigDecimal("50.00");

        player.sellPropertiesToCoverDebt(debt);

        // no change to properties, balance still too low
        assertEquals(0, player.getOwnedProperties().size());
        assertEquals(new BigDecimal("10.00").setScale(2, RoundingMode.HALF_UP), player.getBalance());
    }
}
