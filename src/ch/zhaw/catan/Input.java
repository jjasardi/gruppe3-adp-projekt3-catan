package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.Point;

public class Input {
    TextIO textIO = TextIoFactory.getTextIO();
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_X_COORDINATE = 14;
    private static final int MAX_Y_COORDINATE = 22;

    /**
     * @param textIO
     * @return int
     */
    public int getNumberOfPlayers(TextIO textIO) {
        int numberOfPlayers = textIO.newIntInputReader().withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
                .withMaxVal(Config.Faction.values().length).read("How many players are playing?");
        return numberOfPlayers;
    }

    /**
     * @return Point
     */
    public Point getPosition() {
        int x = textIO.newIntInputReader().withMinVal(MIN_COORDINATE).withMaxVal(MAX_X_COORDINATE)
                .read("Please enter x-coordinate:");

        int y = textIO.newIntInputReader().withMinVal(MIN_COORDINATE).withMaxVal(MAX_Y_COORDINATE)
                .read("Please enter y-coordinate:");
        return new Point(x, y);
    }

    /**
     * @param textIO
     * @param commands
     * @return T
     */
    public <T extends Enum<T>> T getTradeOffer(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("Which Resource do you offer?");
    }

    /**
     * @param textIO
     * @param commands
     * @return T
     */
    public <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    /**
     * @param textIO
     * @param commands
     * @return T
     */
    public <T extends Enum<T>> T getResourceValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("Which Resource do you want to buy?");
    }

    /**
     * @param textIO
     * @param commands
     * @return T
     */
    public <T extends Enum<T>> T getBuildingValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What do you want to build?");
    }
}
