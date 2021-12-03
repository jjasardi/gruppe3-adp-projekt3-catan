package ch.zhaw.catan;

import java.awt.Point;

public class City extends Building {
    private static final int WIN_POINTS_FOR_CITY = 2;
    
    public City(Point position, Player owner) {
        super(position, owner);
    }

    public int getWinPoints() {
        return WIN_POINTS_FOR_CITY;        
    }
}
