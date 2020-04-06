/**
 *
 */
module org.openjfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires org.json;
    requires java.net.http;

    opens org.openjfx to javafx.fxml;
    exports org.openjfx;
}