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
    public static int[][] banknoteArray = null;
    static boolean option_1 = false;
    static boolean option_2 = false;
    static boolean option_3 = false;



    public static int getBanknote1(){
        return banknote1;
    }
    public static int getBanknote2(){
        return banknote2;
    }
    public static int getBanknote3(){
        return banknote3;
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
    Label lbl_optie_1;

    @FXML
    Label lbl_optie_2;

    @FXML
    Label lbl_optie_3;

    @FXML
    Label lbl_menu;

    int biljet = 0;

    @FXML
    public void initialize() {
        for (int combo = 0; combo < 20; combo++) {
            for (int bill = 0; bill < 3; bill++) {
                if (banknoteArray[bill][combo] != 0) {

                    if (combo == 0) {
                        lbl_optie_1.setText("₽10 * " + (banknoteArray[0][combo]) + " + ₽20 * " + (banknoteArray[1][combo]) + "+ ₽50 * " + (banknoteArray[2][combo]));
                        lbl_optie_1.setTextFill(Color.GREEN);
                    }
                    if (combo == 1) {
                        lbl_optie_2.setText("₽10 * " + (banknoteArray[0][combo]) + " + ₽20 * " + (banknoteArray[1][combo]) + " + ₽50 * " + (banknoteArray[2][combo]));
                    }
                    if (combo == 2) {
                        lbl_optie_3.setText("₽10 * " + (banknoteArray[0][combo]) + " + ₽20 * " + (banknoteArray[1][combo]) + " + ₽50 * " + (banknoteArray[2][combo]));
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
        option_1= true;
        withdraw(banknotes);
    }


    @FXML
    public void optie2() throws IOException {
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][1], banknoteArray[1][1], banknoteArray[2][1]);
        option_2= true;
        withdraw(banknotes);

    }


    @FXML
    public void optie3() throws IOException {
        option_3 = true;
        SetOfBanknotes banknotes = new SetOfBanknotes(banknoteArray[0][2], banknoteArray[1][2], banknoteArray[2][2]);
        withdraw(banknotes);
    }

    public void KeyPressEventHandler(char key) {
        try {

            if (key == 'A') {

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
    public static void print(){
        if ( option_1 == true){
            System.out.println("₽10 is " + (banknoteArray[0][0]+" keer uitgeworpen."));
            banknote1 = banknoteArray[0][0];
            System.out.println("₽20 is " + (banknoteArray[1][0]+" keer uitgeworpen."));
            banknote2 = banknoteArray[1][0];
            System.out.println("₽50 is " + (banknoteArray[2][0]+" keer uitgeworpen."));
            banknote3 = banknoteArray[2][0];
            option_1 = false;
        }
        else if(option_2 == true)
        {
            System.out.println("₽10 is " + (banknoteArray[0][1]+" keer uitgeworpen."));
            banknote1 = banknoteArray[0][1];
            System.out.println("₽20 is " + (banknoteArray[1][1]+" keer uitgeworpen."));
            banknote2 = banknoteArray[1][1];
            System.out.println("₽50 is " + (banknoteArray[2][1]+" keer uitgeworpen."));
            banknote3 = banknoteArray[2][1];
            option_2 = false;
        }
        else if(option_3 == true){
            System.out.println("₽10 is " + (banknoteArray[0][2]+" keer uitgeworpen."));
            banknote1 = banknoteArray[0][2];
            System.out.println("₽20 is " + (banknoteArray[1][2]+" keer uitgeworpen."));
            banknote2 = banknoteArray[1][2];
            System.out.println("₽50 is " + (banknoteArray[2][2]+" keer uitgeworpen."));
            banknote3 = banknoteArray[2][2];
            option_3 = false;
        }
    }
}