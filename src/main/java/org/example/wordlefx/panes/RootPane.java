package org.example.wordlefx.panes;

import javafx.scene.layout.BorderPane;

import java.io.FileNotFoundException;

public class RootPane extends BorderPane {

    private final CenterPane centerPane;

    public RootPane() throws FileNotFoundException {
        centerPane = new CenterPane();
        TopPane topPane = new TopPane();
        this.setCenter(centerPane);
        this.setTop(topPane);
    }

    public CenterPane getCenterPane() {
        return centerPane;
    }
}
