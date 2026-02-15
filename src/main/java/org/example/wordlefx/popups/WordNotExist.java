package org.example.wordlefx.popups;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class WordNotExist extends Alert {
    public WordNotExist(Stage mainStage) {
        super(AlertType.ERROR);
        this.initOwner(mainStage);
        this.setTitle("Word Error");
        this.setHeaderText("Word does not exist!");
        this.setContentText("Please enter a valid word.");
    }
}
