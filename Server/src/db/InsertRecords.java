package db;
import java.sql.*;

public class InsertRecords {
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/users";

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
      
      System.out.println("Inserting records into the table...");
      stmt = conn.createStatement();
      
      String sql = "INSERT INTO USERS " +
                   "VALUES ('seppe', 'eppes')";
      stmt.executeUpdate(sql);
      sql = "INSERT INTO USERS " +
                   "VALUES ('killian', 'naillik')";
      stmt.executeUpdate(sql);
      sql = "INSERT INTO USERS " +
                   "VALUES ('robin', 'nibor')";
      stmt.executeUpdate(sql);
      sql = "INSERT INTO USERS " +
                   "VALUES('timo', 'otim')";
      stmt.executeUpdate(sql);
      System.out.println("Inserted records into the table...");

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