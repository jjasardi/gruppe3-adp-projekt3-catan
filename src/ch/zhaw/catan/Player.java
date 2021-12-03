package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.util.HashMap;

public class Player {
    private final static int MAX_POINTS_PLAYER_CAN_GET = 2;

    private final Faction playerFaction;
    private HashMap<Resource, Integer> playerResource;
    private int points;

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

    public int getPoints() {
        return points;
    }

    public void addPoints(int pointsAdd) {
        if (pointsAdd > 0 && pointsAdd <= MAX_POINTS_PLAYER_CAN_GET) {
            points = points + pointsAdd;
        }
    }

    public void removePoints(int pointsRemove) {
        if (pointsRemove >= points) {
            points = points - pointsRemove;
        }
    }

    private void setPlayerResource() {
        // TODO
    }
}
