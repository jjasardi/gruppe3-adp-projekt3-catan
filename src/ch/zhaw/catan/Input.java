package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import java.awt.Point;

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
            .read("Please enter x-coordinate:");
		
        int y = textIO.newIntInputReader()
            .read("Please enter y-coordinate:");
		
        return new Point(x, y);
	}
}
