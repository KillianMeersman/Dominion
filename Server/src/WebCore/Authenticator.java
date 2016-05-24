package WebCore;

import java.sql.*;
import java.util.Random;
import java.security.*;

public class Authenticator {
	private Authenticator() {
		// No instancing
	}
	
	private static String byteToHex(byte[] input) { // convert byte to hexadecimal
		StringBuilder builder = new StringBuilder();
		
		for (int i = 0; i < input.length; i++) {
			builder.append(String.format("%02X", input[i]).toLowerCase());
		}
		return builder.toString();
	}
	
	private static String generateHash(String password, String salt) throws NoSuchAlgorithmException{ // generate a hash from password and salt
		MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
		messageDigest.update((password + salt).getBytes());
		String encryptedString = byteToHex(messageDigest.digest());
		
		return encryptedString;
	}
	
	private static String generateSalt() {	// generate a random salt
		char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWQYZ0123456789".toCharArray();
		StringBuilder builder = new StringBuilder();
		Random random = new Random();
		
		for (int i = 0; i < 16; i++) {
			char c = chars[random.nextInt(chars.length)];
			builder.append(c);
		}
		
		return builder.toString();
	}
	
	public static boolean authenticate(String username, String password) throws Exception { // check if user correctly logged in
		Connection conn = null;
		Statement stmt = null;
		ResultSet result = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://78.20.159.253:3306/Dominion?user=administrator&password=thetarun");
			String query = "select pwdsalt from users where username = '" + username + "'";
			String salt = null;
			
			// SQL communication
			// Get hash salt
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			if (result.next()) {
					salt = result.getString("pwdsalt");
			}
			else {
				throw new Exception("no_such_user"); // User does not exist
			}
			result.close();
			
			// Hash generation
			String hash = generateHash(password, salt);
			
			// Get user password hash and compare it to our generated hash
			query = "select * from users where username = '" + username + "'";
			stmt = conn.createStatement();
			result = stmt.executeQuery(query);
			if (result.next()) {
				if (result.getString("pwdhash").equals(hash)) {
					return true; // Correct password
				}
				else {
					return false; // Wrong password
				}
			}
			else {
				throw new Exception("no_such_user"); // user does not exist
			}
		}
		catch (Exception e){
				throw e; // General connection error
		}
		finally {
			result.close();
			conn.close();
		}
	}
	
	public static void register(String username, String password) throws Exception { // Register a user
		Connection conn = null;
		Statement stmt = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://192.168.0.250:3306/Dominion?user=administrator&password=thetarun");
			
			// Salt and password hash generation
			String salt = generateSalt();
			String hash = generateHash(password, salt);
			String query = "insert into users(username, pwdhash, pwdsalt) values ('" + username + "', '" + hash + "', '" + salt + "')";
		
			// Insert user into database
			stmt = conn.createStatement();
			try {
			stmt.executeUpdate(query);
			} catch (Exception e){
				throw new Exception("user_already_exists"); // User already exists
			}
		}
		catch (Exception e){
				throw e; // General connection error
		}
		finally {
			conn.close();
		}
	}
}