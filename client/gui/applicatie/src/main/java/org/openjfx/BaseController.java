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
//Georgian clock incase there are issues with simpledataformat.
//    @FXML
//    public void clock() {
//        Thread clock = new Thread() {
//
//
//            @Override
//            public void run() {
//
//
//                while (true) {
//
//                    Platform.runLater(new Runnable() {
//                        @Override
//                        public void run() {
//                            Calendar cal = new GregorianCalendar();
//                            int day = cal.get(Calendar.DAY_OF_MONTH);
//                            int month = cal.get(Calendar.MONTH);
//                            int year = cal.get(Calendar.YEAR);
//
//                            int second = cal.get(Calendar.SECOND);
//                            int minute = cal.get(Calendar.MINUTE);
//                            int hour = cal.get(Calendar.HOUR);
//
//                            clockLabel.setText(year + "/" + month + "/" + day + "\n" + hour + ":" + minute + ":" + second);
//                        }
//
//                    });
//
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        };
//        clock.start();
//    }  //

    @FXML
    public void clock() {
        Thread clock = new Thread() {


            @Override
            public void run() {


                while (true) {

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            String clock = "dd/MM/yyyy\nHH:mm:ss";
                            SimpleDateFormat date = new SimpleDateFormat(clock);
                            String datestring = date.format(new Date());


                            clockLabel.setText(datestring);

                        }

                    });

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        clock.start();
    }
}
