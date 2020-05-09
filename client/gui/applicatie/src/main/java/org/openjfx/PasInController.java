package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.LanguageSystem;

import java.io.IOException;

public class PasInController extends BaseController{

    @FXML
    void initialize(){
        cardIn.setText(LanguageSystem.getString("cardIn"));
    }

    @FXML
    AnchorPane toLogin;

    @FXML
    Label cardIn;

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("login");
    }

    protected void RFIDEventHandler(String uid) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                System.out.println(uid);
                try {
                    App.setRoot("login");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        });
    }
}
