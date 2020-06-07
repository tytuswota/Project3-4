package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

/**
 * SimulatieController
 * <p>
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class SimulatieController extends BaseController {
    @FXML
    public void initialize() {
        simulatie.setText("er is ₽" + iets + " gepint.");
        if (brief1.equals("0") && brief2.equals("0") && brief3.equals("0") ) {
            simulatie1.setText("₽10 is: " + MainController.getBanknote1() + "x uitgeworpen");
            simulatie2.setText("₽20 is: " + MainController.getBanknote2() + "x uitgeworpen");
            simulatie3.setText("₽50 is: " + MainController.getBanknote3() + "x uitgeworpen");
            System.out.println("ik zit goed gast");
        } else {
            System.out.println("ik zit verkeerd g");
            simulatie1.setText("₽10 is: " + brief1 + "x uitgeworpen");
            simulatie2.setText("₽20 is: " + brief2 + "x uitgeworpen");
            simulatie3.setText("₽50 is: " + brief3 + "x uitgeworpen");
        }
        switchMethode();
    }

    @FXML
    Label simulatie;

    @FXML
    Label simulatie1;

    @FXML
    Label simulatie2;

    @FXML
    Label simulatie3;


    String iets = (BaseController.getTBedrag());
    String brief1 = (BanknoteSelection.getBankN1());
    String brief2 = (BanknoteSelection.getBankN2());
    String brief3 = (BanknoteSelection.getBankN3());


    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    // Go to pass uit after a given time.
    @FXML
    public void switchMethode() {
        Thread switchMethode = new Thread() {


            @Override
            public void run() {

                boolean change = false;
                while (!change) {
                    try {
                        Thread.sleep(5000);
                        switchToPasUit();
                        change = true;

                    } catch (InterruptedException | IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        switchMethode.start();
    }


}


