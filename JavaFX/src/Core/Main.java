package Core;

import Interface.IKeyDetection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 *
 * @author Sepp
 */
public class Main extends Application {

    private FXMLLoader loader = new FXMLLoader();
    public static Stage window;
    public static Scene credentialsScreen, mainScreen, gameScreen, deckScreen;
        
    /**
     *
     * @param args null
     */
    public static void Main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        
        loader = new FXMLLoader(getClass().getResource("Credentials.fxml"));
        Parent credentials = loader.load();
        IKeyDetection a = loader.getController();
        loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent main = loader.load();
        IKeyDetection b = loader.getController();
        loader = new FXMLLoader(getClass().getResource("Game.fxml"));
        Parent game = loader.load();
        IKeyDetection c = loader.getController();
        loader = new FXMLLoader(getClass().getResource("DeckCreation.fxml"));
        Parent deck = loader.load();
        IKeyDetection d = loader.getController();
        
        credentialsScreen = new Scene(credentials);
        mainScreen = new Scene(main);
        gameScreen = new Scene(game);
        deckScreen = new Scene(deck);

        a.init(credentialsScreen);
        b.init(mainScreen);
        c.init(gameScreen);
        d.init(deckScreen);
        
        window.getIcons().add(new Image("http://i.imgur.com/m1IuO5q.png"));
        window.setTitle("Dominion");
        window.setWidth(1280);
        window.setHeight(720);
        window.setResizable(true);
        window.setScene(credentialsScreen);
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.show(); 
    } 
    
    /**
     *
     * @param scene Scene to switch to
     */
    public static void switchScene(Scene scene) {
        window.setScene(scene);
    }
    
    /**
     *
     */
    public static void closeProgram() {
        System.out.println("Exit Requested");
        Boolean answer = ConfirmBox.display("Exit Requested", "Are you sure you want to quit?");
        if(answer) {window.close();}
    }
    
    // INSERT HERE
}