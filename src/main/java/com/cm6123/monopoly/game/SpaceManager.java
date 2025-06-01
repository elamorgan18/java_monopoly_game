package com.cm6123.monopoly.game;

import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.spaces.Property;
import com.cm6123.monopoly.spaces.Space;
import com.cm6123.monopoly.spaces.SpaceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class to manage the spaces.
 */
public class SpaceManager {

    /**
     * An instance for the Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceManager.class);

    /**
     * An instance variable to store the court manager.
     */
    private final CourtManager courtManager;
    /**
     * An instance variable to store the congestion manager.
     */
    private final CongestionManager congestionManager;
    /**
     * An instance variable to store the property manager.
     */
    private final PropertyManager propertyManager;

    /**
     * A constructor for the space manager class.
     * @param theCongestionManager the congestion manager.
     * @param theCourtManager the court manager.
     * @param thePropertyManager  the property manager.
     */
    public SpaceManager(final CourtManager theCourtManager, final CongestionManager theCongestionManager, final PropertyManager thePropertyManager) {
        this.courtManager = theCourtManager;
        this.congestionManager = theCongestionManager;
        this.propertyManager = thePropertyManager;
    }

    /**
     * A method that determines what the space type of the space the player lands on is.
     * @param currentSpace the current space the player is on.
     * @param player the player we are dealing with.
     * @param players the players
     */
    public void handlePlayerOnSpace(final Space currentSpace, final Player player, final Players players) {

        SpaceType theType = currentSpace.getType();
        // apply charge or bonus based on the type of the space
        if (theType == SpaceType.CONGESTION) {
            congestionManager.processSpaceForPlayer(player);
        } else if (theType == SpaceType.COURT) {
            courtManager.processSpaceForPlayer(player);
        } else if (theType == SpaceType.PROPERTY) {
            Property prop = (Property) currentSpace;
            propertyManager.processSpaceForPlayer(player, prop, players);
        } else {
            LOGGER.info("player with id" + player.getPlayerId() + " landed on a ROAD space, so no action to take.");
        }
    }


}
