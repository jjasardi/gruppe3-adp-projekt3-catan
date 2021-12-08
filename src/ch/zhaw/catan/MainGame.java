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
        secondPhase();
        thirdPhase();

    }

    private void firstPhase() {
        textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        input = new Input();
        playerCount = input.getNumberOfPlayers(textIO);
        siedlerGame = new SiedlerGame(5, playerCount); // Magic Numbers
        output = new Output();

    }

    private void secondPhase() {
        secondPhaseOne();
        secondPhaseTwo();              
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
                siedlerGame.tradeWithBankFourToOne(offer, Resource.GRAIN);
                break;
            case WOOL:
                siedlerGame.tradeWithBankFourToOne(offer, Resource.WOOL);
                break;
            case LUMBER:
                siedlerGame.tradeWithBankFourToOne(offer, Resource.LUMBER);
                break;
            case ORE:
                siedlerGame.tradeWithBankFourToOne(offer, Resource.ORE);
                break;
            case BRICK:
                siedlerGame.tradeWithBankFourToOne(offer, Resource.BRICK);
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
                siedlerGame.buildRoad(roadStart, roadEnd);
                break;
            case SETTELMENT:
                output.printSettelment();
                Point positionSettelment = input.getPosition();
                siedlerGame.buildSettlement(positionSettelment);
                break;
            case CITY:
                output.printCity();
                Point positionCity = input.getPosition();
                siedlerGame.buildCity(positionCity);
                break;
        }
    }
}
