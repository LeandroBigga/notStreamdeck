package com.example.streamdeck;

public interface DeckItem {

    void execute();

    String getLabel();

    default String getIconPath() {
        return null;
    }
}
