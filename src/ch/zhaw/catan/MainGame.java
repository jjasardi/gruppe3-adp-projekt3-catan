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
        SHOW, TRADE, BUILD, END
    }

    public enum Building {
        ROAD, SETTELMENT, CITY
    }

    private void run() {
        firstPhase();
        secondPhase();
        thirdPhase();

    }

    /*
     * private void foundationPhase() {
     * TextIO textIO = TextIoFactory.getTextIO();
     * Faction arrayFaction[] = Faction.values();
     * int numberOfPlayers = setNumberOfPlayers(textIO);
     * for (int i = 0; i < numberOfPlayers; i++) {
     * playerList.add(new Player(arrayFaction[i]));
     * }
     * }
     * 
     * 
     * public int setNumberOfPlayers(TextIO textIO) {
     * int numberOfPlayers = textIO.newIntInputReader()
     * .withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
     * .withMaxVal(Config.Faction.values().length)
     * .read("How many players are playing?");
     * return numberOfPlayers;
     * }
     */

    private void firstPhase() {
        textIO = TextIoFactory.getTextIO();
        textTerminal = textIO.getTextTerminal();
        input = new Input();
        output = new Output();
        playerCount = input.getNumberOfPlayers(textIO);
        siedlerGame = new SiedlerGame(5, playerCount); // Magic Numbers

    }

    private void secondPhase() {
        for (int player = 1; player <= playerCount; player++) {
            output.printCurrentPlayer(siedlerGame.getCurrentPlayerFaction());
            output.printSettelment();
            Point position = input.getPosition();
            siedlerGame.placeInitialSettlement(position, false);
            output.printRoad();
            siedlerGame.placeInitialRoad(position, input.getPosition());
            siedlerGame.switchToNextPlayer();
        }
        for (int player = 1; player <= playerCount; player++) {
            siedlerGame.switchToPreviousPlayer();
            output.printCurrentPlayer(siedlerGame.getCurrentPlayerFaction());
            output.printSettelment();
            Point position = input.getPosition();
            siedlerGame.placeInitialSettlement(position, false);
            output.printRoad();
            siedlerGame.placeInitialRoad(position, input.getPosition());
        }
    }

    private void thirdPhase() {
        while (siedlerGame.getWinner() == null) {
            //throwdice
            commands();
            siedlerGame.switchToNextPlayer();
        }

    }

    private void commands() {
        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, Actions.class)) {
                case SHOW:
                    textTerminal.println(siedlerGame.getView().toString());
                    break;
                case TRADE:
                    tradeResource();
                    break;
                case BUILD:
                    build();
                    break;
                case END:
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Internal error found - Command not implemented.");
            }
        }
    }

    private static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    private static <T extends Enum<T>> T getResourceValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("Which Resource do you want to buy?");
    }

    private static <T extends Enum<T>> T getBuildingValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What do you want to build?");
    }

    private void tradeResource() {
        Resource offer = input.getTradeOffer(textIO, Config.Resource.class);
        output.printTradeBuy();
        switch (getResourceValue(textIO, Config.Resource.class)) {
            case GRAIN:
                siedlerGame.tradeWithBankFourToOne(offer, Resource.GRAIN);
                break;
            case WOOL:
                siedlerGame.tradeWithBankFourToOne(Resource.GRAIN, Resource.WOOL);
                break;
            case LUMBER:
                siedlerGame.tradeWithBankFourToOne(Resource.GRAIN, Resource.LUMBER);
                break;
            case ORE:
                siedlerGame.tradeWithBankFourToOne(Resource.GRAIN, Resource.ORE);
                break;
            case BRICK:
                siedlerGame.tradeWithBankFourToOne(Resource.GRAIN, Resource.BRICK);
                break;
        }
    }

    private void build() {
        switch (getBuildingValue(textIO, Building.class)) {
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
    public static void main(String[] args) {
        new MainGame().run();
    }
}
