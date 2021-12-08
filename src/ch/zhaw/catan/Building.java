package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Building {
    private Point position;
    private Faction owner;
    private int winPointsForBuilduing;

    public Building(Point position, Faction owner, int winPointsForBuilduing) {
        this.position = position;
        this.owner = owner;
        this.winPointsForBuilduing = winPointsForBuilduing;
    }

    public Point getPosition() {
        return position;
    }

    public Faction getOwner() {
        return owner;
    }

    public int getWinPoints() {
        return winPointsForBuilduing;
    }
}
