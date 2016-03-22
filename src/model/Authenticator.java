package model;

import java.sql.*;

import java.security.*;

public class Authenticator {
	private Authenticator() {
		// No instancing
	}
	
	private static String byteToHex(byte[] input) {
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < input.length; i++) {
			builder.append(String.format("%02X", input[i]).toLowerCase());
		}
		return builder.toString();
	}
	
	private static String generateHash(String password, String salt) throws NoSuchAlgorithmException{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update((password + salt).getBytes());
		String encryptedString = byteToHex(messageDigest.digest());
		
		return encryptedString;
	}
	
	public static boolean authenticate(String username, String password) throws Exception {
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/users?user=root&password=thetarun");
			String query = "select salt from users where username = '" + username + "'";
			String salt = null;
			
			// Get hash salt
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			if (result.next()) {
					salt = result.getString("salt");
			}
			else {
				throw new Exception();
			}
			result.close();
			
			// Hash generation
			String hash = generateHash(password, salt);
			
			
			query = "select * from users where username = '" + username + "'";
			
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			if (result.next()) {
				if (result.getString("pwdhash").equals(hash)) {
					return true;
				}
			}
			return false;
		}
		catch (Exception e){
				throw e;
		}
		finally {
			result.close();
			conn.close();
		}
	}
}
