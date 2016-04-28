package Core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class Main extends Application {
    
    public static Stage window;
    public static Scene startScreen, mainMenu;    
    
    public static void Main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        
        Parent start = FXMLLoader.load(getClass().getResource("start.fxml"));
        Parent menu = FXMLLoader.load(getClass().getResource("menu.fxml"));
        
        startScreen = new Scene(start);
        mainMenu = new Scene(menu);
       
        window.getIcons().add(new Image("http://i.imgur.com/m1IuO5q.png"));
        window.setTitle("Dominion");
        window.setWidth(1280);
        window.setHeight(720);
        window.setResizable(false);
        window.setScene(startScreen);
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
        Boolean answer = confirmBox.display("Exit Requested", "Are you sure you want to quit?");
        if(answer) {window.close();}
    }
}