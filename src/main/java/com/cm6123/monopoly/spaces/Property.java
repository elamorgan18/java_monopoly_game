package com.cm6123.monopoly.spaces;

import com.cm6123.monopoly.players.Player;

import java.math.BigDecimal;

public class Property extends Space {


    /**
     * An instance that declares if the property is owned or not.
     */
    private boolean isOwned = false;

    /**
     * An instance that declares who the owner of the property is.
     */
    private Player owner;

    /**
     * A constructor for the property space.
     * @param theCharge the charge they will recieve.
     * @param thePosition the position they land on.
     * @param theIsOwned is the porperty owned or not.
     * @param theName the name of the property.
     */
    public Property(final BigDecimal theCharge, final int thePosition, final boolean theIsOwned, final String theName) {
        super(theCharge, thePosition, SpaceType.PROPERTY, theName);
        this.isOwned = theIsOwned;
        // when we create the property, it doesn't have an owner
        this.owner = null;
    }

    /**
     * A method that sees if property is owned or not.
     * @return if property is owned or not boolean.
     */
    public boolean isOwned() {
        return isOwned;
    }
    /**
     * A method that sets the isOwned to either true or false.
     * @param owned
     */
    public void setOwned(final boolean owned) {
        isOwned = owned;
    }

    /**
     * A method that gets the owner of the property.
     * @return owner of the property.
     */
    public Player getOwner() {
        return owner;
    }

    /**
     * A method that sets the owner of the property.
     * @param theOwner
     */
    public void setOwner(final Player theOwner) {
        this.owner = theOwner;
    }

    /**
     * An override that gives a string of the porperty name and charge.
     * @return
     */
    @Override
    public String toString() {
        return this.getName() + " Cost: £" + this.getCharge();
    }
}
