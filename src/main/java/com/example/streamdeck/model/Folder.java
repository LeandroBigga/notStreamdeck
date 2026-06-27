package com.example.streamdeck.model;

import com.example.streamdeck.service.MenuManager;

public class Folder implements DeckItem {

    private final String name;
    private final Menu submenu;

    public Folder(String name) {
        this.name = name;
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
}
