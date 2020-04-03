package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class saldoController {

    @FXML
    Button saldoToMenu;

    @FXML
    Button afbreken;


    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }


}
