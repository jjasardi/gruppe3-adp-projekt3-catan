package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.Point;
import ch.zhaw.catan.Config.Resource;;

public class Input {
    TextIO textIO = TextIoFactory.getTextIO();

    public int getNumberOfPlayers(TextIO textIO) {
        int numberOfPlayers = textIO.newIntInputReader()
                .withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
                .withMaxVal(Config.Faction.values().length)
                .read("How many players are playing?");
        return numberOfPlayers;
    }

    public Point getPosition() {
		int x = textIO.newIntInputReader()
            .withMinVal(0)
            .withMaxVal(14)
            .read("Please enter x-coordinate:");
		
        int y = textIO.newIntInputReader()
            .withMinVal(0)
            .withMaxVal(22)
            .read("Please enter y-coordinate:");
		
        return new Point(x, y);
	}

    public <T extends Enum<T>> T getTradeOffer(TextIO textIO, Class<T> commands) {
        return textIO.newEnumInputReader(commands).read("Which Resource do you offer?");
      }
}
