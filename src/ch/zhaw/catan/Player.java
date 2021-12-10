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

    private final Faction PLAYER_FACTION;
    private HashMap<Resource, Integer> playerResource;
    private int points;

    /**
     * Creates a player object with a given faction.
     * 
     * @param PlayerFaction is the colour of the player
     */
    public Player(Faction PlayerFaction) {
        this.PLAYER_FACTION = PlayerFaction;
        playerResource = new HashMap<>();
    }

    /**
     * returns the faction of the player.
     * 
     * @return Faction of the player
     */
    public Faction getPlayerFaction() {
        return PLAYER_FACTION;
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
     * returns a set of the current resource the player has.
     * 
     * @return Set<Resource>
     */
    public Set<Resource> getResourceSet() {
        return new HashSet<>(getResourceList());
    }

    /**
     * Returns a list of all the player's current resources, with several of the
     * same resource when present.
     * 
     * @return List<Resource>
     */
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
     * Adds one specific resource to the player.
     * 
     * @param resource the specific resource
     */
    public void addOneResourceToPlayer(Resource resource) {
        if (playerResource.get(resource) != null) {
            playerResource.merge(resource, 1, Integer::sum);
        } else {
            playerResource.put(resource, 1);
        }
    }

    /**
     * Removes an amount of the specific resources from the player. returns true
     * when successful.
     * 
     * @param resource the specific resource
     * @param amount   amount to remove
     * @return if the action was successful
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
     * Removes one specific resource from the player. returns true when successful.
     * 
     * @param resource the specific resource
     * @return if the action was successful
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
     * Add an amount of win points to the player.
     * 
     * @param pointsAdd amount of points to add
     */
    public void addPoints(int pointsAdd) {
        if (pointsAdd > 0 && pointsAdd <= MAX_POINTS_PLAYER_CAN_GET) {
            points = points + pointsAdd;
        }
    }

    /**
     * Removes an amount of win points from the player.
     * 
     * @param pointsRemove amount of points to remove
     */
    public void removePoints(int pointsRemove) {
        if (pointsRemove >= points) {
            points = points - pointsRemove;
        }
    }

    /**
     * returns true if the player has the specific resource and enough of it.
     * 
     * @param resource the specific resource
     * @param amount   the amount the player needs to have
     * @return if the player has enough resource in stock
     */
    private boolean PlayerHasResourceInStock(Resource resource, int amount) {
        if (getPlayerResource(resource) >= amount) {
            return true;
        } else {
            return false;
        }
    }
}
