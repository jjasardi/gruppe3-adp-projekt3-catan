package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import java.util.HashMap;

public class Player {
    private boolean playerActive;
    private final Faction playerFaction;
    private HashMap<Resource, Integer> playerResource;

    public Player(Faction PlayerFaction) {
        playerActive = true;
        this.playerFaction = PlayerFaction;
        playerResource = new HashMap<>();
    }

    public boolean getPlayerActive() {
        return playerActive;
    }

    public Faction getPlayerFaction() {
        return playerFaction;
    }

    public int getPlayerResource(Resource resource) {
        return playerResource.get(resource);

    }

    private void setPlayerResource() {
        //TODO
    }

}
