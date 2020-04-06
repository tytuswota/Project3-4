package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class eigenBedragController {

    @FXML
    Button eigenBedragToMenu;

    @FXML
    TextField eigenBedrag;

    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }


//    SerialReader serialReader = new SerialReader() {
//        @Override
//        protected void qwerty(String key) {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//
////                    if (key == "#") {
////                    }
//
//                    char car = key.charAt(0);
//
//                    if ((car >= '0' && car <= '9')) {
//
//                        eigenBedrag.appendText(key);
//                        System.out.println(key);
//                    }
//                }
//            });
//        }
//    };


//    SerialReader serialReader = new SerialReader() {
//        @Override
//        protected void KeyPressEvent(String key) {
//            Platform.runLater(new Runnable() {
//                @Override
//                public void run() {
//
//                        eigenBedrag.appendText(key);
//                }
//            });
//        }
//    };
}

