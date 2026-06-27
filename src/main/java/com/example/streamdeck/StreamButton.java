package com.example.streamdeck;

import javafx.animation.PauseTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StreamButton {

    private final int id;

    private final StackPane view;
    private final Rectangle background;
    private final Text label;
    private ImageView iconView = new ImageView();

    private final DropShadow glow;

    private ButtonAction action;

    public StreamButton(int id) {
        this.id = id;

        // Hintergrund
        background = new Rectangle(90, 90);
        background.setArcWidth(15);
        background.setArcHeight(15);
        background.setFill(Color.web("#1f1f1f"));

        iconView.setFitWidth(60);
        iconView.setFitHeight(60);

        // Text
        label = new Text("");
        label.setFill(Color.WHITE);

        // StackPane enthält beides
        view = new StackPane(background, iconView, label);


        // Glow Effekt
        glow = new DropShadow();
        glow.setColor(Color.web("#00ffaa"));
        glow.setRadius(40);
        glow.setSpread(0.7);

        // UI Klick → Konfiguration öffnen
        view.setOnMouseClicked(e -> {
            ConfigDialog.open(this);
        });

    }

    public StackPane getView() {
        return view;
    }

    public int getId() {
        return id;
    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void update(DeckItem item) {

        if (item == null) {
            label.setText("");
            iconView.setImage(null);
            return;
        }

        String icon = item.getIconPath();

        if (icon != null && !icon.isEmpty()) {

            Image img = new Image("file:" + icon);
            iconView.setImage(img);
            label.setText("");

        } else {

            iconView.setImage(null);
            label.setText(item.getLabel());
        }

    }


    public void setAction(ButtonAction action) {
        this.action = action;
    }
    public void setIcon(Image img) {
        iconView.setImage(img);
    }
    public void trigger() {
        if (action != null) {
            action.execute();
        }
    }

    public void press() {
        background.setEffect(glow);
        background.setFill(Color.web("#2a2a2a"));
    }

    public void release() {
        background.setEffect(null);
        background.setFill(Color.web("#1f1f1f"));
    }

    public void activate() {

        background.setEffect(glow);
        background.setFill(Color.web("#2a2a2a"));

        PauseTransition pause = new PauseTransition(Duration.millis(300));

        pause.setOnFinished(e -> {
            background.setEffect(null);
            background.setFill(Color.web("#1f1f1f"));
        });

        pause.play();
    }
}
