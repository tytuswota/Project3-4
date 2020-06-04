package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.LanguageSystem;
import model.SessionManager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PasUitController extends BaseController {

    @FXML
    void initialize() {
        cardOut.setText(LanguageSystem.getString("cardOut"));
        SessionManager.deleteSession();
        switchMethode();
    }

    @FXML
    Label cardOut;

    @FXML
    public void switchToPassIn() throws IOException {

        App.setRoot("pasIn");
    }


    @FXML
    public void switchMethode() {
        Thread switchMethode = new Thread() {


            @Override
            public void run() {

                boolean change = false;
                while (!change) {
                    try {
                        Thread.sleep(2000);
                        switchToPassIn();
                        change = true;

                    } catch (InterruptedException | IOException | NullPointerException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        switchMethode.start();
    }
}