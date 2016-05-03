package Core;

import java.util.ArrayList;

public class Supply {
    private final int actionAmount = 10;
    
    private ArrayList<ArrayList<Card>> treasureCards = new ArrayList<>();
    private ArrayList<ArrayList<Card>> victoryCards = new ArrayList<>();
    private ArrayList<ArrayList<Card>> actionCards = new ArrayList<>();
    private ArrayList<Card> curseCards = new ArrayList<Card>();
    private ArrayList<Card> trash = new ArrayList<Card>();
    
    public Supply(Card[] actionCards) {
        
    }

    private void setActionCards(Card[] cards) {
        for (int i = 0; i < cards.length; i++) {
            for (int i2 = 0; i < actionAmount; i++) {
                actionCards.get(i).add(i2, cards[i]);
            }
        }
    }
}