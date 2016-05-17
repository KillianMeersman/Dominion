package Core;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

// Represents an active game - base class
public class Game {

    private IEngineInterface view;
    private List<Player> players = new ArrayList<>();
    private Instant beginTime = null;
    private int turn = 0;
    private Supply supply;
    private Player activePlayer;
    private ArrayList<Card> playArea = new ArrayList<>();
    private ArrayList<Card> currentSet;

    @Override
    public String toString() {
        String output = "[" + players.get(0).getName();
        for (Player player : players) {
            output += ", " + player.getName();
        }
        output += "] " + getDuration().toMinutes() + " minutes";
        return output;
    }

    public Duration getDuration() {
        return Duration.between(beginTime, Instant.now());
    }
    
    public ArrayList<Card> getCurrentSet() {
        return currentSet;
    }
    
    public Supply getSupply() {
        return supply;
    }
    
    public int getTurn() {
        return turn;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
    
    public List<Player> getPlayers() {
        return players;
    }

    public ArrayList<Card> getBuyableCards() {
        ArrayList<Card> out = new ArrayList<>();
        int treasury = getActivePlayer().getTreasury();
        for (Card card : supply.getAllCardsUnique()) {
            if (card.getCost() <= treasury) {
                out.add(card);
            }
        }
        currentSet = out;
        return out;
    }
    
    public ArrayList<Card> getTreasureCards() {
        currentSet = activePlayer.getTreasureCards(PlayerPlace.PLACE_HAND);
        return currentSet;
    }
    
    public ArrayList<Card> getActionCards() {
        currentSet = activePlayer.getActionCards(PlayerPlace.PLACE_HAND);
        return currentSet;
    }

    private Player getPlayer(int playerId) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == playerId) {
                return players.get(i);
            }
        }
        return null;
    }

    public Game(IEngineInterface view, String[] playerNames) {
        this.view = view;
        for (int i = 0; i < playerNames.length; i++) {
            Player player = new Player(i, playerNames[i]);
            players.add(player);
        }
        supply = new Supply((byte) playerNames.length);

        activePlayer = players.get(0);
        this.beginTime = Instant.now();
        ConsoleController.addGame(this);
    }

    public Game(IEngineInterface view, String[] playerNames, ActionCard[] actionDeck) {
        this.view = view;
        for (int i = 0; i < playerNames.length; i++) {
            Player player = new Player(i, playerNames[i]);
            players.add(player);
        }
        try {
            supply = new Supply((byte) playerNames.length);
        } catch (Exception e) {
            System.out.println("FATAL ERROR: Deck too large");
        }

        activePlayer = players.get(0);
        this.beginTime = Instant.now();
        ConsoleController.addGame(this);
    }
    
    public void buy(int cardIndex, int[] treasureCardIndexes) throws Exception {
        Card card = currentSet.get(cardIndex - 1);
        ArrayList<TreasureCard> treasureCards = new ArrayList<>();
        for (int index : treasureCardIndexes){
            treasureCards.add((TreasureCard) activePlayer.getTreasureCards(PlayerPlace.PLACE_HAND).get(index - 1));
        }
        
        // value check
        if (getCardsValue(treasureCards) < card.getCost()) {
            throw new Exception("Not enough money");
        }
        
        activePlayer.discard.add(card);
        supply.reduceAmount(card);
        
        for (TreasureCard c : treasureCards) {
            Card.transferCard(c, activePlayer.getHand(), playArea, true, false);
        }
        activePlayer.addBuy(-1);
        
        if (activePlayer.getBuys() < 1) {
            activePlayer.nextPhase();
        }
    }
    
    public void cleanup() {
        Card.transferCards(activePlayer.hand, activePlayer.discard, true);
        Card.transferCards(playArea, activePlayer.discard, true);
        activePlayer.drawFromDeck(5);
        activePlayer.addBuy(1);
        activePlayer.addAction(1);
        activePlayer.nextPhase();
        nextPlayer();
    }
    
    private void nextPlayer() {
        if (players.indexOf(activePlayer) == players.size() - 1) {
            activePlayer = players.get(0);
        }
        else {
            activePlayer = players.get(players.indexOf(activePlayer) + 1);
        }
        turn += 1;
        //activePlayer = players.get((players.indexOf(activePlayer) + 1) % players.size());
    }
    
    private int getCardsValue(ArrayList<TreasureCard> cards) {
        int value = 0;
        for (TreasureCard treasureCard : cards) {
            value += treasureCard.getValue();
        }
        return value;
    }
    
    public void playActionCard(int cardIndex) throws Exception {
        try {
            activePlayer.inActionMode = true;
            ((ActionCard)currentSet.get(cardIndex)).execute(this, activePlayer);
        } catch (Exception e) {
            throw new Exception("Invalid input");
        }
    }
    
    private <T extends Object> T promptPlayer(List<? extends Object> list, String message, boolean canEnd) {
        try {
            String append = canEnd ? " ('end' to end action-mode) > " : " > ";
            String in = view.promptPlayer(message + append);
            if (canEnd) {
                if (in.equals("end")) { activePlayer.inActionMode = false; return null; }
                return (T)list.get(Integer.parseInt(in));
            }
            else {
                return (T)list.get(Integer.parseInt(in));
            }
        }
        catch (Exception e) {
            view.messagePlayer("Invalid input");
            return promptPlayer(list, message, canEnd);
        }
    }
    
    protected Card promptPlayerCard(String message, boolean canEnd) {
        return promptPlayer(currentSet, message, canEnd);
    }
    
    protected Player promptPlayerPlayer(String message, boolean canEnd) {
        return promptPlayer(players, message, canEnd);
    }
    
    protected void displayCards(ArrayList<Card> cards) {
        Card[] cardsArray = new Card[cards.size()];
        cards.toArray(cardsArray);
        view.displayCards(cardsArray);
    }
}