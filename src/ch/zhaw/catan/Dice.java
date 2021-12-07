package ch.zhaw.catan;

public class Dice {
    private int dicethrow;

    private static final int OFFSET = 1;
    private static final int DICE_NUMBER_OF_EYES = 6;

    public int getDiceThrow() {
        return rollWithTwoDice();
    }

    /**
   * This method simulates rolling a pair of dice.
   */
  private int rollWithTwoDice() {
    int diceOne;
    int diceTwo;

    diceOne = (int) ((Math.random() * DICE_NUMBER_OF_EYES) + OFFSET); // magic number?
    diceTwo = (int) ((Math.random() * DICE_NUMBER_OF_EYES) + OFFSET);

    dicethrow = diceOne + diceTwo;

    return dicethrow;
  }
}
