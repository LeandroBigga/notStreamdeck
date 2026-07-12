package com.example.streamdeck.service;

import com.example.streamdeck.action.ui.BackAction;
import com.example.streamdeck.HelloApplication;
import com.example.streamdeck.model.DeckItem;
import com.example.streamdeck.model.Menu;

public class MenuManager {

    private static Menu rootMenu;
    private static Menu currentMenu;

    public static void setRootMenu(Menu menu) {
        rootMenu = menu;
        currentMenu = menu;
    }

    public static Menu getRootMenu() {
        return rootMenu;
    }

    public static void openMenu(Menu menu) {

        currentMenu = menu;

        // Slot 0 = Back wenn nicht Root
        if (menu != rootMenu) {
            currentMenu.setItem(0, new BackAction());
        }

        HelloApplication.refreshUI();
    }


    public static void goBack() {
        currentMenu = rootMenu;
        HelloApplication.refreshUI();
    }

    public static Menu getCurrentMenu() {
        return currentMenu;
    }

    public static boolean moveOrSwapItem(int sourceIndex, int targetIndex) {

        if (currentMenu == null) return false;
        if (sourceIndex == targetIndex) return false;
        if (sourceIndex < 0 || sourceIndex >= 12) return false;
        if (targetIndex < 0 || targetIndex >= 12) return false;

        DeckItem sourceItem = currentMenu.getItem(sourceIndex);
        DeckItem targetItem = currentMenu.getItem(targetIndex);

        if (sourceItem == null) return false;

        // BackAction schützen: nicht verschieben und nicht überschreiben
        if (sourceItem instanceof BackAction) return false;
        if (targetItem instanceof BackAction) return false;

        currentMenu.setItem(targetIndex, sourceItem);

        // Wenn Ziel leer war: verschieben
        // Wenn Ziel belegt war: tauschen
        currentMenu.setItem(sourceIndex, targetItem);

        MenuPersistence.save(rootMenu);
        HelloApplication.refreshUI();

        return true;
    }
}