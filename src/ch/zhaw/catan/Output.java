package ch.zhaw.catan;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.catan.Config.Faction;

public class Output {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public void printWelcome() {
        textTerminal.println("Welcome to Siedler Game from ADP.");        
    }

    public void printDieceNumber(int dicethrow) {
        textTerminal.println("It was rolled: " + dicethrow); 
    }

    public void printErrorMessage() {
        textTerminal.println("The action could not be successfully executed.");
    }

    public void printWinnter(Faction faction) {
        textTerminal.println("Congratulation!! the winner is:" + faction.name());
    }
}
