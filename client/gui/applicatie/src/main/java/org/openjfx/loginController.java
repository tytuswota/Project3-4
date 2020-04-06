package org.openjfx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.openjfx.loginController;

public class loginController {

    SerialReader reader;

    public loginController() {
        reader = SerialReader.GetReader();
        reader.addKeyPadListener((x) -> {
            KeyPressEventHandler(x);
        });
    }

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

    public void KeyPressEventHandler(String key) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

//                    if (key == "#") {
//                    }

                char car = key.charAt(0);

                if ((car >= '0' && car <= '9')) {
                    if (pin.getLength() != 4) {
                        pin.appendText(key);
                    } else {
                        System.out.println("te lang");
                    }
                }
            }
        });
    }
}
