package com.example.streamdeck.action.app;

import com.example.streamdeck.util.WindowsFocusUtil;
import com.example.streamdeck.model.DeckItem;

import java.io.IOException;

public class SmartOpenAppAction implements DeckItem {

    private final String exePath;
    private final String processName;
    private final String windowTitle;
    private final String iconPath;

    public SmartOpenAppAction(String exePath,
                              String processName,
                              String windowTitle,
                              String iconPath) {
        this.exePath = exePath;
        this.processName = processName;
        this.windowTitle = windowTitle;
        this.iconPath = iconPath;
    }

    @Override
    public void execute() {

        if (isRunning(processName)) {

            boolean focused = WindowsFocusUtil.focusWindow(windowTitle);

            if (!focused) {
                System.out.println("Fenster nicht gefunden.");
            }

        } else {
            try {
                new ProcessBuilder(exePath).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isRunning(String name) {
        return ProcessHandle.allProcesses()
                .anyMatch(p ->
                        p.info().command().isPresent() &&
                                p.info().command().get().toLowerCase().contains(name.toLowerCase())
                );
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public String getLabel() {
        return processName;
    }
}