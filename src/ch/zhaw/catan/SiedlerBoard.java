package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Land;
import ch.zhaw.hexboard.HexBoard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class SiedlerBoard extends HexBoard<Land, Building, Road, String> {

	private HashMap<Point, Integer> diceValueList;

	// TODO: Add fields, constructors and methods as you see fit. Do NOT change
	// the
	// signature
	// of the methods below.
	public SiedlerBoard() {
		diceValueList = new HashMap<>();
		setBoard();
		setDiceValues();
	}

	private void setBoard() {
		for (Entry<Point, Land> index : Config.getStandardLandPlacement().entrySet()) {
			addField(index.getKey(), index.getValue());
		}
	}

	/**
	 * @return HashMap<Point, Integer>
	 */
	public HashMap<Point, Integer> getDiceValues() {
		return diceValueList;
	}

	private void setDiceValues() {
		for (Map.Entry<Point, Integer> index : Config.getStandardDiceNumberPlacement().entrySet()) {
			diceValueList.put(new Point(index.getKey()), index.getValue());
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
		for (Entry<Point, Integer> land : diceValueList.entrySet()) {
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
		return getFields(corner);
	}

	public List<Road> getAllRoadsOfFaction(Faction faction) {
		Set<Road> roadsOfFaction= new HashSet<>();
		List<Building> allBuildings = getAllBuildingsOfFaction(faction);
		for (Building building : allBuildings) {
			List<Road> adjacentRoads = getAdjacentEdges(building.getPosition());
			for (Road road : adjacentRoads) {
				roadsOfFaction.add(road);
			}
		}
		return new ArrayList<>(roadsOfFaction);
	}

	public List<Building> getAllBuildingsOfFaction(Faction faction) {
		List<Building> buildingsOfFaction = new ArrayList<>();
		for (Building building : getCorners()) {
			if (building.getFaction() == faction) {
				buildingsOfFaction.add(building);
			}
		}
		return buildingsOfFaction;
	}

	public List<Building> getAllSettlementsOfFaction(Faction faction){
		List<Building> Settlements = new ArrayList<>();
		for (Building building : getAllBuildingsOfFaction(faction)) {
			if (building instanceof Settlement) {
				Settlements.add(building);
			}
		} 
		return Settlements;
	}

	public List<Building> getAllCitiesOfFaction(Faction faction){
		List<Building> Cities = new ArrayList<>();
		for (Building building : getAllBuildingsOfFaction(faction)) {
			if (building instanceof City) {
				Cities.add(building);
			}
		} 
		return Cities;
	}
}