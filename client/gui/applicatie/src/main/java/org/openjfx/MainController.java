package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {
    @FXML
    Button bedrag;

    @FXML
    Button saldo;

    @FXML
    Button afbreken;

    @FXML
    private void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    @FXML
    private void switchToBedrag() throws IOException {
        App.setRoot("bedrag");
    }

    @FXML
    private void switchToSaldo() throws IOException {
        App.setRoot("saldo");
    }
}