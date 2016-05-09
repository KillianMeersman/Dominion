package Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Holds instance of each card once
class CardRepository {
    private static CardRepository instance = new CardRepository();
    private ArrayList<TreasureCard> treasureCards = new ArrayList<>();
    private ArrayList<VictoryCard> victoryCards = new ArrayList<>();
    private ArrayList<Card> actionCards = new ArrayList<>();
    private ArrayList<Card> curseCards = new ArrayList<>();

    private CardRepository() {
        treasureCards.add(new TreasureCard(1, 60, 0, "copper", "test", 1));
        victoryCards.add(new VictoryCard(2, 20, 2, "estate", "test2", 1));
        for (TreasureCard card : CardMapper.retrieve("treasurecards")) {
            treasureCards.add(card);
        }
    };	
    
    public static CardRepository getInstance() {
        return instance;
    }
        
    public ArrayList<TreasureCard> getTreasureCards() {
        return treasureCards;
    }
    
    public ArrayList<VictoryCard> getVictoryCards() {
        return victoryCards;
    }
    
    public ArrayList<Card> getActionCards() {
        return actionCards;
    }
    
    public ArrayList<Card> getCurseCards() {
        return curseCards;
    }
    
    public ArrayList<Card> getAllCards() {
        ArrayList<Card> out = new ArrayList<>();
        for (Card card : treasureCards) {
            out.add(card);
        }
        for (Card card : victoryCards) {
            out.add(card);
        }
        for (Card card : actionCards) {
            out.add(card);
        }
        for (Card card : curseCards) {
            out.add(card);
        }
        return out;
    }
    
    public Card getCardByName(String name) {
       ArrayList<Card> cards = getAllCards();
       for (Card card : cards) {
           if (card.getName().equals(name)) {
               return card;
           }
       }
       return null;
    }
    
    public Card getCardById(int id) {
        for (Card card : getAllCards()) {
            if (card.getId() == id) {
                return card;
            }
        }
        return null;
    }
    
    public ArrayList<Card> getCardsById(int[] cardId) {
        ArrayList<Card> out = new ArrayList<Card>();
        for (Card card : getAllCards()) {
            if (Utility.arrayContains(cardId, card.getId())) {
                out.add(card);
            }
        }
        return out;
    }
}