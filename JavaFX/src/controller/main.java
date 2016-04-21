/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.control.Hyperlink;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * FXML Controller class
 *
 * @author Sepp
 */
public class main implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        
        Hyperlink hpl = new Hyperlink("java2s.com");
        hpl.setOnAction(new EventHandler<ActionEvent>() {
          @Override public void handle(ActionEvent e) {
              webEngine.load("http://java2s.com");
          }
      });
        
    }    
    
}
