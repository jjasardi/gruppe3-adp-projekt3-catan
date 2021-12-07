package ch.zhaw.catan;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.catan.Config.Faction;

public class MainGame {
    TextIO textIO;
    TextTerminal<?> textTerminal;
    SiedlerGame siedlerGame;

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
        siedlerGame = new SiedlerGame(5, 4); // Magic Numbers

    }

    private void secondPhase() {

    }

    private void thirdPhase() {

    }

    private void commands() {
        boolean running = true;
        while (running) {
            switch (getEnumValue(textIO, Actions.class)) {
                case SHOW:
                    textTerminal.println(siedlerGame.getBoard().toString());
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

    public static <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    public static void main(String[] args) {
        new MainGame().run();
    }
}
