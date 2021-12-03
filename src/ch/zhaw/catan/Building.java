package ch.zhaw.catan;

import java.awt.Point;

public class Building {
    private Point position;
    private Player owner;

    public Building(Point position, Player owner) {
        this.position = position;
        this.owner = owner;
    }

    public Point getPosition() {
        return position;
    }

    public Player getOwner() {
        return owner;
    }
}
