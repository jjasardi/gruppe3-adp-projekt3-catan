package ch.zhaw.catan;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import ch.zhaw.catan.Config.Resource;

public class Bank {
    private Map<Resource, Integer> bank;
    static final int RESOURCE_OFFER = 4;
    static final int RESOURCE_WANT = 1;

    public Bank() {
        bank = new HashMap<>();
        setBank();
    }

    /**
     * @return Map<Resource, Integer>
     */
    public Map<Resource, Integer> getBankStock() {
        return bank;
    }

    private void setBank() {
        for (Entry<Resource, Integer> resource : Config.INITIAL_RESOURCE_CARDS_BANK.entrySet()) {
            bank.put(resource.getKey(), resource.getValue());
        }
    }

    /**
     * @param resource
     * @param neuerWert
     */
    public void setBankResource(Resource resource, Integer neuerWert) {
        bank.put(resource, neuerWert);
    }

    /**
     * @param resource
     * @return boolean
     */
    public boolean removeOneResource(Resource resource) {
        if (bank.get(resource) != null && bank.get(resource) > 0) {
            setBankResource(resource, (bank.get(resource) - RESOURCE_WANT));
            return true;
        } else
            return false;
    }

    /**
     * @param resource
     */
    public void addOneResource(Resource resource) {
        if (bank.get(resource) != null && bank.get(resource) >= 0) {
            setBankResource(resource, (bank.get(resource) + 1));
        } else {
            bank.put(resource, 1);
        }
    }

    /**
     * @param currentPlayer
     * @param offer
     * @param want
     * @return boolean
     */
    public boolean tradeFourForOne(Player currentPlayer, Resource offer, Resource want) {
        if (bank.get(want) > 0 && currentPlayer.getPlayerResource(offer) >= 4) {
            currentPlayer.addResourceToPlayer(want, RESOURCE_WANT);
            currentPlayer.removeResourceFromPlayer(offer, RESOURCE_OFFER);
            setBankResource(want, (bank.get(want) - RESOURCE_WANT));
            setBankResource(offer, (bank.get(offer) + RESOURCE_OFFER));
            return true;
        } else
            return false;
    }
}
