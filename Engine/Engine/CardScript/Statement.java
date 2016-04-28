package Engine.CardScript;

import Engine.Player;

abstract class Statement {
	public Player player;
	
	public abstract void addParameter(String parameter);
	
	public abstract void execute();
	
	public Statement(Player player) {
		this.player = player;
	}
}