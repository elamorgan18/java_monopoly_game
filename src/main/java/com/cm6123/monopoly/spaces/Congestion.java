package com.cm6123.monopoly.spaces;


import java.math.BigDecimal;

public class Congestion extends Space {

    /**
     * A constructor for the congestion spaces.
     * @param theCharge the charge if landed on.
     * @param thePosition the position they are at.
     * @param theName the name of the congestion.
     */
    public Congestion(final BigDecimal theCharge, final int thePosition, final String theName) {
        super(theCharge, thePosition, SpaceType.CONGESTION, theName);
    }

}
