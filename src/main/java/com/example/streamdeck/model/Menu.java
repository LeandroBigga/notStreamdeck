package com.example.streamdeck.model;

public class Menu {

    private DeckItem[] slots = new DeckItem[12];

    public void setItem(int index, DeckItem item) {
        slots[index] = item;
    }

    public DeckItem getItem(int index) {
        return slots[index];
    }
}
