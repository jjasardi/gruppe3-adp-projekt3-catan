package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.catan.Config.Structure;
import ch.zhaw.hexboard.EdgeTest;
import ch.zhaw.hexboard.HexBoard;
import ch.zhaw.hexboard.Label;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SiedlerBoard extends HexBoard<Land, Settlement, Road, String> {

	private ArrayList<Building> buildingList;
	private ArrayList<Road> roadList;

	// TODO: Add fields, constructors and methods as you see fit. Do NOT change the
	// signature
	// of the methods below.
	public SiedlerBoard() {
		buildingList = new ArrayList<>();
		roadList = new ArrayList<>();
		setBoard();
	}

	private void setBoard() {
		for (Entry<Point, Land> index : Config.getStandardLandPlacement().entrySet()) {
			addField(index.getKey(), index.getValue());
		}
	}

	/**
	 * Returns the fields associated with the specified dice value.
	 *
	 * @param dice the dice value
	 * @return the fields associated with the dice value
	 */
	public List<Point> getFieldsForDiceValue(int dice) {
		// TODO: Implement.
		return Collections.emptyList();
	}

	/**
	 * Returns the {@link Land}s adjacent to the specified corner.
	 *
	 * @param corner the corner
	 * @return the list with the adjacent {@link Land}s
	 */
	public List<Land> getLandsForCorner(Point corner) {
		// TODO: Implement.
		return Collections.emptyList();
	}

	//new

	public void addBuilding(Point position, Structure structure, Player owner) {
        if (structure.equals(Structure.SETTLEMENT)) {
            buildingList.add(new Settlement(position, owner));
        } else if (structure.equals(Structure.CITY)) {
            buildingList.add(new City(position, owner));
        }
    }

	public void addRoad(Point firstPoint, Point secondPoint, Player owner) {
		roadList.add(new Road(firstPoint, secondPoint, owner));
	}
}
