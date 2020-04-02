package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class loginController {

    @FXML
    Button bevestigen;
    Button cancel;


    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }
}
