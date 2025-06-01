package com.cm6123.monopoly.s1navigation;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.*;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.rounds.Rounds;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GameControllerTests {

    private final CourtManager courtManager = mock(CourtManager.class);
    private final PropertyManager propertyManager = mock(PropertyManager.class);

    public void setup() {
        doNothing().when(courtManager).processSpaceForPlayer(any());
        doNothing().when(propertyManager).processSpaceForPlayer(any(), any(), any());
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4, 5, 6, 7, 8, 9, 10})
    public void testInitialPlayerPosition2to10Players(int numberOfPlayers) {
        // initialise players, board, and game controller.
        Players players = new Players(numberOfPlayers);
        Board board = new Board(16);
        Rounds rounds = new Rounds(10);
        DiceSet diceSet = new DiceSet(6);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController gameController = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // asserting that each player's initial position is 1.
        for (Player player : players.getPlayerList()) {
            assertEquals(1, player.getPosition());
        }
    }


    private void assertPlayerPosition(int boardSize, int lastRoll, int positionBeforeMove, int positionAfterMove) {
        // assert that the player has moved.
        if (lastRoll != boardSize) {
            assertNotEquals(positionBeforeMove, positionAfterMove, "Player should move from the initial position");
        } else {
            assertEquals(positionBeforeMove, positionAfterMove, "Player should be back to the initial position");
        }

        // assert that the position stays within bounds of the board.
        assertTrue(positionAfterMove >= 1 && positionAfterMove <= boardSize, "Player's position should stay within board bounds");

        // assert that the player landed on the correct position (taking into account wrapping).
        int expectedPositionAfterMove = lastRoll + positionBeforeMove;
        if (expectedPositionAfterMove > boardSize) {
            expectedPositionAfterMove = expectedPositionAfterMove - boardSize;
        }

        assertEquals(expectedPositionAfterMove, positionAfterMove, "Player should land on the expected position");
    }


    @ParameterizedTest
    @CsvSource({
            "2, 10, 5",
            "3, 16, 10",
            "4, 20, 20"
    })
    public void testCurrentRoundUpdatesAfterPlayersTurn(int numberOfPlayers, int boardSize, int maxRounds) {
        Players players = new Players(numberOfPlayers);
        Board board = new Board(boardSize);
        Rounds rounds = new Rounds(maxRounds);
        DiceSet diceSet = new DiceSet(6);
        CongestionManager congestionManager = new CongestionManager();

        // create the game controller.
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // capture the initial round before any moves are made.
        int initialRound = controller.getCurrentRound();

        // make a move, which should increment the round after all players have taken their turns.
        controller.makeRoundOfMoves();

        // assert that the round is incremented after the first set of player moves
        assertEquals(initialRound + 1, controller.getCurrentRound(),
                "The round should increment after all players have taken their turns.");

        // now simulating another round of moves (another complete set of turns)
        controller.makeRoundOfMoves();

        // assert that the round is incremented again
        assertEquals(initialRound + 2, controller.getCurrentRound(),
                "The round should increment again after the second set of player moves.");
    }


    @ParameterizedTest
    @CsvSource({
            "3, 5, true",
            "5, 5, true",
            "6, 5, false",
            "4, 4, true",
            "7, 6, false"
    })
    public void testShouldGameContinueMethod(int currentRound, int maxRounds, boolean expectedResult) {

        Players players = new Players(2);
        Board board = new Board(10);
        Rounds rounds = new Rounds(maxRounds);
        DiceSet diceSet = new DiceSet(6);
        CongestionManager congestionManager = new CongestionManager();

        // Note that here we have set the current round as the value passed in via the test case.
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,currentRound, diceSet, spaceManager);

        boolean result = controller.shouldGameContinue();

        assertEquals(expectedResult, result, "The game continue status is incorrect.");
    }


    @ParameterizedTest
    @CsvSource({
            "12, 9, 200.00, 16, 120.00",  // Passes GO once and has congestion charge.
            "10, 5, 200.00, 16, 0.00",  // Passes GO once
            "3, 7, 0.00, 16, 30.00",     // Doesn't pass GO and has congestion charge.
            "6, 13, 0.00, 16, 60.00",     // Doesn't pass GO and has congestion charge.
            "11, 7, 200.00, 16, 110.00", // Passes GO once and has congestion charge.
            "8, 1, 200.00, 16, 80.00",   // Passes GO once and has congestion charge.
            "5, 11, 0.00, 12, 50.00",   // Doesn't pass GO and has congestion charge.
            "7, 3, 200.00, 12, 70.00",   // Passes GO once
            "12, 5, 400.00, 10, 240.00", // Passes GO twice and has congestion charge.
            "11, 1, 400.00, 11, 0.00", // Passes GO twice
            "9, 9, 200.00, 10, 0.00" // Passes GO once
    })
    void testPlayerGetsMoneyOnlyWhenPassingGo(int roll, int expectedNewPos, BigDecimal expectedGain, int boardSize, BigDecimal congestionFine) {
        // Create mocks for the dice rolls.
        Dice mockDie1 = mock(Dice.class);
        Dice mockDie2 = mock(Dice.class);

        when(mockDie1.roll()).thenReturn(roll);
        when(mockDie2.roll()).thenReturn(0);

        // create the DiceSet with mocked dice.
        DiceSet diceSet = new DiceSet(mockDie1,mockDie2);

        // create the player and game components.
        Players players = new Players(10);
        Board board = new Board(boardSize);
        Rounds rounds = new Rounds(20);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, diceSet, spaceManager);

        // record the initial balance
        Player player1 = players.getPlayerList().get(0);
        BigDecimal initialBalance = player1.getBalance();
        // make two round of moves.
        controller.makeRoundOfMoves();
        controller.makeRoundOfMoves();
        BigDecimal newBalance = player1.getBalance();
        int newPos = player1.getPosition();

        assertEquals((initialBalance.add(expectedGain)).subtract(congestionFine), newBalance, "Wrong balance after move");
        assertEquals(expectedNewPos, newPos, "Wrong position after move");

    }


    // Tests for getting the winner.

    //setting up for mocking the player id and balance.
    private Player mockPlayer(int id, BigDecimal balance) {
        Player player = mock(Player.class);
        when(player.getPlayerId()).thenReturn(id);
        when(player.getBalance()).thenReturn(balance);
        return player;
    }


    @Test
    void testSingleWinner() {
        Player p1 = mockPlayer(1, BigDecimal.valueOf(1200.00).setScale(2, RoundingMode.HALF_UP));
        Player p2 = mockPlayer(2, BigDecimal.valueOf(1800.00).setScale(2, RoundingMode.HALF_UP));  // player 2 winner
        Player p3 = mockPlayer(3, BigDecimal.valueOf(1300.00).setScale(2, RoundingMode.HALF_UP));

        Players playersMock = mock(Players.class);
        when(playersMock.getPlayerList()).thenReturn(Arrays.asList(p1, p2, p3));
        CongestionManager congestionManager = new CongestionManager();

        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(playersMock, null, null, 0, null, spaceManager);

        String result = controller.getWinner();

        assertEquals("\nPlayer 2 Won the Game with £1800.00! Congratulations!", result);
    }

    @Test
    void testTwoPlayerDraw() {
        Player p1 = mockPlayer(1, BigDecimal.valueOf(1600.00).setScale(2, RoundingMode.HALF_UP));
        Player p2 = mockPlayer(2, BigDecimal.valueOf(1600.00).setScale(2, RoundingMode.HALF_UP));  // draw for player 1 and 2
        Player p3 = mockPlayer(3, BigDecimal.valueOf(1400.00).setScale(2, RoundingMode.HALF_UP));

        Players playersMock = mock(Players.class);
        when(playersMock.getPlayerList()).thenReturn(Arrays.asList(p1, p2, p3));
        CongestionManager congestionManager = new CongestionManager();

        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(playersMock, null, null, 0, null, spaceManager);

        String result = controller.getWinner();

        assertEquals("\nIt's a draw between: Player 1, Player 2 with £1600.00", result);
    }

    @Test
    void testAllPlayerDraw() {
        Player p1 = mockPlayer(1, BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_UP)); // all the same balance
        Player p2 = mockPlayer(2, BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_UP));
        Player p3 = mockPlayer(3, BigDecimal.valueOf(1000.00).setScale(2, RoundingMode.HALF_UP));

        Players playersMock = mock(Players.class);
        when(playersMock.getPlayerList()).thenReturn(Arrays.asList(p1, p2, p3));
        CongestionManager congestionManager = new CongestionManager();

        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(playersMock, null, null, 0, null, spaceManager);

        String result = controller.getWinner();

        assertEquals("\nIt's a draw between: Player 1, Player 2, Player 3 with £1000.00", result);
    }


}



