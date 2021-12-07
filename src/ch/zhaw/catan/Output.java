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

    public void printTradeOffer() {
        textTerminal.println("Wich Resource do you want to offer?");  
    }

    public void printTradeBuy() {
        textTerminal.println("Wich Resource do you want to buy?");  
    }

    public void printCurrentPlayer(Faction currentPlayer) {
        textTerminal.println(currentPlayer.name() + "'s turn:"); 
    }

    public void printRoad() {
        textTerminal.println("Position for Road?"); 
    }

    public void printSettelment() {
        textTerminal.println("Position for Settelment?"); 
    }

    public void printCity() {
        textTerminal.println("Position for City?"); 
    }

    public void printErrorMessage() {
        textTerminal.println("The action could not be successfully executed.");
    }

    public void printWinnter(Faction faction) {
        textTerminal.println("Congratulation!! the winner is:" + faction.name());
    }
}
