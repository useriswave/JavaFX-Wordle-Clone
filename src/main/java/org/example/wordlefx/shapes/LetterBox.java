package org.example.wordlefx.shapes;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import org.example.wordlefx.colors.CustomColor;

public class LetterBox extends StackPane{

    private final Rectangle rectangle;
    private final Label letter;

    public LetterBox() {
        rectangle = new Rectangle();
        letter = new Label();
//        rectangle.setFill(CustomColor.WHITE);
        rectangle.setStroke(CustomColor.DEFAULT_GRAY);
        rectangle.setWidth(100);
        rectangle.setHeight(100);


        rectangle.setId("rectangle");
        letter.setId("letter");


        this.getChildren().addAll(rectangle, letter);
    }

    public Label getLetter() {
        return letter;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
