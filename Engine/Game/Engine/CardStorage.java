package Engine;
import java.util.ArrayList;
import java.util.List;

public class CardStorage {
	private static List<Card> cards = new ArrayList<Card>();
	public CardStorage() { };												// @Killian: Why Private ?

	
	public List<Card> getCards() {											//Removed Static
		return cards;
	}

	
	public void addCard(Card newCard) {										//Added AddCard
		cards.add(newCard);
	}
	
}
