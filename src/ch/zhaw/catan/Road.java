package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

/**
 * This class models a {@link Road} object and has all the method which has to
 * do with the {@link Road}.
 * 
 * @author Durim, Ardi, Philipp
 */
public class Road {
    private Point firstPoint, secondPoint;
    private Faction faction;

    /**
     * Creates a road object {@link Road}
     * 
     * @param firstPoint  first position of the {@link Road}
     * @param secondPoint second position of the {@link Road}
     * @param faction     the faction that belongs the {@link Road}
     */
    public Road(Point firstPoint, Point secondPoint, Faction faction) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.faction = faction;
    }

    /**
     * returns the coordinates of the first point from the {@link Road} on the
     * board.
     * 
     * @return position of the first point
     */
    public Point getFirstPoint() {
        return firstPoint;
    }

    /**
     * returns the coordinates of the second point from the {@link Road} on the
     * board.
     * 
     * @return position of the second point
     */
    public Point getSecondPoint() {
        return secondPoint;
    }

    /**
     * returns the faction of the {@link Road}.
     * 
     * @return faction of the road
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * TODO
     * @return String
     */
    @Override
    public String toString() {
        return getFaction().toString();
    }
}
