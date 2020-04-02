package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class loginController {

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

}
