package org.openjfx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class MsgBox {

    private static Alert alert;
    public static ButtonType button1;
    public static ButtonType button2;


    private static void defaultComponetConfirmation() {
        alert = new Alert(Alert.AlertType.CONFIRMATION);
    }

    private static void defaultComponentInformation() {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }

    private static void defaultComponentWarning() {
        alert = new Alert(Alert.AlertType.WARNING);
    }

    public static void warningBox(String exeption) {
        defaultComponentWarning();
        alert.setTitle("Programma fout");
        alert.setHeaderText("info:");
        alert.setContentText(exeption);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmationBox(String title, String headerText) {
        defaultComponentInformation();
        alert.setTitle(title);
        alert.setHeaderText(headerText);

        button1 = new ButtonType("ja");
        button2 = new ButtonType("nee");

        alert.getButtonTypes().setAll(button1, button2);

        return alert.showAndWait();
    }

    public static void informationBox(String title, String header, String content) {
        defaultComponentInformation();
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
