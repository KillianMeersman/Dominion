package db;

import java.sql.*;

public class Register {
   //Settings
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
      
      //Get conection
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      stmt = conn.createStatement();
      
      //SQL statement
      String sql = "INSERT INTO dominion.users (name, password) VALUES ('" + username + "', '" + password + "');"; 
      
      //Execute statement
      stmt.executeUpdate(sql);

   }catch(SQLException se){
      //Handle errors for JDBC
      throw se;
   }catch(ClassNotFoundException e){
      //Handle errors for Class.forName
      throw e;
   }
   return null;
} 
}