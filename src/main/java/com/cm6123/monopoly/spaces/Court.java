package com.cm6123.monopoly.spaces;

import java.math.BigDecimal;

public class Court extends Space {

    /**
     * A constructor for the court spaces.
     * @param theCharge the charge if landed on.
     * @param thePosition the position they are at.
     * @param theName the name of the court.
     */
    public Court(final BigDecimal theCharge, final int thePosition, final String theName) {
        super(theCharge, thePosition, SpaceType.COURT, theName);
    }
}
