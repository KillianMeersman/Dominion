package Controller;

import Interface.IKeyDetection;
import Core.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Sepp
 */
public class Game implements Initializable, IKeyDetection {
    
    @FXML
    public ImageView dropDownImage;
    public Text dropDownText;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TranslateTransition dropImage = new TranslateTransition(Duration.seconds(0.5), dropDownImage);
        TranslateTransition dropText = new TranslateTransition(Duration.seconds(0.5), dropDownText);
        
        dropDownImage.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
            dropImage.setFromY(0);
            dropImage.setToY(145);
            dropImage.play();
            dropText.setFromY(0);
            dropText.setToY(145);
            dropText.play();
        });
        dropDownImage.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
            dropImage.setFromY(145);
            dropImage.setToY(0);
            dropImage.play();
            dropText.setFromY(145);
            dropText.setToY(0);
            dropText.play();
        });
    }

    /**
     *
     * @param scene Current scene
     */
    public void init(Scene scene) {
        scene.setOnKeyPressed((KeyEvent event) -> {
            switch (event.getCode()) {
                case ESCAPE: event.consume(); break;
            }
        }); 
    }
}
