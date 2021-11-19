package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SiedlerBoard extends HexBoard<Land, String, String, String> {


    //TODO: Add fields, constructors and methods as you see fit. Do NOT change the signature
    //      of the methods below.
	public SiedlerBoard() {
		setBoard();
	}
	
	private void setBoard() {
		addField(new Point(4, 2), Land.WATER);
	    addField(new Point(6, 2), Land.WATER);
	    addField(new Point(8, 2), Land.WATER);
	    addField(new Point(10, 2), Land.WATER);
	    addField(new Point(3, 5), Land.WATER);
	    addField(new Point(11, 5), Land.WATER);
	    addField(new Point(2, 8), Land.WATER);
	    addField(new Point(12, 8), Land.WATER);
	    addField(new Point(1, 11), Land.WATER);
	    addField(new Point(13, 11), Land.WATER);
	    addField(new Point(2, 14), Land.WATER);
	    addField(new Point(12, 14), Land.WATER);
	    addField(new Point(3, 17), Land.WATER);
	    addField(new Point(11, 17), Land.WATER);
	    addField(new Point(4, 20), Land.WATER);
	    addField(new Point(6, 20), Land.WATER);
	    addField(new Point(8, 20), Land.WATER);
	    addField(new Point(10, 20), Land.WATER);
	    
	    addField(new Point(5, 5), Land.FOREST);  
	    addField(new Point(7, 5), Land.PASTURE);
	    addField(new Point(9, 5), Land.PASTURE);
	    
	    addField(new Point(4, 8), Land.FIELDS);
	    addField(new Point(6, 8), Land.MOUNTAIN);
	    addField(new Point(8, 8), Land.FIELDS);
	    addField(new Point(10, 8), Land.FOREST);
	    
	    addField(new Point(3, 11), Land.FOREST);
	    addField(new Point(5, 11), Land.HILLS);
	    addField(new Point(7, 11), Land.DESERT);
	    addField(new Point(9, 11), Land.MOUNTAIN);
	    addField(new Point(11, 11), Land.FIELDS);
	    
	    addField(new Point(4, 14), Land.FIELDS);
	    addField(new Point(6, 14), Land.MOUNTAIN);
	    addField(new Point(8, 14), Land.FOREST);
	    addField(new Point(10, 14), Land.PASTURE);
	    
	    addField(new Point(5, 17), Land.HILLS);
	    addField(new Point(7, 17), Land.PASTURE);
	    addField(new Point(9, 17), Land.HILLS);

	    Map<Point, Label> lowerFieldLabel = new HashMap<>();
	    lowerFieldLabel.put(new Point(5, 5), new Label('0', '6'));
	    lowerFieldLabel.put(new Point(7, 5), new Label('0', '3'));
	    lowerFieldLabel.put(new Point(9, 5), new Label('0', '8'));
	    
	    lowerFieldLabel.put(new Point(4, 8), new Label('0', '2'));
	    lowerFieldLabel.put(new Point(6, 8), new Label('0', '4'));
	    lowerFieldLabel.put(new Point(8, 8), new Label('0', '5'));
	    lowerFieldLabel.put(new Point(10, 8), new Label('1', '0'));
	    
	    lowerFieldLabel.put(new Point(3, 11), new Label('0', '5'));
	    lowerFieldLabel.put(new Point(5, 11), new Label('0', '9'));
	    lowerFieldLabel.put(new Point(7, 11), new Label('0', '7'));
	    lowerFieldLabel.put(new Point(9, 11), new Label('0', '6'));
	    lowerFieldLabel.put(new Point(11, 11), new Label('0', '9'));
	    
	    lowerFieldLabel.put(new Point(4, 14), new Label('1', '0'));
	    lowerFieldLabel.put(new Point(6, 14), new Label('1', '1'));
	    lowerFieldLabel.put(new Point(8, 14), new Label('0', '3'));
	    lowerFieldLabel.put(new Point(10, 14), new Label('1', '2'));
	    
	    lowerFieldLabel.put(new Point(5, 17), new Label('0', '8'));
	    lowerFieldLabel.put(new Point(7, 17), new Label('0', '4'));
	    lowerFieldLabel.put(new Point(9, 17), new Label('1', '1'));
	}
	
    /**
     * Returns the fields associated with the specified dice value.
     *
     * @param dice the dice value
     * @return the fields associated with the dice value
     */
    public List<Point> getFieldsForDiceValue(int dice) {
        //TODO: Implement.
        return Collections.emptyList();
    }

    /**
     * Returns the {@link Land}s adjacent to the specified corner.
     *
     * @param corner the corner
     * @return the list with the adjacent {@link Land}s
     */
    public List<Land> getLandsForCorner(Point corner) {
        //TODO: Implement.
        return Collections.emptyList();
    }
}
