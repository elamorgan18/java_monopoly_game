package com.cm6123.monopoly.s6bankruptcy;

import com.cm6123.monopoly.game.PropertyManager;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.spaces.Property;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PropertyManagerTests {

    @Test
    public void testProcessSpaceForPlayerMethodPlayerSellsPropertyAndPaysRent() {

        // player without enough money to cover rent
        Player player1 = new Player(1, 1, new BigDecimal("3.00"), 2);
        //creating owner of property
        Player player2 = new Player(1, 2, new BigDecimal("100.00"), 2);

        // property with charge £50 (rent will be £5.00)
        Property property = new Property(new BigDecimal("50.00"), 3, true, "Station");
        property.setOwner(player2);

        // add a property to player1 to sell
        Property owned = new Property(new BigDecimal("20.00"), 5, true, "Cheap House");
        player1.getOwnedProperties().add(owned);

        // adding the players to the players list
        Players players = new Players(0);
        players.getPlayerList().add(player1);
        players.getPlayerList().add(player2);

        PropertyManager propertyManager = new PropertyManager();

        propertyManager.processSpaceForPlayer(player1, property, players);

        assertEquals(new BigDecimal("8.00").setScale(2, RoundingMode.HALF_UP), player1.getBalance());
        assertEquals(new BigDecimal("105.00").setScale(2, RoundingMode.HALF_UP), player2.getBalance());
        assertTrue(player1.getOwnedProperties().isEmpty());
    }

    @Test
    public void testProcessSpaceForPlayerMethodPlayerSellsTwoPropertiesToPayRent() {
        // player with only £1.00
        Player player1 = new Player(1, 1, new BigDecimal("1.00"), 2);
        // owner of the property
        Player player2 = new Player(1, 2, new BigDecimal("200.00"), 2);

        // property with charge £100, rent is £10.00
        Property property = new Property(new BigDecimal("100.00"), 4, true, "Park Place");
        property.setOwner(player2);

        // add two properties to player1
        // each gives £5.00 when sold (total = £10.00)
        Property prop1 = new Property(new BigDecimal("10.00"), 5, true, "Luxury Mansion");
        Property prop2 = new Property(new BigDecimal("10.00"), 6, true, "Grand Villa");
        player1.getOwnedProperties().add(prop1);
        player1.getOwnedProperties().add(prop2);

        Players players = new Players(0);
        players.getPlayerList().add(player1);
        players.getPlayerList().add(player2);

        PropertyManager propertyManager = new PropertyManager();
        propertyManager.processSpaceForPlayer(player1, property, players);

        // total funds before rent - 1 + 5 + 5 = £11.00
        // rent = £10.00 - remaining =  £1.00
        assertEquals(new BigDecimal("1.00").setScale(2, RoundingMode.HALF_UP), player1.getBalance());
        assertEquals(new BigDecimal("210.00").setScale(2, RoundingMode.HALF_UP), player2.getBalance());
        assertTrue(player1.getOwnedProperties().isEmpty());
    }

    @Test
    public void testProcessSpaceForPlayerMethodPlayerBankruptAfterSellingAllProperties() {
        // player with only £1.00, which is not enough to cover rent
        Player player1 = new Player(1, 1, new BigDecimal("1.00"), 2);
        // owner of the property
        Player player2 = new Player(1, 2, new BigDecimal("200.00"), 2);

        // property with charge 500, rent is 50.00
        Property property = new Property(new BigDecimal("500.00"), 4, true, "Park Place");
        property.setOwner(player2);

        // add three properties to player1, each worth £10.00 (total = £15.00)
        Property prop1 = new Property(new BigDecimal("10.00"), 5, true, "Shed");
        Property prop2 = new Property(new BigDecimal("10.00"), 7, true, "Garage");
        Property prop3 = new Property(new BigDecimal("10.00"), 8, true, "Dog House");
        player1.getOwnedProperties().add(prop1);
        player1.getOwnedProperties().add(prop2);
        player1.getOwnedProperties().add(prop3);

        Players players = new Players(0);
        players.getPlayerList().add(player1);
        players.getPlayerList().add(player2);

        PropertyManager propertyManager = new PropertyManager();

        propertyManager.processSpaceForPlayer(player1, property, players);

        // total funds before rent - 1 + 5 + 5 + 5 = £16.00
        // rent - £50.00 - remaining after rent deduction: £16.00 - £50.00 = -£34.00
        // player 1 sells all properties but still cannot afford the rent
        assertEquals(new BigDecimal("16.00").setScale(2, RoundingMode.HALF_UP), player1.getBalance());
        // player2 (owner) recieves player1 remaining balance since they are bankrupt
        assertEquals(new BigDecimal("216.00").setScale(2, RoundingMode.HALF_UP), player2.getBalance());
        assertTrue(player1.getOwnedProperties().isEmpty());
    }
}
