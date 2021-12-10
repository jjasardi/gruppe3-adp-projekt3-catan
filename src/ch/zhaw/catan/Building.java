package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public abstract class Building { // TODO: abstract testen
    private Point position;
    private Faction faction;
    private int winPointsForBuilduing;
    private int resourceEarning;

    public Building(Point position, Faction faction, int winPointsForBuilduing, int resourceEarning) {
        this.position = position;
        this.faction = faction;
        this.winPointsForBuilduing = winPointsForBuilduing;
        this.resourceEarning = resourceEarning;
    }

    /**
     * @return Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * @return Faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * @return int
     */
    public int getWinPoints() {
        return winPointsForBuilduing;
    }

    /**
     * @return int
     */
    public int getResourceEarning() {
        return resourceEarning;
    }
}
