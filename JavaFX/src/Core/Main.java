package Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    
    public static Stage window;
    public static Scene credentialsScreen, mainScreen, gameScreen;    
    
    public static void Main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        
        Parent credentials = FXMLLoader.load(getClass().getResource("Credentials.fxml"));
        Parent main = FXMLLoader.load(getClass().getResource("Menu.fxml"));
        Parent game = FXMLLoader.load(getClass().getResource("Game.fxml"));
        
        credentialsScreen = new Scene(credentials);
        mainScreen = new Scene(main);
        gameScreen = new Scene(game);
       
        window.getIcons().add(new Image("http://i.imgur.com/m1IuO5q.png"));
        window.setTitle("Dominion");
        window.setWidth(1280);
        window.setHeight(720);
        window.setResizable(false);
        window.setScene(credentialsScreen);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.show(); 
    } 
    
    public static void switchScene(Scene scene) {
        window.setScene(scene);
    }
    
    public static void closeProgram() {
        System.out.println("Exit Requested");
        Boolean answer = ConfirmBox.display("Exit Requested", "Are you sure you want to quit?");
        if(answer) {window.close();}
    }
}