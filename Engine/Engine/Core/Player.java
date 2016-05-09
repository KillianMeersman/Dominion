package Core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

class Player {        
	private String name = null;
	private byte actions = 0;
	private byte buys = 0;
	private int coins = 0;
	public ArrayList<Card> deck = new ArrayList<>();
	public ArrayList<Card> hand = new ArrayList<>();
	public ArrayList<Card> discard = new ArrayList<>();
	
        public void addAction(int amount) {
            actions += amount;
        }
        
        public void addBuy(int amount) {
            buys += amount;
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
        
	public Player(String name) {
            this.name = name;
            
            for (int i = 0; i < Supply.COPPER_START_AMOUNT; i++) {
                deck.add(CardRepository.getInstance().getCardByName("copper"));
            }
            
            for (int i = 0; i < Supply.ESTATE_START_AMOUNT; i++) {
                deck.add(CardRepository.getInstance().getCardByName("estate"));
            }
            
            Collections.shuffle(deck);
            drawFromDeck(5);
	}
        
        public void drawFromDeck(int amount) {
            for (int i = 0; i < amount; i++) {
                try {
                hand.add(deck.get(0));
                deck.remove(0);
                }
                catch(Exception e) {
                    
                }
            }
        }
        
        public ArrayList<Card> resolvePlayerPlace(PlayerPlace place) {
            switch (place) {
                case PLACE_HAND:
                    return hand;
                case PLACE_DECK:
                    return deck;
                default:
                    return discard;
            }
        }
}