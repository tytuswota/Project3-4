package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.ConnectionManager;
import model.SetOfBanknotes;

public class MainController {
    @FXML
    Button bedrag;

    @FXML
    Button saldo;

    @FXML
    Button afbreken;

    @FXML
    Button pinZeventig;

    @FXML
    private void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    @FXML
    private void switchToBedrag() throws IOException {
        App.setRoot("bedrag");
    }

    @FXML
    private void switchToSaldo() throws IOException {
        App.setRoot("saldo");
    }

    @FXML
    private  void PinZeventig()  throws IOException {
        if(ConnectionManager.getSession().withdraw(new SetOfBanknotes(0,2,1)) == true){
            App.setRoot("pasUit");
        }else{
            //TODO handle error
        }
    }
}