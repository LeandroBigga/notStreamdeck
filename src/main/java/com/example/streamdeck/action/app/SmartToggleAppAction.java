package com.example.streamdeck.action.app;

import com.example.streamdeck.util.WindowsWindowUtil;
import com.example.streamdeck.model.DeckItem;
import com.sun.jna.platform.win32.WinDef;

import java.io.IOException;

public class SmartToggleAppAction implements DeckItem {

    private final String exePath;
    private final String processName;
    private final String startArgs;
    private final String iconPath;

    public SmartToggleAppAction(String exePath,
                                String processName,
                                String startArgs,
                                String iconPath) {
        this.exePath = exePath;
        this.processName = processName;
        this.startArgs = startArgs;
        this.iconPath = iconPath;
    }

    @Override
    public void execute() {

        WinDef.HWND hwnd = WindowsWindowUtil.findMainWindowByProcess(processName);

        if (hwnd == null) {
            startApp();
            return;
        }

        if (WindowsWindowUtil.isForeground(hwnd)) {
            WindowsWindowUtil.minimizeWindow(hwnd);
        } else {
            WindowsWindowUtil.focusWindow(hwnd);
        }
    }

    private void startApp() {
        try {

            ProcessBuilder builder;

            if (startArgs == null || startArgs.isBlank()) {

                builder = new ProcessBuilder(exePath);

            } else {

                String[] args = startArgs.split(" ");
                String[] command = new String[args.length + 1];

                command[0] = exePath;
                System.arraycopy(args, 0, command, 1, args.length);

                builder = new ProcessBuilder();
                builder.command(exePath, startArgs);
            }

            builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
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