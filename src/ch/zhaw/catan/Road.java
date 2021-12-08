package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Road {
    private Point firstPoint, secondPoint;
    private Faction owner;

    public Road(Point firstPoint, Point secondPoint, Faction owner) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.owner = owner;
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    public Faction getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return getOwner().toString();
    }
}
