package Controller;

import java.net.URL;
import Core.Main;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;


public class Menu implements Initializable {

    @FXML
    public Button buttonPlay;
    public Button buttonSavedGames;
    public Button buttonExit;
    
    public Button buttonFirstGame;
    public Button buttonBigMoney;
    public Button buttonInteraction;
    public Button buttonSizeDistortion;
    public Button buttonVillageSquare;
    public Button buttonCustomDeck;
    
    public Button buttonSaveGame1;
    public Button buttonSaveGame2;
    public Button buttonSaveGame3;
    public Button buttonSaveGame4;
    public Button buttonSaveGame5;
    
    public VBox vboxSavedGames;
    public VBox vboxNewGame;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonExit.setOnAction(e -> Core.Main.closeProgram());
        buttonPlay.setOnAction(e -> Core.Main.switchScene(Main.mainScreen));
        vboxNewGame.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> vboxNewGame.setVisible(false));
        vboxSavedGames.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> vboxSavedGames.setVisible(false));
        buttonPlay.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            vboxNewGame.setVisible(true);
            vboxSavedGames.setVisible(false);
        });
        buttonSavedGames.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            vboxNewGame.setVisible(false);
            vboxSavedGames.setVisible(true);
        });
        buttonExit.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            vboxNewGame.setVisible(false);
            vboxSavedGames.setVisible(false);
        });
        
        
    }
}
        
