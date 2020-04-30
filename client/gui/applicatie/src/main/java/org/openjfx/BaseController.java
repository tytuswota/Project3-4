package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import model.SerialReader;

import java.io.IOException;

/*
Contains the shared features of the controllers.
 */

public class BaseController {

    protected static SerialReader reader = SerialReader.GetReader();

    // Constructor
    public BaseController() {
        reader.addKeyPadListener((x) -> {
            baseKeyPressEventHandler(x);
        });
        reader.addRFIDListener((x) -> {
            RFIDEventHandler(x);
        });
    }

    private void baseKeyPressEventHandler(String key) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                char car = key.charAt(0);
                KeyPressEventHandler(car);
            }
        });
    }

    public void KeyPressEventHandler(char key) {
    }

    protected void RFIDEventHandler(String uid) {
    }

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }
}
