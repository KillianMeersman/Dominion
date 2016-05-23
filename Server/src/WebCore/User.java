package WebCore;

public class User {
	private String _sessioncode = null;
	private String _username = null;
	
	public String getSessioncode() {
		return _sessioncode;
	}
	
	public String getUsername() {
		return _username;
	}
	
	public User(String sessioncode, String username) {
		_sessioncode = sessioncode;
		_username = username;
	}
}
