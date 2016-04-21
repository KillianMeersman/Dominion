/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
/**
 *
 * @author Sepp
 */
public class Default extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 50, 50, 50));
        
        stage.getIcons().add(new Image("http://i.imgur.com/m1IuO5q.png"));
        stage.setTitle("Dominion");
        stage.setWidth(1280);
        stage.setResizable(false);
        stage.setHeight(720);
        stage.setScene(scene);
        stage.show();
    }
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
