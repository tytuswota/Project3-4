package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import model.ConnectionManager;

public class LoginController extends BaseController {

    @FXML
    PasswordField pin;
    @FXML
    Button bevestigen;
    @FXML
    Button annuleren;

    Dialog dialog;

    @FXML
    public void switchToMainMenu() throws IOException {
        if (false && login()) {
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
        ConnectionManager connectionManager = ConnectionManager.tryLogin(cardId, pin);

        if (connectionManager != null) {
            App.accountId = connectionManager.getAccountname();
            return true;
        }
        return true;//return false; // cheat a bit be because database is empty now.
    }

    // Handles the keypress events
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                if(dialog != null && dialog.isShowing()){
                    dialog.close();
                }else{
                    switchToMainMenu();
                }
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
