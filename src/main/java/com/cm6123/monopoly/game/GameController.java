package com.cm6123.monopoly.game;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.players.Player;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.rounds.Rounds;
import com.cm6123.monopoly.spaces.Property;
import com.cm6123.monopoly.spaces.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


/**
 * A class to control the monopoly game.
 */
public class GameController {
    /**
     * An instance for the Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    /**
     * An instance variable to store the players.
     */
    private final Players players;

    /**
     * A method that gets the players.
     * @return players.
     */
    public Players getPlayers() {
        return players;
    }
    /**
     * An instance variable to store the board.
     */
    private final Board board;
    /**
     * An instance variable to store the dice set.
     */
    private final DiceSet diceSet;
    /**
     * An instance variable to store the rounds.
     */
    private final Rounds rounds;
    /**
     * The round that the players are currently playing in.
     */
    private int currentRound;
    /**
     * An instance for the Space Manager class.
     */
    private final SpaceManager spaceManager;

    /**
     * A constructor for the game controller.
     * @param thePlayers the players we want to pass in.
     * @param theBoard the board we want to pass in.
     * @param theMaxRounds the maximum number of rounds in the game.
     * @param theCurrentRound the current round.
     * @param theDiceSet the dice set value we pass in.
     * @param theSpaceManager the space manager.
     */
    public GameController(final Players thePlayers, final Board theBoard, final Rounds theMaxRounds,
                          final int theCurrentRound, final DiceSet theDiceSet, final SpaceManager theSpaceManager) {
        this.board = theBoard;
        this.players = thePlayers;
        this.diceSet = theDiceSet;
        this.rounds = theMaxRounds;
        this.currentRound = theCurrentRound;
        this.spaceManager = theSpaceManager;
    }

    /**
     * A method that makes a player move on the board.
     */
    public void makeRoundOfMoves() {
        // loop through all the players in the list
        for (Player currentPlayer : players.getPlayerList()) {
            if (currentPlayer.isBankrupt()){
                System.out.println("Player " + currentPlayer.getPlayerId() + " is bankrupt! You're out!");
                continue;
            }
            // checking to see if a player needs to miss a turn
            if (currentPlayer.getRoundsToMiss() > 0) {
                currentPlayer.decrementRoundsToMiss();
                System.out.println("Player " + currentPlayer.getPlayerId() + " FROZEN");
                continue;
            }
            // roll the dice
            int totalRoll = diceSet.addTogether();

            // get the current player from the players list and their position.
            int originalPosition = currentPlayer.getPosition();
            int newPosition = getNewPosition(currentPlayer, totalRoll);

            currentPlayer.setPosition(newPosition);
            currentPlayer.setLastRoll(totalRoll);

            applyChargeOrBonusToPlayer(currentPlayer, originalPosition, totalRoll);
        }
        currentRound++;
    }

    /**
     * A method that returns the current round the game is on.
     * @return an int of the current round the game is on.
     */
    public int getCurrentRound() {
        return currentRound;
    }

    /**
     * A method that gets the new player position.
     * @param currentPlayer the current player that is playing the game.
     * @param value the value that the player has rolled.
     * @return the new position of the player.
     */
    private int getNewPosition(final Player currentPlayer, final int value) {
        int currentPosition = currentPlayer.getPosition();

        // update players position on the board.
        int newPosition = currentPlayer.getPosition() + value;
        if (newPosition > board.getBoardSize()) {
            newPosition = newPosition - board.getBoardSize();
        }

        return newPosition;
    }

    /**
     * A method that determines if the player has landed on a space that gives a charge or bonus.
     * I get the players position and compare it to the space type.
     * @param player the current player.
     * @param originalPosition the original player position.
     * @param totalRoll the total roll value.
     */
    public void applyChargeOrBonusToPlayer(final Player player, final int originalPosition, final int totalRoll) {

        // Has player passed the start? If so add £200 plus
        int total = originalPosition + totalRoll;
        if (total > board.getBoardSize()) {
            BigDecimal rentalFactor = BigDecimal.valueOf(0.01).setScale(2, RoundingMode.HALF_UP);
            BigDecimal rentalIncomeOfOwnedProperties = player.getTotalRentalIncome(player.getOwnedProperties()).setScale(2, RoundingMode.HALF_UP);

            BigDecimal playerTotalRentalPercentage = rentalIncomeOfOwnedProperties.multiply(rentalFactor).setScale(2, RoundingMode.HALF_UP);
            BigDecimal newBalance = player.getBalance().add(BigDecimal.valueOf(200.00).setScale(2, RoundingMode.HALF_UP));
            BigDecimal newBalancePlusRent = newBalance.add(playerTotalRentalPercentage).setScale(2, RoundingMode.HALF_UP);
            player.setBalance(newBalancePlusRent.setScale(2, RoundingMode.HALF_UP));

            System.out.println("Player " + player.getPlayerId()
                    + " passed GO! Bonus £200 and 1% of total rental cost of your properties: £"
                    + playerTotalRentalPercentage.setScale(2, RoundingMode.HALF_UP));

        }

        // Get the space the player is on
        int thePlayersPosition = player.getPosition();
        List<Space> spaces = board.getSpaces();
        Space currentSpace = spaces.get(thePlayersPosition - 1); // spaces is zero based.

        spaceManager.handlePlayerOnSpace(currentSpace, player, players);
    }

    /**
     * A method that determines if the game should continue.
     * @return a boolean that says if the game should continue.
     */
    public boolean shouldGameContinue(){
        if (currentRound > rounds.getNumberOfRounds()) {
            return false;
        }

        // Count players who are not bankrupt
        int activePlayers = 0;
        for (Player player : players.getPlayerList()) {
            if (!player.isBankrupt()) {
                activePlayers++;
            }
        }
        boolean moreThanOneActivePlayer = false;

        if (activePlayers > 1) {
            moreThanOneActivePlayer = true;
        }
        return moreThanOneActivePlayer;
    }

    /**
     * A method that determines who the winner is or if it's a draw.
     * The winner is whoever has the highest balance plus all their property purchase charges.
     * @return details of the player/players that won.
     */

    public String getWinner() {
        List<Player> playerList = players.getPlayerList();
        List<Player> winners = new ArrayList<>();
        BigDecimal highestTotalWealth = BigDecimal.valueOf(-99999.99);

        for (Player player : playerList) {
            BigDecimal totalWealth = player.getBalance();

            // add the value of all owned properties
            for (Property property : player.getOwnedProperties()) {
                totalWealth = totalWealth.add(property.getCharge());
            }

            // determine if this player has the highest wealth
            if (totalWealth.compareTo(highestTotalWealth) > 0) {
                highestTotalWealth = totalWealth;
                winners.clear();
                winners.add(player);
            } else if (totalWealth.compareTo(highestTotalWealth) == 0) {
                winners.add(player);
            }
        }
        if (winners.size() > 1) {
            StringBuilder result = new StringBuilder("\nIt's a draw between: ");
            for (int i = 0; i < winners.size(); i++) {
                result.append("Player ").append(winners.get(i).getPlayerId());
                if (i < winners.size() - 1) {
                    result.append(", ");
                }
            }
            result.append(" with £").append(highestTotalWealth.setScale(2, RoundingMode.HALF_UP));
            return result.toString();
        } else {
            Player winner = winners.get(0);
            return "\nPlayer " + winner.getPlayerId() + " Won the Game with £" + highestTotalWealth.setScale(2, RoundingMode.HALF_UP) + "! Congratulations!";
        }
    }
}
