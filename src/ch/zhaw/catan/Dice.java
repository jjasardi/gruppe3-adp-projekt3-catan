package ch.zhaw.catan;

public class Dice {
    private int dicethrow;

    public int getDiceThrow() {
        return rollWithTwoDice();
    }

    /**
   * This method simulates rolling a pair of dice.
   */
  private int rollWithTwoDice() {
    int diceOne;
    int diceTwo;

    diceOne = (int) ((Math.random() * 6) + 1); // magic number?
    diceTwo = (int) ((Math.random() * 6) + 1);

    dicethrow = diceOne + diceTwo;

    return dicethrow;
  }
}
