package com.cm6123.monopoly.spaces;

import java.util.List;
import java.util.Random;

/**
 * A class to help the creation of spaces in the board class.
 */
public class SpaceHelper {

    /**
     * A method to get a random space that is not a congestion so we can help create a court space.
     *
     * @param theSpaces the list of spaces.
     * @return spacePosition, the random road position.
     */
    public int getRandomRoadSpacePosition(final List<Space> theSpaces) {
        boolean foundARoadSpace = false;
        int randomIndex = 0;
        while (!foundARoadSpace) {
            randomIndex = new Random().nextInt(theSpaces.size());
            Space theRandomSpace = theSpaces.get(randomIndex);
            if (theRandomSpace instanceof Road) {
                foundARoadSpace = true;
            }
        }
        int spacePosition = randomIndex + 1; // since our board starts at 1
        return spacePosition;

    }

}
