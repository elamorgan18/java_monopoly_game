package com.cm6123.monopoly.s2congestion;

import com.cm6123.monopoly.spaces.Space;
import com.cm6123.monopoly.spaces.SpaceType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceTests {

    // Test concrete subclass of Space, for example congestion
    private static class TestCongestion extends Space {
        public TestCongestion(BigDecimal charge, int position, String name) {
            super(charge, position, SpaceType.CONGESTION, name);
        }
    }

    @Test
    void testSpaceConstructorAndGetters() {
        // test data
        BigDecimal charge = BigDecimal.valueOf(5.00).setScale(2, RoundingMode.HALF_UP);
        int position = 10;
        String name = "test";
        SpaceType type = SpaceType.CONGESTION;

        // create a TestCongestion instance (since Space is abstract)
        Space space = new TestCongestion(charge, position, name);

        // Test the getter methods
        assertEquals(charge, space.getCharge(), "Charge should be initialized correctly.");
        assertEquals(position, space.getPosition(), "Position should be initialized correctly.");
        assertEquals(type, space.getType(), "Type should be initialized correctly.");
    }
}
