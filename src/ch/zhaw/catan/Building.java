package ch.zhaw.catan;

import java.awt.Point;

public class Building {
    private Point position;

    public Building(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }
}
