package com.cm6123.monopoly.spaces;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A class for each Space on the board.
 */
public abstract class Space {

    /**
     * An instance of the charge.
     */
    private final BigDecimal charge;
    /**
     * An instance of the position.
     */
    private final int position;
    /**
     * An instance of the type.
     */
    private final SpaceType type;

    /**
     * An instance of the name.
     */
    private final String name;

    /**
     * A constructor for the Space.
     * @param theCharge the charge if landed on.
     * @param thePosition the position they are at.
     * @param theType the type of space they landed on.
     * @param theName the name of the space.
     */
    public Space(final BigDecimal theCharge, final int thePosition, final SpaceType theType, final String theName) {
        this.charge = theCharge;
        this.position = thePosition;
        this.type = theType;
        this.name = theName;

    }
    /**
     * A method to retrieve the charge.
     * @return the charge.
     */
    public BigDecimal getCharge() {
        return charge.setScale(2, RoundingMode.HALF_UP);
    }
    /**
     * A method to retrieve position.
     * @return the position.g
     */
    public int getPosition() {
        return position;
    }
    /**
     * A method to retrieve the type of space.
     * @return the type.
     */
    public SpaceType getType() {
        return type;
    }

    /**
     * A method to retrieve the name of space.
     * @return the name.
     */
    public String getName() {
        return name;
    }

}
