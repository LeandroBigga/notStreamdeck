package com.example.streamdeck;

import com.example.streamdeck.HelloApplication;
import com.example.streamdeck.Menu;
import com.example.streamdeck.MenuManager;

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
