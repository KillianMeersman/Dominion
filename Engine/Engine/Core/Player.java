package Core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

class Player {
	public Game game = null;
	public String name = null;
	public byte actions = 0;
	public byte buys = 0;
	public int coins = 0;
	public List<Card> deck = new ArrayList<Card>();
	public List<Card> hand = new ArrayList<Card>();
	public List<Card> discardPile = new ArrayList<Card>();
        
	// Player card actions
	public void shuffleDiscardPile() {
		Collections.shuffle(discardPile);
	}
	
	public Card[] getDeckCards(int amount) {
		Card[] output = new Card[amount];
		
		if (deck.size() >= amount) {
			for (int i = 0; i < amount; i++) {
				output[i] = deck.get(i);
				deck.remove(i);
			}
		}
		else {
			deck.toArray(output);
		}
		return output;
	}
	
	public Player(String name) {
            this.name = name;
            hand.add(new Card("Cellar", "Discard any number of cards. +1 Card per card discarded."));
            hand.add(new Card("Moneylender", "Trash a copper from your hand. If you do: +3 copper."));
            hand.add(new Card("Chapel", "Trash up to 4 cards from you hand."));
            hand.add(new Card("Copper", ""));
	}
}