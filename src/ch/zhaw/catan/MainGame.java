package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;
import ch.zhaw.catan.Config.Resource;

import java.awt.Point;

public class MainGame {
    private Input input;
    private Output output;
    private TextIO textIO;
    private TextTerminal<?> textTerminal;
    private SiedlerGame siedlerGame;
    private int playerCount;
    private static final int POINTS_TO_WIN = 7;

    public enum Actions {
        SHOW, TRADE, BUILD, BANK_STOCK, MY_STOCK, END
    }

    public enum Building {
        ROAD, SETTELMENT, CITY
    }

    public static void main(String[] args) {
        new MainGame().run();
    }

    private void run() {
        firstPhase();
        secondTestPhase();
        //secondPhase();
        thirdPhase();

    }

    private void firstPhase() {
        textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        input = new Input();
        playerCount = input.getNumberOfPlayers(textIO);
        siedlerGame = new SiedlerGame(POINTS_TO_WIN, playerCount);
        output = new Output();

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
            output.printRoad();
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
            output.printRoad();
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
        while (siedlerGame.getWinner() == null) {
            output.printCurrentPlayer(siedlerGame.getCurrentPlayerFaction());
            // throwdice
            commands();
            siedlerGame.switchToNextPlayer();
        }

    }

    private void commands() {
        boolean running = true;
        while (running) {
            switch (input.getEnumValue(textIO, Actions.class)) {
                case SHOW:
                    textTerminal.println(siedlerGame.getView().toString());
                    break;
                case TRADE:
                    tradeResource();
                    break;
                case BUILD:
                    build();
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
        Resource offer = input.getTradeOffer(textIO, Config.Resource.class);
        switch (input.getResourceValue(textIO, Config.Resource.class)) {
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
        switch (input.getBuildingValue(textIO, Building.class)) {
            case ROAD:
                output.printRoad();
                Point roadStart = input.getPosition();
                output.printRoad();
                Point roadEnd = input.getPosition();
                if (!siedlerGame.buildRoad(roadStart, roadEnd)) {
                    output.printError();
                }
                break;
            case SETTELMENT:
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
