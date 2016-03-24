package Engine;
public class CardEffect {
	public void doEffects(String script) {
		String[] statements = script.split(";");
		for (int i = 0; i < statements.length; i++) {
			executeStatement(statements[i]);
		}
	}
	
	private void executeStatement(String statement) {
		switch (statement) {
		case "DISCARD":
			
		}
	}
	
	private void discard(Card card, int amount) {
		
	}
}
