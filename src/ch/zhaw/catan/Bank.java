package ch.zhaw.catan;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.zhaw.catan.Config.Resource;

/**
 * This class is responsible for evrything Bank related.
 * <ul>
 * <li>Pay out the player</li>
 * <li>Trade 4 : 1 with the Player</li>
 * <li>Get the Structure cost from the Player</li>
 * </ul>
 * The Bank has it's own stock.
 * 
 * @author Durim
 */
public class Bank {
    private Map<Resource, Integer> bank;
    private static final int RESOURCE_OFFER = 4;
    private static final int RESOURCE_WANT = 1;

    /**
     * Creates a bank object {@link Bank} and give it the initial resource.
     */
    public Bank() {
        bank = new HashMap<>();
        setBank();
    }

    /**
     * Returns the total remaining Stock of the Bank.
     * 
     * @return Map<Resource, Integer>
     */
    public Map<Resource, Integer> getBankStock() {
        return bank;
    }

    /**
     * Removes one given specific resource from the Bank.
     * 
     * @param resource the specific resource
     * @return true, if removel was successful
     */
    public boolean removeOneResource(Resource resource) {
        if (bank.get(resource) != null && bank.get(resource) > 0) {
            bank.merge(resource, -1, Integer::sum);
            return true;
        } else
            return false;
    }

    /**
     * Adds one given specific resource to the Bank.
     * 
     * @param resource the specific resource
     */
    public void addOneResource(Resource resource) {
        if (bank.get(resource) != null && bank.get(resource) >= 0) {
            bank.merge(resource, 1, Integer::sum);
        } else {
            bank.put(resource, 1);
        }
    }

    /**
     * Trade with the Bank with a {@link #RESOURCE_OFFER}:{@link #RESOURCE_WANT}
     * ratio.
     * 
     * @param currentPlayer the player whose turn it is
     * @param offer         the resource the player offer
     * @param want          the resource the player wants
     * @return true, if the trade was successful
     */
    public boolean tradeFourForOne(Player currentPlayer, Resource offer, Resource want) {
        if (bank.get(want) > 0 && currentPlayer.getPlayerResource(offer) >= 4) {
            currentPlayer.addOneResourceToPlayer(want);
            currentPlayer.removeResourceFromPlayer(offer, RESOURCE_OFFER);
            setBankResource(want, (bank.get(want) - RESOURCE_WANT));
            setBankResource(offer, (bank.get(offer) + RESOURCE_OFFER));
            return true;
        } else
            return false;
    }

    private void setBank() {
        for (Entry<Resource, Integer> resource : Config.INITIAL_RESOURCE_CARDS_BANK.entrySet()) {
            bank.put(resource.getKey(), resource.getValue());
        }
    }

    private void setBankResource(Resource resource, Integer neuerWert) {
        bank.put(resource, neuerWert);
    }
}
