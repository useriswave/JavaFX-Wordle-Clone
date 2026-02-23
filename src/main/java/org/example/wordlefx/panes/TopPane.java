package org.example.wordlefx.panes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.example.wordlefx.enums.Constant;

public class TopPane extends StackPane {

    public TopPane() {

        VBox layout = new VBox();

        BorderPane bar = new BorderPane();
        bar.setPadding(new Insets(10, 30, 10, 30));

        Label name = new Label("@useriswave");
        Label title = new Label(Constant.TITLE.getIdentifier());
        title.setStyle("-fx-font-weight: bold;\n" +
                       "-fx-font-size: 30;");
        title.setAlignment(Pos.CENTER);
        name.setStyle("-fx-font-weight: bold;\n" +
                "-fx-font-size: 15;");

        Button discord = new Button("Discord");
        discord.setStyle("-fx-background-color: #5865f2;" + "-fx-text-fill: white;" +
                         "-fx-font-weight: bold;" + "-fx-cursor: hand;" + "-fx-background-radius: 15px;");
        discord.setPadding(new Insets(10, 20, 10, 20));


        Button youtube = new Button("YouTube");
        youtube.setStyle("-fx-background-color: red;" + "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" + "-fx-cursor: hand;" + "-fx-background-radius: 15px;");
        youtube.setPadding(new Insets(10, 20, 10, 20));

        Button github = new Button("GitHub");
        github.setStyle("-fx-background-color: black;" + "-fx-text-fill: white;" +
                "-fx-font-weight: bold;" + "-fx-cursor: hand;" + "-fx-background-radius: 15px;");
        github.setPadding(new Insets(10, 20, 10, 20));
        discord.setFocusTraversable(false);
        youtube.setFocusTraversable(false);
        github.setFocusTraversable(false);

        hover(youtube);
        hover(discord);
        hover(github);


        HBox rightButtons = new HBox(10, discord, youtube, github);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        Separator sep = new Separator();

        bar.setLeft(name);
        bar.setRight(rightButtons);

        StackPane.setAlignment(title, Pos.CENTER);

        layout.getChildren().addAll(bar, sep);

        getChildren().addAll(layout, title);
    }

    private void hover(Button btn1) {
        btn1.setOnMouseEntered( e-> {
            btn1.setOpacity(0.5);
        });
        btn1.setOnMouseExited(e -> {
            btn1.setOpacity(1);
        });
    }
}



