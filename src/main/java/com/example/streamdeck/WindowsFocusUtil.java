package com.example.streamdeck;

import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;

public class WindowsFocusUtil {

    public static boolean focusWindow(String windowTitle) {

        HWND hwnd = User32.INSTANCE.FindWindow(null, windowTitle);

        if (hwnd == null) return false;

        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_RESTORE);
        User32.INSTANCE.SetForegroundWindow(hwnd);

        return true;
    }
}