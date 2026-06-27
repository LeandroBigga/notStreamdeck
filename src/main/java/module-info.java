module com.example.streamdeck {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fazecast.jSerialComm;
    requires java.desktop;
    requires javafx.media;
    requires com.google.gson;
    requires com.sun.jna.platform;
    requires com.sun.jna;
    requires javafx.swing;



    opens com.example.streamdeck to javafx.fxml;
    exports com.example.streamdeck;
}