package org.openjfx;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

public class SimulatieController extends BaseController{

    @FXML
    public void initialize(){
        simulatie.setText("er is ₽"+iets+" gepint.");
        simulatie1.setText("₽10 is: "+brief1+"x uitgeworpen");
        simulatie2.setText("₽20 is: "+brief2+"x uitgeworpen");
        simulatie3.setText("₽50 is: "+brief3+"x uitgeworpen");
    }

    @FXML
    Label simulatie;

    @FXML
    Label simulatie1;

    @FXML
    Label simulatie2;

    @FXML
    Label simulatie3;

    @FXML
    Button afbreken;

    String iets = Integer.toString(BaseController.getTotBedrag());
    String brief1 = Integer.toString(BanknoteSelection.getBanknote1());
    String brief2 = Integer.toString(BanknoteSelection.getBanknote2());
    String brief3 = Integer.toString(BanknoteSelection.getBanknote3());



    @FXML
    public void switchToPasUit() throws IOException {
        App.setRoot("pasUit");
    }


    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                switchToPasUit();


            }else if(key == '*'){
                switchToPasUit();

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}


