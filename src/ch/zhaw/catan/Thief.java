package ch.zhaw.catan;

import java.awt.Point;
import java.util.List;

import ch.zhaw.catan.Config.Resource;;

/**
 * This class represents the thief of the game. It creates a {@link Thief}
 * object and has all the method for the thief.
 * 
 * @author Durim
 */
public class Thief {
    private Point position;

    /**
     * Creates a thief object {@link Thief}
     * 
     * @param position position with the coordinates of the {@link Thief}
     */
    public Thief(Point position) {
        this.position = position;

    }

    /**
     * returns position of the thief.
     * 
     * @return Point
     */
    public Point getPosition() {
        return position;
    }

    /**
     * moves the Thief to the new position.
     * 
     * @param position is the new position
     */
    public void setNewThiefPosition(Point position) {
        this.position = position;
    }

    /**
     * gets a random resource from a resource list.
     * 
     * @param list the resource list
     * @return a random resource
     */
    public Resource getRandomResource(List<Resource> list) {
        int totalResource = list.size();
        int randomResourceIndex = (int) ((Math.random() * totalResource));
        return list.get(randomResourceIndex);
    }

    /**
     * returns the position of the thief object as String.
     * @return String
     */
    @Override
    public String toString() {
        return "Thief is on Position: " + "[" + position.x + "," + position.y + "]";
    }

}
