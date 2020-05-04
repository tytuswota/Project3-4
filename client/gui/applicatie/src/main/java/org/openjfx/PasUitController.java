package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.LanguageSystem;

import java.io.IOException;

public class PasUitController {

    @FXML
    void initialize(){
        cardOut.setText(LanguageSystem.getString("cardOut"));
    }

    @FXML
    Label cardOut;

    @FXML
    public void switchToPassIn() throws IOException {
        App.setRoot("pasIn");
    }
}