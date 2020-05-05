package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.LanguageSystem;
import model.SessionManager;

public class SaldoController extends BaseController {

    @FXML
    Button saldoToMenu;
    @FXML
    Label saldoField;
    @FXML
    Button afbreken;

    @FXML
    Label quit;
    @FXML
    Label mainMenu;
    @FXML
    Label yourBalance;

    @FXML
    public void initialize() {
        quit.setText(LanguageSystem.getString("quit"));
        mainMenu.setText(LanguageSystem.getString("mainMenu"));
        yourBalance.setText(LanguageSystem.getString("yourBalance"));
        try {
            String balance = SessionManager.getSession().getBalance();
            saldoField.setText("€ " + balance);
        }catch (Exception e){
            saldoField.setText(LanguageSystem.getString("noConnection"));
        }
    }

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
