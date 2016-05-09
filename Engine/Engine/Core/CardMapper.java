package Core;

import java.sql.*;
import java.util.ArrayList;

public class CardMapper {
    public ArrayList<TreasureCard> getTreasureCards() {
        ResultSet rs = retrieve("treasurecards");
        while(rs.next()) {
            TreasureCard card = new TreasureCard(rs.getInt("id"), rs.get);
            
        }
                
    }
    
    private static ResultSet retrieve(String table) {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    String url = "jdbc:mysql://localhost:3306/dominion";
    String user = "root";
    String password = "thetarun";
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dominion?user=root&password=thetarun");
            pst = con.prepareStatement("SELECT * FROM " + table);
            rs = pst.executeQuery();
            
            while (rs.next()) {
                System.out.print(rs.getInt(1));
                System.out.print(": ");
                System.out.println(rs.getString(2));
            }

        } catch (SQLException ex) {
            System.out.println("FATAL ERROR: could not connect to card database");
        } catch (Exception e) {
            System.out.println("FATAL ERROR: could not connect to card database");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
