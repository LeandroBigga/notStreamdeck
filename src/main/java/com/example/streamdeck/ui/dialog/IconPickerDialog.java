package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.action.ui.component.Theme;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.function.Consumer;

public class IconPickerDialog {

    // ===== Hier neue mitgelieferte Icons eintragen (Dateiname ohne Pfad) =====
    private static final String[] PRESET_ICONS = {
            "discord.png",
            "spotify.png",
            "obs.png",
            "chrome.png",
            "twitch.png",
            "mic.png",
            "volume.png",
            "folder.png"
    };

    public static void open(Consumer<String> onIconSelected) {

        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Icon auswählen");

        FlowPane gallery = new FlowPane();
        gallery.setHgap(12);
        gallery.setVgap(12);
        gallery.setPadding(new Insets(10));

        for (String fileName : PRESET_ICONS) {

            InputStream stream = IconPickerDialog.class
                    .getResourceAsStream("/preset-icons/" + fileName);

            if (stream == null) continue; // Datei fehlt -> überspringen

            ImageView iv = new ImageView(new Image(stream));
            iv.setFitWidth(56);
            iv.setFitHeight(56);

            Button iconBtn = new Button();
            iconBtn.setGraphic(iv);
            iconBtn.setStyle("-fx-background-color: #1f1f1f;");

            iconBtn.setOnAction(e -> {
                String resolvedPath = copyPresetToRuntimeIcons(fileName);
                if (resolvedPath != null) {
                    onIconSelected.accept(resolvedPath);
                }
                stage.close();
            });

            gallery.getChildren().add(iconBtn);
        }

        ScrollPane scrollPane = new ScrollPane(gallery);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefViewportHeight(260);

        Button customBtn = new Button("📂 Eigenes Icon von Festplatte wählen");
        customBtn.setMaxWidth(Double.MAX_VALUE);

        customBtn.setOnAction(e -> {

            FileChooser chooser = new FileChooser();
            chooser.setTitle("PNG Icon auswählen");
            chooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PNG Files", "*.png")
            );

            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                onIconSelected.accept(file.getAbsolutePath());
                stage.close();
            }
        });

        VBox layout = new VBox(15, scrollPane, new Separator(), customBtn);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #121212;");

        Scene scene = new Scene(layout, 380, 420);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }

    // Kopiert das gewählte, mitgelieferte Icon aus dem Classpath in den
    // Laufzeit-Ordner "icons/", damit es genauso wie extrahierte Exe-Icons
    // per "file:" Pfad geladen werden kann.
    private static String copyPresetToRuntimeIcons(String fileName) {

        try {
            File dir = new File("icons");
            if (!dir.exists()) dir.mkdirs();

            File target = new File(dir, fileName);

            // Nicht erneut kopieren wenn schon vorhanden
            if (!target.exists()) {
                try (InputStream in = IconPickerDialog.class
                        .getResourceAsStream("/preset-icons/" + fileName);
                     FileOutputStream out = new FileOutputStream(target)) {

                    if (in == null) return null;
                    in.transferTo(out);
                }
            }

            return target.getPath();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}