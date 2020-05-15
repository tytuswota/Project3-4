package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import model.SerialReader;
import model.SetOfBanknotes;
import model.Withdrawer;

import java.io.IOException;

/*
Contains the shared features of the controllers.
 */

public class BaseController {

    protected static SerialReader reader = SerialReader.GetReader();

    // Constructor
    public BaseController() {
        reader.addKeyPadListener((x) -> {
            baseKeyPressEventHandler(x);
        });
        reader.addRFIDListener((x) -> {
            RFIDEventHandler(x);
        });
    }

    private void baseKeyPressEventHandler(String key) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                char car = key.charAt(0);
                KeyPressEventHandler(car);
            }
        });
    }

    public void KeyPressEventHandler(char key) {
    }

    protected void RFIDEventHandler(String uid) {
    }

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    public void withdraw(SetOfBanknotes banknotes) throws IOException {
        Withdrawer withdrawer = new Withdrawer();

        boolean balanceEnough = withdrawer.isBalanceEnough(banknotes.getTotalAmount());
        boolean banknotesAvailable = withdrawer.banknotesAvailable(banknotes);

        if (!balanceEnough) {
            App.showErrorScreen("Saldo is niet hoog genoeg.");
            System.out.println("Balance not high enough");
        } else if (!banknotesAvailable) {
            App.showErrorScreen("Biljetten niet aanwezig.");
            System.out.println("BanknotesAvailable.");
        } else {
            if (withdrawer.withdraw(banknotes)) {
                App.setRoot("Bon");
            } else {
                App.showErrorScreen("Opnemen mislukt.");
                System.out.println("Withdrawing failed.");
            }
        }
    }

    public void withdraw(SetOfBanknotes banknotes, int A) throws IOException {
        Withdrawer withdrawer = new Withdrawer();

        boolean balanceEnough = withdrawer.isBalanceEnough(banknotes.getTotalAmount());
        boolean banknotesAvailable = withdrawer.banknotesAvailable(banknotes);

        if (!balanceEnough) {
            App.showErrorScreen("Saldo is niet hoog genoeg.");
            System.out.println("Balance not high enough");
        } else if (!banknotesAvailable) {
            App.showErrorScreen("Biljetten niet aanwezig.");
            System.out.println("BanknotesAvailable.");
        } else {
            if (withdrawer.withdraw(banknotes)) {
                App.setRoot("pasUit");
            } else {
                App.showErrorScreen("Opnemen mislukt.");
                System.out.println("Withdrawing failed.");
            }
        }
    }
}
