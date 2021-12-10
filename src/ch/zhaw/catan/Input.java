package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.Point;

/**
 * This class is for all the input of the game. It uses TextIO to read the input
 * from the console.
 * 
 * @author Durim, Philipp
 */
public class Input {
    TextIO textIO = TextIoFactory.getTextIO();
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_X_COORDINATE = 14;
    private static final int MAX_Y_COORDINATE = 22;

    /**
     * Ask how many players are playing the game. Only allows min 2 player and max
     * lenght of {@link Config.Faction}.
     * 
     * @param textIO TODO:?
     * @return the amount of players playing the game
     */
    public int getNumberOfPlayers(TextIO textIO) {
        int numberOfPlayers = textIO.newIntInputReader().withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
                .withMaxVal(Config.Faction.values().length).read("How many players are playing?");
        return numberOfPlayers;
    }

    /**
     * Ask for the x-coordinate and y-coordinate.
     * 
     * @return Point of the coordinates
     */
    public Point getPosition() {
        int x = textIO.newIntInputReader().withMinVal(MIN_COORDINATE).withMaxVal(MAX_X_COORDINATE)
                .read("Please enter x-coordinate:");

        int y = textIO.newIntInputReader().withMinVal(MIN_COORDINATE).withMaxVal(MAX_Y_COORDINATE)
                .read("Please enter y-coordinate:");
        return new Point(x, y);
    }

    /**
     * Ask for the command. TODO: finish javaDoc
     * 
     * @param textIO
     * @param commands the {@link Enum} of commands
     * @return T
     */
    public <T extends Enum<T>> T getClassInput(TextIO textIO, Class<T> commands, String output) {
        return textIO.newEnumInputReader(commands).read(output);
    }
}
