package com.example.streamdeck.util;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class WindowsProcessUtils {

    public static boolean isRunning(String exePath) {
        try {
            String exeName = new java.io.File(exePath).getName();
            Process process = Runtime.getRuntime().exec("tasklist /FI \"IMAGENAME eq " + exeName + "\"");
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(exeName)) return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void focusWindow(String exePath) {
        String exeName = new java.io.File(exePath).getName();
        User32 user32 = User32.INSTANCE;

        // Nur beispielhafte Umsetzung: Fokus auf das erste Fenster setzen
        HWND hwnd = user32.FindWindow(null, exeName);
        if (hwnd != null) {
            user32.ShowWindow(hwnd, 9); // SW_RESTORE
            user32.SetForegroundWindow(hwnd);
        }
    }
}