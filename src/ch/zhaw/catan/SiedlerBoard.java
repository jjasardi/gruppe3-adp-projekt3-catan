package ch.zhaw.catan;

import ch.zhaw.catan.Config.Land;
import ch.zhaw.catan.Config.Structure;
import ch.zhaw.hexboard.HexBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SiedlerBoard extends HexBoard<Land, Building, Road, String> {

	private ArrayList<Building> buildingList;
	private ArrayList<Road> roadList;
	private HashMap<Point, Integer> landList;

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

	private void test() {
		landList = new HashMap<>();
		for (Map.Entry<Point, Integer> index : Config.getStandardDiceNumberPlacement().entrySet()) {
			landList.put(new Point(index.getKey()), index.getValue());
		}

	}

	/**
	 * Returns the fields associated with the specified dice value.
	 *
	 * @param dice the dice value
	 * @return the fields associated with the dice value
	 */
	public List<Point> getFieldsForDiceValue(int dice) {
		List<Point> fieldMatch = new ArrayList<>();
		for (Entry<Point, Integer> land : landList.entrySet()) {
			if (land.getValue() == dice) {
				fieldMatch.add(land.getKey());
			}
		}
		return fieldMatch;
	}

	/**
	 * Returns the {@link Land}s adjacent to the specified corner.
	 *
	 * @param corner the corner
	 * @return the list with the adjacent {@link Land}s
	 */
	public List<Land> getLandsForCorner(Point corner) {
		// TODO: Implement.
		return getFields(corner);
	}

	// new

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
