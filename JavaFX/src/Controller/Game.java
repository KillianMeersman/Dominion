package Controller;

import Interface.IKeyDetection;
import Core.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author Sepp
 */
public class Game implements Initializable, IKeyDetection {
    
    @FXML
    public ImageView dropDown;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition playerStats = 
        new TranslateTransition(Duration.seconds(0.5), dropDown);
        
        dropDown.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            playerStats.setFromY(0);
            playerStats.setToY(145);
            playerStats.play();
        });
        dropDown.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            playerStats.setFromY(145);
            playerStats.setToY(0);
            playerStats.play();
        });
    }

    /**
     *
     * @param scene Current scene
     */
    public void init(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ESCAPE: Main.switchScene(Main.mainScreen); break;
            }
        }); 
    }
}
