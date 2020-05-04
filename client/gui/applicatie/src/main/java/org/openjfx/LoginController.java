package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import model.LanguageSystem;
import model.SessionManager;

public class LoginController extends BaseController {

    public void initialize() {
        enter_pin.setText(LanguageSystem.getString("enter_pin"));
        confirm.setText(LanguageSystem.getString("confirm"));
        confirm.setText(LanguageSystem.getString("cancel"));
    }

    @FXML
    PasswordField pin;

    @FXML
    Button bevestigen;

    @FXML
    Button annuleren;

    @FXML
    Label enter_pin;

    @FXML
    Label confirm;

    @FXML
    Label cancel;

    @FXML
    public void switchToMainMenu() throws IOException {
        if (true || login()) {// bypass to test
            App.setRoot("mainMenu");
        } else {
            dialog = new Dialog("pincode verkeert");
        }
    }

    // Try toe log in using the card id and the pin.
    private boolean login() {
        //id for testing
        String cardId = reader.getLastCardNumber(); //"SU-DASB-00000002";

        String pin = this.pin.getText(); // "1234"
        SessionManager sessionManager = SessionManager.tryLogin(cardId, pin);

        if (sessionManager != null) {
            App.accountId = sessionManager.getAccountname();
            return true;
        }
        return true;//return false; // cheat a bit be because database is empty now.
    }

    // Handles the keypress events
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                 switchToMainMenu();
            }
            if (key == '*') {
                switchToPasUit();
            }

            if ((key >= '0' && key <= '9')) {
                if (pin.getLength() != 4) {
                    pin.appendText(String.valueOf(key));
                } else {
                    System.out.println("te lang");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
