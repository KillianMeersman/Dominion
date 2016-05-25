package Core;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

// Represents an active game - base class
public class Game {

    protected IEngineInterface view;
    private List<Player> players = new ArrayList<>();
    private Instant beginTime = null;
    private int turn = 1;
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

    public Game(IEngineInterface view, String[] playerNames, Card[] actionDeck) {
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
    
    public void buy(int cardIndex) throws Exception {
        Card card = currentSet.get(cardIndex - 1);
        
        // value check
        if (activePlayer.getCoins() < card.getCost()) {
            throw new Exception("Not enough money");
        }
        
        activePlayer.discard.add(card);
        supply.reduceAmount(card);
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
    
    public void playActionCard(Card actionCard) throws Exception {
        try {
            activePlayer.inActionMode = true;
            ((ActionCard)actionCard).execute(this, activePlayer);
        } catch (Exception e) {
            throw new Exception("Invalid input");
        }
    }
}