package com.cm6123.monopoly.board;


import com.cm6123.monopoly.spaces.Space;
import com.cm6123.monopoly.spaces.SpaceHelper;
import com.cm6123.monopoly.spaces.Congestion;
import com.cm6123.monopoly.spaces.Road;
import com.cm6123.monopoly.spaces.Court;
import com.cm6123.monopoly.spaces.Property;
import com.cm6123.monopoly.spaces.AvailableProperty;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that stores all board information.
 */
public class Board {

    /**
     * An instance variable to store the size of the board.
     */
    private final int boardSize;

    /**
     * An instance variable to store the spaces.
     */
    private List<Space> spaces;

    /**
     * An instance of the spaceHelper.
     */
    private final SpaceHelper spaceHelper;
    /**
     * A constructor for the board.
     *
     * @param sizeOfboard the size of the board.
     * @param theSpaceHelper the space helper.
     */
    public Board(final int sizeOfboard, final SpaceHelper theSpaceHelper) {
        this.boardSize = sizeOfboard;
        this.spaceHelper = theSpaceHelper;
        this.spaces = createSpaces(sizeOfboard);
    }
    /**
     * A constructor for the board.
     * @param sizeOfboard the size of the board.
     */
    public Board(final int sizeOfboard) {
        this.boardSize = sizeOfboard;
        this.spaceHelper = new SpaceHelper();
        this.spaces = createSpaces(sizeOfboard);
    }

    /**
     * A constructor for creating our own spaces so we can test it works.
     * @param theSpaces the list of spaces we are using to test.
     */
    public void setSpaces(final List<Space> theSpaces) {
        this.spaces = theSpaces;
    }

    /**
     * A method to retrieve the size of the board.
     *
     * @return an integer of the size of the board.
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * A method to find the middle of the board rounded up to the nearest whole number
     * For example, input of 10 will return 5, input of 11 will return 6.
     * @param theBoardSize
     * @return middle of board value.
     */
    public int findMiddleOfBoard(final int theBoardSize) {
        int middleOfBoard = (int) Math.ceil(theBoardSize / 2.0);
        return middleOfBoard;
    }

    /**
     * A method to create the spaces on the board.
     * Assumption - the congestion is put in the middle of the board as a block of 5 spaces (board size between 10-30)
     * and a block of 9 spaces if board size between 31 and 50. Only one block of congestion every time.
     * We are also putting the Court at a random space that isn't congestion.
     * @param theBoardSize
     * @return spaces.
     */
    private List<Space> createSpaces(final int theBoardSize) {
        int middleOfBoard = findMiddleOfBoard(theBoardSize);

        List<Space> theSpaces = new ArrayList<>();
        for (int i = 1; i <= boardSize; i++) {

            if (boardSize <= 30) {
                if (i >= (middleOfBoard - 2) && i <= (middleOfBoard + 2)) {
                    Congestion congestion = new Congestion(BigDecimal.ZERO, i, "Congestion");
                    theSpaces.add(congestion);
                } else {
                    Road road = new Road(BigDecimal.ZERO, i, "Road");
                    theSpaces.add(road);
                }
            } else {// boardSize > 30
                if (i >= (middleOfBoard - 4) && i <= (middleOfBoard + 4)) {
                    Congestion congestion = new Congestion(BigDecimal.ZERO, i, "Congestion");
                    theSpaces.add(congestion);
                } else {
                    Road road = new Road(BigDecimal.ZERO, i, "Road");
                    theSpaces.add(road);
                }
            }

        }  // here we have a board created with Roads and Congestion

        // add the court
        addCourtToSpaces(theSpaces);

        // add properties to spaces
        addPropertiesToSpaces(theSpaces);


        return theSpaces;
    }

    /**
     * A method that adds the court to a space.
     * We are calling a method from SpaceHelper class which makes sure it's not a congestion space.
     * @param theSpaces list of the spaces.
     */
    private void addCourtToSpaces(final List<Space> theSpaces) {
        int positionOfRandomRoadSpace = spaceHelper.getRandomRoadSpacePosition(theSpaces);
        Court court = new Court(BigDecimal.valueOf(0), positionOfRandomRoadSpace, "Crown Court");
        theSpaces.set(positionOfRandomRoadSpace - 1, court);
    }

    /**
     * A method that adds the properties to a space.
     * We are calling a method from SpaceHelper class which makes sure it's not a congestion space or a court.
     * @param theSpaces list of the spaces.
     */
    private void addPropertiesToSpaces(final List<Space> theSpaces) {
        int numberOfProperties = 0;


        if (theSpaces.size() >= 10 && theSpaces.size() <= 15) {
            numberOfProperties = 3;
        } else if (theSpaces.size() >= 16 && theSpaces.size() <= 30) {
            numberOfProperties = 5;
        } else {
            numberOfProperties = 8;
        }

        List<AvailableProperty> availableProperties = new ArrayList<>();

        // making properties with charge and names
        availableProperties.add(new AvailableProperty("Old Kent Road", BigDecimal.valueOf(500)));
        availableProperties.add(new AvailableProperty("Pall Mall", BigDecimal.valueOf(200)));
        availableProperties.add(new AvailableProperty("The Strand", BigDecimal.valueOf(345)));
        availableProperties.add(new AvailableProperty("Leicester Square", BigDecimal.valueOf(450)));
        availableProperties.add(new AvailableProperty("Park Lane", BigDecimal.valueOf(250)));
        availableProperties.add(new AvailableProperty("Ritz", BigDecimal.valueOf(300)));
        availableProperties.add(new AvailableProperty("Mayflower", BigDecimal.valueOf(350)));
        availableProperties.add(new AvailableProperty("Chippy Lane", BigDecimal.valueOf(100)));


        for (int i = 0; i < numberOfProperties; i++) {
            int positionOfRandomRoadSpace = spaceHelper.getRandomRoadSpacePosition(theSpaces);
            AvailableProperty availableProperty = availableProperties.get(i);
            Property property = new Property(availableProperty.getPrice(), positionOfRandomRoadSpace, false, availableProperty.getName());
            theSpaces.set(positionOfRandomRoadSpace - 1, property);
        }
    }

    /**
     * A method to retrieve the list of spaces on the board.
     * @return a list of Space objects.
     */
    public List<Space> getSpaces() {
        return spaces;
    }
    /**
     * A method to print the board and its space type.
     * @return the string containing space, charge and type.
     */
    public String printBoard() {
        StringBuilder sb = new StringBuilder();
        for (Space space : spaces) {

            String chargeToPrint = "";

            if (space instanceof Road) {
                chargeToPrint = ", No Charges";
            } else if (space instanceof Court) {
                chargeToPrint = ", Bonus: £100";
            } else if (space instanceof Congestion) {
                chargeToPrint = ", Charge: 10% of last roll";
            } else {
                chargeToPrint = ", Charge: £" + space.getCharge().toString();
            }

            sb.append("\nSpace: " + space.getPosition()
                    + " " + space.getName() +  chargeToPrint);
        }
        return sb.toString();
    }

    /**
     * A method to print the board banner to display nicely.
     * @return the print out of the banner.
     */
    public String printBoardBanner() {
        String printOut = "\n---------------------B O A R D---------------------";
        return printOut;
    }
}

