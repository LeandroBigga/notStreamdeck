package com.example.streamdeck.model;

import com.example.streamdeck.service.MenuManager;

public class Folder implements DeckItem {

    private final String name;
    private final Menu submenu;
    private String iconPath;

    public Folder(String name) {
        this(name, null);
    }

    public Folder(String name, String iconPath) {
        this.name = name;
        this.iconPath = iconPath;
        this.submenu = new Menu();
    }



    public Menu getSubmenu() {
        return submenu;
    }

    @Override
    public void execute() {
        MenuManager.openMenu(submenu);
    }

    @Override
    public String getLabel() {
        return name;
    }

    @Override
    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}