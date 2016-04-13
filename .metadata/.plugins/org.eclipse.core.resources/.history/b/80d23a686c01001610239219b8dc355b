package Engine;

import java.util.ArrayList;
import Engine.Cards.*;
import java.util.List;

class Supply {
	private List<Card> trashPile, cursePile = new ArrayList<Card>(); // Generate piles
	private List<TreasureCard> copperPile, silverPile, goldPile = new ArrayList<TreasureCard>();
	private List<VictoryCard> victoryPile1, victoryPile2, victoryPile3 = new ArrayList<VictoryCard>();
	
	public void trashCard(Card card) {
		trashPile.add(card);
	}
	
	public void trashCard(Card[] cards) {
		for (int i = 0; i < cards.length; i++) {
			trashPile.add(cards[i]);
		}
	}
	
	public TreasureCard getTreasureCard(TreasureType type) throws Exception {
		TreasureCard output = null;
		switch (type) {
		case TREASURE_COPPER:
			output = copperPile.get(0);
			copperPile.remove(0);
			return output;
		case TREASURE_SILVER:
			output = silverPile.get(0);
			silverPile.remove(0);
			return output;
		case TREASURE_GOLD:
			output = goldPile.get(0);
			goldPile.remove(0);
			return output;
		default:
			throw new Exception("no_more_cards"); 
		}
	}
	
	public VictoryCard getVictoryCard(VictoryType type) throws Exception {
		VictoryCard output = null;
		switch (type) {
		case VICTORY_ESTATE:
			output = victoryPile1.get(0);
			victoryPile1.remove(0);
			return output;
		case VICTORY_DUCHY:
			output = victoryPile2.get(0);
			victoryPile2.remove(0);
			return output;
		case VICTORY_PROVINCE:
			output = victoryPile3.get(0);
			victoryPile3.remove(0);
			return output;
		default:
			throw new Exception("no_more_cards");
		}
	}
}

enum TreasureType {
	TREASURE_COPPER,
	TREASURE_SILVER,
	TREASURE_GOLD
}

enum VictoryType {
	VICTORY_ESTATE,
	VICTORY_DUCHY,
	VICTORY_PROVINCE
}