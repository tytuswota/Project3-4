package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.LanguageSystem;

public class DialogController {
    @FXML
    public void initialize() {
        goBack.setText(LanguageSystem.getString("goBack"));
    }

    @FXML
    Label message;

    @FXML
    Label goBack;
}
