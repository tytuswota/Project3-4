package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class BedragController extends BaseController{

    @FXML
    Button bedragToMenu;

    @FXML
    Button bedragToEigenBedrag;

    @FXML
    public void switchToEigenBedrag() throws IOException {
        App.setRoot("eigenBedrag");
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
                // withdraw 10
            } else if (key == 'B') {
                // withdraw 50
            } else if (key == 'C') {
                // withdraw 20
            } else if (key == 'D') {
                // withdraw 100
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
