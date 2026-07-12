package com.example.streamdeck.action.ui.component;

import com.example.streamdeck.action.ButtonAction;
import com.example.streamdeck.action.ui.BackAction;
import com.example.streamdeck.model.DeckItem;
import com.example.streamdeck.service.MenuManager;
import com.example.streamdeck.ui.dialog.ConfigDialog;
import javafx.animation.PauseTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
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

        enableDragAndDrop();

        // UI Klick → Konfiguration öffnen
        view.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                DeckItem item = MenuManager.getCurrentMenu().getItem(id - 1);
                if (item != null) {
                    item.execute();
                } else {
                    ConfigDialog.open(this);
                }
            } else if (e.getButton() == javafx.scene.input.MouseButton.SECONDARY) {
                ConfigDialog.open(this);
            }
        });

    }
    private void enableDragAndDrop() {

        view.setOnDragDetected(event -> {

            DeckItem item = MenuManager.getCurrentMenu().getItem(id - 1);

            if (item == null || item instanceof BackAction) {
                event.consume();
                return;
            }

            Dragboard dragboard = view.startDragAndDrop(TransferMode.MOVE);

            ClipboardContent content = new ClipboardContent();
            content.putString(String.valueOf(id));
            dragboard.setContent(content);

            view.setOpacity(0.5);

            event.consume();
        });

        view.setOnDragOver(event -> {

            Dragboard dragboard = event.getDragboard();

            if (event.getGestureSource() != view && dragboard.hasString()) {

                DeckItem targetItem = MenuManager.getCurrentMenu().getItem(id - 1);

                if (!(targetItem instanceof BackAction)) {
                    event.acceptTransferModes(TransferMode.MOVE);
                    background.setStroke(Color.web("#00ffaa"));
                    background.setStrokeWidth(3);
                }
            }

            event.consume();
        });

        view.setOnDragExited(event -> {
            background.setStroke(null);
            event.consume();
        });

        view.setOnDragDropped(event -> {

            Dragboard dragboard = event.getDragboard();
            boolean success = false;

            if (dragboard.hasString()) {
                try {
                    int sourceButtonId = Integer.parseInt(dragboard.getString());
                    int targetButtonId = id;

                    success = MenuManager.moveOrSwapItem(
                            sourceButtonId - 1,
                            targetButtonId - 1
                    );

                } catch (NumberFormatException ignored) {
                    success = false;
                }
            }

            background.setStroke(null);
            event.setDropCompleted(success);
            event.consume();
        });

        view.setOnDragDone(event -> {
            view.setOpacity(1.0);
            background.setStroke(null);
            event.consume();
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
            label.setText("+");
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
