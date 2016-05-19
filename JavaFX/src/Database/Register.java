package Database;

import java.sql.*;

/**
 *
 * @author Sepp
 */
public class Register {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/Dominion";
   static final String USER = "root";
   static final String PASS = "toor";
   public static String returnValue;
   
   public static String updateRecords(String username, String password) throws Exception {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");
      
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      
      String sql = "INSERT INTO dominion.users (name, password) VALUES ('" + username + "', '" + password + "');"; 
      
      stmt.executeUpdate(sql);

   }catch(SQLException se){
      throw se;
   }catch(ClassNotFoundException e){
      throw e;
   }
   return null;
} 
}