package com.example.streamdeck.service;

public class AppConfig {

    private final String exePath;
    private final String processName;
    private final String startArgs;

    public AppConfig(String exePath, String processName, String startArgs) {
        this.exePath = exePath;
        this.processName = processName;
        this.startArgs = startArgs;
    }

    public String getExePath() {
        return exePath;
    }

    public String getProcessName() {
        return processName;
    }

    public String getStartArgs() {
        return startArgs;
    }
}