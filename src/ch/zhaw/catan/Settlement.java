package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Settlement extends Building {
    private static final int WIN_POINTS_FOR_SETTLEMENT = 1;
    private static final int RESOURCE_EARNING = 1;

    public Settlement(Point position, Faction faction) {
        super(position, faction, WIN_POINTS_FOR_SETTLEMENT, RESOURCE_EARNING);
    }

    @Override
    public String toString() {
        return getFaction().toString();
    }
}
