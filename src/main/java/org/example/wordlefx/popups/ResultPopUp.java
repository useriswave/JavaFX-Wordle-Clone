package org.example.wordlefx.popups;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.wordlefx.colors.CustomColor;
import org.example.wordlefx.enums.GameState;
import org.example.wordlefx.managers.GameManager;

import java.util.Objects;

public class ResultPopUp extends Stage {

    private Image icon;
    private ImageView iconViewer;
    private Label state;
    private Label description;
    private Label wordTitle;
    private Label word;
    private Button restartButton;
    private Button quitButton;
    private VBox root;
    private VBox wordBox;
    private Scene scene;
    private GameState returnState;

    public ResultPopUp() {

    }

    public GameState showPopUp(Stage mainStage, String generatedWord, GameState gameState, boolean won, int attempts) {
        iconViewer = new ImageView();
        state = new Label();
        description = new Label();
        restartButton = new Button("Restart");
        quitButton = new Button("Quit");
        wordTitle = new Label("THE WORD WAS");
        word = new Label(generatedWord);

        wordBox = new VBox(wordTitle, word);
        wordBox.setStyle("-fx-background-color: #EBEBEB;");
        wordBox.setAlignment(Pos.CENTER);


        if(won) {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/wordlefx/images/confetti.png")));
            iconViewer.setImage(icon);
            iconViewer.setFitWidth(200);
            state.setText("You Won!");
            state.setTextFill(CustomColor.CUSTOM_GREEN);
            restartButton.getStyleClass().add("correct");
            description.setText("Congratulations! You guessed the word in " +  Integer.toString(attempts) + " tries!");
        }
        else {
            icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/org/example/wordlefx/images/incorrect.png")));
            iconViewer.setImage(icon);
            iconViewer.setFitWidth(100);
            state.setText("You Lost!");
            state.setTextFill(Color.RED);
            restartButton.getStyleClass().add("incorrect");
            description.setText("Don't worry, you'll get it next time.");
        }


        restartButton.setOnAction(event -> {
            returnState = GameManager.restartGame(gameState);
            close();
        });

        quitButton.setOnAction(event -> {
            returnState = GameManager.quitGame(gameState);
            close();
        });

        root = new VBox(iconViewer, state, description, wordBox, restartButton, quitButton);
        root.setAlignment(Pos.CENTER);

        restartButton.setPrefSize(242, 39);
        quitButton.setPrefSize(242, 39);
        wordBox.setBackground(new Background(new BackgroundFill(CustomColor.DEFAULT_GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        wordBox.setPrefSize(340, 85);
        wordBox.setSpacing(20);
        root.setSpacing(10);

        iconViewer.setId("icon-viewer");
        iconViewer.setPreserveRatio(true);
        iconViewer.setSmooth(true);
        state.setId("state");
        description.setId("description");
        wordTitle.setId("word-title");
        word.setId("word");
        restartButton.setId("restart-button");
        quitButton.setId("quit-button");
        root.setId("root-vbox");
        wordBox.setId("word-box");

        hover(restartButton);
        hover(quitButton);


        scene = new Scene(root, 400, 550);
        String resultPopUpCss = Objects.requireNonNull(this.getClass().getResource("/org/example/wordlefx/styling/result-pop-up.css")).toExternalForm();
        scene.getStylesheets().add(resultPopUpCss);
        this.setScene(scene);

        initOwner(mainStage);    // informing it the main stage. (game stage)
        initModality(Modality.APPLICATION_MODAL); // the lock: blocks interaction with the App.
        setAlwaysOnTop(true);
        setResizable(false);
        setOnCloseRequest(e -> e.consume());

        showAndWait();
        return returnState;
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