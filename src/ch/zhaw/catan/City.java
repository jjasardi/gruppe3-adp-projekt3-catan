package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

/**
 * This class is a subclass of {@link Building}. A {@link City} is a building
 * that can be built. It can only be built if there is already a
 * {@link Settlement}.
 * 
 * @author Philipp
 */
public class City extends Building {
    private static final int WIN_POINTS_FOR_CITY = 2;
    private static final int RESOURCE_EARNING = 2;

    /**
     * Creates a city object {@link City}
     * 
     * @param position position with the coordinates of the city
     * @param faction  faction who belongs the {@link City}
     */
    public City(Point position, Faction faction) {
        super(position, faction, WIN_POINTS_FOR_CITY, RESOURCE_EARNING);
    }

    /**
     * Converts the Faction of the City object the an uppercase String
     * 
     * @return String
     */
    @Override
    public String toString() {
        return getFaction().toString().toUpperCase();
    }
}
