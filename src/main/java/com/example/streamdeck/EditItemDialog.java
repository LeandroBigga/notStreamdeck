package com.example.streamdeck;

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

        Button changeIcon = new Button("PNG ändern");
        Button clear = new Button("Clear (Löschen)");
        Button cancel = new Button("Abbrechen");

        changeIcon.setMaxWidth(Double.MAX_VALUE);
        clear.setMaxWidth(Double.MAX_VALUE);
        cancel.setMaxWidth(Double.MAX_VALUE);

        changeIcon.setMaxWidth(Double.MAX_VALUE);
        clear.setMaxWidth(Double.MAX_VALUE);
        cancel.setMaxWidth(Double.MAX_VALUE);

        DeckItem item =
                MenuManager.getCurrentMenu().getItem(button.getId() - 1);

        // ===== ICON ÄNDERN =====
        changeIcon.setOnAction(e -> {

            javafx.stage.FileChooser chooser = new javafx.stage.FileChooser();
            chooser.getExtensionFilters().add(
                    new javafx.stage.FileChooser.ExtensionFilter("PNG Files", "*.png")
            );

            java.io.File file = chooser.showOpenDialog(stage);

            if (file != null && item != null) {

                DeckItem newItem = null;

                if (item instanceof SoundAction sound) {
                    newItem = new SoundAction(
                            sound.getFilePath(),
                            file.getAbsolutePath()
                    );
                }

                else if (item instanceof SelectAppVolumeAction volume) {
                    newItem = new SelectAppVolumeAction(
                            volume.getProcessName(),
                            file.getAbsolutePath()
                    );
                }

                if (newItem != null) {
                    MenuManager.getCurrentMenu()
                            .setItem(button.getId() - 1, newItem);

                    MenuPersistence.save(MenuManager.getRootMenu());
                    HelloApplication.refreshUI();
                }
            }

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