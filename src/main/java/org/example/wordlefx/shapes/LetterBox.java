package org.example.wordlefx.shapes;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.wordlefx.colors.CustomColor;

public class LetterBox extends StackPane{

    private final Rectangle rectangle;
    private final Label letter;

    public LetterBox() {
        rectangle = new Rectangle();
        letter = new Label();
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
    public void changeLbAppearance(String text, Color fillColor, Color strokeColor) {
        changeText(text);
        changeRectFill(fillColor);
        changeRectStroke(strokeColor);
    }

    public void changeLbAppearance(Color rectangleFillColor, Color strokeColor, Color letterFillColor) {
        changeRectFill(rectangleFillColor);
        changeRectStroke(strokeColor);
        changeLetterFill(letterFillColor);
    }

    public void changeLbAppearance(Color rectangleFillColor, Color strokeColor) {
        changeRectFill(rectangleFillColor);
        changeRectStroke(strokeColor);
    }

    public void changeText(String text) {
        letter.setText(text);
    }

    public void changeRectFill(Color color) {
        rectangle.setFill(color);
    }

    public void changeRectStroke(Color color) {
        rectangle.setStroke(color);
    }

    public void resetLbAppearance() {
        letter.setText("");
        changeLbAppearance(Color.WHITE, CustomColor.DEFAULT_GRAY, Color.BLACK);
    }

    public void changeLetterFill(Color fillColor) {
        letter.setTextFill(fillColor);
    }
}
