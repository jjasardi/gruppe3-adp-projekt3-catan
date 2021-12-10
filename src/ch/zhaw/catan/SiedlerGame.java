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

/**
 * This class performs all actions related to modifying the game state. Has all
 * information of the current game state. Has the method for building, trading
 * and the thief.
 *
 * @author Durim, Ardi, Philipp
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
  private Thief thief;

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
    siedlerBoard = new SiedlerBoard();
    view = new SiedlerBoardTextView(siedlerBoard);
    bank = new Bank();
    currentPlayerIndex = FIRST_PLAYER_IN_LIST;
    thief = new Thief(Config.INITIAL_THIEF_POSITION);

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

  /**
   * @return SiedlerBoardTextView
   */
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
    if (isSettlementPositionValid(position)) {
      Player currentPlayer = getCurrentPlayer();
      Settlement initalSettlement = new Settlement(position, currentPlayer.getPlayerFaction());
      placeBuilding(initalSettlement, position, currentPlayer);
      if (payout) {
        List<Land> landsForSettlement = siedlerBoard.getLandsForCorner(position);
        for (Land land : landsForSettlement) {
          Resource landResource = land.getResource();
          if (bank.removeOneResource(landResource)) {
            currentPlayer.addOneResourceToPlayer(landResource);
          }
        }
      }
      return true;
    } else {
      return false;
    }
  }

  /**
   * checks if the settlement position meets the required position rules
   * <p>
   * The settlement position rules are:
   * <ul>
   * <li>the position is a corener</li>
   * <li>the corner is free</li>
   * <li>the neighbour corners are free</li>
   * </ul>
   * 
   * @param position the position to be checked
   * @return true, if the position is valid
   */
  private boolean isSettlementPositionValid(Point position) {
    if (siedlerBoard.hasCorner(position) && siedlerBoard.getCorner(position) == null
        && siedlerBoard.getNeighboursOfCorner(position).isEmpty()) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * places the building in the game board at the specified position and updates
   * the points of the specified player
   * 
   * @param building the building to be placed
   * @param position the position where the building is placed
   * @param player   the player that places the building
   */
  private void placeBuilding(Building building, Point position, Player player) {
    Building oldBuilding = siedlerBoard.setCorner(position, building);
    if (oldBuilding != null) {
      player.removePoints(oldBuilding.getWinPoints());
    }
    player.addPoints(building.getWinPoints());
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
    if (isRoadPositionValid(roadStart, roadEnd)) {
      Road initalRoad = new Road(roadStart, roadEnd, getCurrentPlayerFaction());
      siedlerBoard.setEdge(roadStart, roadEnd, initalRoad);
      return true;
    } else {
      return false;
    }

  }

  private boolean isRoadPositionValid(Point roadStart, Point roadEnd) {
    if (siedlerBoard.hasEdge(roadStart, roadEnd) && siedlerBoard.getEdge(roadStart, roadEnd) == null
        && ((isBuildingOfCurrentPlayerFaction(roadStart) || isBuildingOfCurrentPlayerFaction(roadEnd)
            || isAdjacentToRoad(roadStart) || isAdjacentToRoad(roadEnd)))) {
      return true;
    } else {
      return false;
    }
  }

  private boolean isBuildingOfCurrentPlayerFaction(Point point) {
    Building building = siedlerBoard.getCorner(point);
    if (building != null) {
      if (building.getFaction().equals(getCurrentPlayerFaction())) {
        return true;
      }
    }
    return false;
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
   * @param dicethrow the number of the dice roll.
   * @return the resource cards added to the stock of the different players
   */
  public Map<Faction, List<Resource>> throwDice(int dicethrow) {
    Map<Faction, List<Resource>> factionResourceMap = new HashMap<>();
    Land landType;
    List<Resource> resourceList;
    List<Point> fieldPositions;
    List<Building> buildingsOfField;

    setEmptyFactionMap(factionResourceMap);

    if (dicethrow != THIEF_NUMBER) {
      fieldPositions = siedlerBoard.getFieldsForDiceValue(dicethrow);
      for (Point fieldPosition : fieldPositions) {
        if (fieldPosition != thief.getPosition()) {
          landType = siedlerBoard.getField(fieldPosition);
          buildingsOfField = siedlerBoard.getCornersOfField(fieldPosition);
          for (Building building : buildingsOfField) {
            Faction buildingFaction = building.getFaction();
            Player player = getPlayerofFaction(building.getFaction());
            resourceList = resourceEarningByBuilding(building, landType);
            resourceList.addAll(factionResourceMap.getOrDefault(buildingFaction, Collections.emptyList()));
            factionResourceMap.put(buildingFaction, resourceList);
            for (Resource resource : resourceList) {
              if (bank.removeOneResource(resource)) {
                player.addOneResourceToPlayer(resource);
              }
            }
          }
        }
      }
    } else {
      removeHalfResource();
    }
    return factionResourceMap;
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
    if (isSettlementPositionValid(position) && isAdjacentToRoad(position)
        && hasResourceToBuild(Structure.SETTLEMENT.getCosts())
        && isBelowMaxNumberOfSettlement(getCurrentPlayerFaction())) {
      Player currentPlayer = getCurrentPlayer();
      Settlement settlement = new Settlement(position, currentPlayer.getPlayerFaction());
      placeBuilding(settlement, position, currentPlayer);
      for (Resource resource : Structure.SETTLEMENT.getCosts()) {
        currentPlayer.removeOneResourceFromPlayer(resource);
      }
      return true;
    } else {
      return false;
    }
  }

  private boolean isBelowMaxNumberOfSettlement(Faction faction) {
    int numberOfSettlementsOfFaction = siedlerBoard.getAllSettlementsOfFaction(faction).size();
    int requiredStock = Structure.SETTLEMENT.getStockPerPlayer();
    if (numberOfSettlementsOfFaction < requiredStock) {
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
  public boolean buildCity(Point position) {
    boolean success = false;
    if (siedlerBoard.hasCorner(position)) {
      Building building = siedlerBoard.getCorner(position);
      boolean isSettlement = building instanceof Settlement;
      if (isBuildingOfCurrentPlayerFaction(position) && isSettlement && hasResourceToBuild(Structure.CITY.getCosts())
          && isBelowMaxNumberOfCity(getCurrentPlayerFaction())) {
        City city = new City(position, getCurrentPlayerFaction());
        Player currentPlayer = getCurrentPlayer();
        placeBuilding(city, position, currentPlayer);
        for (Resource resource : Structure.CITY.getCosts()) {
          currentPlayer.removeOneResourceFromPlayer(resource);
        }
        success = true;
      }
    }
    return success;
  }

  private boolean isBelowMaxNumberOfCity(Faction faction) {
    int numberOfCitiesOfFaction = siedlerBoard.getAllCitiesOfFaction(faction).size();
    int requiredStock = Structure.CITY.getStockPerPlayer();
    if (numberOfCitiesOfFaction < requiredStock) {
      return true;
    } else {
      return false;
    }
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
    if (isRoadPositionValid(roadStart, roadEnd) && hasResourceToBuild(Structure.ROAD.getCosts())
        && isBelowMaxNumberOfRoad(getCurrentPlayerFaction())) {
      Road road = new Road(roadStart, roadEnd, getCurrentPlayerFaction());
      siedlerBoard.setEdge(roadStart, roadEnd, road);
      for (Resource resource : Structure.ROAD.getCosts()) {
        getCurrentPlayer().removeOneResourceFromPlayer(resource);
      }
      return true;
    } else
      return false;
  }

  private boolean isBelowMaxNumberOfRoad(Faction faction) {
    int numberOfRoadsOfFaction = siedlerBoard.getAllRoadsOfFaction(faction).size();
    int requiredStock = Structure.ROAD.getStockPerPlayer();
    if (numberOfRoadsOfFaction < requiredStock) {
      return true;
    } else {
      return false;
    }
  }

  private boolean hasResourceToBuild(List<Resource> resourceList) {
    if (getCurrentPlayer().getResourceList().containsAll(resourceList)) {
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

  /**
   * @return Map<Resource, Integer>
   */
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
    if (siedlerBoard.hasField(field)) {
      List<Building> corners = siedlerBoard.getCornersOfField(field);
      if (corners != null && corners.size() > 0) {
        thief.setNewThiefPosition(field);
        List<Faction> factions = new ArrayList<>();
        for (Building building : corners) {
          if (building.getFaction() != getCurrentPlayerFaction()) {
            factions.add(building.getFaction());
          }
        }
        int totalFactions = factions.size();
        int randomFactionIndex = (int) ((Math.random() * totalFactions));
        Player randomPlayer = getPlayerofFaction(factions.get(randomFactionIndex));
        List<Resource> resourceList = new ArrayList<>(randomPlayer.getResourceSet());
        if (resourceList.size() > 0) {
          Resource resourceToSteal = thief.getRandomResource(resourceList);
          randomPlayer.removeOneResourceFromPlayer(resourceToSteal);
          getCurrentPlayer().addOneResourceToPlayer(resourceToSteal);
        }

      }
      return true;
    } else
      return false;
  }

  /**
   * returns a String of the position of the thief.
   * 
   * @return String postion of thief
   */
  public String getThiefPositionAsString() {
    return thief.toString();
  }

  /**
   * returns the current postion of the thief.
   * 
   * @return Point
   */
  public Point getThiefPositiong() {
    return thief.getPosition();
  }

  /**
   * returns the active player
   * 
   * @return Player
   */

  public Player getCurrentPlayer() {
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
      if (playerStock > Config.MAX_CARDS_IN_HAND_NO_DROP) {
        int resourceToSteal = playerStock / 2;
        for (int i = 0; i < resourceToSteal; i++) {
          List<Resource> resourceList = new ArrayList<>(player.getResourceSet());
          int randomIndex = (int) ((Math.random() * resourceList.size()));
          Resource resource = resourceList.get(randomIndex);
          player.removeOneResourceFromPlayer(resource);
          bank.addOneResource(resource);
        }
      }
    }
  }

  private List<Resource> resourceEarningByBuilding(Building building, Land landType) {
    List<Resource> resourceEarning = new ArrayList<>();
    for (int i = 0; i < building.getResourceEarning(); i++) {
      resourceEarning.add(landType.getResource());
    }
    return resourceEarning;
  }

  private Map<Faction, List<Resource>> setEmptyFactionMap(Map<Faction, List<Resource>> factionResourceMap) {
    for (Faction faction : getPlayerFactions()) {
      factionResourceMap.put(faction, Collections.emptyList());
    }
    return factionResourceMap;
  }
}
