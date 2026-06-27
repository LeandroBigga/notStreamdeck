package com.example.streamdeck;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FolderCreationDialog {

    public static void open(StreamButton button) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ordner erstellen");

        TextField nameField = new TextField();
        nameField.setPromptText("Ordnername");

        Button createBtn = new Button("Erstellen");
        createBtn.setMaxWidth(Double.MAX_VALUE);

        createBtn.setOnAction(e -> {

            String name = nameField.getText().trim();

            if (!name.isEmpty()) {

                Folder folder = new Folder(name);

                Menu current = MenuManager.getCurrentMenu();
                current.setItem(button.getId() - 1, folder);
                MenuPersistence.save(MenuManager.getRootMenu());

                HelloApplication.refreshUI();
                stage.close();
            }
        });

        VBox layout = new VBox(15, nameField, createBtn);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 150);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}
