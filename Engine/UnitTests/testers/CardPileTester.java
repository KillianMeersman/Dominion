
package testers;

import Engine.CardStorage;
import static org.junit.Assert.*;
import org.junit.Test;

import Engine.CardStorage;
import Engine.CardScript.TreasureCard;
import Engine.CardScript.VictoryCard;

public class CardPileTester {
	
	
	@Test
	public void CanaryTest() {
		System.err.println("====(DECK TEST)====");
	}
	
	@Test
	public void addCard() {
		CardStorage deck = CardStorage.getInstance();
		TreasureCard goldCard = new TreasureCard();
		goldCard.name = "Gold";
		goldCard.cost = 6;
		goldCard.value = 3;
		
		VictoryCard provinceCard = new VictoryCard();
		provinceCard.name = "Province";
		provinceCard.cost = 8;
		provinceCard.victoryPoints = 6;
		
		//Add Card
		deck.addCard(goldCard);
		deck.addCard(provinceCard);
		
		
		assertEquals(deck.getCards().get(0), goldCard);
		assertTrue(deck.getCards().contains(provinceCard));
		assertEquals(deck.getCards().size(),2);
		assertEquals(deck.getCards().get(1).name,"Province");
	}

}
