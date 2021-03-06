package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.LanguageSystem;
import model.SessionManager;

/**
 *
 * SaldoController
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class SaldoController extends BaseController {

    @FXML
    Button saldoToMenu;
    @FXML
    Label saldoField;
    @FXML
    Button afbreken;

    @FXML
    Label abort;
    @FXML
    Label mainMenu;
    @FXML
    Label yourBalance;

    @FXML
    public void initialize() {
        abort.setText(LanguageSystem.getString("abort"));
        mainMenu.setText(LanguageSystem.getString("menu"));
        yourBalance.setText(LanguageSystem.getString("yourBalance"));
        try {
            String balance = SessionManager.getSession().getBalance();
            saldoField.setText(" ₽ " + balance);
        }catch (Exception e){
            System.out.println(e.toString());
            saldoField.setText(LanguageSystem.getString("noConnection"));
        }
    }

    // Handles the keypress event.
    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                switchToPasUit();
            }else if(key == '*'){
                switchToMainMenu();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
