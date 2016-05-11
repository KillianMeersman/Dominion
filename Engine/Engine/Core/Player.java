package Core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private int id;
    private String name = null;
    private byte actions = 1;
    private byte buys = 1;
    private PlayerPhase phase = PlayerPhase.PHASE_BUY;
    protected ArrayList<Card> deck = new ArrayList<>();
    protected ArrayList<Card> hand = new ArrayList<>();
    protected ArrayList<Card> discard = new ArrayList<>();

    protected void addAction(int amount) {
        actions += amount;
    }

    protected void addBuy(int amount) {
        buys += amount;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }
    
    public int getActions() {
        return actions;
    }

    public int getBuys() {
        return buys;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public PlayerPhase getPhase() {
        return phase;
    }

    public ArrayList<ActionCard> getActionCards(PlayerPlace source) {
        ArrayList<Card> cards = resolvePlayerPlace(source);
        ArrayList<ActionCard> out = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof ActionCard) {
                out.add((ActionCard) card);
            }
        }
        return out;
    }

    public ArrayList<TreasureCard> getTreasureCards(PlayerPlace source) {
        ArrayList<Card> cards = resolvePlayerPlace(source);
        ArrayList<TreasureCard> out = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof TreasureCard) {
                out.add((TreasureCard) card);
            }
        }
        return out;
    }

    public ArrayList<VictoryCard> getVictoryCards(PlayerPlace source) {
        ArrayList<Card> cards = resolvePlayerPlace(source);
        ArrayList<VictoryCard> out = new ArrayList<>();
        for (Card card : cards) {
            if (card instanceof VictoryCard) {
                out.add((VictoryCard) card);
            }
        }
        return out;
    }
    
    public int getTreasury() {
        int coins = 0;
        for (TreasureCard card : getTreasureCards(PlayerPlace.PLACE_HAND)) {
            coins += card.getValue();
        }
        return coins;
    }

    protected void drawFromDeck(int amount) {
        if (amount > deck.size() && discard.size() >= amount) {
            Collections.shuffle(discard);
            int size = amount - deck.size();
            for (int i = 0; i < size; i++) {
                Card.transferCard(discard.get(0), discard, deck, true, false);
            }
        }
        else if (discard.size() < amount && discard.size() > 0) {
            Collections.shuffle(discard);
            Card.transferCards(discard, deck, true);
        }
        for (int i = 0; i < amount; i++) {
                hand.add(deck.get(0));
                deck.remove(0);
        }
    }

    protected ArrayList<Card> resolvePlayerPlace(PlayerPlace place) {
        switch (place) {
            case PLACE_HAND:
                return hand;
            case PLACE_DECK:
                return deck;
            default:
                return discard;
        }
    }

    public void nextPhase() {
        switch (phase) {
            case PHASE_ACTION:
                phase = PlayerPhase.PHASE_BUY;
                break;
            case PHASE_BUY:
                phase = PlayerPhase.PHASE_CLEANUP;
                break;
            case PHASE_CLEANUP:
                phase = PlayerPhase.PHASE_ACTION;
                break;
        }
    }

    public boolean hasActionCards(PlayerPlace place) {
        for (Card card : resolvePlayerPlace(place)) {
            if (card instanceof ActionCard) {
                return true;
            }
        }
        return false;
    }

    protected Player(int id, String name) {
        this.id = id;
        this.name = name;

        for (Card card : CardRepository.getInstance().getStartingCards()) {
            for (int i = 0; i < card.getStartingAmount(); i++) {
                deck.add(card);
            }
        }

        Collections.shuffle(deck);
        drawFromDeck(5);
    }
}