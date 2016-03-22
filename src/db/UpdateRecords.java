package db;

import java.sql.*;

public class UpdateRecords {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://localhost/DOMINION";
   
   static final String USER = "root";
   static final String PASS = "toor";
   
   public static void main(String[] args) {
   Connection conn = null;
   Statement stmt = null;
   try{
      Class.forName("com.mysql.jdbc.Driver");

      System.out.println("Connecting to a selected database...");
      conn = DriverManager.getConnection(DB_URL, USER, PASS);
      System.out.println("Connected database successfully...");
      
      System.out.println("Creating statement...");
      stmt = conn.createStatement();
      String sql = "UPDATE users " +
                   "SET password = 'seppe' WHERE name = 'seppe'";
      stmt.executeUpdate(sql);

      sql = "SELECT name, password FROM USERS";
      ResultSet rs = stmt.executeQuery(sql);

      while(rs.next()){
         String name = rs.getString("name");
         String password = rs.getString("password");

         System.out.print("name: " + name);
         System.out.print(", password: " + password);
      }
      rs.close();
   }catch(SQLException se){
      se.printStackTrace();
   }catch(Exception e){
      e.printStackTrace();
   }finally{
      try{
         if(stmt!=null)
            conn.close();
      }catch(SQLException se){
      }
      try{
         if(conn!=null)
            conn.close();
      }catch(SQLException se){
         se.printStackTrace();
      }
   }
   System.out.println("Goodbye!");
}
}