/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Core.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import Interface.IKeyDetection;

/**
 * FXML Controller class
 *
 * @author Sepp
 */
public class DeckCreation implements Initializable, IKeyDetection {

    /**
     * Initializes the controller class.
     */
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
                case ESCAPE: 
                    boolean answer = Core.ConfirmBox.display("Returning to main menu.","Are you sure?");
                    if (answer) {
                      Main.switchScene(Main.mainScreen); 
                        break;  
                    } else {
                    break;
                    }
            }
        }); 
    }
    
}
