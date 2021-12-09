package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

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
        if (playerResource.get(resource) != null) {
            return playerResource.get(resource);
        } else
            return 0;
    }

    public HashMap<Resource, Integer> getPlayerStock() {
        return playerResource;
    }

    public int getPlayerStockVolume() {
        int stock = 0;
        for (Entry<Resource, Integer> resource : playerResource.entrySet()) {
            stock += resource.getValue();
        }
        return stock;
    }

    public int getPlayerPoints() {
        return points;
    }

    public List<Resource> getResourceList() {
        List<Resource> list = new ArrayList<>();
        for (Entry<Resource, Integer> resource : playerResource.entrySet()) {
            if (resource.getValue() > 0) {
                list.add(resource.getKey());
            }
        }
        return list;
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

    public boolean removeOneResourceFromPlayer(Resource resource) {
        if (PlayerHasResourceInStock(resource, 1)) {
            playerResource.merge(resource, -1, Integer::sum);
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
