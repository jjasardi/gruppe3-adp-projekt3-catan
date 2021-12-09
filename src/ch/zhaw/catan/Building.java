package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Building {
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

    public Point getPosition() {
        return position;
    }

    public Faction getFaction() {
        return faction;
    }

    public int getWinPoints() {
        return winPointsForBuilduing;
    }

    public int getResourceEarning() {
        return resourceEarning;
    }
}
