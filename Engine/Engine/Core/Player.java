package Core;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

class Player {
	private String name = null;
	private byte actions = 0;
	private byte buys = 0;
	private int coins = 0;
	public List<Card> deck = new ArrayList<Card>();
	public List<Card> hand = new ArrayList<Card>();
	public List<Card> discardPile = new ArrayList<Card>();
	
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
	}
}