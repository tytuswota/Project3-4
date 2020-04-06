package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class pasInController {


    SerialReader reader;

    public pasInController() {
        reader = SerialReader.GetReader();
        reader.addRFIDListener((x) -> {
            RFIDEventHandler(x);
        });
    }

    @FXML
    AnchorPane toLogin;

//    @FXML
//    TextField test;

    @FXML
    public void switchToLogin() throws IOException {
        App.setRoot("login");
    }

<<<<<<< HEAD
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
=======
    private void RFIDEventHandler(String uid) {
        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                if (test.getLength() != 16) {
                    test.appendText(uid);
                } else {
                    try {
                        switchToLogin();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
//werkt nog niet
                /*
                 * iets voor uid check om naar volgende scherm te gaan.
                 *
                 * */

            }

        });
    }
>>>>>>> c1f776b2206a8584f0326e0d3422acda037d3fd6
}
