package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;

import java.io.IOException;

public class pasInController {
    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("login");
    }

}
