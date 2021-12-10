package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

/**
 * This class is a subclass of {@link Building}. A {@link Settlement} is a building
 * that can be built.
 * 
 * @author Philipp
 */
public class Settlement extends Building {
    private static final int WIN_POINTS_FOR_SETTLEMENT = 1;
    private static final int RESOURCE_EARNING = 1;

    /**
     * Creates a sattlement object {@link Settlement}
     * 
     * @param position position with the coordinates of the settlement
     * @param faction  faction who belongs the {@link Settlement}
     */
    public Settlement(Point position, Faction faction) {
        super(position, faction, WIN_POINTS_FOR_SETTLEMENT, RESOURCE_EARNING);
    }

    //TODO
    @Override
    public String toString() {
        return getFaction().toString();
    }
}
