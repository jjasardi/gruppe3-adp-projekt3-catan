package ch.zhaw.catan;

import java.util.Map;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.MainGame.Read;

/**
 * This class is for all the output of the game. It uses TextIO to print the
 * output in the console.
 * 
 * @author Durim, Philipp
 */
public class Output {
    TextIO textIO = TextIoFactory.getTextIO();
    TextTerminal<?> textTerminal = textIO.getTextTerminal();

    /**
     * prints a welcome message for the game.
     */
    public void printWelcome() {
        textTerminal.println("Welcome to Siedler Game from ADP.");
    }

    /**
     * prints a general error.
     */
    public void printError() {
        textTerminal.println("Error");
    }

    /**
     * prints the question which resource the player wants to offer.
     */
    public void printTradeOffer() {
        textTerminal.println("Wich Resource do you want to offer?");
    }

    /**
     * prints the question which resource the player wants to buy.
     */
    public void printTradeBuy() {
        textTerminal.println("Wich Resource do you want to buy?");
    }

    /**
     * prints the faction name of the current player turn.
     * 
     * @param currentPlayer the current player faction
     */
    public void printCurrentPlayer(Faction currentPlayer) {
        textTerminal.println(currentPlayer.name() + "'s turn:");
    }

    /**
     * prints the request to set the road start position.
     */
    public void printRoadStart() {
        textTerminal.println("Set Position for roadStart:");
    }

    /**
     * prints the request to set the road end position.
     */
    public void printRoadEnd() {
        textTerminal.println("Set Position for roadEnd:");
    }

    /**
     * prints the request to set the position for the settlement.
     */
    public void printSettelment() {
        textTerminal.println("Set Position for Settlement:");
    }

    /**
     * prints the request to set the position for the city.
     */
    public void printCity() {
        textTerminal.println("Set Position for City: ");
    }

    /**
     * prints a general error of an action it couldn't execute.
     */
    public void printErrorMessage() {
        textTerminal.println("The action could not be successfully executed.");
    }

    /**
     * prints the request to set the position for the city.
     */
    public void printThief() {
        textTerminal.println("Set Position for Thief: ");
    }

    /**
     * prints what was rolled.
     * 
     * @param diceThrow the rolled number
     */
    public void printDice(int diceThrow) {
        textTerminal.println("You rolled: " + diceThrow);
    }

    /**
     * prints the faction of the Payer who has won the game.
     * 
     * @param faction the faction
     */
    public void printWinnter(Faction faction) {
        textTerminal.println("Congratulation!! the winner is:" + faction.name());
    }

    /**
     * returns a String depending on the command.
     * 
     * @param command the chosen command
     * @return String of the command
     */
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
     * prints all the remaining stock of the bank.
     * 
     * @param bankStock the map of the bank resource
     */
    public void printBankStock(Map<Resource, Integer> bankStock) {
        for (Resource resource : bankStock.keySet()) {
            String key = resource.toString();
            String value = bankStock.get(resource).toString();
            textTerminal.println(key + " " + value);
        }
    }

    /**
     * prints all the remaining stock of the current player.
     * 
     * @param playerStock the map of the current player resource
     */
    public void printPlayerStock(Map<Resource, Integer> playerStock) {
        for (Resource resource : playerStock.keySet()) {
            String key = resource.toString();
            String value = playerStock.get(resource).toString();
            textTerminal.println(key + " " + value);
        }
    }
}
