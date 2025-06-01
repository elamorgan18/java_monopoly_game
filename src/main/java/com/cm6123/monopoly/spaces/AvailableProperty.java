package com.cm6123.monopoly.spaces;

import java.math.BigDecimal;

public class AvailableProperty {

    /**
     * An instance for the name of the available property.
     */
    private final String name;

    /**
     * An instance for the porpety price.
     */
    private final BigDecimal price;

    /**
     * A constructor for the available property.
     * @param theName
     * @param thePrice
     */
    public AvailableProperty(final String theName, final BigDecimal thePrice) {
        this.name = theName;
        this.price = thePrice;
    }

    /**
     * A method that returns the name of the property.
     * @return the name of the property.
     */
    public String getName() {
        return name;
    }

    /**
     * A method that returns the price of the property.
     * @return the price of the property.
     */
    public BigDecimal getPrice() {
        return price;
    }

}
