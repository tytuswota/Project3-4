package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class eigenBedragController {

    @FXML
    Button eigenBedragToMenu;

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }


}
