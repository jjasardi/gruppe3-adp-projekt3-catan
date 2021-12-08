package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.Point;

public class Input {
    TextIO textIO = TextIoFactory.getTextIO();
    Validation valid;
    private static final int MIN_COORDINATE = 0;
    private static final int MAX_X_COORDINATE = 14;
    private static final int MAX_Y_COORDINATE = 22;

    public int getNumberOfPlayers(TextIO textIO) {
        int numberOfPlayers = textIO.newIntInputReader()
                .withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
                .withMaxVal(Config.Faction.values().length)
                .read("How many players are playing?");
        return numberOfPlayers;
    }

    public Point getPosition() {
        int x = textIO.newIntInputReader()
                .withMinVal(MIN_COORDINATE)
                .withMaxVal(MAX_X_COORDINATE)
                .read("Please enter x-coordinate:");

        int y = textIO.newIntInputReader()
                .withMinVal(MIN_COORDINATE)
                .withMaxVal(MAX_Y_COORDINATE)
                .read("Please enter y-coordinate:");
        return new Point(x, y);
    }

    public <T extends Enum<T>> T getTradeOffer(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("Which Resource do you offer?");
    }

    public <T extends Enum<T>> T getEnumValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What would you like to do?");
    }

    public <T extends Enum<T>> T getResourceValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("Which Resource do you want to buy?");
    }

    public <T extends Enum<T>> T getBuildingValue(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("What do you want to build?");
    }
}
