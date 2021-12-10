package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Structure;

import java.awt.Point;

/**
 * This class represents the the different phases of the game and is controlling
 * the game. It starts the game and goes through all the different phases until
 * one player wins the game. Execute the {@link #main(String[])} method to start
 * the game.
 * 
 * @author Durim, Ardi, Philipp
 */

public class MainGame {
    private Input input;
    private Output output;
    private TextIO textIO;
    private TextTerminal<?> textTerminal;
    private SiedlerGame siedlerGame;
    private int playerCount;
    private static final int POINTS_TO_WIN = 7;

    /**
     * This {@link Enum} specifies the available commands in the game.
     * 
     * @author Durim
     */
    public enum Actions {
        SHOW, TRADE, BUILD, STRUCTURE_COST, BANK_STOCK, MY_STOCK, END
    }

    /**
     * This {@link Enum} specifies the Strings that are used for the InputReader.
     * 
     * @author Durim
     */
    public enum Read {
        COMMAND, BUY, OFFER, BUILD
    }

    /**
     * main method
     * 
     * @param args Eingabe vom Typ String
     */
    public static void main(String[] args) {
        new MainGame().run();
    }

    /**
     * Starts the game and goes through the 3 phases until a player wins the game.
     */
    private void run() {
        firstPhase();
        secondTestPhase();
        // secondPhase();
        thirdPhase();

    }

    private void firstPhase() {
        textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        input = new Input();
        output = new Output();
        output.printWelcome();
        playerCount = input.getNumberOfPlayers(textIO);
        siedlerGame = new SiedlerGame(POINTS_TO_WIN, playerCount);

    }

    private void secondPhase() {
        secondPhaseOne();
        secondPhaseTwo();
    }

    private void secondTestPhase() {
        playerCount = 2;
        Point settlement = new Point(8, 4);
        siedlerGame.placeInitialSettlement(settlement, false);
        Point roadEnd = new Point(8, 6);
        siedlerGame.placeInitialRoad(settlement, roadEnd);
        siedlerGame.switchToNextPlayer();

        Point settlement2 = new Point(9, 15);
        siedlerGame.placeInitialSettlement(settlement2, false);
        Point roadEnd2 = new Point(9, 13);
        siedlerGame.placeInitialRoad(settlement2, roadEnd2);
        Point settlement3 = new Point(5, 15);
        siedlerGame.placeInitialSettlement(settlement3, true);
        Point roadEnd3 = new Point(6, 16);
        siedlerGame.placeInitialRoad(settlement3, roadEnd3);
        siedlerGame.switchToPreviousPlayer();

        Point settlement4 = new Point(5, 7);
        siedlerGame.placeInitialSettlement(settlement4, true);
        Point roadEnd4 = new Point(5, 9);
        siedlerGame.placeInitialRoad(settlement4, roadEnd4);
        siedlerGame.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        siedlerGame.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        siedlerGame.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        siedlerGame.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        siedlerGame.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
    }

    private void secondPhaseOne() {
        for (int player = 1; player <= playerCount; player++) {
            textTerminal.println(siedlerGame.getView().toString());
            output.printCurrentPlayer(siedlerGame.getCurrentPlayerFaction());
            output.printSettelment();
            Point settlement = input.getPosition();
            if (!siedlerGame.placeInitialSettlement(settlement, false)) {
                boolean success = false;
                while (!success) {
                    output.printError();
                    settlement = input.getPosition();
                    success = siedlerGame.placeInitialSettlement(settlement, false);
                }
            }
            output.printRoadEnd();
            Point roadEnd = input.getPosition();
            if (!siedlerGame.placeInitialRoad(settlement, roadEnd)) {
                boolean success = false;
                while (!success) {
                    output.printError();
                    roadEnd = input.getPosition();
                    success = siedlerGame.placeInitialRoad(settlement, roadEnd);
                }
            }
            siedlerGame.switchToNextPlayer();
        }
    }

    private void secondPhaseTwo() {
        for (int player = 1; player <= playerCount; player++) {
            textTerminal.println(siedlerGame.getView().toString());
            siedlerGame.switchToPreviousPlayer();
            output.printCurrentPlayer(siedlerGame.getCurrentPlayerFaction());
            output.printSettelment();
            Point settlement = input.getPosition();
            if (!siedlerGame.placeInitialSettlement(settlement, true)) {
                boolean success = false;
                while (!success) {
                    output.printError();
                    settlement = input.getPosition();
                    success = siedlerGame.placeInitialSettlement(settlement, true);
                }
            }
            output.printRoadEnd();
            Point roadEnd = input.getPosition();
            if (!siedlerGame.placeInitialRoad(settlement, roadEnd)) {
                boolean success = false;
                while (!success) {
                    output.printError();
                    roadEnd = input.getPosition();
                    success = siedlerGame.placeInitialRoad(settlement, roadEnd);
                }
            }
        }
    }

    private void thirdPhase() {
        int diceThrow = 0;
        while (siedlerGame.getWinner() == null) {
            output.printCurrentPlayer(siedlerGame.getCurrentPlayerFaction());
            diceThrow = Dice.rollWithTwoDice();
            diceThrow = 8;
            output.printDice(diceThrow);
            siedlerGame.throwDice(diceThrow);
            if (diceThrow == 7) {
                output.printThief();
                Point field = input.getPosition();
                while (!siedlerGame.placeThiefAndStealCard(field)) {
                    output.printError();
                    output.printThief();
                    field = input.getPosition();
                }
            }
            commands();
            if (siedlerGame.getWinner() != null) {
                output.printWinnter(siedlerGame.getCurrentPlayerFaction());
            }
            siedlerGame.switchToNextPlayer();
        }

    }

    private void commands() {
        boolean running = true;
        while (running) {
            switch (input.getClassInput(textIO, Actions.class, output.getInputReadString(Read.COMMAND))) {
            case SHOW:
                textTerminal.println(siedlerGame.getView().toString());
                textTerminal.println(siedlerGame.getThiefPositionAsString() + "\n");
                break;
            case TRADE:
                tradeResource();
                break;
            case BUILD:
                build();
                break;
            case STRUCTURE_COST:
                output.printStructuresCost();
                break;
            case BANK_STOCK:
                output.printBankStock(siedlerGame.getBankStock());
                break;
            case MY_STOCK:
                output.printPlayerStock(siedlerGame.getCurrentPlayerStock());
                break;
            case END:
                running = false;
                break;
            default:
                throw new IllegalStateException("Internal error found - Command not implemented.");
            }
        }
    }

    private void tradeResource() {
        Resource offer = input.getClassInput(textIO, Config.Resource.class, output.getInputReadString(Read.OFFER));
        switch (input.getClassInput(textIO, Config.Resource.class, output.getInputReadString(Read.BUY))) {
        case GRAIN:
            if (!siedlerGame.tradeWithBankFourToOne(offer, Resource.GRAIN)) {
                output.printError();
            }
            break;
        case WOOL:
            if (!siedlerGame.tradeWithBankFourToOne(offer, Resource.WOOL)) {
                output.printError();
            }
            break;
        case LUMBER:
            if (!siedlerGame.tradeWithBankFourToOne(offer, Resource.LUMBER)) {
                output.printError();
            }
            break;
        case ORE:
            if (!siedlerGame.tradeWithBankFourToOne(offer, Resource.ORE)) {
                output.printError();
            }
            break;
        case BRICK:
            if (!siedlerGame.tradeWithBankFourToOne(offer, Resource.BRICK)) {
                output.printError();
            }
            break;
        }
    }

    private void build() {
        switch (input.getClassInput(textIO, Structure.class, output.getInputReadString(Read.BUILD))) {
        case ROAD:
            output.printRoadStart();
            Point roadStart = input.getPosition();
            output.printRoadEnd();
            Point roadEnd = input.getPosition();
            if (!siedlerGame.buildRoad(roadStart, roadEnd)) {
                output.printError();
            }
            break;
        case SETTLEMENT:
            output.printSettelment();
            Point positionSettelment = input.getPosition();
            if (!siedlerGame.buildSettlement(positionSettelment)) {
                output.printError();
            }
            break;
        case CITY:
            output.printCity();
            Point positionCity = input.getPosition();
            if (!siedlerGame.buildCity(positionCity)) {
                output.printError();
            }
            break;
        }
    }
}
