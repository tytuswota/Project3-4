package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class bedragController {




    @FXML
    public void switchToSecondary() throws IOException {
        App.setRoot("secondary");


    }
}
