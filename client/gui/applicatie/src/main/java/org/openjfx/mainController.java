package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class mainController {
    @FXML
    Button bedrag;
    Button saldo;


    @FXML
    private void switchToBedrag() throws IOException {
        App.setRoot("bedrag");
    }

    @FXML
    private void switchToSaldo() throws IOException {
        App.setRoot("saldo");
    }
}