package com.cm6123.monopoly.s2congestion;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.*;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.rounds.Rounds;
import com.cm6123.monopoly.spaces.Court;
import com.cm6123.monopoly.spaces.Road;
import com.cm6123.monopoly.spaces.Space;
import com.cm6123.monopoly.spaces.SpaceHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A class to test the Game Controller class, continued for section 2- congestion.
 * I have not repeated the tests from the s1 navigation package for this class,
 * but they all still apply.
 */
public class GameControllerTests {

    private final CourtManager courtManager = mock(CourtManager.class);
    private final PropertyManager propertyManager = mock(PropertyManager.class);

    public void setup() {
        doNothing().when(courtManager).processSpaceForPlayer(any());
        doNothing().when(propertyManager).processSpaceForPlayer(any(), any(), any());
    }

    @ParameterizedTest
    @CsvSource({
//             Not on congestion cases (should only get GO money if passing)
            "10, 1, 200.00, 0.00, 10",    // Passes GO (+200), lands on 1 (no congestion)
            "6, 7, 0.00, 0.00, 50",      // Doesn't pass GO, lands on 7 (no congestion)

            // On congestion cases (should get charged if on congestion tile)
            "4, 5, 0.00, 40.00, 10",      // Doesn't pass GO, lands on 5 (congestion -40)
            "12, 13, 0.00, 120.00, 25",  // Doesn't pass GO, lands on 8 (congestion -120)
    })
    void testCongestionChargePlayerStartsAt1(int roll, int expectedNewPos, BigDecimal expectedGain, BigDecimal expectedCharge, int boardSize) {
        // Create mocks for the dice rolls.
        Dice mockDie1 = mock(Dice.class);
        Dice mockDie2 = mock(Dice.class);

        when(mockDie1.roll()).thenReturn(roll);
        when(mockDie2.roll()).thenReturn(0);

        // create the DiceSet with mocked dice.
        DiceSet diceSet = new DiceSet(mockDie1, mockDie2);

        // create the player and game components.
        Players players = new Players(1);
        Board board = new Board(boardSize);
        Rounds rounds = new Rounds(4);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // record the initial balance
        Player player1 = players.getPlayerList().get(0);
        BigDecimal initialBalance = player1.getBalance();

        controller.makeRoundOfMoves();

        BigDecimal newBalance = player1.getBalance();
        int newPos = player1.getPosition();

        assertEquals((initialBalance.add(expectedGain)).subtract(expectedCharge), newBalance, "Wrong balance after move");
        assertEquals(expectedNewPos, newPos, "Wrong position after move");
    }

    @Test
    void testCongestionChargeMoveIntoAndStayInCongestionZone() {
        // Create mocks for the dice rolls.
        Dice mockDie1 = mock(Dice.class);
        Dice mockDie2 = mock(Dice.class);

        when(mockDie1.roll()).thenReturn(4).thenReturn(2);
        when(mockDie2.roll()).thenReturn(0);

        // create the DiceSet with mocked dice.
        DiceSet diceSet = new DiceSet(mockDie1, mockDie2);

        // create the player and game components.
        Players players = new Players(2);
        Board board = new Board(12);
        Rounds rounds = new Rounds(4);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // record the initial balance
        Player player1 = players.getPlayerList().get(0);
        BigDecimal initialBalance = player1.getBalance();

        controller.makeRoundOfMoves();

        BigDecimal newBalance = player1.getBalance();
        int newPos = player1.getPosition();

        assertEquals(initialBalance.subtract(BigDecimal.valueOf(40)), newBalance, "Wrong balance after first move");
        assertEquals(5, newPos, "Wrong position after move");

        // now we are on space 5 (in the Congestion Zone), so make another move
        controller.makeRoundOfMoves();

        BigDecimal newBalanceAfterSecondMove = player1.getBalance();
        int newPositionAfterSecondMove = player1.getPosition();
        assertEquals(initialBalance.subtract(BigDecimal.valueOf(60)), newBalanceAfterSecondMove, "Wrong balance after second move");
        assertEquals(7, newPositionAfterSecondMove, "Wrong position after move");

    }


    @Test
    void testCongestionChargeMoveIntoButStayOutOfCongestionZone() {
        // Create mocks for the dice rolls.
        Dice mockDie1 = mock(Dice.class);
        Dice mockDie2 = mock(Dice.class);

        when(mockDie1.roll()).thenReturn(4).thenReturn(6);
        when(mockDie2.roll()).thenReturn(0);

        // create the DiceSet with mocked dice.
        DiceSet diceSet = new DiceSet(mockDie1, mockDie2);

        // create the player and game components.
        Players players = new Players(2);
        Board board = new Board(12);
        Rounds rounds = new Rounds(4);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // record the initial balance
        Player player1 = players.getPlayerList().get(0);
        BigDecimal initialBalance = player1.getBalance();

        controller.makeRoundOfMoves();

        BigDecimal newBalance = player1.getBalance();
        int newPos = player1.getPosition();

        assertEquals(initialBalance.subtract(BigDecimal.valueOf(40)), newBalance, "Wrong balance after first move");
        assertEquals(5, newPos, "Wrong position after move");

        // now we are on space 5 (in the Congestion Zone), so make another move to go out of congestion zone
        controller.makeRoundOfMoves();

        BigDecimal newBalanceAfterSecondMove = player1.getBalance();
        int newPositionAfterSecondMove = player1.getPosition();
        assertEquals(initialBalance.subtract(BigDecimal.valueOf(40)), newBalanceAfterSecondMove, "Wrong balance after second move");
        assertEquals(11, newPositionAfterSecondMove, "Wrong position after move");

    }


    @Test
    void testCongestionChargeNeverInCongestionZone() {
        // Create mocks for the dice rolls.
        Dice mockDie1 = mock(Dice.class);
        Dice mockDie2 = mock(Dice.class);

        when(mockDie1.roll()).thenReturn(2).thenReturn(8);
        when(mockDie2.roll()).thenReturn(0);

        // create the DiceSet with mocked dice.
        DiceSet diceSet = new DiceSet(mockDie1, mockDie2);

        // create the player and game components.
        Players players = new Players(2);
        Board board = new Board(12);
        Rounds rounds = new Rounds(4);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // record the initial balance
        Player player1 = players.getPlayerList().get(0);
        BigDecimal initialBalance = player1.getBalance();

        controller.makeRoundOfMoves();

        BigDecimal newBalance = player1.getBalance();
        int newPos = player1.getPosition();

        assertEquals(initialBalance, newBalance, "Wrong balance after first move");
        assertEquals(3, newPos, "Wrong position after move");

        controller.makeRoundOfMoves();

        BigDecimal newBalanceAfterSecondMove = player1.getBalance();
        int newPositionAfterSecondMove = player1.getPosition();
        assertEquals(initialBalance, newBalanceAfterSecondMove, "Wrong balance after second move");
        assertEquals(11, newPositionAfterSecondMove, "Wrong position after move");

    }

}
