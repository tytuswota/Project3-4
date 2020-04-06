package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class saldoController {

    @FXML
    Button saldoToMenu;
    @FXML
    Label saldoField;
    @FXML
    Button afbreken;

    @FXML
    public void initialize() {

        String balance = ConnectionManager.userBalance(App.accountId);
        saldoField.setText("€ " + balance);
    }


    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }


}
