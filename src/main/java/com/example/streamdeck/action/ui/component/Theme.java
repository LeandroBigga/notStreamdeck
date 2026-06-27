package com.example.streamdeck.action.ui.component;

import javafx.scene.Scene;

public class Theme {

    public static void apply(Scene scene) {
        scene.getStylesheets().add(
                Theme.class.getResource("/style.css").toExternalForm()
        );
    }
}