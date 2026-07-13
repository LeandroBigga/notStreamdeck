package com.example.streamdeck.service;

import java.io.File;
import java.io.IOException;

public class AudioManager {

    private static String targetProcess = null;

    private static String resolveExePath() {
        // Liegt neben der gestarteten JAR/exe, im Unterordner "native"
        String appDir = new File(AudioManager.class
                .getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getParentFile()
                .getPath();
        return appDir + File.separator + "native" + File.separator + "AudioManagment.exe";
    }

    public static void setTargetProcess(String processName) {
        targetProcess = processName;
    }

    public static String getTargetProcess() {
        return targetProcess;
    }

    public static void setVolume(double volume) {

        if (targetProcess == null) return;

        String exePath = resolveExePath();
        File exeFile = new File(exePath);

        if (!exeFile.exists()) {
            System.err.println("AudioManagment.exe nicht gefunden unter: " + exePath);
            return;
        }

        try {
            ProcessBuilder builder = new ProcessBuilder(
                    exePath,
                    targetProcess,
                    String.valueOf(volume)
            );

            builder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}