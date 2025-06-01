package com.cm6123.monopoly.s1navigation;


import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.spaces.SpaceHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A class to test the Board class.
 */
public class BoardTests {

    // Test the board constructor.
    @ParameterizedTest
    @ValueSource(ints = {10, 16, 25, 34, 50})
    public void shouldCreateBoardWithValidInputs(int inputSize) {
        SpaceHelper spaceHelper = mock(SpaceHelper.class);
        when(spaceHelper.getRandomRoadSpacePosition(anyList())).thenReturn(9);
        Board board = new Board(inputSize, spaceHelper);
        assertEquals(inputSize, board.getBoardSize());
    }
}
