package ch.zhaw.catan;

/**
 * This class represents two Dice with a range from 1 to 6. It can return a
 * random number within the range.
 * 
 * @author Philipp
 */
public class Dice {

    private static final int OFFSET = 1;
    private static final int DICE_NUMBER_OF_EYES = 6;

    /**
     * This method simulates rolling a pair of dice.
     * 
     * @return random number between 2-12
     */
    public static int rollWithTwoDice() {
        int diceOne;
        int diceTwo;

        diceOne = (int) ((Math.random() * DICE_NUMBER_OF_EYES) + OFFSET);
        diceTwo = (int) ((Math.random() * DICE_NUMBER_OF_EYES) + OFFSET);

        int dicethrow = diceOne + diceTwo;

        return dicethrow;
    }
}
