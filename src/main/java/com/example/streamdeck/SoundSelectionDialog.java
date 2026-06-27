package com.example.streamdeck;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class SoundSelectionDialog {

    public static void open(StreamButton button, String iconPath)
    {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Sound auswählen");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav")
        );


        File file = fileChooser.showOpenDialog(new Stage());

        if (file != null) {

            SoundAction action =
                    new SoundAction(file.getAbsolutePath(), iconPath);


            Menu current = MenuManager.getCurrentMenu();
            current.setItem(button.getId() - 1, action);
            MenuPersistence.save(MenuManager.getRootMenu());

            HelloApplication.refreshUI();
        }
    }
}
