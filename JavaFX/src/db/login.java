package db;

import java.sql.*;

public class Login {
   //Settings
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
      
      //Get conection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      
      //SQL statement
      String sql = "SELECT * FROM users WHERE name = '" + username + "'"; 
      
      //Execute statement
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
          //Select column          
         String password = rs.getString("password");
         returnValue = rs.getString("password");

         //Display values
         return password;
      }
      rs.close();
   }catch(SQLException se){
      //Handle errors for JDBC
      throw se;
   }catch(ClassNotFoundException e){
      //Handle errors for Class.forName
      throw e;
   }
   return returnValue;
} 
}