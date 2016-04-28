package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class menu implements Initializable {

    @FXML
    public Button buttonPlay;
    public Button buttonSettings;
    public Button buttonExit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonExit.setOnAction(e -> Core.Main.closeProgram());
    } 
    
}
