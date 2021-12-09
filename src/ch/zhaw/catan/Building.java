package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Building {
    private Point position;
    private Faction faction;
    private int winPointsForBuilduing;

    public Building(Point position, Faction faction, int winPointsForBuilduing) {
        this.position = position;
        this.faction = faction;
        this.winPointsForBuilduing = winPointsForBuilduing;
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
}
