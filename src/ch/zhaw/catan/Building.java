package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class Building {
    private Point position;
    private Faction owner;

    public Building(Point position, Faction owner) {
        this.position = position;
        this.owner = owner;
    }

    public Point getPosition() {
        return position;
    }

    public Faction getOwner() {
        return owner;
    }
}
