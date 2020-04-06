package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class eigenBedragController {

    SerialReader reader;

    public eigenBedragController() {
        reader = SerialReader.GetReader();
        reader.addKeyPadListener((x) -> {
            KeyPressEventHandler(x);
        });
    }

    @FXML
    Button eigenBedragToMenu;

    @FXML
    TextField saldoText;

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    public void KeyPressEventHandler(String key){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                saldoText.appendText(key);
            }
        });
    }

}
