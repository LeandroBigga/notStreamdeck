package com.example.streamdeck.action.app;

import com.example.streamdeck.action.ButtonAction;

import java.io.IOException;

public class AppLaunchAction implements ButtonAction {

    private final String path;

    public AppLaunchAction(String path) {
        this.path = path;
    }

    @Override
    public void execute() {
        try {
            Runtime.getRuntime().exec(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getDescription() {
        return "Launch: " + path;
    }
}
