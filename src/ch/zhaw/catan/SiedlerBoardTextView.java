package ch.zhaw.catan;

import ch.zhaw.hexboard.Label;
import java.awt.Point;
import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoardTextView;
import java.util.HashMap;
import java.util.Map;

public class SiedlerBoardTextView extends HexBoardTextView<Land, Building, Road, String> {

  public SiedlerBoardTextView(SiedlerBoard board) {
    super(board);
    setDiceValues();
  }

  private void setDiceValues() {
    Map<Point, Label> lowerFieldLabel = new HashMap<>();
    for (Map.Entry<Point, Integer> index : Config.getStandardDiceNumberPlacement().entrySet()) {
      char char1 = '0';
      char char2 = '0';
      char[] chars = ("" + index.getValue()).toCharArray();
      if (index.getValue() > 9) {
        char1 = chars[0];
        char2 = chars[1];
      } else {
        char2 = chars[0];
      }
      lowerFieldLabel.put(new Point(index.getKey()), new Label(char1, char2));
    }
    for (Map.Entry<Point, Label> e : lowerFieldLabel.entrySet()) {
      setLowerFieldLabel(e.getKey(), e.getValue());
    }
  }
}
