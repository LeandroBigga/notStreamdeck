package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.*;
import com.example.streamdeck.action.audio.SelectAppVolumeAction;
import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.model.Menu;
import com.example.streamdeck.action.ui.component.Theme;
import com.example.streamdeck.service.AppConfig;
import com.example.streamdeck.service.MenuManager;
import com.example.streamdeck.service.MenuPersistence;
import com.example.streamdeck.util.ProcessUtils;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class AppSelectionDialog {

    public static AppConfig show(Stage owner) {

        Stage dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("App auswählen");

        TextField exeField = new TextField();
        exeField.setEditable(false);

        TextField processField = new TextField();
        TextField argsField = new TextField();

        Button browseBtn = new Button("Durchsuchen");
        Button saveBtn = new Button("Speichern");
        Button cancelBtn = new Button("Abbrechen");

        browseBtn.setOnAction(e -> {

            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Executable", "*.exe")
            );

            File file = chooser.showOpenDialog(dialog);

            if (file != null) {
                exeField.setText(file.getAbsolutePath());

                // Prozessname automatisch setzen
                processField.setText(file.getName());

                // Riot Detection
                if (file.getName().equalsIgnoreCase("RiotClientServices.exe")) {
                    argsField.setText("--launch-product=valorant --launch-patchline=live");
                }
            }
        });

        final AppConfig[] result = {null};

        saveBtn.setOnAction(e -> {
            result[0] = new AppConfig(
                    exeField.getText(),
                    processField.getText(),
                    argsField.getText()
            );
            dialog.close();
        });

        cancelBtn.setOnAction(e -> dialog.close());

        GridPane root = new GridPane();
        root.setPadding(new Insets(15));
        root.setVgap(10);
        root.setHgap(10);

        root.add(new Label("EXE:"), 0, 0);
        root.add(exeField, 1, 0);
        root.add(browseBtn, 2, 0);

        root.add(new Label("Prozessname:"), 0, 1);
        root.add(processField, 1, 1);

        root.add(new Label("Startparameter:"), 0, 2);
        root.add(argsField, 1, 2);

        root.add(saveBtn, 1, 3);
        root.add(cancelBtn, 2, 3);

        Scene scene = new Scene(root, 600, 200);
        dialog.setScene(scene);
        dialog.showAndWait();

        return result[0];
    }
    public static void open(StreamButton button, String iconPath) {

        Stage stage = new Stage();
        stage.setTitle("App auswählen");

        // Prozesse holen
        List<String> processes = ProcessUtils.getRunningProcesses();

        // Alphabetisch sortieren
        processes.sort(String::compareToIgnoreCase);

        TextField searchField = new TextField();
        searchField.setPromptText("Suchen...");

        FilteredList<String> filtered =
                new FilteredList<>(FXCollections.observableArrayList(processes));

        ListView<String> listView = new ListView<>(filtered);

        // Live Suche
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            filtered.setPredicate(process ->
                    process.toLowerCase().contains(newVal.toLowerCase())
            );
        });

        Button assign = new Button("Zuweisen");
        assign.setMaxWidth(Double.MAX_VALUE);

        assign.setOnAction(e -> {

            String selected = listView.getSelectionModel().getSelectedItem();
            if (selected == null) return;

            SelectAppVolumeAction action =
                    new SelectAppVolumeAction(selected, iconPath);

            Menu current = MenuManager.getCurrentMenu();
            current.setItem(button.getId() - 1, action);

            MenuPersistence.save(MenuManager.getRootMenu());
            HelloApplication.refreshUI();

            stage.close();
        });

        VBox layout = new VBox(10, searchField, listView, assign);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #121212;");
        Scene scene = new Scene(layout, 350, 400);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}