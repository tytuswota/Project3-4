package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import model.LanguageSystem;
import model.SetOfBanknotes;

import java.io.IOException;

public class BanknoteSelection extends BaseController {
    public static int BNote;
    public static int banknote1;
    public static int banknote2;
    public static int banknote3;
    public static String bankN1;
    public static String bankN2;
    public static String bankN3;
    public static int[][] banknoteArray = null;



    public static String getBankN1(){
        bankN1 = Integer.toString(banknote1);
        return bankN1;
    }
    public static String getBankN2(){
        bankN2 = Integer.toString(banknote2);
        return bankN2;
    }
    public static String getBankN3(){
        bankN3 = Integer.toString(banknote3);
        return bankN3;
    }
    @FXML
    Button optie_1;

    @FXML
    Button optie_2;

    @FXML
    Button optie_3;

    @FXML
    Button menu;

    @FXML
    Label lbl_optie_1_10;

    @FXML
    Label lbl_optie_2_10;

    @FXML
    Label lbl_optie_2_20;

    @FXML
    Label lbl_optie_2_50;

    @FXML
    Label lbl_optie_3_10;

    @FXML
    Label lbl_optie_3_20;

    @FXML
    Label lbl_optie_3_50;

    @FXML
    Label lbl_menu;


    int biljet = 0;

    @FXML
    public void initialize() {
        for (int combo = 0; combo < 20; combo++) {
            for (int bill = 0; bill < 3; bill++) {
                if (banknoteArray[bill][combo] != 0) {

                    if (combo == 0) {
                        if (banknoteArray[0][combo] != 0) {
                            lbl_optie_1_10.setText("₽10 X " + (banknoteArray[0][combo]));
                        }
                    }
                    if (combo == 1) {
                        if (banknoteArray[0][combo] != 0) {
                            lbl_optie_2_10.setText("₽10 X " + (banknoteArray[0][combo]));
                            lbl_optie_2_20.setText("₽20 X " + (banknoteArray[1][combo]));
                            lbl_optie_2_50.setText("₽50 X " + (banknoteArray[2][combo]));
                        }
                    }
                    if (combo == 2) {
                        if (banknoteArray[0][combo] != 0) {
                            lbl_optie_3_10.setText("₽10 X " + (banknoteArray[0][combo]));
                            lbl_optie_3_20.setText("₽20 X " + (banknoteArray[1][combo]));
                            lbl_optie_3_50.setText("₽50 X " + (banknoteArray[2][combo]));
                        }
                    }
                    lbl_menu.setText(LanguageSystem.getString("menu"));
                }
            }
        }
    }

    // biljet opties die je krijgt als je gaat pinnen.
    @FXML
    private void optie1() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][0], banknoteArray[1][0], banknoteArray[2][0]);
        banknote1= banknoteArray[0][0];
        banknote2= banknoteArray[1][0];
        banknote3= banknoteArray[2][0];
        withdraw(banknotes);
    }


    @FXML
    public void optie2() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][1], banknoteArray[1][1], banknoteArray[2][1]);
        banknote1= banknoteArray[0][1];
        banknote2= banknoteArray[1][1];
        banknote3= banknoteArray[2][1];
        withdraw(banknotes);
    }


    @FXML
    public void optie3() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][2], banknoteArray[1][2], banknoteArray[2][2]);
        banknote1= banknoteArray[0][2];
        banknote2= banknoteArray[1][2];
        banknote3= banknoteArray[2][2];
        withdraw(banknotes);
    }

    public void KeyPressEventHandler(char key) {
        try {

            if (key == 'A' && banknoteArray[0][0] != 0) {
                optie1();
            }
            if (key == 'B') {
                optie2();
            }
            if (key == 'C') {
                optie3();
            }
            if (key == '*') {
                switchToMainMenu();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}