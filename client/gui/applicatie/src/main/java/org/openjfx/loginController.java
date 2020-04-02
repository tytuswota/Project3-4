package org.openjfx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.openjfx.loginController;

public class loginController {

    @FXML
    PasswordField pin;
    @FXML
    Button bevestigen;
    @FXML
    Button annuleren;


    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    SerialReader serialReader = new SerialReader() {
        @Override
        protected void KeyPressEvent(String key) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pin.appendText(key);
                }
            });
        }
    };

}
