package com.example.streamdeck.action.app;

import com.example.streamdeck.action.ButtonAction;
import com.example.streamdeck.model.DeckItem;

import java.io.File;

public class OpenAppAction implements DeckItem, ButtonAction {

    private final String exePath;
    private final String iconPath; // muss persistiert werden

    public OpenAppAction(String exePath, String iconPath) {
        this.exePath = exePath;
        this.iconPath = iconPath;
    }

    // ===== DeckItem =====
    @Override
    public void execute() {
        try {
            new ProcessBuilder(exePath).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getLabel() {
        File file = new File(exePath);
        return file.getName();
    }

    @Override
    public String getIconPath() {
        return iconPath; // Path wird jetzt aus JSON geladen
    }

    // ===== ButtonAction =====
    @Override
    public String getDescription() {
        return "Open App: " + getLabel();
    }

    // Getter für JSON / Menü
    public String getExePath() { return exePath; }
    public String getIconPathRaw() { return iconPath; }
}