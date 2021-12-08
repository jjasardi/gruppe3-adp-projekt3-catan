package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.util.HashMap;
import java.util.List;

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

    public HashMap<Resource, Integer> getPlayerStock() {
        return playerResource;
    }

    public int getPlayerPoints() {
        return points;
    }

    public void addResourceToPlayer(Resource resource, int amount) {
        if (amount > 0) {
            playerResource.merge(resource, amount, Integer::sum);
        }
    }

    public void addResourceToPlayer(List<Resource> resourceList) {
        for (Resource resource : resourceList) {
            addResourceToPlayer(resource, 1);
        }
    }

    public boolean removeResourceFromPlayer(Resource resource, int amount) {
        if (amount > 0 && PlayerHasResourceInStock(resource, amount)) {
            playerResource.merge(resource, -amount, Integer::sum);
            return true;
        } else {
            return false;
        }
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

    private boolean PlayerHasResourceInStock(Resource resource, int amount) {
        if (getPlayerResource(resource) >= amount) {
            return true;
        } else {
            return false;
        }
    }
}
