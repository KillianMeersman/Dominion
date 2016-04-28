package Core;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class alertBox {
    
    public static void display(String title, String message) {
     
        Stage alertBox = new Stage();
        
        alertBox.initModality(Modality.APPLICATION_MODAL);
        alertBox.setTitle(title);
        alertBox.setMinWidth(300);
        alertBox.setMinHeight(100);
        alertBox.setResizable(false);
        alertBox.getIcons().add(new Image("http://i.imgur.com/StwnYe8.png"));
        
        Label alert = new Label();
        alert.setText(message);
        Button confirm = new Button();
        if (title.equals("Exit?")) {
          confirm.setText("yes");  
        } else {
            confirm.setText("Close");
        }
        confirm.setOnAction(e -> alertBox.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(alert, confirm);
        layout.setAlignment(Pos.CENTER);
        
        Scene box = new Scene(layout);
        alertBox.setScene(box);
        alertBox.showAndWait();
        
    }
    
}
