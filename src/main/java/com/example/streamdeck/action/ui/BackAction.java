package com.example.streamdeck.action.ui;

import com.example.streamdeck.model.DeckItem;
import com.example.streamdeck.service.MenuManager;

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
