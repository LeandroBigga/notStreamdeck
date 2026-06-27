package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.action.ui.component.Theme;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.io.File;

public class ActionSelectionDialog {

    public static void open(StreamButton button) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Aktion auswählen");

        final String[] selectedIcon = {null};

        Button chooseIconBtn = new Button("PNG Icon wählen");
        Button openAppBtn = new Button("App öffnen");
        Button volumeBtn = new Button("App Lautstärke");
        Button soundBtn = new Button("Soundboard Sound");
        Button spotifyBtn = new Button("Spotify Steuerung");

        chooseIconBtn.setMaxWidth(Double.MAX_VALUE);
        openAppBtn.setMaxWidth(Double.MAX_VALUE);
        volumeBtn.setMaxWidth(Double.MAX_VALUE);
        soundBtn.setMaxWidth(Double.MAX_VALUE);
        spotifyBtn.setMaxWidth(Double.MAX_VALUE);

        // PNG auswählen
        chooseIconBtn.setOnAction(e -> {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("PNG Icon auswählen");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
            );
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                selectedIcon[0] = file.getAbsolutePath();
                chooseIconBtn.setText("Icon gewählt ✅");
            }
        });

        // App öffnen
        openAppBtn.setOnAction(e -> {
            AppOpenDialog.open(button); // neue OpenAppAction wird erstellt
            stage.close();
        });

        // App Lautstärke
        volumeBtn.setOnAction(e -> {
            AppSelectionDialog.open(button, selectedIcon[0]); // vorhandene Methode nutzen
            stage.close();
        });

        // Soundboard Sound
        soundBtn.setOnAction(e -> {
            SoundSelectionDialog.open(button, selectedIcon[0]);
            stage.close();
        });

        // Spotify Steuerung
        spotifyBtn.setOnAction(e -> {
            SpotifyActionDialog.open(button); // hier muss SpotifyActionDialog existieren
            stage.close();
        });

        VBox layout = new VBox(15,
                chooseIconBtn,
                openAppBtn,
                volumeBtn,
                soundBtn,
                spotifyBtn
        );

        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #121212;"); // dunkles Theme

        Scene scene = new Scene(layout, 300, 300);
        Theme.apply(scene); // falls du globales Styling hast
        stage.setScene(scene);
        stage.show();
    }
}