package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.SetOfBanknotes;

import java.io.IOException;

public class BanknoteSelection extends BaseController {
    public static int[][] banknoteArray = null;


    @FXML
    Button optie_1;

    @FXML
    Button optie_2;

    @FXML
    Button optie_3;


    @FXML
    public void initialize() {
        System.out.println("==================================================");

        for (int combo = 0; combo < 20; combo++) {
            for (int bill = 0; bill < 3; bill++) {
                if (banknoteArray[bill][combo] != 0) {
                    System.out.println("bill:" + bill);
                    System.out.println("combo: " + combo);
                    System.out.println("==the value");
                    System.out.println(banknoteArray[bill][combo]);
                    System.out.println("==the value");
                }
            }
        }
        System.out.println("=================================================");
    }

    // biljet opties die je krijgt als je gaat pinnen.
    @FXML
    private void optie1() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][0], banknoteArray[1][0], banknoteArray[2][0]);
        withdraw(banknotes);
    }


    @FXML
    public void optie2() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][1], banknoteArray[1][1], banknoteArray[2][1]);
        withdraw(banknotes);
    }


    @FXML
    public void optie3() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][1], banknoteArray[1][1], banknoteArray[2][1]);
        withdraw(banknotes);
    }


}