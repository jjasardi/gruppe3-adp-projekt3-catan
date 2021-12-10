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

/**
 * This class inherits from {@link HexBoard} and initializes. It adds all the
 * fields to the board. It contains methods that are used in SiedlerGame. Thee
 * class contains methods that return Structure or Points of the board.
 *
 * @author Durim, Ardi, Philipp
 */
public class SiedlerBoard extends HexBoard<Land, Building, Road, String> {

	private HashMap<Point, Integer> diceValueList;

	/**
	 * Creates a SiedlerBoard and adds all required fields to the board. The fields
	 * are arranged according to the {@link Config} class. Sets the dice value to
	 * the fields.
	 */
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
	 * returns a map with dice values of the fields coordinates.
	 * 
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

	/**
	 * returns a list of all Roads on the board from the specific faction.
	 * 
	 * @param faction the specific faction
	 * @return list of all roads from specific faction
	 */
	public List<Road> getAllRoadsOfFaction(Faction faction) {
		Set<Road> roadsOfFaction = new HashSet<>();
		List<Building> allBuildings = getAllBuildingsOfFaction(faction);
		for (Building building : allBuildings) {
			List<Road> adjacentRoads = getAdjacentEdges(building.getPosition());
			for (Road road : adjacentRoads) {
				roadsOfFaction.add(road);
			}
		}
		return new ArrayList<>(roadsOfFaction);
	}

	/**
	 * returns a list of all buildings on the board from the specific faction.
	 * 
	 * @param faction the specific faction
	 * @return list of all buildings from specific faction
	 */
	public List<Building> getAllBuildingsOfFaction(Faction faction) {
		List<Building> buildingsOfFaction = new ArrayList<>();
		for (Building building : getCorners()) {
			if (building.getFaction() == faction) {
				buildingsOfFaction.add(building);
			}
		}
		return buildingsOfFaction;
	}

	/**
	 * returns a list of all settlements on the board from the specific faction.
	 * 
	 * @param faction the specific faction
	 * @return list of all settlements from specific faction
	 */
	public List<Building> getAllSettlementsOfFaction(Faction faction) {
		List<Building> Settlements = new ArrayList<>();
		for (Building building : getAllBuildingsOfFaction(faction)) {
			if (building instanceof Settlement) {
				Settlements.add(building);
			}
		}
		return Settlements;
	}

	/**
	 * returns a list of all Cities on the board from the specific faction.
	 * 
	 * @param faction the specific faction
	 * @return list of all Cities from specific faction
	 */
	public List<Building> getAllCitiesOfFaction(Faction faction) {
		List<Building> Cities = new ArrayList<>();
		for (Building building : getAllBuildingsOfFaction(faction)) {
			if (building instanceof City) {
				Cities.add(building);
			}
		}
		return Cities;
	}
}