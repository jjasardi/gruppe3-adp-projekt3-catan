package ch.zhaw.catan;

import java.util.Map;

public class Bank {
private Map<Config.Resource, Integer> bank;

    public Bank() {
        bank = Config.INITIAL_RESOURCE_CARDS_BANK;
    }

    public Map<Config.Resource, Integer> getBankStock() {
        return bank;

    }

    public void setBankRecource(Config.Resource recource, Integer neuerWert) {
        bank.put(recource, neuerWert);
    }

    public boolean tradeFourForOne(Config.Resource offer, Config.Resource want) {
        if (bank.get(want) > 0) {
            //PlayerWantRecource + 1
            //PlayerOfferRecource - 4
            return true;
        }
        return false;
    }
}
