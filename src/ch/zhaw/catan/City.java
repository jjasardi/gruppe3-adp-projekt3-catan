package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

public class City extends Building {
    private static final int WIN_POINTS_FOR_CITY = 2;
    
    public City(Point position, Faction owner) {
        super(position, owner, WIN_POINTS_FOR_CITY);
    }
}
