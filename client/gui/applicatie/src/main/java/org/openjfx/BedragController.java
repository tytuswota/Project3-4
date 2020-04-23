package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import java.io.IOException;

public class BedragController {

    @FXML
    Button bedragToMenu;

    @FXML
    Button bedragToEigenBedrag;

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    @FXML
    public void switchToEigenBedrag() throws IOException {
        App.setRoot("eigenBedrag");
    }
}
