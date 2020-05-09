package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class BonController extends BaseController {

    @FXML
    Button noReceipt;

    @FXML
    Button getReceipt;


    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }

    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                //bon  functie moet komen
            }else if(key == '*'){
                switchToPasUit();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
