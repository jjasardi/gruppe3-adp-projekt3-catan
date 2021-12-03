package ch.zhaw.catan;

import java.awt.Point;

public class Road {
    private Point firstPoint, secondPoint;
    private Player owner;

    public Road(Point firstPoint, Point secondPoint, Player owner) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
        this.owner = owner;
    }

    public Point getFirstPoint () {
        return firstPoint;
    }
    
    public Point getSecondPoint () {
        return secondPoint;
    }

    public Player getOwner() {
        return owner;
    }
}
