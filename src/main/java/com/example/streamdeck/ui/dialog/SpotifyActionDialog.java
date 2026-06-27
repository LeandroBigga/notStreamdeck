package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.action.spotify.SpotifyControlAction;
import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.action.ui.component.Theme;
import com.example.streamdeck.service.MenuManager;
import com.example.streamdeck.service.MenuPersistence;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SpotifyActionDialog {

    public static void open(StreamButton button) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Spotify Aktion wählen");

        Button playPauseBtn = new Button("▶/⏸ Play/Pause");
        Button nextBtn = new Button("⏭ Next");
        Button prevBtn = new Button("⏮ Prev");

        playPauseBtn.setMaxWidth(Double.MAX_VALUE);
        nextBtn.setMaxWidth(Double.MAX_VALUE);
        prevBtn.setMaxWidth(Double.MAX_VALUE);

        playPauseBtn.setOnAction(e -> {
            SpotifyControlAction action = new SpotifyControlAction(SpotifyControlAction.Command.PLAY_PAUSE);
            button.setAction(action);
            button.update(action);
            MenuManager.getCurrentMenu().setItem(button.getId() - 1, action);
            MenuPersistence.save(MenuManager.getRootMenu());
            stage.close();
        });

        nextBtn.setOnAction(e -> {
            SpotifyControlAction action = new SpotifyControlAction(SpotifyControlAction.Command.NEXT);
            button.setAction(action);
            button.update(action);
            MenuManager.getCurrentMenu().setItem(button.getId() - 1, action);
            MenuPersistence.save(MenuManager.getRootMenu());
            stage.close();
        });

        prevBtn.setOnAction(e -> {
            SpotifyControlAction action = new SpotifyControlAction(SpotifyControlAction.Command.PREVIOUS);
            button.setAction(action);
            button.update(action);
            MenuManager.getCurrentMenu().setItem(button.getId() - 1, action);
            MenuPersistence.save(MenuManager.getRootMenu());
            stage.close();
        });

        HBox buttonBox = new HBox(10, prevBtn, playPauseBtn, nextBtn);
        VBox layout = new VBox(15, buttonBox);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #121212;");

        Scene scene = new Scene(layout);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}