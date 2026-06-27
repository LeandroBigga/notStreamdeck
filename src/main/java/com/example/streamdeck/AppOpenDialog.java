package com.example.streamdeck;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;

public class AppOpenDialog {

    public static void open(StreamButton button) {

        // FileChooser für EXE
        FileChooser chooser = new FileChooser();
        chooser.setTitle("App auswählen");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Executable", "*.exe")
        );

        File file = chooser.showOpenDialog(new Stage());
        if (file != null) {

            // Icon aus EXE extrahieren
            Image icon = ExeIconExtractor.extract(file.getAbsolutePath());
            String iconPath = null;

            // Icon speichern
            if (icon != null) {
                try {
                    File dir = new File("icons");
                    if (!dir.exists()) dir.mkdirs();

                    iconPath = "icons/" + file.getName() + ".png";

                    // FX Image → BufferedImage → Datei
                    ImageIO.write(SwingFXUtils.fromFXImage(icon, null), "png", new File(iconPath));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            // Action erstellen
            OpenAppAction action = new OpenAppAction(file.getAbsolutePath(), iconPath);

            // Button sofort updaten
            button.setAction(action);
            button.update(action);

            // Menü speichern
            MenuManager.getCurrentMenu().setItem(button.getId() - 1, action);
            MenuPersistence.save(MenuManager.getRootMenu());

            // Status-Label aktualisieren
            HelloApplication.updateStatus("Assigned App: " + file.getName());
        }
    }
}