package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.SerialReader;

import java.io.IOException;

import static org.openjfx.MsgBox.informationBox;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;
    public static String accountId;
    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("pasIn"));
        //sets the stage to full screen
        stage.setFullScreen(true);
        // remove the buttons
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        // remove listeners to prevent from unexpected behaviour.
        SerialReader.GetReader().removeListeners();
        scene.setRoot(loadFXML(fxml));
    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
}