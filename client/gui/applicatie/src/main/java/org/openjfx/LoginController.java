package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import model.LanguageSystem;
import model.SessionManager;
import org.json.JSONObject;

/**
 *
 * LoginController
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class LoginController extends BaseController {

   // private String cardId = reader.getLastCardNumber();

    private String cardId = "SO-DASB-00000002";
    public void initialize() {
        enterPin.setText(LanguageSystem.getString("enterPin"));
        abort.setText(LanguageSystem.getString("abort"));
        confirm.setText(LanguageSystem.getString("confirm"));
        backspace.setText(LanguageSystem.getString("backspace"));
    }

    @FXML
    PasswordField pin;

    @FXML
    Button bevestigen;

    @FXML
    Button annuleren;

    @FXML
    Button btBackspace;

    @FXML
    Label backspace;

    @FXML
    Label enterPin;

    @FXML
    Label confirm;

    @FXML
    public void removeCharacter() throws IOException {
        String text = pin.getText();
        text = text.substring(0, text.length() - 1);
        pin.setText(text);
    }

    @FXML
    Label abort;
    private int efforts = 0;

    @FXML
    public void switchToMainMenu() throws IOException {

        int status = login(cardId);

        JSONObject json = SessionManager.getCard(cardId);
        System.out.println("=================");
        System.out.println(json);
        this.efforts = Integer.parseInt(json.getString("attempts"));

        if (status != 403) {
            if (this.efforts != 3) {
                if (status == 200) {
                    SessionManager.sendFailedAttempt(1, cardId);
                    App.setRoot("mainMenu");
                } else {
                    pin.setText("");
                    SessionManager.sendFailedAttempt(0, cardId);
                    App.showErrorScreenPin("pinWrong");
                    //
                    //
                }
            } else {
                SessionManager.blockCard(cardId);

                App.showErrorScreenPin("cardBlocked");
            }
        } else {
            App.showErrorScreenPin("cardBlocked");
        }

    }

    // Try to log in using the card id and the pin.
    private int login(String cardId) {
        //id for testing

        String pin = this.pin.getText(); // "1234"
        System.out.println("dit is de pin " + pin);
        SessionManager sessionManager = SessionManager.tryLogin(cardId, pin);
        if (sessionManager != null) {
            App.pin = pin;
            App.accountId = sessionManager.getAccountname();
        }
        return sessionManager.getStatus();
    }

    // Handles the keypress events
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#' ) {
                switchToMainMenu();
            }
            if (key == '*') {
                switchToPasUit();
            }
            if (key == 'B') {
                removeCharacter();
            }

            if ((key >= '0' && key <= '9')) {
                if (pin.getLength() != 4) {
                    pin.appendText(String.valueOf(key));
                } else {
                    System.out.println("te lang");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
