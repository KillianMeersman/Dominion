package Core;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 *
 * @author Sepp
 */
public class ConfirmBox {

    static boolean answer;

    /**
     *
     * @param title title to Display
     * @param message Message to display
     * @return returns The answer in boolean
     */
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
        buttonNo.setText("No");
        buttonYes.setText("Yes");

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
        
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ENTER:
                    answer = true;
                    confirmBox.close();
                    break;
                case ESCAPE:
                    answer = false;
                    confirmBox.close();
                    break;
                case Y:
                    answer = true;
                    confirmBox.close();
                    break;
                case N:
                    answer = false;
                    confirmBox.close();
                    break;
            }
        }); 
        
        confirmBox.showAndWait();

        return answer;
    }
    
}