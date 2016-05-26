package Core;

import java.util.List;

public class Card {
    private final int id;
    private final boolean isStartingCard;
    private final int startingAmount;
    private final int amount;
    private final int cost;
    private final String name;
    private final String description;
    
    public int getId() {
        return id;
    }
    
    public boolean isStartingCard() {
        return isStartingCard;
    }
    
    protected int getStartingAmount() {
        return startingAmount;
    }
    
    protected int getAmount() {
        return amount;
    }
    
    public int getCost() {
        return cost;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return Utility.capitalize(this.name) + "\t\t - " + this.description;
    }
    
    protected Card(int id, int amount, int cost, String name, String description) {
     Card card = this;
     this.id = id;
     this.amount = amount;
     this.cost = cost;
     this.name = name;
     this.description = description;
     this.isStartingCard = false;
     this.startingAmount = 0;
    }
    
    protected Card(int id, int amount, int cost, String name, String description, int startAmount) {
     Card card = this;
     this.id = id;
     this.amount = amount;
     this.cost = cost;
     this.name = name;
     this.description = description;      
     this.isStartingCard = true;
     this.startingAmount = startAmount;
    }
    
    protected static void transferCard(Card card, List<Card> source, List<Card> destination, boolean removeFromSource, boolean force) {
        if (source.contains(card) || force) {
            destination.add(card);
        }
        if (removeFromSource) { source.remove(card); }
    }
    
    protected static void transferCard(Card[] cards, List<Card> source, List<Card> destination, boolean removeFromSource, boolean force) {
       for (Card card : cards) {
            if (source.contains(card) || force) {
            destination.add(card);
            }
        if (removeFromSource) { source.remove(card); }
        }
        
    } 
    
    protected static void transferCards(List<Card> source, List<Card> destination, boolean removeFromSource) {
        for (int i = 0; i < source.size(); i++) {
            destination.add(source.get(i));
        }
        if (removeFromSource) { source.clear(); }
    }
    
    protected static Card[] listToArray(List<Card> cards) {
        Card[] out = new Card[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            out[i] = cards.get(i);
        }
        return out;
    }
}