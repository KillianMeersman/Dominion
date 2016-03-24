package Engine;
import java.awt.Image;

public class Player {
	private String name = null;
	private Image avatar = null;
	private Card[] cards = new Card[5];
	
	public String getName() {
		return name;
	}
	
	public Image getAvatar() {
		return avatar;
	}
	
	public Player(String name, Image avatar) {
		this.name = name;
		this.avatar = avatar;
	}
}