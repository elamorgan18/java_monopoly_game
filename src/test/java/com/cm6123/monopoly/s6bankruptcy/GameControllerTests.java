package com.cm6123.monopoly.s6bankruptcy;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.dice.Dice;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.*;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.rounds.Rounds;
import com.cm6123.monopoly.spaces.Property;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
/**
 * A class to test the Game Controller class, continued for section 6 - bankruptcy.
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
    @Test
    public void testBankruptPlayerDoesNotMove() {

        // create the player and game components.
        Players players = new Players(1);
        Board board = new Board(12);
        Rounds rounds = new Rounds(20);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds,1, null, spaceManager);

        // record the initial balance
        Player player = players.getPlayerList().get(0);
        player.setBankrupt(true);
        int originalPosition = player.getPosition();

        BigDecimal initialBalance = player.getBalance();

        controller.makeRoundOfMoves();

        int newPosition = player.getPosition();

        BigDecimal newBalance = player.getBalance();

        assertEquals(originalPosition, newPosition, "Player should not move if bankrupt");
        assertEquals(initialBalance, newBalance, "Player should not update balance if bankrupt");

    }

    @ParameterizedTest
    @CsvSource({
            "3, 5, true",  // Round 3, max round 5, should continue
            "5, 5, true",  // Round 5, max round 5, should continue
            "6, 5, false", // Round 6, max round 5, should stop (game should end)
            "4, 4, true", // Round 4, max round 4, should continue
            "7, 6, false", // Round 7, max round 6, should stop (game should end)
            "2, 5, true",  // Round 2, max round 5, should continue
            "4, 5, true"   // Round 4, max round 5, should continue
    })
    public void testShouldGameContinueMethod(int currentRound, int maxRounds, boolean expectedResult) {
        // set up players
        Players players = new Players(0);
        Player player1 = new Player(1, 1, BigDecimal.valueOf(100), 2);
        Player player2 = new Player(2, 2, BigDecimal.valueOf(10), 2);
        players.getPlayerList().add(player1);
        players.getPlayerList().add(player2);

        Board board = new Board(10);
        Rounds rounds = new Rounds(maxRounds);
        DiceSet diceSet = new DiceSet(6);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds, currentRound, diceSet, spaceManager);

        boolean result = controller.shouldGameContinue();

        assertEquals(expectedResult, result, "The game continue status is incorrect.");
    }

    @ParameterizedTest
    @CsvSource({
            "3, 5, false", // Round 3, max round 5, game should not continue
            "4, 6, false" // Round 4, max round 6, game should not continue
    })
    public void testShouldGameContinueMethodWithOnlyOneActivePlayer(int currentRound, int maxRounds, boolean expectedResult) {
        // set up players
        Players players = new Players(0);
        Player player1 = new Player(1, 1, BigDecimal.valueOf(100), 2);
        Player player2 = new Player(2, 2, BigDecimal.valueOf(10), 2);
        player2.setBankrupt(true);
        players.getPlayerList().add(player1);
        players.getPlayerList().add(player2);

        Board board = new Board(10);
        Rounds rounds = new Rounds(maxRounds);
        DiceSet diceSet = new DiceSet(6);
        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController controller = new GameController(players, board, rounds, currentRound, diceSet, spaceManager);

        boolean result = controller.shouldGameContinue();

        assertEquals(expectedResult, result, "The game continue status is incorrect.");
    }
}
