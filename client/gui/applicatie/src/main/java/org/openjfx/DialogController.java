package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.LanguageSystem;

public class DialogController {
    @FXML
    public void initialize() {
        go_back.setText(LanguageSystem.getString("go_back"));
    }

    @FXML
    Label message;

    @FXML
    Label go_back;
}
