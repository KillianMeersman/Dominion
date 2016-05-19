package Database;

import java.sql.*;

/**
 *
 * @author Sepp
 */
public class Login {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/Dominion";
   static final String USER = "root";
   static final String PASS = "toor";
   public static String returnValue;
   
   public static String getPassword(String username) throws SQLException, Exception {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");
      
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      
      String sql = "SELECT * FROM users WHERE name = '" + username + "'"; 
      
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){       
         String password = rs.getString("password");
         returnValue = rs.getString("password");

         return password;
      }
      rs.close();
   }catch(SQLException se){
      throw se;
   }catch(ClassNotFoundException e){
      throw e;
   }
   return returnValue;
} 
}