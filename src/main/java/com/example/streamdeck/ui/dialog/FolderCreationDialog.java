package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.*;
import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.action.ButtonAction;
import com.example.streamdeck.action.ui.BackAction;
import com.example.streamdeck.model.DeckItem;
import com.example.streamdeck.service.MenuManager;
import com.example.streamdeck.ui.dialog.ConfigDialog;
import javafx.animation.PauseTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.example.streamdeck.model.Folder;
import com.example.streamdeck.model.Menu;
import com.example.streamdeck.action.ui.component.Theme;
import com.example.streamdeck.service.MenuManager;
import com.example.streamdeck.service.MenuPersistence;
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

        final String[] selectedIcon = {null};

        Button chooseIconBtn = new Button("Icon wählen");
        chooseIconBtn.setMaxWidth(Double.MAX_VALUE);

        chooseIconBtn.setOnAction(e -> {
            IconPickerDialog.open(iconPath -> {
                selectedIcon[0] = iconPath;
                chooseIconBtn.setText("Icon gewählt ✅");
            });
        });

        Button createBtn = new Button("Erstellen");
        createBtn.setMaxWidth(Double.MAX_VALUE);

        createBtn.setOnAction(e -> {

            String name = nameField.getText().trim();

            if (!name.isEmpty()) {

                Folder folder = new Folder(name, selectedIcon[0]);

                Menu current = MenuManager.getCurrentMenu();
                current.setItem(button.getId() - 1, folder);
                MenuPersistence.save(MenuManager.getRootMenu());

                HelloApplication.refreshUI();
                stage.close();
            }
        });

        VBox layout = new VBox(15, nameField, chooseIconBtn, createBtn);
        layout.setPadding(new Insets(20));
        Scene scene = new Scene(layout, 300, 200);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}