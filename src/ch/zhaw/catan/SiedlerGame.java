package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Land;
import ch.zhaw.catan.Config.Resource;
import ch.zhaw.catan.Config.Structure;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beryx.textio.TextIO;

/**
 * This class performs all actions related to modifying the game state. TODO:
 * (your documentation)
 *
 * @author TODO
 */
public class SiedlerGame {
  static final int FOUR_TO_ONE_TRADE_OFFER = 4;
  static final int FOUR_TO_ONE_TRADE_WANT = 1;
  private static final int FIRST_PLAYER_IN_LIST = 0;
  private static final int THIEF_NUMBER = 7;

  private List<Player> playerList;
  private int winPoints;
  private SiedlerBoard siedlerBoard;
  private int currentPlayerIndex;
  private Bank bank;
  private SiedlerBoardTextView view;
  private Thief thiefPosition;

  /**
   * Constructs a SiedlerGame game state object.
   * 
   * @param winPoints       the number of points required to win the game
   * @param numberOfPlayers the number of players
   * @throws IllegalArgumentException if winPoints is lower than three or players
   *                                  is not between two and four
   */
  public SiedlerGame(int winPoints, int numberOfPlayers) {
    this.winPoints = winPoints;
    setPlayerList(numberOfPlayers);
    siedlerBoard = new SiedlerBoard(); // TODO: dicevalues
    view = new SiedlerBoardTextView(siedlerBoard);
    bank = new Bank();
    currentPlayerIndex = FIRST_PLAYER_IN_LIST;
    placeInitialThief();
    // dice

  }

  /**
   * Switches to the next player in the defined sequence of players.
   */
  public void switchToNextPlayer() {
    if (currentPlayerIndex < (playerList.size() - 1)) {
      currentPlayerIndex++;
    } else {
      currentPlayerIndex = FIRST_PLAYER_IN_LIST;
    }
  }

  /**
   * Switches to the previous player in the defined sequence of players.
   */
  public void switchToPreviousPlayer() {
    if (currentPlayerIndex != FIRST_PLAYER_IN_LIST) {
      currentPlayerIndex--;
    } else {
      currentPlayerIndex = playerList.size() - 1;
    }
  }

  /**
   * Returns the {@link Faction}s of the active players.
   * <p>
   * The order of the player's factions in the list must correspond to the oder in
   * which they play. Hence, the player that sets the first settlement must be at
   * position 0 in the list etc. <strong>Important note:</strong> The list must
   * contain the factions of active players only.
   * </p>
   * 
   * @return the list with player's factions
   */
  public List<Faction> getPlayerFactions() {
    List<Faction> playerFactionList = new ArrayList<>();
    for (Player player : playerList) {
      playerFactionList.add(player.getPlayerFaction());
    }
    return playerFactionList;
  }

  /**
   * Returns the game board.
   * 
   * @return the game board
   */
  public SiedlerBoard getBoard() {
    return siedlerBoard;
  }

  public SiedlerBoardTextView getView() {
    return view;
  }

  /**
   * Returns the {@link Faction} of the current player.
   * 
   * @return the faction of the current player
   */
  public Faction getCurrentPlayerFaction() {
    return getCurrentPlayer().getPlayerFaction();
  }

  /**
   * Returns how many resource cards of the specified type the current player
   * owns.
   * 
   * @param resource the resource type
   * @return the number of resource cards of this type
   */
  public int getCurrentPlayerResourceStock(Resource resource) {
    return getCurrentPlayer().getPlayerResource(resource);
  }

  /**
   * Places a settlement in the founder's phase (phase II) of the game.
   * <p>
   * The placement does not cost any resource cards. If payout is set to true, for
   * each adjacent resource-producing field, a resource card of the type of the
   * resource produced by the field is taken from the bank (if available) and
   * added to the players' stock of resource cards.
   * </p>
   * 
   * @param position the position of the settlement
   * @param payout   if true, the player gets one resource card per adjacent
   *                 resource-producing field
   * @return true, if the placement was successful
   */
  public boolean placeInitialSettlement(Point position, boolean payout) {
    // TODO: testing
    if (isSettlementPositionValid(position)) {
      Player currentPlayer = getCurrentPlayer();
      Settlement initalSettlement = new Settlement(position, currentPlayer.getPlayerFaction());
      placeBuilding(initalSettlement, position, currentPlayer);
      if (payout) {
        List<Land> landsForSettlement = siedlerBoard.getLandsForCorner(position);
        for (Land land : landsForSettlement) {
          Resource landResource = land.getResource();
          if (bank.removeOneResource(landResource)) {
            currentPlayer.addResourceToPlayer(landResource, 1);
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  private boolean isSettlementPositionValid(Point position) {
    // TODO: testing
    if (siedlerBoard.hasCorner(position) && siedlerBoard.getCorner(position) == null
        && siedlerBoard.getNeighboursOfCorner(position).isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  private void placeBuilding(Building building, Point position, Player currentPlayer) {
    siedlerBoard.setCorner(position, building);
    currentPlayer.addPoints(building.getWinPoints());
  }

  /**
   * Places a road in the founder's phase (phase II) of the game. The placement
   * does not cost any resource cards.
   * 
   * @param roadStart position of the start of the road
   * @param roadEnd   position of the end of the road
   * @return true, if the placement was successful
   */
  public boolean placeInitialRoad(Point roadStart, Point roadEnd) {
    // TODO: fertig implementieren
    if (isInitialRoadPositionValid(roadStart, roadEnd)) {
      Road initalRoad = new Road(roadStart, roadEnd, getCurrentPlayerFaction());
      siedlerBoard.setEdge(roadStart, roadEnd, initalRoad);
      return true;
    } else {
      return false;
    }

  }

  private boolean isInitialRoadPositionValid(Point roadStart, Point roadEnd) {
    // TODO: second street only on second settlement
    if (siedlerBoard.hasEdge(roadStart, roadEnd) && siedlerBoard.getEdge(roadStart, roadEnd) == null
        && ((isBuilduingFaction(roadStart) || isBuilduingFaction(roadEnd)
            || isAdjacentToRoad(roadStart) || isAdjacentToRoad(roadEnd)))) {
      return true;
    } else {
      return false;
    }
  }

  private boolean isBuilduingFaction(Point point) {
    Faction buildingFaction = siedlerBoard.getCorner(point).getFaction();
    if (buildingFaction.equals(getCurrentPlayerFaction())) {
      return true;
    } else {
      return false;
    }
  }

  private boolean isAdjacentToRoad(Point point) {
    List<Road> AdjacentRoads = siedlerBoard.getAdjacentEdges(point);
    for (Road road : AdjacentRoads) {
      Faction roadFaction = road.getFaction();
      if (roadFaction.equals(getCurrentPlayerFaction())) {
        return true;
      }
    }
    return false;
  }

  /**
   * This method takes care of actions depending on the dice throw result. A key
   * action is the payout of the resource cards to the players according to the
   * payout rules of the game. This includes the "negative payout" in case a 7 is
   * thrown and a player has more than {@link Config#MAX_CARDS_IN_HAND_NO_DROP}
   * resource cards. If a player does not get resource cards, the list for this
   * players' {@link Faction} is <b>an empty list (not null)</b>!.
   * <p>
   * The payout rules of the game take into account factors such as, the number of
   * resource cards currently available in the bank, settlement types (settlement
   * or city), and the number of players that should get resource cards of a
   * certain type (relevant if there are not enough left in the bank).
   * </p>
   *
   * @param dicethrow the resource cards that have been distributed to the players
   *                  TODO: ist das richtig?
   * @return the resource cards added to the stock of the different players
   */
  public Map<Faction, List<Resource>> throwDice(int dicethrow) {
    // TODO: finish implementation
    Map<Faction, List<Resource>> factionResourceList = new HashMap<>();
    Land landType;
    List<Resource> resourceList;
    List<Point> fieldPositions;
    List<Building> buildingsOfField;

    if (dicethrow != THIEF_NUMBER) {
      fieldPositions = siedlerBoard.getFieldsForDiceValue(dicethrow);
      for (Point fieldPosition : fieldPositions) {
        landType = siedlerBoard.getField(fieldPosition);
        buildingsOfField = siedlerBoard.getCornersOfField(fieldPosition);
        for (Building building : buildingsOfField) {
          Faction buildingFaction = building.getFaction();
          // resourceList =
        }
      }

    } else {
      removeHalfResource();
      factionResourceList = Collections.emptyMap();
    }
    return factionResourceList;
  }

  /**
   * Builds a settlement at the specified position on the board.
   * <p>
   * The settlement can be built if:
   * <ul>
   * <li>the player possesses the required resource cards</li>
   * <li>a settlement to place on the board</li>
   * <li>the specified position meets the build rules for settlements</li>
   * </ul>
   * 
   * @param position the position of the settlement
   * @return true, if the placement was successful
   */
  public boolean buildSettlement(Point position) {
    if (isSettlementPositionValid(position)
        && isAdjacentToRoad(position) && hasEnoughForSettlement()) {
      Player currentPlayer = getCurrentPlayer();
      Settlement settlement = new Settlement(position, currentPlayer.getPlayerFaction());
      placeBuilding(settlement, position, currentPlayer);
      getCurrentPlayer().removeResourceFromPlayer(Resource.LUMBER, 1);
      getCurrentPlayer().removeResourceFromPlayer(Resource.BRICK, 1);
      getCurrentPlayer().removeResourceFromPlayer(Resource.WOOL, 1);
      getCurrentPlayer().removeResourceFromPlayer(Resource.GRAIN, 1);
      return true;
    } else {
      return false;
    }
  }

  /**
   * Builds a city at the specified position on the board.
   * <p>
   * The city can be built if:
   * <ul>
   * <li>the player possesses the required resource cards</li>
   * <li>a city to place on the board</li>
   * <li>the specified position meets the build rules for cities</li>
   * </ul>
   * 
   * @param position the position of the city
   * @return true, if the placement was successful
   */
  public boolean buildCity(Point position) { // TODO: Resource, Settlement City conflict
    List<Building> settlements = siedlerBoard.getCorners();
    boolean isSettlementFaction = false;
    for (Building building : settlements) {
      Faction buildingFaction = building.getFaction();
      if (buildingFaction.equals(getCurrentPlayerFaction())) {
        isSettlementFaction = true;
      }
    }
    if (isSettlementFaction && hasEnoughForCity()) {
      City city = new City(position, getCurrentPlayerFaction());
      siedlerBoard.setCorner(position, city);
      getCurrentPlayer().addPoints(city.getWinPoints());
      getCurrentPlayer().removeResourceFromPlayer(Resource.ORE, 3);
      getCurrentPlayer().removeResourceFromPlayer(Resource.GRAIN, 2);
      return true;
    } else
      return true;
  }

  /**
   * Builds a road at the specified position on the board.
   * <p>
   * The road can be built if:
   * <ul>
   * <li>the player possesses the required resource cards</li>
   * <li>a road to place on the board</li>
   * <li>the specified position meets the build rules for roads</li>
   * </ul>
   * 
   * @param roadStart the position of the start of the road
   * @param roadEnd   the position of the end of the road
   * @return true, if the placement was successful
   */
  public boolean buildRoad(Point roadStart, Point roadEnd) {
    if (siedlerBoard.hasEdge(roadStart, roadEnd) && isAdjacentToRoad(roadStart) && hasEnoughForRoad()) {
      Road road = new Road(roadStart, roadEnd, getCurrentPlayerFaction());
      siedlerBoard.setEdge(roadStart, roadEnd, road);
      getCurrentPlayer().removeResourceFromPlayer(Resource.LUMBER, 1);
      getCurrentPlayer().removeResourceFromPlayer(Resource.BRICK, 1);
      return true;
    } else
      return false;
  }

  private boolean hasEnoughForRoad() { // TODO: magic numbers
    if (getCurrentPlayer().getPlayerResource(Resource.LUMBER) >= 1
        && getCurrentPlayer().getPlayerResource(Resource.BRICK) >= 1) {
      return true;
    } else
      return false;
  }

  private boolean hasEnoughForSettlement() { // TODO: magic numbers
    if (getCurrentPlayer().getPlayerResource(Resource.LUMBER) >= 1
        && getCurrentPlayer().getPlayerResource(Resource.BRICK) >= 1
        && getCurrentPlayer().getPlayerResource(Resource.WOOL) >= 1
        && getCurrentPlayer().getPlayerResource(Resource.GRAIN) >= 1) {
      return true;
    } else
      return false;
  }

  private boolean hasEnoughForCity() { // TODO: magic numbers
    // List<Resource> costs = Structure.CITY.getCosts();
    // for (Resource cost : costs) {
    // }
    if (getCurrentPlayer().getPlayerResource(Resource.ORE) >= 3
        && getCurrentPlayer().getPlayerResource(Resource.GRAIN) >= 2) {
      return true;
    } else
      return false;
  }

  /**
   * Trades in {@link #FOUR_TO_ONE_TRADE_OFFER} resource cards of the offered type
   * for {@link #FOUR_TO_ONE_TRADE_WANT} resource cards of the wanted type. The
   * trade only works when bank and player possess the resource cards for the
   * trade before the trade is executed.
   *
   * @param offer offered type
   * @param want  wanted type
   * @return true, if the trade was successful
   */
  public boolean tradeWithBankFourToOne(Resource offer, Resource want) {
    return bank.tradeFourForOne(getCurrentPlayer(), offer, want);
  }

  public Map<Resource, Integer> getBankStock() {
    return bank.getBankStock();
  }

  /**
   * Returns the winner of the game, if any.
   * 
   * @return the winner of the game or null, if there is no winner (yet)
   */
  public Faction getWinner() {
    Faction winner = null;
    for (Player player : playerList) {
      if (player.getPlayerPoints() >= winPoints) {
        winner = player.getPlayerFaction();
      }
    }
    return winner;
  }

  /**
   * Places the thief on the specified field and steals a random resource card (if
   * the player has such cards) from a random player with a settlement at that
   * field (if there is a settlement) and adds it to the resource cards of the
   * current player.
   * 
   * @param field the field on which to place the thief
   * @return false, if the specified field is not a field or the thief cannot be
   *         placed there (e.g., on water)
   */
  public boolean placeThiefAndStealCard(Point field) {
    List<Building> corners = null;
    if (siedlerBoard.hasField(field)) {
      corners = siedlerBoard.getCornersOfField(field);
    }
    if (siedlerBoard.hasField(field) && corners != null) {
      thiefPosition.setNewThiefPosition(field);
      List<Faction> factions = new ArrayList<>();
      for (Building building : corners) {
        if (building.getFaction() != getCurrentPlayerFaction()) {
          factions.add(building.getFaction());
        }
      }
      int totalFactions = factions.size();
      int randomFactionIndex = (int) ((Math.random() * totalFactions));
      Player randomPlayer = getPlayerofFaction(factions.get(randomFactionIndex));
      Resource resourceToSteal = thiefPosition.getRandomResource(randomPlayer.getResourceList());
      randomPlayer.removeResourceFromPlayer(resourceToSteal, 1);
      getCurrentPlayer().addResourceToPlayer(resourceToSteal, 1);
      siedlerBoard.addFieldAnnotation(field, thiefPosition.getPositionOffset(), thiefPosition.toString());
      return true;
    } else
      return false;
  }

  private void placeInitialThief() {
    thiefPosition = new Thief(Config.INITIAL_THIEF_POSITION);
    siedlerBoard.addFieldAnnotation(Config.INITIAL_THIEF_POSITION, thiefPosition.getPositionOffset(),
        thiefPosition.toString());
  }

  // new implement

  private Player getCurrentPlayer() {
    return playerList.get(currentPlayerIndex);
  }

  private Player getPlayerofFaction(Faction faction) {
    Player player1 = null;
    for (Player player : playerList) {
      if (player.getPlayerFaction() == faction) {
        player1 = player;
      }
    }
    return player1;
  }

  /*
   * private Map<Faction, Point> getAllSettlements() {
   * Map<Faction, Point> allSettlements = new HashMap<>();
   * for (Player player : playerList) {
   * 
   * }
   * }
   */

  /**
   * Returns how many cards the current player owns.
   * 
   * @return the number of cards in hand
   */
  public HashMap<Resource, Integer> getCurrentPlayerStock() {
    return getCurrentPlayer().getPlayerStock();
  }

  private void setPlayerList(int numberOfPlayers) {
    Faction faction[] = Faction.values();
    playerList = new ArrayList<>();
    for (int i = 0; i < numberOfPlayers; i++) {
      playerList.add(new Player(faction[i]));
    }
  }

  private void removeHalfResource() {
    for (Player player : playerList) {
      int playerStock = player.getPlayerStockVolume();
      if (playerStock >= THIEF_NUMBER) {
        int resourceToSteal = playerStock / 2;
        for (int i = 0; i < resourceToSteal; i++) {
          List<Resource> resourceList = player.getResourceList();
          int randomIndex = (int) ((Math.random() * resourceList.size()));
          Resource resource = resourceList.get(randomIndex);
          player.removeOneResourceFromPlayer(resource);
          bank.addOneResource(resource);
        }
      }
    }
  }
}
