package com.example.streamdeck;

import java.io.IOException;

public class AudioManager {

    private static final String EXE_PATH =
            "C:\\Users\\leand\\source\\repos\\AudioManagment\\x64\\Debug\\AudioManagment.exe";

    private static String targetProcess = null;

    public static void setTargetProcess(String processName) {
        targetProcess = processName;
    }

    public static String getTargetProcess() {
        return targetProcess;
    }

    public static void setVolume(double volume) {

        if (targetProcess == null) return;

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    EXE_PATH,
                    targetProcess,
                    String.valueOf(volume)
            );

            builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
