package com.cm6123.monopoly.spaces;

import java.math.BigDecimal;

public class Road extends Space {
    /**
     * A constructor for the road space.
     * @param theCharge the charge they will recieve.
     * @param thePosition the position they land on.
     * @param theName the name of the road.
     */
    public Road(final BigDecimal theCharge, final int thePosition, final String theName) {
        super(theCharge, thePosition, SpaceType.ROAD, theName);
    }
}
