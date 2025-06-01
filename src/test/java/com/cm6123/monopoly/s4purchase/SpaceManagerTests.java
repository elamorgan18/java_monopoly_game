package com.cm6123.monopoly.s4purchase;

import com.cm6123.monopoly.game.CongestionManager;
import com.cm6123.monopoly.game.CourtManager;
import com.cm6123.monopoly.game.PropertyManager;
import com.cm6123.monopoly.game.SpaceManager;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.spaces.Property;
import com.cm6123.monopoly.spaces.Space;
import com.cm6123.monopoly.spaces.SpaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class SpaceManagerTests {

    // variables for the SpaceManager and mocks
    private SpaceManager spaceManager;
    private CongestionManager congestionManager;
    private CourtManager courtManager;
    private PropertyManager propertyManager;
    private Player player;
    private Space currentSpace;
    private Property property;
    private Players players;

    @BeforeEach
    public void setUp() {
        // creating the mocks for the dependencies
        congestionManager = mock(CongestionManager.class);
        courtManager = mock(CourtManager.class);
        propertyManager = mock(PropertyManager.class);
        property = mock(Property.class);

        spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);

        player = mock(Player.class);
        currentSpace = mock(Space.class);
        players = mock(Players.class);
    }

    @Test
    public void testHandlePlayerOnCongestionSpace() {
        // making the space a congestion
        when(currentSpace.getType()).thenReturn(SpaceType.CONGESTION);

        // calling handle player on space method
        spaceManager.handlePlayerOnSpace(currentSpace, player, players);

        // verify that the congestion manager's method was called
        verify(congestionManager).processSpaceForPlayer(player);

        // verify that the court manager and property manager were never called
        verify(courtManager, never()).processSpaceForPlayer(player);
        verify(propertyManager, never()).processSpaceForPlayer(player, null, players);
    }

    @Test
    public void testHandlePlayerOnCourtSpace() {
        when(currentSpace.getType()).thenReturn(SpaceType.COURT);

        spaceManager.handlePlayerOnSpace(currentSpace, player, players);

        verify(courtManager).processSpaceForPlayer(player);
        verify(congestionManager, never()).processSpaceForPlayer(player);
        verify(propertyManager, never()).processSpaceForPlayer(player, null, players);
    }


    @Test
    public void testHandlePlayerOnRoadSpace() {
        when(currentSpace.getType()).thenReturn(SpaceType.ROAD);

        spaceManager.handlePlayerOnSpace(currentSpace, player, players);

        verify(congestionManager, never()).processSpaceForPlayer(player);
        verify(courtManager, never()).processSpaceForPlayer(player);
        verify(propertyManager, never()).processSpaceForPlayer(player, null, players);
    }

    @Test
    public void testHandlePlayerOnPropertySpace() {

        Space propertySpace = mock(Property.class);
        when(propertySpace.getType()).thenReturn(SpaceType.PROPERTY);

        spaceManager.handlePlayerOnSpace(propertySpace, player, players);

        verify(propertyManager).processSpaceForPlayer(eq(player), any(Property.class),any(Players.class));
        verify(congestionManager, never()).processSpaceForPlayer(player);
        verify(courtManager, never()).processSpaceForPlayer(player);

    }
}
