package org.openjfx;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.LanguageSystem;
import java.io.IOException;

/**
 *
 * PasInController
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class PasInController extends BaseController {

    @FXML
    public void initialize() {
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

    // Handles the keypress events
    public void KeyPressEventHandler(char key) {
        try {
            if (key == 'A') {

                Task<Void> longRunningTask = new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        LanguageSystem.setLanguage(LanguageSystem.Language.NEDERLANDS);
                        Platform.runLater(() -> cardIn.setText(LanguageSystem.getString("cardIn")));
                        return null;
                    }
                };
                new Thread(longRunningTask).start();

            }
            if (key == 'B') {
                Task<Void> longRunningTask = new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        LanguageSystem.setLanguage(LanguageSystem.Language.ENGLISH);
                        Platform.runLater(() -> cardIn.setText(LanguageSystem.getString("cardIn")));
                        return null;
                    }
                };
                new Thread(longRunningTask).start();

            }
            if (key == 'C') {
                Task<Void> longRunningTask = new Task<Void>() {

                    @Override
                    protected Void call() throws Exception {
                        LanguageSystem.setLanguage(LanguageSystem.Language.RUSSIAN);
                        Platform.runLater(() -> cardIn.setText(LanguageSystem.getString("cardIn")));
                        return null;
                    }
                };
                new Thread(longRunningTask).start();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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