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

    /**
     * @return Faction
     */
    public Faction getPlayerFaction() {
        return playerFaction;
    }

    /**
     * @param resource
     * @return int
     */
    public int getPlayerResource(Resource resource) {
        if (playerResource.get(resource) != null) {
            return playerResource.get(resource);
        } else
            return 0;
    }

    /**
     * @return HashMap<Resource, Integer>
     */
    public HashMap<Resource, Integer> getPlayerStock() {
        return playerResource;
    }

    /**
     * @return int
     */
    public int getPlayerStockVolume() {
        int stock = 0;
        for (Entry<Resource, Integer> resource : playerResource.entrySet()) {
            stock += resource.getValue();
        }
        return stock;
    }

    /**
     * @return int
     */
    public int getPlayerPoints() {
        return points;
    }

    /**
     * @return List<Resource>
     */
    public List<Resource> getResourceList() {
        List<Resource> list = new ArrayList<>();
        for (Entry<Resource, Integer> resource : playerResource.entrySet()) {
            if (resource.getValue() > 0) {
                list.add(resource.getKey());
            }
        }
        return list;
    }

    /**
     * @param resource
     * @param amount
     */
    private void addResourceToPlayer(Resource resource, int amount) {
        if (amount > 0) {
            playerResource.merge(resource, amount, Integer::sum);
        }
    }

    /**
     * @param resource
     */
    public void addOneResourceToPlayer(Resource resource) {
        if (playerResource.get(resource) != null) {
            playerResource.merge(resource, 1, Integer::sum);
        } else {
            playerResource.put(resource, 1);
        }
    }

    /**
     * @param resource
     * @param amount
     * @return boolean
     */
    public boolean removeResourceFromPlayer(Resource resource, int amount) {
        if (amount > 0 && PlayerHasResourceInStock(resource, amount)) {
            playerResource.merge(resource, -amount, Integer::sum);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param resource
     * @return boolean
     */
    public boolean removeOneResourceFromPlayer(Resource resource) {
        if (PlayerHasResourceInStock(resource, 1)) {
            playerResource.merge(resource, -1, Integer::sum);
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param pointsAdd
     */
    public void addPoints(int pointsAdd) {
        if (pointsAdd > 0 && pointsAdd <= MAX_POINTS_PLAYER_CAN_GET) {
            points = points + pointsAdd;
        }
    }

    /**
     * @param pointsRemove
     */
    public void removePoints(int pointsRemove) {
        if (pointsRemove >= points) {
            points = points - pointsRemove;
        }
    }

    /**
     * @param resource
     * @param amount
     * @return boolean
     */
    private boolean PlayerHasResourceInStock(Resource resource, int amount) {
        if (getPlayerResource(resource) >= amount) {
            return true;
        } else {
            return false;
        }
    }
}
