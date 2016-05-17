package Controller;

import Core.Main;
import static Core.Main.gameScreen;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

public class Game implements Initializable, IKeyDetection {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO     
    }

    public void init(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ESCAPE: Main.switchScene(Main.mainScreen); break;
            }
        }); 
    }
}
