package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.BankNoteCombo;
import model.LanguageSystem;
import model.SetOfBanknotes;

import java.io.IOException;

public class EigenBedragController extends BaseController {

    public void initialize() {
        confirm.setText(LanguageSystem.getString("confirm"));
        desiredAmount.setText(LanguageSystem.getString("desiredAmount"));
        backspace.setText(LanguageSystem.getString("backspace"));
    }

    @FXML
    Button eigenBedragToMenu;

    @FXML
    TextField saldoText;

    @FXML
    Label confirm;

    @FXML
    Label desiredAmount;

    @FXML
    Label backspace;

    @FXML
    public void removeCharacter() throws  IOException{
        String text = saldoText.getText();
        text = text.substring(0,text.length()-1);
        saldoText.setText(text);
    }

    public void commitTransActions() throws IOException{

        System.out.println("in the commit transaction methode");

        int amount = Integer.parseInt(saldoText.getText());

        int[][] bankNoteOptions = getBanknoteOptions(amount);

        //x = 0 = 10
        //x = 1 = 20
        //x = 2 = 50

        BanknoteSelection.banknoteArray = bankNoteOptions;

        App.setRoot("banknoteSelection");

        //show banknotes

        //give bankNoteOptions

    }

    public int[][] getBanknoteOptions(int amount) throws IOException{
        BankNoteCombo bankNoteCombo = new BankNoteCombo();

        int b[] = bankNoteCombo.calBankNoteCombo(amount);

        //index's 0 = 10 1 = 20 2 = 50
        int bankNotesOption[] = new int[3];

        int options[][] = new int[3][20];

        for(int i = 0; i < b.length; i++){
            if(b[i] != 0) {
                int option = amount / b[i];
                bankNotesOption[i] = option;
            }
        }

        //post calculation
        int rowOfOptions = 0;
        for(int i = 0; i < bankNotesOption.length; i++){
            if(bankNotesOption[i] != 0){
                int bil = 0;
                if(i == 0){
                    bil = 10;
                }
                if(i == 1){
                    bil = 20;
                }
                if(i == 2){
                    bil = 50;
                }
                int num = bankNotesOption[i] * bil;
                int subtraction = amount - num;

                if(subtraction != 0){
                    if(subtraction >= 10){
                        //return multi d options
                        //the rowOfOptions variable is the same when there are more options
                        int otherArray[][] = getBanknoteOptions(subtraction);
                        for(int x = 0; x < 3; x++){
                            options[x][rowOfOptions] = otherArray[x][0];
                        }
                        options[i][rowOfOptions] = num / bil;

                    }else{
                        bankNotesOption[0]++;
                    }
                }else{
                    options[i][rowOfOptions] = bankNotesOption[i];
                }
            }
            rowOfOptions++;
        }

        return options;
    }

    public void KeyPressEventHandler(char key) {
        try {

            if (key == '#') {
                // Do transaction.
                switchToMainMenu();
            }

            if (key == '*') {
                removeCharacter();
            }

            if ((key >= '0' && key <= '9')) {
                if (saldoText.getLength() != 3) {
                    saldoText.appendText(String.valueOf(key));
                } else {
                    System.out.println("te lang");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}