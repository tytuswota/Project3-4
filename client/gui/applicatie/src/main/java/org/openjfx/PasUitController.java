package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.LanguageSystem;
import model.SessionManager;

import java.io.IOException;

public class PasUitController extends BaseController {

    @FXML
    void initialize(){
        cardOut.setText(LanguageSystem.getString("cardOut"));
        SessionManager.deleteSession();
    }

    @FXML
    Label cardOut;

    @FXML
    public void switchToPassIn() throws IOException {
        App.setRoot("pasIn");
    }
}