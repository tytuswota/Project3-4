package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.LanguageSystem;
import model.SessionManager;
import java.io.IOException;

/**
 *
 * EigenBedragController
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class EigenBedragController extends BaseController {

    public void initialize() {
        confirm.setText(LanguageSystem.getString("confirm"));
        desiredAmount.setText(LanguageSystem.getString("desiredAmount"));
        backspace.setText(LanguageSystem.getString("backspace"));
        menu.setText(LanguageSystem.getString("menu"));
    }

    @FXML
    Button eigenBedragToMenu;

    @FXML
    Button corrigeren;

    @FXML
    Button bevestigen;

    @FXML
    TextField saldoText;

    @FXML
    Label confirm;

    @FXML
    Label desiredAmount;

    @FXML
    Label backspace;

    @FXML
    Label menu;

    @FXML
    public void removeCharacter() throws IOException {
        String text = saldoText.getText();
        text = text.substring(0, text.length() - 1);
        saldoText.setText(text);
    }

    double balance = Double.parseDouble(SessionManager.getSession().getBalance());

    public void commitTransActions() throws IOException {
        int amount = Integer.parseInt(saldoText.getText());
        int[][] bankNoteOptions = getBanknoteOptions(amount);
        BanknoteSelection.banknoteArray = bankNoteOptions;

        if (amount > balance || amount < 10) {
            App.setRoot("saldoLaag");
        } else {
            App.setRoot("banknoteSelection");
        }
    }

    // Handles the keypress event.
    public void KeyPressEventHandler(char key) {
        try {

            if (key == '#') {
                commitTransActions();
            }

            if (key == 'B') {
                removeCharacter();
            }

            if (key == '*') {
                switchToMainMenu();
            }


            if ((key >= '0' && key <= '9')) {
                if (saldoText.getLength() != 3) {
                    saldoText.appendText(String.valueOf(key));
                } else {
                    System.out.println("te lang");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}