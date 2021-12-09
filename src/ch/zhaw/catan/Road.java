package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Road {
    private Point firstPoint, secondPoint;
    private Faction faction;

    public Road(Point firstPoint, Point secondPoint, Faction faction) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.faction = faction;
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public Faction getFaction() {
        return faction;
    }

    @Override
    public String toString() {
        return getFaction().toString();
    }
}
