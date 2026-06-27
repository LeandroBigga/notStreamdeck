package com.example.streamdeck;

public class SelectAppVolumeAction implements DeckItem {

    private final String processName;
    private String iconPath;
    public SelectAppVolumeAction(String processName, String iconPath) {
        this.processName = processName;
        this.iconPath = iconPath;
    }

    @Override
    public void execute() {
        AudioManager.setTargetProcess(processName);
        HelloApplication.updateStatus("Controlling: " + processName);
        System.out.println("NOW CONTROLLING: " + processName);
    }
    public SelectAppVolumeAction(String processName) {
        this(processName, null);
    }
    @Override
    public String getIconPath() {

        return iconPath;
    }

    @Override
    public String getLabel() {
        return "Vol: " + processName;
    }
    public String getIconPathRaw() {
        return iconPath;
    }
    public String getProcessName() {
        return processName;
    }
}
