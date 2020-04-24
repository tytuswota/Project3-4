package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class EigenBedragController extends BaseController {

    @FXML
    Button eigenBedragToMenu;

    @FXML
    TextField saldoText;

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    public void removeCharacter() throws  IOException{
        String text = saldoText.getText();
        text = text.substring(0,text.length()-1);
        saldoText.setText(text);
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