package org.openjfx;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import model.SerialReader;

import java.util.Optional;

public class MsgBox {

    private static Alert alert;
    public static ButtonType button1;
    public static ButtonType button2;
//    private static SerialReader reader;

    private static void defaultComponetConfirmation() { alert = new Alert(Alert.AlertType.CONFIRMATION); }

    private static void defaultComponentInformation() {
        alert = new Alert(Alert.AlertType.INFORMATION);
    }

    private static void defaultComponentWarning() {
        alert = new Alert(Alert.AlertType.WARNING);
    }

//    public static void warningBox(String exeption) {
//        defaultComponentWarning();
//        alert.setTitle("Programma fout");
//        alert.setHeaderText("info:");
//        alert.setContentText(exeption);
//        alert.showAndWait();
//    }
//
//    public static Optional<ButtonType> confirmationBox(String title, String headerText) {
//        defaultComponentInformation();
//        alert.setTitle(title);
//        alert.setHeaderText(headerText);
//
//        button1 = new ButtonType("ja");
//        button2 = new ButtonType("nee");
//
//        alert.getButtonTypes().setAll(button1, button2);
//
//        return alert.showAndWait();
//    }

    public static Alert informationBox(String title, String header, String content) {
        defaultComponentInformation();
//        reader = SerialReader.GetReader();
//        reader.addKeyPadListener((x) -> {
//            handler(x);
//        });
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
//        Image image = new Image(MsgBox.class.getResourceAsStream("/fotos/passwordError.jpg"));
//        ImageView imageView = new ImageView(image);
//        imageView.setFitHeight(100);
//        imageView.setFitWidth(150);
//        alert.setGraphic(imageView);
        //alert.getButtonTypes().clear();
        alert.initStyle(StageStyle.UNDECORATED);
        return alert;
    }

//    public static void handler(String key) {
//        if (alert.isShowing() && key.charAt(0) == '#') {
//            alert.close();
//        }
//    }
}
