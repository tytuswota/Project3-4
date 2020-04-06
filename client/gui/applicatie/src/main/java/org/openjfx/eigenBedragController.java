package org.openjfx;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;

public class EigenBedragController {

    SerialReader reader;

    public EigenBedragController() {
        reader = SerialReader.GetReader();
        reader.addKeyPadListener((x) -> {
            KeyPressEventHandler(x);
        });
    }

    @FXML
    Button eigenBedragToMenu;

    @FXML

    TextField saldoText;
    @FXML
    public void switchToMainMenu() throws IOException {
        App.setRoot("mainMenu");
    }

    public void KeyPressEventHandler(String key){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                saldoText.appendText(key);
            }
        });
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

