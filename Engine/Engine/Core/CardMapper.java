package Core;

import java.sql.*;
import java.util.ArrayList;

class CardMapper {
    private static int currentId = 0;
    
    public static ArrayList<TreasureCard> getTreasureCards() {
        ResultSet rs = retrieve("treasurecards");
        ArrayList<TreasureCard> out = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    TreasureCard card = new TreasureCard(currentId++, rs.getInt("amount"), rs.getInt("cost"), rs.getString("cardname"), rs.getString("description"), rs.getInt("startingAmount"), rs.getInt("value"));
                    out.add(card);
                } catch (Exception e) {
                    System.out.println("ERROR: card field could not be read -- " + e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println("FATAL ERROR: no cards to read");
        }
        return out;
    }

    public static ArrayList<VictoryCard> getVictoryCards() {
        ResultSet rs = retrieve("victorycards");
        ArrayList<VictoryCard> out = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    VictoryCard card = new VictoryCard(currentId++, rs.getInt("amount"), rs.getInt("cost"), rs.getString("cardname"), rs.getString("description"), rs.getInt("startingAmount"), rs.getInt("value"));
                    out.add(card);
                } catch (Exception e) {
                    System.out.println("ERROR: card field could not be read -- " + e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println("FATAL ERROR: no cards to read");
        }
        return out;
    }

    public static ArrayList<ActionCard> getActionCards() {
        ResultSet rs = retrieve("actioncards");
        ArrayList<ActionCard> out = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    ActionCard card = new ActionCard(currentId++, Supply.ACTIONSETAMOUNT,rs.getInt("cost"), rs.getString("cardname"), rs.getString("description"), rs.getInt("startingAmount"));
                    out.add(card);
                } catch (Exception e) {
                    System.out.println("ERROR: card field could not be read -- " + e.getMessage());
                }

            }
        } catch (Exception e) {
            System.out.println("FATAL ERROR: no cards to read");
        }
        return out;
    }

    private static ResultSet retrieve(String table) {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/Dominion";
        String user = "administrator";
        String password = "thetarun";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.0.250:3306/Dominion?user=administrator&password=thetarun");
            pst = con.prepareStatement("SELECT * FROM " + table);
            rs = pst.executeQuery();

        } catch (SQLException ex) {
            System.out.println("FATAL ERROR: could not connect to card database");
        } catch (Exception e) {
            System.out.println("FATAL ERROR: could not connect to card database");
        }
        return rs;
    }
}