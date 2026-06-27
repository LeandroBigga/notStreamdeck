package com.example.streamdeck.model;

import com.example.streamdeck.service.AppConfig;
import com.example.streamdeck.action.app.SmartToggleAppAction;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DeckButton extends Button {

    private SmartToggleAppAction action;
    private AppConfig config;

    public DeckButton() {
        setPrefSize(100, 100);
        setStyle("-fx-background-color: #2b2b2b; -fx-text-fill: white;");
    }

    public void setAction(SmartToggleAppAction action) {
        this.action = action;
        setOnAction(e -> action.execute());
    }

    public SmartToggleAppAction getAction() {
        return action;
    }

    public void setConfig(AppConfig config) {
        this.config = config;
    }

    public AppConfig getConfig() {
        return config;
    }

    public void setIcon(Image image) {
        if (image != null) {
            ImageView view = new ImageView(image);
            view.setFitWidth(48);
            view.setFitHeight(48);
            setGraphic(view);
        }
    }

    public void setRunning(boolean running) {
        if (running) {
            setStyle("-fx-background-color: #2b2b2b; -fx-border-color: lime; -fx-border-width: 2;");
        } else {
            setStyle("-fx-background-color: #2b2b2b;");
        }
    }
}