package ch.zhaw.catan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ch.zhaw.catan.Config.Resource;

import java.awt.*;
import java.util.Map;

/***
 * TODO Write your own tests in this class.
 *
 * Note: Have a look at {@link ch.zhaw.catan.games.ThreePlayerStandard}. It can
 * be used to get several different game states.
 *
 */
public class SiedlerGameTest {

    private Map<Resource, Integer> redResourceList = Map.of(Resource.ORE, 1, Resource.GRAIN, 1, Resource.LUMBER, 1);
    private Map<Resource, Integer> blueResourceList = Map.of(Resource.WOOL, 1, Resource.ORE, 1, Resource.GRAIN, 1);

    private void setUpTwoPlayer(SiedlerGame model) {
        Point settlement = new Point(8, 4);
        model.placeInitialSettlement(settlement, false);
        Point roadEnd = new Point(8, 6);
        model.placeInitialRoad(settlement, roadEnd);
        model.switchToNextPlayer();

        Point settlement2 = new Point(9, 15);
        model.placeInitialSettlement(settlement2, false);
        Point roadEnd2 = new Point(9, 13);
        model.placeInitialRoad(settlement2, roadEnd2);
        Point settlement3 = new Point(5, 15);
        model.placeInitialSettlement(settlement3, true);
        Point roadEnd3 = new Point(6, 16);
        model.placeInitialRoad(settlement3, roadEnd3);
        model.switchToPreviousPlayer();

        Point settlement4 = new Point(5, 7);
        model.placeInitialSettlement(settlement4, true);
        Point roadEnd4 = new Point(5, 9);
        model.placeInitialRoad(settlement4, roadEnd4);
    }

    /**
     * Tests if thief can steal Resources if Enemy doesn't have any.
     */
    @Test
    public void thiefTestNullResourcen() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        model.getCurrentPlayer().removeOneResourceFromPlayer(Resource.ORE);
        model.getCurrentPlayer().removeOneResourceFromPlayer(Resource.GRAIN);
        model.getCurrentPlayer().removeOneResourceFromPlayer(Resource.LUMBER);
        Map<Resource, Integer> redStock = model.getCurrentPlayerStock();
        model.switchToNextPlayer();
        Point point = new Point(6, 8);
        model.placeThiefAndStealCard(point);
        Map<Resource, Integer> blueStock = model.getCurrentPlayerStock();
        model.switchToNextPlayer();
        Map<Resource, Integer> redStockAfterSteal = model.getCurrentPlayerStock();

        assertTrue(redStock.equals(redStockAfterSteal));
        assertTrue(blueStock.equals(blueResourceList));
    }

    /**
     * Tests if thief steals Resources when there's no Settlements in the adjacent
     * Corners of the field.
     */
    @Test
    public void thiefTestNullCorners() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        Point point = new Point(10, 8);
        model.placeThiefAndStealCard(point);
        Map<Resource, Integer> redStock = model.getCurrentPlayerStock();
        model.switchToNextPlayer();
        Map<Resource, Integer> blueStock = model.getCurrentPlayerStock();

        assertTrue(redStock.equals(redResourceList));
        assertTrue(blueStock.equals(blueResourceList));
    }

    /**
     * Tests if thief can be placed on Water Field.
     */
    @Test
    public void thiefTestOnWater() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        Point point = new Point(10, 2);
        model.placeThiefAndStealCard(point);

        assertTrue(model.getThiefPositiong() == Config.INITIAL_THIEF_POSITION);
    }

    /**
     * Tests if a City can be Build ontop of a Settlement while having enough
     * Resources.
     */
    @Test
    public void cityTestOnSettlement() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        assertTrue(model.buildCity(new Point(8, 4)));
    }

    /**
     * Tests if a City can be build on a Corner that doesn't have a Settlement.
     */
    @Test
    public void cityTestOnNullCorner() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        assertFalse(model.buildCity(new Point(9, 3)));
    }

    /**
     * Tests if a city can be build on enemy Settlement.
     */
    @Test
    public void cityTestOnEnemyCorner() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        assertFalse(model.buildCity(new Point(8, 12)));
    }

    /**
     * Tests if a City can be build if player doesn't have enough resources.
     */
    @Test
    public void cityTestWithoutResource() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);
        assertFalse(model.buildCity(new Point(8, 12)));
    }

    /**
     * Tests if a City can be build in a position that's not allowed.
     */
    @Test
    public void cityTestOnNonCorner() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        assertFalse(model.buildCity(new Point(4, 7)));
    }

    /**
     * Tests if a City can be build after the Max number is already achieved.
     */
    @Test
    public void cityTestIfOverMax() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);
        for (int i = 0; i < 31; i++) {
            model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        }
        for (int i = 0; i < 31; i++) {
            model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        }
        for (int i = 0; i < 31; i++) {
            model.getCurrentPlayer().addOneResourceToPlayer(Resource.LUMBER);
        }
        for (int i = 0; i < 31; i++) {
            model.getCurrentPlayer().addOneResourceToPlayer(Resource.WOOL);
        }
        for (int i = 0; i < 31; i++) {
            model.getCurrentPlayer().addOneResourceToPlayer(Resource.BRICK);
        }
        model.buildRoad(new Point(8, 4), new Point(9, 3));
        model.buildRoad(new Point(9, 3), new Point(10, 4));
        model.buildSettlement(new Point(10, 4));
        model.buildRoad(new Point(10, 4), new Point(10, 6));
        model.buildRoad(new Point(10, 6), new Point(11, 7));
        model.buildSettlement(new Point(11, 7));
        model.buildRoad(new Point(11, 7), new Point(11, 9));
        model.buildRoad(new Point(11, 9), new Point(12, 10));
        model.buildSettlement(new Point(11, 7));
        model.buildCity(new Point(5, 7));
        model.buildCity(new Point(8, 4));
        model.buildCity(new Point(10, 4));
        model.buildCity(new Point(11, 7));

        assertFalse(model.buildCity(new Point(11, 7)));
    }
}