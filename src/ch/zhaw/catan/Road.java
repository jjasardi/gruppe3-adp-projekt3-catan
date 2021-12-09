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

    /**
     * @return Point
     */
    public Point getFirstPoint() {
        return firstPoint;
    }

    /**
     * @return Point
     */
    public Point getSecondPoint() {
        return secondPoint;
    }

    /**
     * @return Faction
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * @return String
     */
    @Override
    public String toString() {
        return getFaction().toString();
    }
}
