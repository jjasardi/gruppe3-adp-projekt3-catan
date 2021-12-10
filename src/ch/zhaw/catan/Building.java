package ch.zhaw.catan;

import java.awt.Point;
import ch.zhaw.catan.Config.Faction;

/**
 * This class is the superclass for {@link Settlement} and {@link City}. It has
 * all the get method which has to do with the buildings.
 * 
 * @author Durim, Ardi, Philipp
 */
public abstract class Building {
    private Point position;
    private Faction faction;
    private int winPointsForBuilduing;
    private int resourceEarning;

    /**
     * Creates a building object {@link Building} with a position, faction, win
     * points and resource earning.
     * 
     * @param position              coordinates of the Building
     * @param faction               faction who owns the Building
     * @param winPointsForBuilduing how many win points it gives
     * @param resourceEarning       how many resource it gives
     */
    public Building(Point position, Faction faction, int winPointsForBuilduing, int resourceEarning) {
        this.position = position;
        this.faction = faction;
        this.winPointsForBuilduing = winPointsForBuilduing;
        this.resourceEarning = resourceEarning;
    }

    /**
     * returns the coordinates of the {@link Building} on the board.
     * 
     * @return position of the building
     */
    public Point getPosition() {
        return position;
    }

    /**
     * returns the faction of the {@link Building}.
     * 
     * @return faction of the building
     */
    public Faction getFaction() {
        return faction;
    }

    /**
     * returns the win points of the {@link Building}.
     * 
     * @return win point number
     */
    public int getWinPoints() {
        return winPointsForBuilduing;
    }

    /**
     * returns the resource earning value of the {@link Building}. How many
     * resources the player gets from the building.
     * 
     * @return earning value of the building
     */
    public int getResourceEarning() {
        return resourceEarning;
    }
}
