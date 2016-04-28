package controller;

import Core.Main;
import Core.alertBox;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class start implements Initializable {
    
    @FXML
    public TextField userName;
    public PasswordField userPassword;
    public Button buttonLogin;
    public Button buttonRegister;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        buttonLogin.setOnAction(e -> {
            if (userPassword.getText().equals(db.login.getPassword(userName.getText()))) {
                Main.switchScene(Main.mainMenu);
            } else {
                System.out.println("Wrong combination!");
                System.out.println("Please try again or register.");
                alertBox.display("Wrong combination", "Please try again or register.");
            }
        });
        buttonRegister.setOnAction(e -> {
            db.register.updateRecords(userName.getText(), userPassword.getText());
            System.out.println("Game on!");
            System.out.println("You are now registered.");
            alertBox.display("Game on!", "You are now registered.");
        });
    }  
}