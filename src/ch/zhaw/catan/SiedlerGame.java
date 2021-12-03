package ch.zhaw.catan;

import ch.zhaw.catan.Config.Faction;
import ch.zhaw.catan.Config.Resource;
import java.awt.Point;
import java.util.List;
import java.util.Map;

/**
 * This class performs all actions related to modifying the game state. TODO:
 * (your documentation)
 *
 * @author TODO
 */
public class SiedlerGame {
  static final int FOUR_TO_ONE_TRADE_OFFER = 4;
  static final int FOUR_TO_ONE_TRADE_WANT = 1;

  private List<Player> playerList;
  private List<Faction> playerFactionList;
  private int dicethrow;
  private int currentPlayer = 0;

  /**
   * Constructs a SiedlerGame game state object.
   * 
   * @param winPoints       the number of points required to win the game
   * @param numberOfPlayers the number of players
   * @throws IllegalArgumentException if winPoints is lower than three or players
   *                                  is not between two and four
   */
  public SiedlerGame(int winPoints, int numberOfPlayers) {
    // TODO: finish Implement
    setPlayerList(numberOfPlayers);

  }

  /**
   * Switches to the next player in the defined sequence of players.
   */
  public void switchToNextPlayer() {
    if (currentPlayer < (playerList.size() - 1)) {
      currentPlayer++;
    } else {
      currentPlayer = 0;
    }
  }

  /**
   * Switches to the previous player in the defined sequence of players.
   */
  public void switchToPreviousPlayer() {
    if (currentPlayer != 0) {
      currentPlayer--;
    } else {
      currentPlayer = playerList.size() - 1;
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
    // TODO: Implement
    return null;
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
    // TODO: Implement
    return false;
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
    // TODO: Implement
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
    int dummy = 3;
    // TODO: Implement
    for (int i = 0; i < dummy; i++) {

    }
    int cardsInHand = getCurrentPlayerStock();
    if (dicethrow == 7) {
      if (cardsInHand > Config.MAX_CARDS_IN_HAND_NO_DROP) {
        cardsInHand = cardsInHand / 2;
      }

    } else {
    }
    return null;
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
    // TODO: Implement
    return false;
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
    // TODO: OPTIONAL task - Implement
    return false;
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
    // TODO: Implement
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
    // TODO: Implement
    return false;
  }

  /**
   * Returns the winner of the game, if any.
   * 
   * @return the winner of the game or null, if there is no winner (yet)
   */
  public Faction getWinner() {
    // TODO: Implement
    return null;
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
    // TODO: Implement (or longest road functionality)
    return false;
  }

  // new implement

  private Player getCurrentPlayer() {
    return playerList.get(currentPlayer);
  }

  /**
   * This method simulates rolling a pair of dice.
   */
  private int rollWithTwoDice() {
    int diceOne;
    int diceTwo;

    diceOne = (int) ((Math.random() * 6) + 1); // magic number?
    diceTwo = (int) ((Math.random() * 6) + 1);

    dicethrow = diceOne + diceTwo;

    return dicethrow;
  }

  /**
   * Returns how many cards the current player owns.
   * 
   * @return the number of cards in hand
   */
  private int getCurrentPlayerStock() {
    int allCards = getCurrentPlayerResourceStock(Resource.GRAIN)
        + getCurrentPlayerResourceStock(Resource.WOOL) 
        + getCurrentPlayerResourceStock(Resource.LUMBER)
        + getCurrentPlayerResourceStock(Resource.ORE) 
        + getCurrentPlayerResourceStock(Resource.BRICK);

    return allCards;
  }

  private void setPlayerList(int numberOfPlayers) {
    Faction faction[] = Faction.values();
    for (int i = 0; i < numberOfPlayers; i++) {
      playerList.add(new Player(faction[i]));
    }
  }
}
