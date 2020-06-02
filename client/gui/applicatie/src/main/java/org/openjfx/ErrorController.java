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
        if (mainMenu != null) {
            mainMenu.setText(LanguageSystem.getString("mainMenu"));
        }
    }

    @FXML
    Label quit;

    @FXML
    Label mainMenu;

    @FXML
    Label goBack;

    @FXML
    Button BTgoBack;

    @FXML
    Button BTquit;

    @FXML
    Button BTmainMenu;


    @FXML
    public void goBack() throws IOException {
        App.restoreLast();
    }

    @Override
    public void KeyPressEventHandler(char key) {
        try {
            if (key == '#') {
                goBack();
            }
            if (key == '*') {
                switchToPasUit();
            }
            if (key == 'A') {
                switchToMainMenu();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
