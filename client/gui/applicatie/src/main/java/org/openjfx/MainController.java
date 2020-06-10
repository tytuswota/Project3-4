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

    public static int banknote1;

    public static int banknote2;
    public static int banknote3;

    public static int getBanknote1() {
        return banknote1;
    }

    public static int getBanknote2() {
        return banknote2;
    }

    public static int getBanknote3() {
        return banknote3;
    }

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

        banknote1 = banknotes.getTens();
        banknote2 = banknotes.getTwenties();
        banknote3 = banknotes.getFifties();

        BanknoteSelection.banknote1 = banknote1;
        BanknoteSelection.banknote2 = banknote2;
        BanknoteSelection.banknote3 = banknote3;

        withdraw(banknotes,0);
    }

    // Handles the keypress event.
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