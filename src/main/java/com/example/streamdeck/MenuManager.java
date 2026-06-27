package com.example.streamdeck;

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
}
