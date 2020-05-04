package org.openjfx;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.lang.reflect.Constructor;

import static org.openjfx.App.loadFXML;

/*
Custom messagebox.
 */

public class Dialog extends Stage{

    // Constructor set label text on screen.
    Dialog(String message) throws IOException {
        Parent root = loadFXML("dialog");
        var ch = root.getChildrenUnmodifiable();
        for (Node node : ch){
            if(node instanceof Label){
                Label label = (Label)node;
                String id = label.getId();
                if(id != null && id.compareTo("message") == 0){
                    label.setText(message);
                }
            }
        }

        Scene scene = new Scene(root);
        this.initStyle(StageStyle.UNDECORATED);
        this.setScene(scene);
        this.show();
    }
}
