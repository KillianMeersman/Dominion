package Engine.CardScript;

import Engine.Player;
import java.util.List;

public class GainStatement extends Statement
	implements IFollowUp, IFailure {
	// gives the player a free card(s)
	
	private Statement followUp;
	private Statement failure;
	private Card card;
	private List<Card> source, destination;
	
	public GainStatement(Player player, List<Card> source, List<Card> destination, Card card) {
		super(player);
		this.source = source;
		this.destination = destination;
		this.card = card;
	}

	@Override
	public void setFollowUp(Statement followUp) {
		this.followUp = followUp;
	}
	
	@Override
	public void setFailure(Statement failure) {
		this.failure= failure;
	}

	@Override
	public void addParameter(String parameter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute() {
		if (!Card.transferCards(source, destination, card, true)) { failure.execute(); }
		followUp.execute();
	}
}
