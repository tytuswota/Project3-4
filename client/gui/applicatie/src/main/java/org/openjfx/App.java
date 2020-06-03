package org.openjfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.LanguageSystem;
import model.SerialReader;

import java.io.IOException;

/**
 * JavaFX App
 */

public class App extends Application {

    // Saves the lastRoot except it is a error message.
    private static Parent lastRoot;

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
        lastRoot = loadFXML(fxml);
        scene.setRoot(lastRoot);
    }

    //
    static void showErrorScreen(String message ) throws IOException {
        // remove listeners to prevent from unexpected behaviour.
        SerialReader.GetReader().removeListeners();
        Parent root = loadFXML("saldoLaag");
        var ch = root.getChildrenUnmodifiable();
        for (Node node : ch){
            if(node instanceof Label){
                Label label = (Label)node;
                String id = label.getId();
                if(id != null && id.compareTo("saldoLaagId") == 0){
                    label.setText(LanguageSystem.getString(message));
                }
            }
        }
        scene.setRoot(root);

    }

    static void showErrorScreenPin(String message) throws IOException {
        // remove listeners to prevent from unexpected behaviour.
        SerialReader.GetReader().removeListeners();
        Parent root = loadFXML("pinFout");
        var ch = root.getChildrenUnmodifiable();
        for (Node node : ch) {
            if (node instanceof Label) {
                Label label = (Label) node;
                String id = label.getId();
                if (id != null && id.compareTo("pinErrorId") == 0) {
                    label.setText(LanguageSystem.getString(message));
                }
            }
        }
        scene.setRoot(root);

    }

    static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    static public void restoreLast(){
        if(lastRoot != null)
            scene.setRoot(lastRoot);
    }

}