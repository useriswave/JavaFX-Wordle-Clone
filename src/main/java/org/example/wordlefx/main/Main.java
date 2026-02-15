package org.example.wordlefx.main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.wordlefx.panes.RootPane;


import javafx.scene.image.Image;
import java.util.Objects;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        var root = new RootPane();
        String letterBoxCss = Objects.requireNonNull(this.getClass().getResource("/org/example/wordlefx/styling/letter-box.css")).toExternalForm();


        Scene scene = new Scene(root, 1200, 800);

        stage.setScene(scene);

        root.getCenterPane().registerKey(stage, scene);
        scene.getStylesheets().add(letterBoxCss);

        Image appIcon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/wordlefx/images/icon.png")));
        stage.getIcons().add(appIcon);

        stage.show();
    }
}
