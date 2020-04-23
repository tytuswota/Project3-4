package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ConnectionManager;

import java.io.IOException;

public class SaldoController {

    @FXML
    Button saldoToMenu;
    @FXML
    Label saldoField;
    @FXML
    Button afbreken;

    @FXML
    public void initialize() {
        try {
            String balance = ConnectionManager.getSession().getBalance();
            saldoField.setText("€ " + balance);
        }catch (Exception e){
            saldoField.setText("Sorrie, er kon geen verbinding gemaakt worden.");
        }
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
