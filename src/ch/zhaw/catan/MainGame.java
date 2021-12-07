package ch.zhaw.catan;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.catan.Config.Faction;
import java.awt.Point;

public class MainGame {
    private Input input;
    private TextIO textIO;
    private TextTerminal<?> textTerminal;
    private SiedlerGame siedlerGame;
    private int playerCount;

    public enum Actions {
        SHOW, TRADE, BUILD, END
    }

    public enum Buildins {
        ROAD, SETTELMENT, CITY
    }

    private void run() {
        firstPhase();
        commands();
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
        playerCount = input.getNumberOfPlayers(textIO);
        siedlerGame = new SiedlerGame(5, playerCount); // Magic Numbers

    }

    private void secondPhase() {
        for (int player = 1; player <= playerCount; player++) {
            Point position = input.getPosition();
            siedlerGame.placeInitialSettlement(position, false);
            Point roadEnd = input.getPosition();
            siedlerGame.placeInitialRoad(position, roadEnd);
            siedlerGame.switchToNextPlayer();
        }
        for (int player = 1; player <= playerCount; player++) {
            siedlerGame.switchToPreviousPlayer();
            Point position = input.getPosition();
            siedlerGame.placeInitialSettlement(position, false);
            Point roadEnd = input.getPosition();
            siedlerGame.placeInitialRoad(position, roadEnd);
        }

    }

    private void thirdPhase() {

    }

    private void commands() {
        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, Actions.class)) {
                case SHOW:
                    textTerminal.println(siedlerGame.getView().toString());
                    break;
                case TRADE:
                    // System.out.println(bank.getBankStock().values());
                    break;
                case BUILD:
                    running = false;
                    break;
                case END:
                    running = false;
                    break;
                default:
                    throw new IllegalStateException("Internal error found - Command not implemented.");
            }
        }
        textIO.dispose();
    }

    private static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    private static Point inputPosition(TextIO textIO) {
        int x = textIO.newIntInputReader().withMinVal(0).withMaxVal(14).read("x Position?");
        int y = textIO.newIntInputReader().withMinVal(0).withMaxVal(22).read("y Position?");
        Point point = new Point (x, y);
        return point;
    }

    private static int setPlayerCount(TextIO textIO) {
        return textIO.newIntInputReader().withMinVal(2).withMaxVal(4).read("Wieviele Spieler? 2-4");
    }

    public static void main(String[] args) {
        new MainGame().run();
    }
}
