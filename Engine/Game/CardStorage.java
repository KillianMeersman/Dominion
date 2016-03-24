import java.util.ArrayList;
import java.util.List;

public class CardStorage {
	private static List<Card> cards = new ArrayList<Card>();
	private CardStorage() { };
	
	public static List<Card> getCards() {
		return cards;
	}
}
