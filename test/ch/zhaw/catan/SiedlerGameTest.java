package ch.zhaw.catan;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.games.ThreePlayerStandard;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/***
 * This Clas Contains methods to test the buildCity() method, the
 * placeThiefAndStealCard() method and the throwDice() class.
 *
 * Note: Have a look at {@link ch.zhaw.catan.games.ThreePlayerStandard}. It can
 * be used to get several different game states.
 *
 */
public class SiedlerGameTest {

    private Map<Resource, Integer> redResourceList = Map.of(Resource.ORE, 1, Resource.GRAIN, 1, Resource.LUMBER, 1);
    private Map<Resource, Integer> blueResourceList = Map.of(Resource.WOOL, 1, Resource.ORE, 1, Resource.GRAIN, 1);

    private void setUpTwoPlayer(SiedlerGame model) {
        Point settlement = new Point(8, 18);
        model.placeInitialSettlement(settlement, false);
        Point roadEnd = new Point(8, 16);
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
     * This test checks if the bank gives out recources while it has none.
     */
    @Test
    public void bankNoStock() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);
        for (int i = 0; i < 17; i++) {
            model.removeOneResourceFromBank(Resource.ORE);
        }
        for (int i = 0; i < 19; i++) {
            model.removeOneResourceFromBank(Resource.BRICK);
        }
        for (int i = 0; i < 18; i++) {
            model.removeOneResourceFromBank(Resource.WOOL);
        }
        for (int i = 0; i < 17; i++) {
            model.removeOneResourceFromBank(Resource.GRAIN);
        }
        for (int i = 0; i < 18; i++) {
            model.removeOneResourceFromBank(Resource.LUMBER);
        }
        model.throwDice(6);
        Map<Resource, Integer> redStock = model.getCurrentPlayer().getPlayerStock();
        assertEquals(redResourceList, redStock);
    }

    /**
     * This Test checks if a recource is given out to only one player while it's
     * supposed to be given to two players, if only one recource is in bank stock.
     * 
     * this test fails because the codelines to make it work are excluded in the
     * SiedlerGame class.
     */
    @Test
    public void bankResourceForOnlyOnePlayer() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);
        for (int i = 0; i < 17; i++) {
            model.removeOneResourceFromBank(Resource.ORE);
        }
        for (int i = 0; i < 18; i++) {
            model.removeOneResourceFromBank(Resource.BRICK);
        }
        for (int i = 0; i < 18; i++) {
            model.removeOneResourceFromBank(Resource.WOOL);
        }
        for (int i = 0; i < 17; i++) {
            model.removeOneResourceFromBank(Resource.GRAIN);
        }
        for (int i = 0; i < 18; i++) {
            model.removeOneResourceFromBank(Resource.LUMBER);
        }
        model.throwDice(11);
        Map<Resource, Integer> redStock = model.getCurrentPlayer().getPlayerStock();
        model.switchToNextPlayer();
        Map<Resource, Integer> blueStock = model.getCurrentPlayer().getPlayerStock();
        assertEquals(redResourceList, redStock);
        assertEquals(blueResourceList, blueStock);
    }

    /**
     * Tests if player receives two Resources for city.
     */
    @Test
    public void diceThrowWithCity() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.ORE);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        model.getCurrentPlayer().addOneResourceToPlayer(Resource.GRAIN);
        model.buildCity(new Point(8, 18));
        model.throwDice(11);
        Map<Resource, Integer> redList = new HashMap<>(redResourceList);
        redList.put(Resource.BRICK, 2);
        assertEquals(model.getCurrentPlayer().getPlayerStock(), redList);
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

        assertEquals(redStock, redStockAfterSteal);
        assertEquals(blueResourceList, blueStock);
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

        assertEquals(redResourceList, redStock);
        assertEquals(blueResourceList, blueStock);
    }

    /**
     * Tests if thief position ist correct.
     */
    @Test
    public void thiefTestOnNormalField() {
        SiedlerGame model = new SiedlerGame(7, 2);
        setUpTwoPlayer(model);

        Point point = new Point(8, 14);
        model.placeThiefAndStealCard(point);

        assertEquals(point, model.getThiefPositiong());
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

        assertEquals(Config.INITIAL_THIEF_POSITION, model.getThiefPositiong());
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
        assertTrue(model.buildCity(new Point(8, 18)));
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

    /**
     * Tests if the right winner of the game is returned when the player reaches the
     * specified win points.
     */
    @Test
    public void winnerTest() {
        SiedlerGame model = ThreePlayerStandard.getPlayerOneReadyToBuildFifthSettlement(5);
        model.buildSettlement(ThreePlayerStandard.PLAYER_ONE_READY_TO_BUILD_FIFTH_SETTLEMENT_FIFTH_SETTLEMENT_POSITION);
        assertEquals(model.getWinner(), Faction.RED);
    }
}