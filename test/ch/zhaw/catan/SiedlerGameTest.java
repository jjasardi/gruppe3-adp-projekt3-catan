package ch.zhaw.catan;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ch.zhaw.catan.Config.Resource;

import java.awt.*;
import java.util.List;
import java.util.Map;

/***
 * TODO Write your own tests in this class.
 *
 * Note: Have a look at {@link ch.zhaw.catan.games.ThreePlayerStandard}. It can
 * be used
 * to get several different game states.
 *
 */
public class SiedlerGameTest {

    private Map<Resource, Integer> redResourceList = Map.of(Resource.ORE, 1, Resource.GRAIN, 1, Resource.LUMBER, 1);
    private Map<Resource, Integer> blueResourceList = Map.of(Resource.WOOL, 1, Resource.ORE, 1, Resource.GRAIN, 1);

    @Test
    public void dummyTestMethod() {
        assertTrue(false);
    }

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

    @Test
    public void bankTest() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        Map<Resource, Integer> bankStock = Map.of(Resource.WOOL, 18, Resource.ORE, 17, Resource.GRAIN, 17, Resource.LUMBER, 18, Resource.BRICK, 19);
        assertTrue(model.getBankStock().equals(bankStock));
    }

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
        Map<Resource, Integer> redStock2 = model.getCurrentPlayerStock();

        assertTrue(redStock.equals(redStock2));
        assertTrue(blueStock.equals(blueResourceList));
    }

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

    @Test
    public void thiefTestOnWater() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        Point point = new Point(10, 2);
        model.placeThiefAndStealCard(point);

        assertTrue(model.getThiefPositiong() == Config.INITIAL_THIEF_POSITION);
    }

    // @Test
    // public void cityTest() {
    //     SiedlerGame model = new SiedlerGame(7, 2);
    //     setUpTwoPlayer(model);

    //     model.getCurrentPlayer().addResourceToPlayer(Resource.ORE, 10);
    //     model.getCurrentPlayer().addResourceToPlayer(Resource.GRAIN, 10);
    //     model.buildCity(new Point(8, 4));

    //     Building building = model.getBoard().getCorner(new Point(8, 4));
    //     assertTrue(model.toString().equals(model.getCurrentPlayerFaction().toString().toUpperCase()));
    // }

}