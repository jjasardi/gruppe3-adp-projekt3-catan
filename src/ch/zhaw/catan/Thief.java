package ch.zhaw.catan;

import java.awt.Point;
import java.util.List;

import ch.zhaw.catan.Config.Resource;;

public class Thief {
    private Point position;

    public Thief(Point position) {
        this.position = position;

    }

    public Point getPosition() {
        return position;
    }

    public Point getPositionOffset() {
        Point newPosition = new Point(position.x, (position.y + 2));
        return newPosition;
    }

    public void setNewThiefPosition(Point position) {
        this.position = position;
    }

    public Resource getRandomResource(List<Resource> list) {
        int totalResource = list.size();
        int randomResourceIndex = (int) ((Math.random() * totalResource));
        return list.get(randomResourceIndex);
    }

    @Override
    public String toString() {
        return "TH";
    }

}
