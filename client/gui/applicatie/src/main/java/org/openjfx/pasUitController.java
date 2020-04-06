package org.openjfx;

import javafx.fxml.FXML;

import java.io.IOException;

public class PasUitController {
    @FXML
    public void switchToPassIn() throws IOException {
        App.setRoot("pasIn");
    }
}
