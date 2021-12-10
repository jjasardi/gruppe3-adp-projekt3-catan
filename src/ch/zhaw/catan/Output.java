package ch.zhaw.catan;

import java.util.HashMap;
import java.util.Map;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.MainGame.Read;

public class Output {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal<?> textTerminal = textIO.getTextTerminal();

    public void printWelcome() {
        textTerminal.println("Welcome to Siedler Game from ADP.");
    }

    /**
     * @param dicethrow
     */
    public void printDieceNumber(int dicethrow) {
        textTerminal.println("It was rolled: " + dicethrow);
    }

    public void printError() {
        textTerminal.println("Error");
    }

    public void printTradeOffer() {
        textTerminal.println("Wich Resource do you want to offer?");
    }

    public void printTradeBuy() {
        textTerminal.println("Wich Resource do you want to buy?");
    }

    /**
     * @param currentPlayer
     */
    public void printCurrentPlayer(Faction currentPlayer) {
        textTerminal.println(currentPlayer.name() + "'s turn:");
    }

    public void printRoadStart() {
        textTerminal.println("Position for roadStart?");
    }

    public void printRoadEnd() {
        textTerminal.println("Position for roadEnd?");
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

    public void printThief() {
        textTerminal.println("Position for Thief?");
    }

    /**
     * @param diceThrow
     */
    public void printDice(int diceThrow) {
        textTerminal.println("You rolled: " + diceThrow);
    }

    /**
     * @param faction
     */
    public void printWinnter(Faction faction) {
        textTerminal
                .println("Congratulation!! the winner is:" + faction.name());
    }

    public String getInputReadString(Read command) {
        if (command.equals(Read.OFFER)) {
            return "Which Resource do you offer?";
        } else if (command.equals(Read.COMMAND)) {
            return "What would you like to do?";
        } else if (command.equals(Read.BUY)) {
            return "Which Resource do you want to buy?";
        } else if (command.equals(Read.BUILD)) {
            return "What do you want to build?";
        } else
            return "huh?";
    }

    /**
     * @param bankStock
     */
    public void printBankStock(Map<Resource, Integer> bankStock) {
        for (Resource resource : bankStock.keySet()) {
            String key = resource.toString();
            String value = bankStock.get(resource).toString();
            textTerminal.println(key + " " + value);
        }
    }

    /**
     * @param playerStock
     */
    public void printPlayerStock(Map<Resource, Integer> playerStock) {
        for (Resource resource : playerStock.keySet()) {
            String key = resource.toString();
            String value = playerStock.get(resource).toString();
            textTerminal.println(key + " " + value);
        }
    }
}
