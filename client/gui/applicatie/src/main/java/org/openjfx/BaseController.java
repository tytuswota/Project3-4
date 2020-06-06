package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.BankNoteCombo;
import model.SerialReader;
import model.SetOfBanknotes;
import model.Withdrawer;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/*
 * BaseController
 *
 * Contains the shared features of the controllers.
 * produced by Tymek, Shabir, Robin and Jaco.
 */

public class BaseController extends Thread {

    protected static SerialReader reader = SerialReader.GetReader();
    private static int totBedrag;
    private static String TBedrag;
    private static String dateString;

    public static String getTBedrag() {
        TBedrag= Integer.toString(totBedrag);
        return TBedrag;
    }
    public static String getDateString(){
        return dateString;
    }


    // Constructor
    public BaseController() {
        reader.addKeyPadListener((x) -> {
            baseKeyPressEventHandler(x);
        });
        reader.addRFIDListener((x) -> {
            RFIDEventHandler(x);
        });
        clock();
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

    @FXML
    public void switchToSimulatie() throws IOException {
        App.setRoot("simulatie");
    }

    @FXML
    Label clockLabel;

    public void withdraw(SetOfBanknotes banknotes) throws IOException {
        Withdrawer withdrawer = new Withdrawer();

        boolean balanceEnough = withdrawer.isBalanceEnough(banknotes.getTotalAmount());
        boolean banknotesAvailable = withdrawer.banknotesAvailable(banknotes);

        if (!balanceEnough) {
            App.showErrorScreen("balanceLow");
            System.out.println("Balance not high enough");
        } else if (!banknotesAvailable) {
            App.showErrorScreen("Biljetten niet aanwezig.");
            System.out.println("BanknotesAvailable.");
        } else {
            if (withdrawer.withdraw(banknotes)) {
                totBedrag= banknotes.getTotalAmount();
                System.out.println(totBedrag);
                App.setRoot("bon");
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
            App.showErrorScreen("balanceLow");
            System.out.println("Balance not high enough");
        } else if (!banknotesAvailable) {
            App.showErrorScreen("Biljetten niet aanwezig.");
            System.out.println("BanknotesAvailable.");
        } else {
            if (withdrawer.withdraw(banknotes)) {
                totBedrag= banknotes.getTotalAmount();
                App.setRoot("pasUit");
            } else {
                App.showErrorScreen("Opnemen mislukt.");
                System.out.println("Withdrawing failed.");
            }
        }
    }

    public int[][] getBanknoteOptions(int amount) throws IOException {
        BankNoteCombo bankNoteCombo = new BankNoteCombo();

        int b[] = bankNoteCombo.calBankNoteCombo(amount);

        //index's 0 = 10 1 = 20 2 = 50
        int bankNotesOption[] = new int[3];

        int options[][] = new int[3][20];

        for (int i = 0; i < b.length; i++) {
            if (b[i] != 0) {
                int option = amount / b[i];
                bankNotesOption[i] = option;
            }
        }

        //post calculation
        int rowOfOptions = 0;
        for (int i = 0; i < bankNotesOption.length; i++) {
            if (bankNotesOption[i] != 0) {
                int bil = 0;
                if (i == 0) {
                    bil = 10;
                }
                if (i == 1) {
                    bil = 20;
                }
                if (i == 2) {
                    bil = 50;
                }
                int num = bankNotesOption[i] * bil;
                int subtraction = amount - num;

                if (subtraction != 0) {
                    if (subtraction >= 10) {
                        //return multi d options
                        //the rowOfOptions variable is the same when there are more options
                        int otherArray[][] = getBanknoteOptions(subtraction);
                        for (int x = 0; x < 3; x++) {
                            options[x][rowOfOptions] = otherArray[x][0];
                        }
                        options[i][rowOfOptions] = num / bil;

                    } else {
                        bankNotesOption[0]++;
                    }
                } else {
                    options[i][rowOfOptions] = bankNotesOption[i];
                }
            }
            rowOfOptions++;
        }

        return options;
    }

    public void clock() {
        Thread clock = new Thread() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                String clock = "dd/MM/yyyy\nHH:mm";
                                SimpleDateFormat date = new SimpleDateFormat(clock);
                                dateString = date.format(new Date());

                            clockLabel.setText(dateString);
                            }
                        });
                    } catch (InterruptedException | NullPointerException  e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        clock.start();
    }
}
