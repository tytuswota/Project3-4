package org.openjfx;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import org.openjfx.loginController;

public class loginController {

    @FXML
    PasswordField pin;
    @FXML
    Button bevestigen;
    @FXML
    Button annuleren;


    @FXML
    public void switchToMainMenu() throws IOException {
        if(login()){
            App.setRoot("mainMenu");
        }else{
            MsgBox.informationBox("Login","Pin fout", "Probeer opnieuw");
        }

    }

    private boolean login(){
        //id for testing
        String cardId = "SU-DASB-1";

        String pin = this.pin.getText();
        ConnectionManager connectionManager = ConnectionManager.tryLogin(cardId, pin);

        if(connectionManager!= null){
            App.accountId = connectionManager.getAccountname();
            return true;
        }

        return false;
    }

    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    SerialReader serialReader = new SerialReader() {
        @Override
        protected void KeyPressEvent(String key) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    pin.appendText(key);
                }
            });
        }
    };

}
