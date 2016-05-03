package Core;

import java.util.ArrayList;
import java.util.List;

class CardRepository {
    private static CardRepository instance = new CardRepository();
    private List<Card> treasureCards = new ArrayList<Card>();
    private List<Card> victoryCards = new ArrayList<Card>();
    private List<Card> actionCards = new ArrayList<Card>();
    private List<Card> curseCards = new ArrayList<Card>();

    private CardRepository() {  };	
    
    public static CardRepository getInstance() {
        return instance;
    }

    public ArrayList<Card> getCards() {
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
    
    public ArrayList<Card> getCardsById(int[] cardId) {
        ArrayList<Card> out = new ArrayList<Card>();
        for (Card card : CardRepository.getInstance().getCards()) {
            if (Utility.arrayContains(cardId, card.getId())) {
                out.add(card);
            }
        }
        return out;
    }
}