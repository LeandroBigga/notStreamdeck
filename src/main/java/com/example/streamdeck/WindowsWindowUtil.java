package com.example.streamdeck;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.*;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;
public class WindowsWindowUtil {

    public static WinDef.HWND findMainWindowByProcess(String processName) {

        final WinDef.HWND[] result = {null};

        User32.INSTANCE.EnumWindows((hWnd, data) -> {

            if (!User32.INSTANCE.IsWindowVisible(hWnd)) return true;

            // Kein ToolWindow
            int style = User32.INSTANCE.GetWindowLong(hWnd, WinUser.GWL_EXSTYLE);
            if ((style & 0x00000080) != 0) return true;

            IntByReference pid = new IntByReference();
            User32.INSTANCE.GetWindowThreadProcessId(hWnd, pid);

            ProcessHandle process = ProcessHandle.of(pid.getValue()).orElse(null);
            if (process == null) return true;

            String cmd = process.info().command().orElse("").toLowerCase();

            if (cmd.contains(processName.toLowerCase())) {
                result[0] = hWnd;
                return false;
            }

            return true;

        }, Pointer.NULL);

        return result[0];
    }

    public static void focusWindow(WinDef.HWND hwnd) {
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_RESTORE);
        User32.INSTANCE.SetForegroundWindow(hwnd);
    }

    public static void minimizeWindow(WinDef.HWND hwnd) {
        User32.INSTANCE.ShowWindow(hwnd, WinUser.SW_MINIMIZE);
    }

    public static boolean isForeground(WinDef.HWND hwnd) {
        return hwnd.equals(User32.INSTANCE.GetForegroundWindow());
    }

    public static boolean isProcessRunning(String processName) {
        return ProcessHandle.allProcesses()
                .anyMatch(ph ->
                        ph.info().command()
                                .map(cmd -> cmd.toLowerCase().contains(processName.toLowerCase()))
                                .orElse(false));
    }
}