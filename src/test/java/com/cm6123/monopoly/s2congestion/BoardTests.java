package com.cm6123.monopoly.s2congestion;


import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.spaces.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class to test the Board class, continued for section 2- congestion.
 * I have not repeated the tests from the s1 navigation package for this class,
 * but they all still apply.
 */
public class BoardTests {

    /**
     * An instance for the mock space helper
     */
    private final SpaceHelper mockSpaceHelper = mock(SpaceHelper.class);


    @BeforeEach
    public void setup() {
        when(mockSpaceHelper.getRandomRoadSpacePosition(anyList())).thenReturn(2).thenReturn(1);
    }

    // Testing to see if the spaces created are correct in relation to board size and congestion zones.
    @ParameterizedTest
    @CsvSource({
            // 5 congestion spaces expected for board sizes <= 30
            "10, 5",
            "15, 5",
            "30, 5",
            // 9 congestion spaces expected for board sizes > 30
            "31, 9",
            "40, 9",
            "50, 9"
    })
    void testCreateSpacesAccordingToBoardSize(int boardSize, int expectedCongestions) {
        Board board = new Board(boardSize, mockSpaceHelper);
        List<Space> spaces = board.getSpaces();

        assertEquals(boardSize, spaces.size(), "Board size should match number of spaces");

        int middle = (int) Math.ceil(boardSize / 2.0);
        int congestionCount = 0;

        for (int i = 0; i < spaces.size(); i++) {
            Space space = spaces.get(i);
            int position = i + 1;

            boolean isCongestionZone = (expectedCongestions == 5 && position >= middle - 2 && position <= middle + 2)
                    || (expectedCongestions == 9 && position >= middle - 4 && position <= middle + 4);

            if (isCongestionZone) {
                assertEquals(SpaceType.CONGESTION, space.getType(), "Expected CONGESTION at position " + position);
                congestionCount++;
            } else if (position == 2) {
                assertEquals(SpaceType.COURT, space.getType(), "Expected COURT at position " + position);
            } else {
                boolean spaceIsARoadOrProperty = space.getType() == SpaceType.ROAD || space.getType() == SpaceType.PROPERTY;
                assertTrue(spaceIsARoadOrProperty, "Expected ROAD or PROPERTY at position " + position);
            }
        }

        assertEquals(expectedCongestions, congestionCount, "Unexpected number of congestion spaces");
    }


    // Tests for findMiddleOfBaord method in board.
    @Test
    void testFindMiddleOfBoardEven() {
        Board board = new Board(10, mockSpaceHelper);
        int middle = board.findMiddleOfBoard(10);
        assertEquals(5, middle);
    }

    @Test
    void testFindMiddleOfBoardOdd() {
        Board board = new Board(11, mockSpaceHelper);
        int middle = board.findMiddleOfBoard(11);
        assertEquals(6, middle);
    }

    @Test
    void testPrintBoardWithSize16() {
        Board board = new Board(16, mockSpaceHelper);
        String boardOutput = board.printBoard();

        // Check that there are 16 lines in the output (1 per space) plus 1 line because of \n
        long lineCount = boardOutput.lines().count();
        assertEquals(17, lineCount, "Board output should have 17 space lines");

        // check that all positions 1–16 appear
        for (int i = 1; i <= 16; i++) {
            assertTrue(boardOutput.contains("Space: " + i), "Missing Space: " + i);
        }

        // Congestion block should be around the middle (positions 7, 8, 9)
        assertTrue(boardOutput.contains("Space: 7") && boardOutput.contains("Congestion"), "Missing congestion at position 7");
        assertTrue(boardOutput.contains("Space: 8") && boardOutput.contains("Congestion"), "Missing congestion at position 8");
        assertTrue(boardOutput.contains("Space: 9") && boardOutput.contains("Congestion"), "Missing congestion at position 9");

        // The rest should be ROADs
        int congestionCount = (int) boardOutput.lines()
                .filter(line -> line.contains("Congestion"))
                .count();
        assertEquals(5, congestionCount, "There should be 5 congestion spaces on a 16-size board");
    }

}
