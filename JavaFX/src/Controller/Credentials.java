package Controller;

import Interface.IKeyDetection;
import Core.Main;
import Core.AlertBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Sepp
 */
public class Credentials implements Initializable, IKeyDetection {
    
    @FXML
    public TextField userName;
    public PasswordField userPassword;
    public Button buttonLogin;
    public Button buttonRegister;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonLogin.setOnAction(e -> {
            try {
                if (userPassword.getText().equals(Database.Login.getPassword(userName.getText()))) {
                    Main.switchScene(Main.mainScreen);
                    Core.Main.window.setFullScreen(true);
                } else {
                    System.out.println("Wrong combination!");
                    System.out.println("Please try again or register.");
                    AlertBox.display("Wrong combination", "Please try again or register.");
                }
            } catch (Exception ex) {
                Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
                AlertBox.display("Database", "Connection failed.");
            }
        });
        buttonRegister.setOnAction(e -> {
            try {
                Database.Register.updateRecords(userName.getText(), userPassword.getText());
                System.out.println("Game on!");
                System.out.println("You are now registered.");
                AlertBox.display("Game on!", "You are now registered.");
            } catch (Exception ex) {
                Logger.getLogger(Credentials.class.getName()).log(Level.SEVERE, null, ex);
                AlertBox.display("Database", "Connection failed.");
            }
            
        });
    }
    
    /**
     *
     * @param scene Current scene.
     */
    public void init(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case BACK_SPACE: Main.closeProgram(); break;
            }
        }); 
    }
}