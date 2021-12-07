package ch.zhaw.catan;

import java.util.HashMap;
import java.util.Map;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

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

    public void printBankStock(Map<Resource, Integer> bankStock) {
        for (Resource resource: bankStock.keySet()) {
            String key = resource.toString();
            String value = bankStock.get(resource).toString();
            textTerminal.println(key + " " + value);
        }
    }

    public void printPlayerStock(Map<Resource, Integer> playerStock) {
        for (Resource resource: playerStock.keySet()) {
            String key = resource.toString();
            String value = playerStock.get(resource).toString();
            textTerminal.println(key + " " + value);
        } textTerminal.println("Test"); // TODO: remove
    }
}
