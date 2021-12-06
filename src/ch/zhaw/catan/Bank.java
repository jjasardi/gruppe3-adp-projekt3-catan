package ch.zhaw.catan;

import java.util.Map;

import ch.zhaw.catan.Config.Resource;

public class Bank {
    private Map<Config.Resource, Integer> bank;


    public Bank() {
        //super(playerFaction); // todo - wrong as is
        bank = Config.INITIAL_RESOURCE_CARDS_BANK;
        

    }

    public Map<Config.Resource, Integer> getBankStock() {
        return bank;

    }

    public void setBankRecource(Resource resource, Integer neuerWert) {
        bank.put(resource, neuerWert);
    }

    public boolean tradeFourForOne(Player currentPlayer, Resource offer, Resource want) {
        if (bank.get(want) > 0 && currentPlayer.getPlayerResource(offer) >= 4) {
            currentPlayer.setPlayerResource(want, (currentPlayer.getPlayerResource(want) + 1)); // PlayerWantRecource + 1
            currentPlayer.setPlayerResource(offer, (currentPlayer.getPlayerResource(offer) - 4)); // PlayerOfferRecource - 4
            setBankRecource(want, (bank.get(want) - 1));
            setBankRecource(offer, (bank.get(offer) + 4));
            return true;
        } else
            return false;
    }
}
