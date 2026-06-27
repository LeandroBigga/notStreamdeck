package com.example.streamdeck.model;

public interface DeckItem {

    void execute();

    String getLabel();

    default String getIconPath() {
        return null;
    }
}
