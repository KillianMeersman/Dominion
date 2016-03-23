package testers;

import static org.junit.Assert.*;
import model.CardPile;
import model.TreasureCard;
import model.VictoryCard;

import org.junit.Test;

public class CardPileTester {
	
	
	@Test
	public void CanaryTest() {
		System.err.println("====(DECK TEST)====");
	}
	
	@Test
	public void addCard() {
		CardPile deck = new CardPile();
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
		
		
		assertEquals(deck.getDeck().get(0), goldCard);
		assertTrue(deck.getDeck().contains(provinceCard));
		assertEquals(deck.getDeck().size(),2);
		assertEquals(deck.getDeck().get(1).name,"Province");
	}

}
