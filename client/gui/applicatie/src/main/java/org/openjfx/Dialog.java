package org.openjfx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.LanguageSystem;

import java.io.IOException;
import java.lang.reflect.Constructor;

import static org.openjfx.App.loadFXML;

/*
Custom messagebox.
 */

public class Dialog extends Stage{

    // Constructor, sets the message in the dialog.
    // Give a language key, if key isn't recognised by the language system, the key wil be shown.
    Dialog(String message) throws IOException {
        Parent root = loadFXML("dialog");
        var ch = root.getChildrenUnmodifiable();
        for (Node node : ch){
            if(node instanceof Label){
                Label label = (Label)node;
                String id = label.getId();
                if(id != null && id.compareTo("message") == 0){
                    label.setText(LanguageSystem.getString(message));
                }
            }
        }
        Scene scene = new Scene(root);


        //Creating the mouse event handler
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
               close();
            }
        };
        //Registering the event filter
        scene.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

        this.initStyle(StageStyle.UNDECORATED);
        this.setScene(scene);
        this.show();

    }
}
