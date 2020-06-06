package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.LanguageSystem;
import model.SetOfBanknotes;
import model.Withdrawer;

/**
 *
 * MainController
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class MainController extends BaseController {
    @FXML
    public void initialize() {
        balance.setText(LanguageSystem.getString("balance"));
        abort.setText(LanguageSystem.getString("abort"));
        withdraw.setText(LanguageSystem.getString("withdraw"));
        withdraw70.setText(LanguageSystem.getString("withdraw70"));
    }

    @FXML
    Label balance;

    @FXML
    Label abort;

    @FXML
    Label withdraw;

    @FXML
    Label withdraw70;

    @FXML
    Button bedrag;

    @FXML
    Button saldo;

    @FXML
    Button afbreken;

    @FXML
    Button pinZeventig;

    @FXML
    private void switchToBedrag() throws IOException {
        App.setRoot("bedrag");
    }

    @FXML
    private void switchToSaldo() throws IOException {
        App.setRoot("saldo");
    }

    @FXML
    private void PinZeventig() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(0, 1, 1);
        withdraw(banknotes,0);
    }

    public void KeyPressEventHandler(char key) {
        try {
            if (key == 'A') {
                switchToSaldo();
            }
            else if (key == 'B') {
                switchToPasUit();
            }
            else if (key == 'C') {
                switchToBedrag();
            }
            else if (key == 'D') {
                PinZeventig();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}