 package Core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Holds instance of each card once
public class CardRepository {
    private static CardRepository instance = new CardRepository();
    private ArrayList<TreasureCard> treasureCards = new ArrayList<>();
    private ArrayList<VictoryCard> victoryCards = new ArrayList<>();
    private ArrayList<Card> actionCards = new ArrayList<>();
    private ArrayList<Card> curseCards = new ArrayList<>();

    private CardRepository() {
        tempFillFunc();
    }
    
    public static CardRepository getInstance() {
        return instance;
    }
    
    public ArrayList<Card> getStartingCards() {
        ArrayList<Card> out = new ArrayList<>();
        for (Card card : getAllCards()) {
            if (card.isStartingCard()) {
                out.add(card);
            }
        }
        return out;
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
    
    // ugly temporary function to fill repo
    private void tempFillFunc() {
        // treasure cards
        treasureCards.add(new TreasureCard(1, 60, 0, "copper", "A copper coin worth 1", 7, 1)); // starting card
        treasureCards.add(new TreasureCard(2, 40, 3, "silver", "A silver coin worth 2", 2));
        treasureCards.add(new TreasureCard(3, 30, 6, "gold", "A gold coin worth 3", 3));
        
        // victory cards
        victoryCards.add(new VictoryCard(4, 24, 2, "estate", "A grand estate worth 1 victory point", 3, 1)); // starting card
        victoryCards.add(new VictoryCard(5, 12, 5, "ducky", "An estate worth 1 victory point", 3));
        victoryCards.add(new VictoryCard(6, 12, 8, "estate", "A province worth 1 victory point", 6));
        
        // action cards
        actionCards.add(new ActionCard(7, Supply.ACTIONSETAMOUNT, 2, "cellar", "+1 Action, Discard any number of cards. +1 Card per card discarded"));
        actionCards.add(new ActionCard(8, Supply.ACTIONSETAMOUNT, 5, "market", "+1 Card, +1 Action, +1 Buy, +1 Coin"));
        actionCards.add(new ActionCard(9, Supply.ACTIONSETAMOUNT, 4, "militia", "+2 Coins, Each other player discards down to 3 cards in his hand"));
        actionCards.add(new ActionCard(10, Supply.ACTIONSETAMOUNT, 5, "mine", "Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand"));
        actionCards.add(new ReactionCard(11, Supply.ACTIONSETAMOUNT, 2, "moat", "+2 Cards, When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that Attack"));
        actionCards.add(new ActionCard(12, Supply.ACTIONSETAMOUNT, 4, "remodel", "+2 Coins, Each other player discards down to 3 cards in his hand."));
        actionCards.add(new ActionCard(13, Supply.ACTIONSETAMOUNT, 4, "smithy", "+3 Cards"));
        actionCards.add(new ActionCard(14, Supply.ACTIONSETAMOUNT, 3, "village", "+1 Card, +2 Actions"));
        actionCards.add(new ActionCard(15, Supply.ACTIONSETAMOUNT, 3, "woodcutter", "+1 Buy, +2 Coins"));
        actionCards.add(new ActionCard(16, Supply.ACTIONSETAMOUNT, 3, "workshop", "Gain a card costing up to 4 Coins"));
        
    }
}