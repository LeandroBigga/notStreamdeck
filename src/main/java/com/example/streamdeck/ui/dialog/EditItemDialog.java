package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.*;
import com.example.streamdeck.action.app.OpenAppAction;
import com.example.streamdeck.action.audio.SelectAppVolumeAction;
import com.example.streamdeck.action.audio.SoundAction;
import com.example.streamdeck.action.spotify.SpotifyControlAction;
import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.model.DeckItem;
import com.example.streamdeck.action.ui.component.Theme;
import com.example.streamdeck.service.MenuManager;
import com.example.streamdeck.service.MenuPersistence;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class EditItemDialog {

    public static void open(StreamButton button) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Button bearbeiten");

        Button changeIcon = new Button("Icon ändern");
        Button clear = new Button("Clear (Löschen)");
        Button cancel = new Button("Abbrechen");

        changeIcon.setMaxWidth(Double.MAX_VALUE);
        clear.setMaxWidth(Double.MAX_VALUE);
        cancel.setMaxWidth(Double.MAX_VALUE);

        DeckItem item =
                MenuManager.getCurrentMenu().getItem(button.getId() - 1);

        // ===== ICON ÄNDERN =====
        changeIcon.setOnAction(e -> {

            IconPickerDialog.open(iconPath -> {

                if (item == null) return;

                DeckItem newItem = null;

                if (item instanceof SoundAction sound) {
                    newItem = new SoundAction(sound.getFilePath(), iconPath);
                }

                else if (item instanceof SelectAppVolumeAction volume) {
                    newItem = new SelectAppVolumeAction(volume.getProcessName(), iconPath);
                }

                else if (item instanceof OpenAppAction app) {
                    newItem = new OpenAppAction(app.getExePath(), iconPath);
                }

                else if (item instanceof SpotifyControlAction spotify) {
                    newItem = new SpotifyControlAction(spotify.getCommand(), iconPath);
                }

                if (newItem != null) {
                    MenuManager.getCurrentMenu()
                            .setItem(button.getId() - 1, newItem);

                    MenuPersistence.save(MenuManager.getRootMenu());
                    HelloApplication.refreshUI();
                }
            });

            stage.close();
        });

        // ===== CLEAR =====
        clear.setOnAction(e -> {

            MenuManager.getCurrentMenu()
                    .setItem(button.getId() - 1, null);

            MenuPersistence.save(MenuManager.getRootMenu());
            HelloApplication.refreshUI();

            stage.close();
        });

        cancel.setOnAction(e -> stage.close());

        VBox layout = new VBox(15, changeIcon, clear, cancel);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 250, 180);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}