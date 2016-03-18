package model;
import java.sql.*;

public class Authenticator {
	private Authenticator() {
		// No instancing
	}
	
	public static boolean authenticate(String username, String password) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		String query = "select * from users where username = " + username;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root&password=thetarun");
			
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			
			if (result.getString("pwd") == password) {
				return true;
			}
			return false;
		}
		catch (Exception e){
				throw e;
			}
		}
		finally {
			result.close();
			conn.close();
		}
	}
}
