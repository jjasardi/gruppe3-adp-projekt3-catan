package ch.zhaw.catan;

import java.util.List;
import java.util.Map;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;

public class Bank extends Player {
    private Map<Config.Resource, Integer> bank;

    public Bank(Faction playerFaction) {
        super(playerFaction); // todo - wrong as is
        bank = Config.INITIAL_RESOURCE_CARDS_BANK;        
    }

    public Map<Config.Resource, Integer> getBankStock() {
        return bank;

    }

    public void setBankRecource(Resource resource, Integer neuerWert) {
        bank.put(resource, neuerWert);
    }

    public boolean tradeFourForOne(Resource offer, Resource want) {
        if (bank.get(want) > 0 && getPlayerResource(offer) >= 4) {
            setPlayerResource(want, (getPlayerResource(want) + 1)); // PlayerWantRecource + 1
            setPlayerResource(offer, (getPlayerResource(offer) - 4)); // PlayerOfferRecource - 4
            return true;
        } else
            return false;
    }
}
