package Controller;

import Core.Main;
import static Core.Main.window;
import Interface.IKeyDetection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Sepp
 */
public class Menu implements Initializable, IKeyDetection {
    
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
    
    public Rectangle rectangleNewgame;
    public Rectangle rectangleSavegame;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonExit.setOnAction(e -> Core.Main.closeProgram());
        buttonCustomDeck.setOnAction(e -> {
            Json.Write.Object("FirstGame", "gameType");
            Core.Main.switchScene(Main.deckScreen); 
            Core.Main.window.setFullScreen(true);
        });
        buttonFirstGame.setOnAction(e -> {
            Json.Write.Object("FirstGame", "gameType");
            Core.Main.switchScene(Main.gameScreen); 
            Core.Main.window.setFullScreen(true);
        });
        buttonBigMoney.setOnAction(e -> {
            Json.Write.Object("BigMoney", "gameType");
            Core.Main.switchScene(Main.gameScreen); 
            Core.Main.window.setFullScreen(true);
        });
        buttonInteraction.setOnAction(e -> {
            Json.Write.Object("Interaction", "gameType");
            Core.Main.switchScene(Main.gameScreen); 
            Core.Main.window.setFullScreen(true);
        });
        buttonSizeDistortion.setOnAction(e -> {
            Json.Write.Object("SizeDistortion", "gameType");
            Core.Main.switchScene(Main.gameScreen); 
            Core.Main.window.setFullScreen(true);
        });
        buttonVillageSquare.setOnAction(e -> {
            Json.Write.Object("FirstGame", "gameType");
            Core.Main.switchScene(Main.gameScreen);
            Core.Main.window.setFullScreen(true);
        });
        vboxNewGame.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            vboxNewGame.setVisible(false);
            rectangleNewgame.setVisible(false);
        });
        vboxSavedGames.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            vboxSavedGames.setVisible(false);
            rectangleSavegame.setVisible(false);
        });
        buttonPlay.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            vboxNewGame.setVisible(true);
            vboxSavedGames.setVisible(false);
            rectangleNewgame.setVisible(true);
            rectangleSavegame.setVisible(false);
        });
        buttonSavedGames.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            vboxNewGame.setVisible(false);
            vboxSavedGames.setVisible(true);
            rectangleNewgame.setVisible(false);
            rectangleSavegame.setVisible(true);
        });
        buttonExit.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            vboxNewGame.setVisible(false);
            vboxSavedGames.setVisible(false);
            rectangleNewgame.setVisible(false);
            rectangleSavegame.setVisible(false);
            
        });
        
              
    }
    
    /**
     *
     * @param scene Current Scene
     */
    public void init(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ESCAPE: Main.closeProgram(); break;
            }
        }); 
    }
}
        
