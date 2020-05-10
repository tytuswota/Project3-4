package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.LanguageSystem;
import model.SetOfBanknotes;

import java.io.IOException;

public class BedragController extends BaseController{

    @FXML
    public void initialize() {
        other.setText(LanguageSystem.getString("other"));
        menu.setText(LanguageSystem.getString("menu"));
    }

    @FXML
    Label other;

    @FXML
    Label menu;

    @FXML
    Button bedragToMenu;

    @FXML
    Button bedragToEigenBedrag;

    @FXML
    public void switchToEigenBedrag() throws IOException {
        App.setRoot("eigenBedrag");
    }

    @FXML
    public void withdrawTen() throws IOException{
        SetOfBanknotes banknotes = new SetOfBanknotes(1, 0, 0);
        withdraw(banknotes);
    }

    @FXML
    public void withdrawTwenty()throws IOException{
        SetOfBanknotes banknotes = new SetOfBanknotes(0, 1, 0);
        withdraw(banknotes);
    }

    @FXML
    public void withdrawFifty() throws IOException{
        SetOfBanknotes banknotes = new SetOfBanknotes(0, 0, 1);
        withdraw(banknotes);
    }

    @FXML
    public void withdrawHundred() throws IOException{
        SetOfBanknotes banknotes = new SetOfBanknotes(0, 0, 2);
        withdraw(banknotes);
    }

    public void calculateNotes() {

    }

    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                // "anders"
                switchToEigenBedrag();
            } else if (key == '*') {
                // "menu"
                switchToMainMenu();
            } else if (key == 'A') {
                SetOfBanknotes banknotes = new SetOfBanknotes(1, 0, 0);
                withdraw(banknotes);
                // withdraw 10
            } else if (key == 'B') {
                SetOfBanknotes banknotes = new SetOfBanknotes(0, 0, 1);
                withdraw(banknotes);
                // withdraw 50
            } else if (key == 'C') {
                SetOfBanknotes banknotes = new SetOfBanknotes(0, 1, 0);
                withdraw(banknotes);
                // withdraw 20
            } else if (key == 'D') {
                SetOfBanknotes banknotes = new SetOfBanknotes(0, 0, 2);
                withdraw(banknotes);
                // withdraw 100
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
