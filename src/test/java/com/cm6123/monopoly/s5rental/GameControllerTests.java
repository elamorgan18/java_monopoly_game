package com.cm6123.monopoly.s5rental;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.GameController;
import com.cm6123.monopoly.game.SpaceManager;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.rounds.Rounds;
import com.cm6123.monopoly.spaces.Property;
import com.cm6123.monopoly.spaces.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
/**
 * A class to test the Game Controller class, continued for section 5 - rental.
 * I have not repeated the tests from the s1 navigation package for this class,
 * but they all still apply.
 */
public class GameControllerTests {
    private GameController gameController;
    private Player player;
    private Board board;
    private DiceSet diceSet;
    private Rounds rounds;
    private Players players;



    @BeforeEach
    public void setUp() {
        SpaceManager spaceManager = mock(SpaceManager.class);
        board = new Board(16);
        gameController = new GameController(players, board, rounds, 1, diceSet, spaceManager);
        player = new Player(1, 1, BigDecimal.valueOf(1000), 0);
    }

    @Test
    public void test200BonusPlus1PercentRentalPriceOfAllPropertiesOwn() {
        // player owns properties with rental income
        Property property1 = new Property(BigDecimal.valueOf(500).setScale(2, RoundingMode.HALF_UP), 2, true, "Property1");
        Property property2 = new Property(BigDecimal.valueOf(600).setScale(2, RoundingMode.HALF_UP), 2, true, "Property2");
        player.getOwnedProperties().add(property1);
        player.getOwnedProperties().add(property2);

        // simulate the player rolling the dice and moving beyond home space
        int originalPosition = player.getPosition();
        // roll moving player past starting position.
        int totalRoll = 17;

        // before applying the charge or bonus
        BigDecimal initialBalance = player.getBalance();

        gameController.applyChargeOrBonusToPlayer(player, originalPosition, totalRoll);

        // calculate rent value of all properties they own
        BigDecimal rentalIncome = player.getTotalRentalIncome(player.getOwnedProperties()).multiply(BigDecimal.valueOf(0.1));
        BigDecimal rentalIncomeOnePercent = rentalIncome.multiply(BigDecimal.valueOf(0.1));

        // calculate expected balance (initial balance + £200 + 1% of rental income)
        BigDecimal twoHundred = BigDecimal.valueOf(200);
        BigDecimal expectedBalance = initialBalance.add(twoHundred).add(rentalIncomeOnePercent).setScale(2, RoundingMode.HALF_UP);


        assertEquals(expectedBalance, player.getBalance(), "The balance should reflect the £200 bonus and 1% rental income");
    }
}

