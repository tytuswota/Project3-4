package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.ConnectionManager;

import java.io.IOException;

public class SaldoController extends BaseController {

    @FXML
    Button saldoToMenu;
    @FXML
    Label saldoField;
    @FXML
    Button afbreken;

    @FXML
    public void initialize() {
        try {
            String balance = ConnectionManager.getSession().getBalance();
            saldoField.setText("€ " + balance);
        }catch (Exception e){
            saldoField.setText("Sorrie, geen verbinding");
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
