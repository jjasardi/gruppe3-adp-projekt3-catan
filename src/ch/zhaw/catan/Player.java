package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.util.HashMap;
import java.awt.Point;

public class Player {
    private final Faction playerFaction;
    private HashMap<Resource, Integer> playerResource;

    public Player(Faction PlayerFaction) {
        this.playerFaction = PlayerFaction;
        playerResource = new HashMap<>();
    }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public int getPlayerResource(Resource resource) {
        return playerResource.get(resource);
    }

    public void addRoad(Point positionOne, Point positionTwo) {

    }

    private void setPlayerResource() {
        // TODO
    }
}
