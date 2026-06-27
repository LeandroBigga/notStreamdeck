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
    exports com.example.streamdeck.ui.dialog;
    opens com.example.streamdeck.ui.dialog to javafx.fxml;
    exports com.example.streamdeck.model;
    opens com.example.streamdeck.model to javafx.fxml;
    exports com.example.streamdeck.service;
    opens com.example.streamdeck.service to javafx.fxml;
    exports com.example.streamdeck.action.app;
    opens com.example.streamdeck.action.app to javafx.fxml;
    exports com.example.streamdeck.action.audio;
    opens com.example.streamdeck.action.audio to javafx.fxml;
    exports com.example.streamdeck.action.spotify;
    opens com.example.streamdeck.action.spotify to javafx.fxml;
    exports com.example.streamdeck.action.ui;
    opens com.example.streamdeck.action.ui to javafx.fxml;
    exports com.example.streamdeck.action;
    opens com.example.streamdeck.action to javafx.fxml;
    exports com.example.streamdeck.action.ui.component;
    opens com.example.streamdeck.action.ui.component to javafx.fxml;
    exports com.example.streamdeck.util;
    opens com.example.streamdeck.util to javafx.fxml;
}