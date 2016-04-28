package Core;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;

public class confirmBox {

    static boolean answer;

    public static boolean display(String title, String message) {
        Stage confirmBox = new Stage();
        confirmBox.initModality(Modality.APPLICATION_MODAL);
        confirmBox.setTitle(title);
        confirmBox.setMinWidth(300);
        confirmBox.setMinHeight(200);
        confirmBox.setResizable(false);
        confirmBox.getIcons().add(new Image("http://i.imgur.com/StwnYe8.png"));
        
        
        Label label = new Label();
        label.setText(message);

        Button buttonYes = new Button();
        Button buttonNo = new Button();
        if (title.equals("Exit?!")) {
            buttonNo.setText("nnn no srry");  
            buttonYes.setText("i think..");
        } else {
            buttonNo.setText("No");
            buttonYes.setText("Yes");
        }

        buttonYes.setOnAction(e -> {
            answer = true;
            confirmBox.close();
        });
        buttonNo.setOnAction(e -> {
            answer = false;
            confirmBox.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, buttonYes, buttonNo);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        confirmBox.setScene(scene);
        confirmBox.showAndWait();

        return answer;
    }

}