package org.openjfx;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.SetOfBanknotes;
import model.Withdrawer;

public class MainController extends BaseController {

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
    private void PinZeventig() throws IOException {
        Withdrawer withdrawer = new Withdrawer();
        SetOfBanknotes banknotes = new SetOfBanknotes(0, 2, 1);
        boolean balanceEnough = withdrawer.isBalanceEnough(banknotes.getTotalAmount());
        boolean banknotesAvailable = withdrawer.withdraw(banknotes);
        if(balanceEnough && banknotesAvailable) {
            if(withdrawer.withdraw(banknotes)) {
                App.setRoot("pasUit");
            }else {
                System.out.println("withdrawing failed");
            }
        }
    }

    public void KeyPressEventHandler(char key) {
        try {
            if (key == 'A') {
                switchToSaldo();
            }
            else if (key == 'B') {
                switchToPasUit();
            }
            else if (key == 'C') {
                switchToBedrag();
            }
            else if (key == 'D') {
                PinZeventig();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}