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


    public void commitTransActions() throws IOException {
        SessionManager session = SessionManager.getSession();
        if(session != null) {
            double balance = Double.parseDouble(session.getBalance());

            int amount = Integer.parseInt(saldoText.getText());
            int[][] bankNoteOptions = getBanknoteOptions(amount);
            BanknoteSelection.banknoteArray = bankNoteOptions;

            if (amount > balance || amount < 10) {
                App.showErrorScreen("saldoLaag");// .setRoot("saldoLaag");
            } else {
                App.setRoot("banknoteSelection");
            }
        }
        App.showErrorScreen("Error in initialising connection");
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