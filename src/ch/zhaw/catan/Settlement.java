package ch.zhaw.catan;

import java.awt.Point;

public class Settlement extends Building {
    private static final int WIN_POINTS_FOR_SETTLEMENT = 1;

    public Settlement(Point position, Player owner) {
        super(position, owner);
    }

    public int getWinPoints() {
        return WIN_POINTS_FOR_SETTLEMENT;
    }
}
