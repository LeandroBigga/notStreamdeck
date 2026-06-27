package com.example.streamdeck;

import com.fazecast.jSerialComm.SerialPort;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HelloApplication extends Application {

    private Rectangle sliderBackground;
    private Rectangle sliderFill;
    StreamButton[] buttons = new StreamButton[12];
    private double smoothedSlider = 0;
    private static HelloApplication instance;
    private javafx.scene.control.Label statusLabel;


    @Override
    public void start(Stage stage) {
        AudioManager.setTargetProcess("spotify.exe");

        instance = this;

        // ===== DARK BACKGROUND =====
        BorderPane mainRoot = new BorderPane();
        statusLabel = new javafx.scene.control.Label("No App Selected");
        statusLabel.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        HBox topBar = new HBox(statusLabel);
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #1a1a1a;");
        topBar.setPrefHeight(40);

        mainRoot.setTop(topBar);

        mainRoot.setStyle("-fx-background-color: #121212;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setAlignment(Pos.CENTER);

        int index = 0;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 4; col++) {

                StreamButton button = new StreamButton(index + 1);
                buttons[index] = button;

                grid.add(button.getView(), col, row);
                index++;
            }
        }

        mainRoot.setCenter(grid);


        // ===== CUSTOM VERTICAL SLIDER =====

        sliderBackground = new Rectangle(30, 250);
        sliderBackground.setArcWidth(20);
        sliderBackground.setArcHeight(20);
        sliderBackground.setFill(Color.web("#1a1a1a"));

        sliderFill = new Rectangle(30, 0);
        sliderFill.setArcWidth(20);
        sliderFill.setArcHeight(20);
        sliderFill.setFill(Color.web("#00ffaa"));

        // Stack damit fill von unten wächst
        StackPane sliderPane = new StackPane(sliderBackground, sliderFill);
        sliderPane.setAlignment(Pos.BOTTOM_CENTER);

        VBox sliderBox = new VBox(sliderPane);
        sliderBox.setAlignment(Pos.CENTER);
        sliderBox.setPrefWidth(100);

        mainRoot.setRight(sliderBox);


        Scene scene = new Scene(mainRoot, 500, 400);
        scene.getStylesheets().add(
                getClass().getResource("/style.css").toExternalForm()
        );
        stage.setTitle("StreamDeck");
        stage.setScene(scene);
        stage.show();

        Menu root;

        if (MenuPersistence.exists()) {

            root = MenuPersistence.load();

        } else {

            root = new Menu();
            root.setItem(0, new Folder("Volume"));
            root.setItem(1, new Folder("Sounds"));

            MenuPersistence.save(root);
        }

        MenuManager.setRootMenu(root);
        refreshUI();


        startSerial();
    }

    private void startSerial() {

        SerialPort port = SerialPort.getCommPort("COM3"); // ggf anpassen
        port.setBaudRate(115200);
        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

        if (!port.openPort()) {
            System.out.println("Port konnte nicht geöffnet werden.");
            return;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ignored) {}

        new Thread(() -> {
            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(port.getInputStream()))) {

                String line;
                while ((line = reader.readLine()) != null) {

                    String finalLine = line;

                    Platform.runLater(() -> {
                        System.out.println("Empfangen: " + finalLine);

                        if (finalLine.startsWith("SLIDER:")) {

                            String value = finalLine.replace("SLIDER:", "").replace("%", "");
                            int percent = Integer.parseInt(value);

                            // auf 2er Schritte runden
                            int stepped = (percent / 2) * 2;

                            // nur updaten wenn wirklich anders
                            if (stepped != (int) smoothedSlider) {
                                smoothedSlider = stepped;
                                double normalized = smoothedSlider / 100.0;

                                animateSlider(normalized);
                                AudioManager.setVolume(normalized);


                            }
                        }







                        if (finalLine.startsWith("BUTTON:")) {

                            String data = finalLine.replace("BUTTON:", "").trim();
                            String[] parts = data.split("_");

                            int id = Integer.parseInt(parts[0]);
                            String action = parts[1];

                            if (id >= 1 && id <= 12) {

                                if (action.equals("DOWN")) {
                                    buttons[id - 1].press();

                                    DeckItem item = MenuManager.getCurrentMenu().getItem(id - 1);

                                    if (item != null) {
                                        item.execute();
                                    }

                                }

                                if (action.equals("UP")) {
                                    buttons[id - 1].release();
                                }
                            }
                        }

                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
    private void updateButtons() {

        Menu current = MenuManager.getCurrentMenu();

        for (int i = 0; i < 12; i++) {

            DeckItem item = current.getItem(i);

            if (item != null) {
                buttons[i].update(item);

            } else {
                buttons[i].setLabel("");
            }
        }
    }

    public static void refreshUI() {

        if (instance == null) return;

        Menu current = MenuManager.getCurrentMenu();

        for (int i = 0; i < 12; i++) {

            DeckItem item = current.getItem(i);

            instance.buttons[i].update(item);
        }
    }

    private void animateSlider(double percent) {

        double maxHeight = 250; // gleiche Höhe wie Background
        double targetHeight = percent * maxHeight;

        Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(120),
                        new KeyValue(sliderFill.heightProperty(), targetHeight)
                )
        );

        timeline.play();
    }

    public static void updateStatus(String text) {
        if (instance != null) {
            instance.statusLabel.setText(text);
        }
    }


    private void handleButtonPress(int id) {

        if (id >= 1 && id <= 12) {
            buttons[id - 1].activate();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
