package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;

/**
 * This class represent a Player. Every player has a faction, a number of points
 * and a stock of resources. This class performs all actions related to the
 * player.
 * 
 * @author Durim, Ardi, Philipp
 */
public class Player {
    private final static int MAX_POINTS_PLAYER_CAN_GET = 2;

    private final Faction playerFaction;
    private HashMap<Resource, Integer> playerResource;
    private int points;

    /**
     * Creates a player object with a given faction.
     * 
     * @param PlayerFaction is the colour of the player
     */
    public Player(Faction PlayerFaction) {
        this.playerFaction = PlayerFaction;
        playerResource = new HashMap<>();
    }

    /**
     * returns the faction of the player.
     * 
     * @return Faction of the player
     */
    public Faction getPlayerFaction() {
        return playerFaction;
    }

    /**
     * returns the amount of a specific resource. If it is null, 0 is returned.
     * 
     * @param resource the specific resource
     * @return amount of the resource the player has in stock
     */
    public int getPlayerResource(Resource resource) {
        if (playerResource.get(resource) != null) {
            return playerResource.get(resource);
        } else
            return 0;
    }

    /**
     * returns all resources the player has in stock.
     * 
     * @return HashMap<Resource, Integer> playerResource
     */
    public HashMap<Resource, Integer> getPlayerStock() {
        return playerResource;
    }

    /**
     * returns how many resources the player has as a number. "How many cards in
     * hand."
     * 
     * @return number of resources in stock
     */
    public int getPlayerStockVolume() {
        int stock = 0;
        for (Entry<Resource, Integer> resource : playerResource.entrySet()) {
            stock += resource.getValue();
        }
        return stock;
    }

    /**
     * returns the current win points the player has.
     * 
     * @return points of the player
     */
    public int getPlayerPoints() {
        return points;
    }

    /**
     * @return List<Resource>
     */
    public Set<Resource> getResourceSet() {
        return new HashSet<>(getResourceList());
    }

    public List<Resource> getResourceList() {
        List<Resource> list = new ArrayList<>();
        for (Entry<Resource, Integer> resource : playerResource.entrySet()) {
            if (resource.getValue() > 0) {
                for (int i = 0; i < resource.getValue(); i++) {
                    list.add(resource.getKey());
                }
            }
        }
        return list;
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
