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
 *
 * Main class for the application.
 *
 * Produced by Tymek, Shabir, Robin and Jaco.
 */

public class App extends Application {

    // Saves the lastRoot except it is a error message.
    private static Parent lastRoot;
    private static Scene scene;
    private static BaseController lastController;
    public static String accountId;
    public static String pin;//bad work around for gos bank


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("pasIn").load());
        //sets the stage to full screen
        //stage.setFullScreen(true);
        // remove the buttons
        //stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = loadFXML(fxml);
        Parent root = loader.load();
        scene.setRoot(root);
        if (!fxml.equals("pinFout") && !fxml.equals("saldoLaag")){
            lastController = loader.getController();
            lastRoot = root;
        }
    }

    //
    static void showErrorScreen(String message ) throws IOException {
        Parent root = loadFXML("saldoLaag").load();
        var ch = root.getChildrenUnmodifiable();
        for (Node node : ch){
            if(node instanceof Label){
                Label label = (Label)node;
                String id = label.getId();
                if(id != null && id.compareTo("lowBalanceId") == 0){// label with saldolaagId  as id
                    label.setText(LanguageSystem.getString(message));
                }
            }
        }
        scene.setRoot(root);

    }

    static void showErrorScreenPin(String message) throws IOException {
        Parent root = loadFXML("pinFout").load();
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

    static FXMLLoader loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader;
    }

    static public void restoreLast(){
        if(lastRoot != null) {
            scene.setRoot(lastRoot);
        }
        if(lastController != null){
            lastController.AddSerialHandlers();
        }
    }

}