package com.example.streamdeck;

public class BackAction implements DeckItem {

    @Override
    public void execute() {
        MenuManager.goBack();
    }

    @Override
    public String getLabel() {
        return "← Back";
    }
}
