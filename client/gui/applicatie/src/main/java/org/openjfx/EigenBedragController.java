package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.BankNoteCombo;
import model.LanguageSystem;
import model.SessionManager;
import model.SetOfBanknotes;

import java.io.IOException;

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

        System.out.println("in the commit transaction methode");

        int amount = Integer.parseInt(saldoText.getText());

        int[][] bankNoteOptions = getBanknoteOptions(amount);


        BanknoteSelection.banknoteArray = bankNoteOptions;

        System.out.println("dit is kanker eigenbalance " + balance);
        if (amount > balance || amount < 10) {
            App.setRoot("saldoLaag");
        } else {
            App.setRoot("banknoteSelection");
        }

    }

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