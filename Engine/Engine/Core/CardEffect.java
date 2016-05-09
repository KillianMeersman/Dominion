package Core;

import java.util.Collections;
import java.util.List;

class CardEffect {

    private static void transferCards(List<Card> source, List<Card> destination, Card[] cards, boolean removeFromSource) {
        for (int i = 0; i < cards.length; i++) {
            if (source.contains(cards[i])) {
                destination.add(cards[i]);
                if (removeFromSource) { source.remove(cards[i]); }
            }
        }
    }

    private static void transferCards(List<Card> source, List<Card> destination, int amount, boolean removeFromSource) throws Exception {
        if (amount > source.size()) { throw new Exception("not enough cards in source"); }
        for (int i = 0; i < source.size(); i++) {
            destination.add(source.get(i));
            if (removeFromSource) { source.remove(i); }
        }
    }

    // Card effects

    public static void discard(Player player, List<Card> source, Card[] cards) {
            for (int i = 0; i < cards.length; i++) {
                    try {
                            transferCards(source, player.discard, cards, true);
                    } catch (Exception e) {
                            System.out.println("Debug: " + player.getName() + " did not have the cards for transfer (DISCARD)");
                    }
            }
    }

    public static void trash(Player player, Card[] cards) {
        //player.game.supply.trashCard(cards);
    }

    public static void gain(Player player, Card card) {
	//player.transferCards(source, destination, cards, removeSource);
    }

    public static void gain(Player player, int minimumCost, int maximumCost) {

    }

    public static void draw(Player player, int amount) {
            boolean drawFromDiscard = false;
            int rest = 0;
            if (player.deck.size() < amount) {
                    rest = amount % player.deck.size();
                    drawFromDiscard = true;
            }

            for (int i = 0; i < amount - rest; i++) {
                    try {
                            //player.transferCards(player.deck, player.hand, amount, true);
                    }
                    catch (Exception e) {
                            System.out.println("Debug: " + player.getName() + " did not have enough cards for transfer (DRAW) <- drawing from deck");
                    }
            }

            if (drawFromDiscard) {
                    Collections.shuffle(player.discard);
                    for (int i = 0; i < rest; i++) {
                            try {
                                    //player.transferCards(player.discardPile, player.hand, amount, true);
                            } catch (Exception e) {
                                    System.out.println("Debug: " + player.getName() + " did not have enough cards for transfer (DRAW) <- drawing from discardPile");
                            }
                    }
            }
    }

    /*
    public static void doEffects(String script) {
            String[] statements = script.split(";");
            for (int i = 0; i < statements.length; i++) {
                    //executeStatement(statements[i]);
            }
    }

    public static void test() {
            String testSource = "TRASH 1 treasure hand ;GAIN 1 treasure +3;";
            parse(testSource);
    }
    public static void parse(String source) {
            // Delimit by ;

            String[] statementStrings = source.split(";");
            List<Statement> statements = new ArrayList<Statement>();
            for (int i = 0; i < statementStrings.length; i++) {
                    String[] words = statementStrings[i].trim().split(" ");
                    statements.add(new Statement(words));
            }
    }
    */

}