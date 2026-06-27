package com.example.streamdeck.action.audio;

import com.example.streamdeck.model.DeckItem;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundAction implements DeckItem {

    private final String filePath;
    private MediaPlayer player;
    private final String iconPath;
    public SoundAction(String filePath, String iconPath) {
        this.filePath = filePath;
        this.iconPath = iconPath;
    }

    public SoundAction(String filePath) {
        this(filePath, null);
    }

    @Override
    public void execute() {

        if (player != null &&
                player.getStatus() == MediaPlayer.Status.PLAYING) {

            // Stop wenn bereits am laufen
            player.stop();
            return;
        }

        // Neu starten
        File file = new File(filePath);
        Media media = new Media(file.toURI().toString());
        player = new MediaPlayer(media);

        player.setOnEndOfMedia(() -> player = null);

        player.play();
    }

    public String getFilePath() {
        return filePath;
    }
    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public String getLabel() {

        if (filePath == null) return "Sound";

        java.io.File file = new java.io.File(filePath);
        return file.getName();
    }
    public String getIconPathRaw() {
        return iconPath;
    }
}
