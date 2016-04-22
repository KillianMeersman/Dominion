package Engine;

import Engine.CardScript.Card;
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
	}
}