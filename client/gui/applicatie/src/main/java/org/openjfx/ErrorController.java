package org.openjfx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import model.LanguageSystem;

import java.io.IOException;

public class ErrorController extends BaseController {

    @FXML
    public void initialize() {
        goBack.setText(LanguageSystem.getString("goBack"));
        quit.setText(LanguageSystem.getString("quit"));
        mainMenu.setText(LanguageSystem.getString("mainMenu"));
    }

    @FXML
    Label message;

    @FXML
    Button BTgoBack;

    @FXML
    Label goBack;

    @FXML
    Button BTquit;

    @FXML
    Label quit;

    @FXML
    Button BTmainMenu;

    @FXML
    Label mainMenu;

    @FXML
    public void goBack() throws IOException {
        App.restoreLast();
    }

    @Override
    public void KeyPressEventHandler(char key) {

    }
}
