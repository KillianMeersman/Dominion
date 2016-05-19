package Controller;

import Interface.IKeyDetection;
import Core.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Sepp
 */
public class Game implements Initializable, IKeyDetection {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
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
