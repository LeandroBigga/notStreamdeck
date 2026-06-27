package com.example.streamdeck.ui.dialog;

import com.example.streamdeck.action.app.AppLaunchAction;
import com.example.streamdeck.action.ui.component.StreamButton;
import com.example.streamdeck.action.ui.component.Theme;
import com.example.streamdeck.action.audio.VolumeAction;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ActionConfigDialog {

    public static void open(StreamButton button) {

        Stage stage = new Stage();
        VBox root = new VBox(10);

        ComboBox<String> actionType = new ComboBox<>();
        actionType.getItems().addAll("Volume +10", "Volume -10", "Launch App");

        TextField appPath = new TextField();
        appPath.setPromptText("App Path");

        Button save = new Button("Save");

        save.setOnAction(e -> {

            String selected = actionType.getValue();

            if (selected == null) return;

            if (selected.equals("Volume +10")) {
                button.setAction(new VolumeAction(10));
            }

            if (selected.equals("Volume -10")) {
                button.setAction(new VolumeAction(-10));
            }

            if (selected.equals("Launch App")) {
                button.setAction(new AppLaunchAction(appPath.getText()));
            }

            stage.close();
        });

        root.getChildren().addAll(actionType, appPath, save);
        root.setStyle("-fx-background-color: #121212;");
        Scene scene = new Scene(root, 300, 200);
        Theme.apply(scene);
        stage.setScene(scene);
        stage.show();
    }
}
