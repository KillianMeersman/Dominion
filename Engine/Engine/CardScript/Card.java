package Engine.CardScript;

import java.util.List;

public abstract class Card {
	public String name = null;
	public int cost = 0;
	
	public Card() {
            System.out.println("Card build: " + name);
	}
	
	public static boolean transferCards(List<Card> source, List<Card> destination, int amount, boolean removeSource, boolean returnFalse) {
		Card card = null;
		for (int i = 0; i < amount; i++) {
			card = source.get(i);
			if (card == null && returnFalse) { return false; }
			destination.add(card);
			if (removeSource) { source.remove(i); }
		}
		return true;
	}
	
	public static boolean transferCards(List<Card> source, List<Card> destination, Card card, boolean removeSource) {
		if (!source.contains(card)) { return false; }
		destination.add(card);
		if (removeSource) { source.remove(card); }
		return true;
	}
	
	public static boolean transferCards(List<Card> source, List<Card> destination, Card[] cards, boolean removeSource, boolean returnFalse) {
		int sourceIndex = 0;
		for (int i = 0; i < cards.length; i++) {
			if (!source.contains(cards[i]) && returnFalse) { return false; }
			sourceIndex = source.indexOf(cards[i]);
			destination.add(source.get(sourceIndex));
			if (removeSource) { source.remove(sourceIndex); }
		}
                return true;
	}
}
