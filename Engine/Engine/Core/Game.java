package Core;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

// Represents an active game - base class
public class Game {

    private List<Player> players = new ArrayList<>();
    private Instant beginTime = null;
    private int turn = 0;
    private boolean gameRunning = true;
    private Supply supply;
    private Player activePlayer;
    private ArrayList<Card> playArea = new ArrayList<>();
    private ArrayList<Card> currentSet;
    private ArrayList<ActionCard> reactionCards = new ArrayList<>();

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

    private Player getPlayer(int playerId) {
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getId() == playerId) {
                return players.get(i);
            }
        }
        return null;
    }

    public Game(String[] playerNames) {
        for (int i = 0; i < playerNames.length; i++) {
            Player player = new Player(i, playerNames[i]);
            players.add(player);
        }
        supply = new Supply((byte) playerNames.length);

        activePlayer = players.get(0);
        this.beginTime = Instant.now();
        ConsoleController.addGame(this);
    }

    public Game(String[] playerNames, ActionCard[] actionDeck) {
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

    public void playActionCard(int CardIndex) {
        
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
        //activePlayer = players.get((players.indexOf(activePlayer) + 1) % players.size());
    }
    
    private int getCardsValue(ArrayList<TreasureCard> cards) {
        int value = 0;
        for (TreasureCard treasureCard : cards) {
            value += treasureCard.getValue();
        }
        return value;
    }
    
    public String[] getActionCardParamMessages(int CardIndex) {
        return ((ActionCard) currentSet.get(CardIndex)).parameterMessages;
    }
    
    public String[] getActionCardModeMessages(int CardIndex) {
        return ((ActionCard) currentSet.get(CardIndex)).actionMessages;
    }
}