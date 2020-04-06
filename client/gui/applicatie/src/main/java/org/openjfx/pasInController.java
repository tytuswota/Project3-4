package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class pasInController {

    @FXML
    AnchorPane toLogin;

//    @FXML
//    TextField test;

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("login");
    }

//    SerialReader serialReader = new SerialReader() {
//        @Override
//        protected void rfidEvent(String uid) {
//            Platform.runLater(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    if (test.getLength() != 16) {
//                        test.appendText(uid);
//                    } else {
//                        try {
//                            switchToLogin();
//                        } catch (Exception e) {
//                            System.out.println(e);
//                        }
//                    }
////werkt nog niet
//                    /*
//                     * iets voor uid check om naar volgende scherm te gaan.
//                     *
//                     * */
//
//                }
//
//            });
//        }
//    };
}
