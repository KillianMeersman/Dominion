package Core;

import java.util.ArrayList;
import java.util.List;
import java.time.*;

// Represents an active game - base class
public class Game {

    protected IEngineInterface view;
    private List<Player> players = new ArrayList<>();
    private Instant beginTime = null;
    private int turn = 0;
    private Supply supply;
    private Player activePlayer;
    private ArrayList<Card> playArea = new ArrayList<>();
    private boolean running = true;
    
    public boolean isRunning() {
        return running;
    }
    
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
    
    public void buy(Card card) throws Exception {
        
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
        checkGameOver();
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
            Card.transferCard(actionCard, activePlayer.hand, playArea,  true, false);
            activePlayer.inActionMode = true;
            ((ActionCard)actionCard).execute(this, activePlayer);
        } catch (Exception e) {
            throw new Exception("Invalid input");
        }
        checkGameOver();
    }
    
    private void checkGameOver() {
        if (supply.getCardAmount(CardRepository.getInstance().getCardByName("province")) < 1) { // province pile empty?
            activePlayer = getWinningPlayer();
        }
        int emptyPiles = 0;
        for (Card card : supply.getAllCardsUnique()) {  // 3 or more piles empty?
            if (supply.getCardAmount(card) < 1) {
                emptyPiles++;
            }
        }
        if (emptyPiles >= 3) {
            activePlayer = getWinningPlayer();
        }
    }
    
    private Player getWinningPlayer() {
        Player top = players.get(0);
        for (Player player : players) {
            if (player.getVictoryCards(PlayerPlace.PLACE_DECK).size() > top.getVictoryCards(PlayerPlace.PLACE_DECK).size()) {
                top = player;
            }
            else if (player.getVictoryCards(PlayerPlace.PLACE_DECK).size() == top.getVictoryCards(PlayerPlace.PLACE_DECK).size()) {
                if (player.getTurns() > top.getTurns()) {
                    top = player;
                }
            }
        }
        return top;
    }
    
    public Card[] getBuyableCards() {
        ArrayList<Card> out = new ArrayList<Card>();
        for (Card card : supply.getAllCardsUnique()) {
            if (activePlayer.getCoins() >= card.getCost()) {
                out.add(card);
            }
        }
        Card[] outArray = new Card[out.size()];
        out.toArray(outArray);
        
        return outArray;
    }
}