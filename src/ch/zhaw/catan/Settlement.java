package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Settlement extends Building {
    private static final int WIN_POINTS_FOR_SETTLEMENT = 1;

    public Settlement(Point position, Faction owner) {
        super(position, owner, WIN_POINTS_FOR_SETTLEMENT);
    }

    @Override
    public String toString() {
        return getOwner().toString();
    }
}
