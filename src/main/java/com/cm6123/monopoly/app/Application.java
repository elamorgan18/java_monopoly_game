package com.cm6123.monopoly.app;

import com.cm6123.monopoly.board.Board;
import com.cm6123.monopoly.board.BoardValidator;
import com.cm6123.monopoly.dice.DiceSet;
import com.cm6123.monopoly.game.CongestionManager;
import com.cm6123.monopoly.game.CourtManager;
import com.cm6123.monopoly.game.PropertyManager;
import com.cm6123.monopoly.game.SpaceManager;
import com.cm6123.monopoly.game.GameController;
import com.cm6123.monopoly.game.Welcome;
import com.cm6123.monopoly.players.PlayerValidator;
import com.cm6123.monopoly.players.Players;
import com.cm6123.monopoly.rounds.RoundValidator;
import com.cm6123.monopoly.rounds.Rounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public final class Application {
    /**
     * Create a logger for the class.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private Application() {
    }

    /**
     * main entry point.  If args provided, result is displayed and program exists. Otherwise, user is prompted for
     * input.
     *
     * @param args command line args.
     */
    public static void main(final String[] args) {
        LOGGER.info("Application Started with args:{}", String.join(",", args));

        welcome();
        GameController gameController = initiateTheGame();
        playTheGame(gameController);
        declareTheWinner(gameController);

        LOGGER.info("Application closing");
    }
    /**
     * A method that calls the welcome message.
     */
    private static void welcome() {

        // Create a new instance of the Welcome class. This is an example of
        // how the UI gives and takes information from the game.
        Welcome welcome = new Welcome();

        // Print the welcome message to the console. The content comes from the game package.
        System.out.println(welcome.welcomeMessage());
    }
    /**
     * A method that initiates the game. Sets up the players, board and number of rounds.
     * @return gameController which controls the game.
     */
    private static GameController initiateTheGame() {

        // Get and validate the number of players.
        int numOfPlayers = getAndValidateNumberOfPlayers();

        // Creating a List of players.
        Players players = new Players(numOfPlayers);

        // Asking user for the size of gthe board.
        int sizeOfBoard = getAndValidateSizeOfBoard();

        Board board = new Board(sizeOfBoard);
        System.out.println(board.printBoardBanner());
        System.out.println(board.printBoard());
        System.out.println(board.printBoardBanner());


        // Asking user for the number of rounds they want to play.
        int numberOfRounds = getAndValidateNumberOfRounds();

        Rounds rounds = new Rounds(numberOfRounds);
        System.out.println("Number of rounds is: " + numberOfRounds);

        // Starting Game.
        // Note that we are setting the current round to one as it is the start of the game.
        DiceSet diceSet = new DiceSet(6);
        CourtManager courtManager = new CourtManager();
        CongestionManager congestionManager = new CongestionManager();
        PropertyManager propertyManager = new PropertyManager();
        SpaceManager spaceManager = new SpaceManager(courtManager, congestionManager, propertyManager);
        GameController gameController = new GameController(players, board, rounds, 1, diceSet, spaceManager);
        return gameController;
    }
    /**
     * A method that start the game.
     * @param gameController the game controller.
     */
    private static void playTheGame(final GameController gameController) {

        while (gameController.shouldGameContinue()) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Would you like the game to continue? (Y/N)");
            String continueOrNot = scanner.nextLine().trim().toUpperCase();

            if (continueOrNot.equals("Y")) {
                gameController.makeRoundOfMoves();
                gameController.getPlayers().printPlayers();
            } else if (continueOrNot.equals("N")) {
                System.out.println("Game stopped by user.");
                break;
            } else {
                System.out.println("Invalid input. Please try again.");
            }
        }
    }
    private static void declareTheWinner(final GameController gameController) {
        // declaring the winner of the game.
        String resultOfGame = gameController.getWinner();
        System.out.println("\n************");
        System.out.println("** RESULT **");
        System.out.println("************");
        System.out.println(resultOfGame);
    }
    /**
     * A method to get the number of players from the user and check that it is valid.
     * @return an int of the number of players.
     */
    private static int getAndValidateNumberOfPlayers() {

        // Creating boolean that controls the action of getting the number of players.
        boolean isValidNumberOfPlayers = false;
        PlayerValidator playerValidator = new PlayerValidator();
        String numOfPlayers = "";
        while (!isValidNumberOfPlayers) {
            System.out.println("Enter number of players. Between 2 and 10: ");
            numOfPlayers = new Scanner(System.in).nextLine();
            isValidNumberOfPlayers = playerValidator.validate(numOfPlayers);
        }

        // Convert the String to an int here. It's ok to do this as we have validated it above.
        return Integer.parseInt(numOfPlayers);
    }

    /**
     * A method to get the size of the board check that it is valid.
     * @return an int of the size of the board.
     */
    private static int getAndValidateSizeOfBoard() {
        boolean isValidSizeOfBoard = false;
        BoardValidator boardValidator = new BoardValidator();
        String sizeOfBoard = "";
        while (!isValidSizeOfBoard) {
            System.out.println("Enter size of board. Between 10 and 50: ");
            sizeOfBoard = new Scanner(System.in).nextLine();
            isValidSizeOfBoard = boardValidator.validate(sizeOfBoard);
        }

        // Convert the String to an int here. We know this is OK to do as we have validated it above.
        return Integer.parseInt(sizeOfBoard);
    }

    /**
     * A method to get the number of rounds and check that it is valid.
     * @return an int of the number of rounds.
     */
    private static int getAndValidateNumberOfRounds() {
        boolean isValidNumberOfRounds = false;
        RoundValidator roundValidator = new RoundValidator();
        String numberOfRounds = "";
        while (!isValidNumberOfRounds) {
            System.out.println("\nEnter number of rounds. Between 1 and 20: ");
            numberOfRounds = new Scanner(System.in).nextLine();
            isValidNumberOfRounds = roundValidator.validate(numberOfRounds);
        }
        return Integer.parseInt(numberOfRounds);
    }
}

