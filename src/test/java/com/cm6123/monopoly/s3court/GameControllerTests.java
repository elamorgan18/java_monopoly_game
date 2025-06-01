package com.cm6123.monopoly.s3court;

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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;
/**
 * A class to test the Game Controller class, continued for section 3- court.
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
    void testCourtFreezePlayerStaysInSamePositionForTwoRounds() {
        // mocking the dice so we roll a 2 (position 1 to 3)
        Dice mockDie1 = mock(Dice.class);
        Dice mockDie2 = mock(Dice.class);
        when(mockDie1.roll()).thenReturn(1).thenReturn(6).thenReturn(6);
        when(mockDie2.roll()).thenReturn(1).thenReturn(6).thenReturn(6);

        DiceSet diceSet = new DiceSet(mockDie1, mockDie2);
        // get one player from the list
        Players players = new Players(1);
        Player player = players.getPlayerList().get(0);
        // ensure player starts at position 1
        player.setPosition(1);

        // calling the getGameController and passing in the player and diceset.
        // this will make sure the player has 2 rounds to miss so that we can test it.
        GameController controller = getGameController(players, diceSet);

        // Round 1: Player lands on Court (position 3)
        controller.makeRoundOfMoves();
        assertEquals(3, player.getPosition(), "Player should land on Court at position 3");
        assertEquals(2, player.getRoundsToMiss(), "Player should be frozen for 2 rounds");

        // Round 2: Player is frozen
        controller.makeRoundOfMoves();
        assertEquals(3, player.getPosition(), "Player should still be on position 3 (frozen - round 1)");
        assertEquals(1, player.getRoundsToMiss(), "Player should now miss 1 more round");

        // Round 3: Player is still frozen
        controller.makeRoundOfMoves();
        assertEquals(3, player.getPosition(), "Player should still be on position 3 (frozen - round 2)");
        assertEquals(0, player.getRoundsToMiss(), "Player should now be free to move next round");
    }


    // set up a game controller that we can control - player set rounds to miss is 2
    private GameController getGameController(Players players, DiceSet diceSet) {
        // Create custom list of spaces with Court at position 3.
        List<Space> theSpaces = new ArrayList<>();
        theSpaces.add(new Road(BigDecimal.ZERO, 1, "Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 2, "Road"));
        theSpaces.add(new Court(BigDecimal.ZERO, 3, "Crown Court"));  // position 3 = court space
        theSpaces.add(new Road(BigDecimal.ZERO, 4,"Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 5,"Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 6,"Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 7,"Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 8,"Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 9,"Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 10, "Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 11, "Road"));
        theSpaces.add(new Road(BigDecimal.ZERO, 12, "Road"));

        // Create board with this custom list
        Board board = new Board(12);
        board.setSpaces(theSpaces);
        // 3 rounds
        Rounds rounds = new Rounds(3);

        // create a CourtManager that freezes the player if on position 3
        CourtManager courtManager = new CourtManager() {
            @Override
            public void processSpaceForPlayer(Player player) {
                if (player.getPosition() == 3) {
                    player.setRoundsToMiss(2);
                }
            }
        };

        CongestionManager congestionManager = new CongestionManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        return new GameController(players, board, rounds,1, diceSet, spaceManager);
    }
}
