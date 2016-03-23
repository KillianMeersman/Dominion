package model;
import java.util.*;

public class CardPile {
private List<Card> cards = new ArrayList<Card>(); 
	
	public CardPile(){
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card newCard){
		cards.add(newCard);
	}
	
	public List<Card> getDeck(){
		return cards;
	}
	
	public void shuffle(){
		Collections.shuffle(cards);		
	}

	/*public void addCard(TreasureCard newCard) {					//DELETE
		cards.add(newCard);		
	}*/
}
