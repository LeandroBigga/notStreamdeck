package com.example.streamdeck.action.spotify;

import com.example.streamdeck.action.ButtonAction;
import com.example.streamdeck.model.DeckItem;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.win32.W32APIOptions;

public class SpotifyControlAction implements DeckItem, ButtonAction {

    public enum Command {
        PLAY_PAUSE,
        NEXT,
        PREVIOUS
    }

    private final Command command;
    private final String iconPath;

    public Command getCommand() {
        return command;
    }

    public SpotifyControlAction(Command command) {
        this(command, null);
    }

    public SpotifyControlAction(Command command, String iconPath) {
        this.command = command;
        this.iconPath = iconPath;
    }

    // JNA Interface für Windows
    private interface User32 extends Library {
        User32 INSTANCE = Native.load("user32", User32.class, W32APIOptions.DEFAULT_OPTIONS);

        void keybd_event(byte bVk, byte bScan, int dwFlags, int dwExtraInfo);
    }

    // Virtual-Key Codes
    private static final byte VK_MEDIA_NEXT_TRACK     = (byte) 0xB0;
    private static final byte VK_MEDIA_PREV_TRACK     = (byte) 0xB1;
    private static final byte VK_MEDIA_PLAY_PAUSE     = (byte) 0xB3;
    private static final int KEYEVENTF_KEYDOWN        = 0x0000;
    private static final int KEYEVENTF_KEYUP          = 0x0002;

    @Override
    public void execute() {
        byte vk;

        switch (command) {
            case PLAY_PAUSE -> vk = VK_MEDIA_PLAY_PAUSE;
            case NEXT       -> vk = VK_MEDIA_NEXT_TRACK;
            case PREVIOUS   -> vk = VK_MEDIA_PREV_TRACK;
            default -> throw new IllegalArgumentException("Unknown command: " + command);
        }

        // Taste drücken
        User32.INSTANCE.keybd_event(vk, (byte) 0, KEYEVENTF_KEYDOWN, 0);
        // Taste loslassen
        User32.INSTANCE.keybd_event(vk, (byte) 0, KEYEVENTF_KEYUP, 0);
    }

    @Override
    public String getLabel() {
        return "Spotify: " + command.name();
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    @Override
    public String getDescription() {
        return "Spotify: " + command.name();
    }
}