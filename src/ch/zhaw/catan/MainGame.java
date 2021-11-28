package ch.zhaw.catan;

import java.util.ArrayList;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;

import ch.zhaw.catan.Config.Faction;

public class MainGame {
    private List<Player> playerList;

    public MainGame() {
        playerList = new ArrayList<>();
    }

    private void run() {
        foundationPhase();

    }

    private void foundationPhase() {
        TextIO textIO = TextIoFactory.getTextIO();
        Faction arrayFaction[] = Faction.values();
        int numberOfPlayers = setNumberOfPlayers(textIO);
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new Player(arrayFaction[i]));
        }
    }

    public int setNumberOfPlayers(TextIO textIO) {
        int numberOfPlayers = textIO.newIntInputReader()
                .withMinVal(Config.MIN_NUMBER_OF_PLAYERS)
                .withMaxVal(Config.Faction.values().length)
                .read("How many players are playing?");
        return numberOfPlayers;
    }

    public static void main(String[] args) {
        new MainGame().run();
    }
}
