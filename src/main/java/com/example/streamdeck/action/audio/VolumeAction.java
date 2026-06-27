package com.example.streamdeck.action.audio;

import com.example.streamdeck.action.ButtonAction;

public class VolumeAction implements ButtonAction {

    private final int volumeChange;

    public VolumeAction(int volumeChange) {
        this.volumeChange = volumeChange;
    }

    @Override
    public void execute() {
        System.out.println("Volume change: " + volumeChange);
        // später echte Lautstärke ändern
    }

    @Override
    public String getDescription() {
        return "Volume " + (volumeChange > 0 ? "+" : "") + volumeChange;
    }
}
