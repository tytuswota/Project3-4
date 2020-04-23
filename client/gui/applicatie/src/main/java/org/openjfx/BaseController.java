package org.openjfx;

import javafx.application.Platform;
import model.SerialReader;

/*
Contains the shared features of the controllers.
 */

public class BaseController {

    SerialReader reader;

    // Constructor
    public BaseController() {
        reader = SerialReader.GetReader();
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
}
