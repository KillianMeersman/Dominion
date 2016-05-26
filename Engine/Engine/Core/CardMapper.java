package Core;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Driver;

class CardMapper {
    //78.20.159.253
    private static int currentId = 0;
    private static final String URL = "jdbc:mysql://localhost:3306/Dominion";
    private static final String USER = "root";
    private static final String PASSWORD = "thetarun";
    
    private static final String ERROR_FIELDREAD = "ERROR: card field could not be read: ";
    private static final String ERROR_CONNECTION = "FATAL ERROR: card field could not be read: ";

    public static ArrayList<TreasureCard> getTreasureCards() throws Exception {
        ResultSet rs = retrieve("treasurecards");
        ArrayList<TreasureCard> out = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    TreasureCard card = new TreasureCard(currentId++, rs.getInt("amount"), rs.getInt("cost"), rs.getString("cardname").toLowerCase(), rs.getString("description"), rs.getInt("startingAmount"), rs.getInt("value"));
                    out.add(card);
                } catch (Exception e) {
                    throw new Exception(ERROR_FIELDREAD + e.getMessage());
                }

            }
        } catch (Exception e) {
            throw new Exception(ERROR_CONNECTION);
        }
        return out;
    }

    public static ArrayList<VictoryCard> getVictoryCards() throws Exception {
        ResultSet rs = retrieve("victorycards");
        ArrayList<VictoryCard> out = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    VictoryCard card = new VictoryCard(currentId++, rs.getInt("amount"), rs.getInt("cost"), rs.getString("cardname").toLowerCase(), rs.getString("description"), rs.getInt("startingAmount"), rs.getInt("value"));
                    out.add(card);
                } catch (Exception e) {
                    throw new Exception(ERROR_FIELDREAD + e.getMessage());
                }

            }
        } catch (Exception e) {
            throw new Exception(ERROR_CONNECTION);
        }
        return out;
    }

    public static ArrayList<ActionCard> getActionCards() throws Exception {
        ResultSet rs = retrieve("actioncards");
        ArrayList<ActionCard> out = new ArrayList<>();
        try {
            while (rs.next()) {
                try {
                    ActionCard card = new ActionCard(currentId++, Supply.ACTIONSETAMOUNT, rs.getInt("cost"), rs.getString("cardname").toLowerCase(), rs.getString("description"), rs.getInt("startingAmount"));
                    out.add(card);
                } catch (Exception e) {
                    throw new Exception(ERROR_FIELDREAD + e.getMessage());
                }

            }
        } catch (Exception e) {
            throw new Exception(ERROR_CONNECTION);
        }
        return out;
    }

    private static ResultSet retrieve(String table) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        con = DriverManager.getConnection(URL + "?user=" + USER + "&password=" + PASSWORD);
        pst = con.prepareStatement("SELECT * FROM " + table);
        rs = pst.executeQuery();
        return rs;
    }
}
