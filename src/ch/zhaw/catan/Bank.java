package ch.zhaw.catan;

import java.util.Map;

import ch.zhaw.catan.Config.Resource;

public class Bank {
    private Map<Resource, Integer> bank;
    static final int RESOURCE_OFFER = 4;
    static final int RESOURCE_WANT = 1;

    public Bank() {
        bank = Config.INITIAL_RESOURCE_CARDS_BANK;
    }

    public Map<Resource, Integer> getBankStock() {
        return bank;
    }

    public void setBankResource(Resource resource, Integer neuerWert) {
        bank.put(resource, neuerWert);
    }

    public boolean giveOneResource(Resource resource) {
        if (bank.get(resource) > 0) {
            setBankResource(resource, (bank.get(resource) - RESOURCE_WANT));
            return true;
        } else
            return false;
    }

    public boolean tradeFourForOne(Player currentPlayer, Resource offer, Resource want) {
        if (bank.get(want) > 0 && currentPlayer.getPlayerResource(offer) >= 4) {
            currentPlayer.setPlayerResource(want, (currentPlayer.getPlayerResource(want) + RESOURCE_WANT));
            currentPlayer.setPlayerResource(offer, (currentPlayer.getPlayerResource(offer) - RESOURCE_OFFER));
            setBankResource(want, (bank.get(want) - RESOURCE_WANT));
            setBankResource(offer, (bank.get(offer) + RESOURCE_OFFER));
            return true;
        } else
            return false;
    }
}
