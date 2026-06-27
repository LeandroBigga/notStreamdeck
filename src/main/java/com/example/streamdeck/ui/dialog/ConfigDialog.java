package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.model.DeckItem;
import com.example.streamdeck.action.ui.component.Theme;
import com.example.streamdeck.service.MenuManager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfigDialog {

    public static void open(StreamButton button) {

        DeckItem existing =
                MenuManager.getCurrentMenu().getItem(button.getId() - 1);

        // ===== WENN BEREITS BELEGT → EDIT DIALOG =====
        if (existing != null) {
            EditItemDialog.open(button);
            return;
        }

        // ===== WENN LEER → NORMALER CONFIG DIALOG =====
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Button konfigurieren");

        Button folderBtn = new Button("📁 Ordner erstellen");
        Button actionBtn = new Button("⚡ Aktion direkt binden");

        folderBtn.setMaxWidth(Double.MAX_VALUE);
        actionBtn.setMaxWidth(Double.MAX_VALUE);

        folderBtn.setOnAction(e -> {
            FolderCreationDialog.open(button);
            stage.close();
        });

        actionBtn.setOnAction(e -> {
            ActionSelectionDialog.open(button);
            stage.close();
        });

        VBox layout = new VBox(15, folderBtn, actionBtn);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 150);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}