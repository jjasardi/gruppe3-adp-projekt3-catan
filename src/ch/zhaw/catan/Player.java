package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Point;

public class Player {
    private final Faction playerFaction;
    private HashMap<Resource, Integer> playerResource;
    private ArrayList<Building> playerBuildings;

    public Player(Faction PlayerFaction) {
        this.playerFaction = PlayerFaction;
        playerResource = new HashMap<>();
        playerBuildings = new ArrayList<>();
    }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public int getPlayerResource(Resource resource) {
        return playerResource.get(resource);
    }

    public void addBuilding(Point position, Structure structure) {
        if (structure.equals(Structure.SETTLEMENT)) {
            playerBuildings.add(new Settlement(position));
        } else if (structure.equals(Structure.CITY)) {
            playerBuildings.add(new City(position));
        }
    }

    public void addRoad(Point positionOne, Point positionTwo) {

    }

    private void setPlayerResource() {
        // TODO
    }
}
