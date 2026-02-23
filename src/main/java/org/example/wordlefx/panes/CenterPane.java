package org.example.wordlefx.panes;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.wordlefx.colors.CustomColor;
import org.example.wordlefx.enums.AllowedKey;
import org.example.wordlefx.enums.GameState;
import org.example.wordlefx.enums.GuessOutcome;
import org.example.wordlefx.enums.LetterState;
import org.example.wordlefx.external.Word;
import org.example.wordlefx.logic.Algorithm;
import org.example.wordlefx.popups.ResultPopUp;
import org.example.wordlefx.popups.WordNotExist;
import org.example.wordlefx.shapes.LetterBox;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class CenterPane extends GridPane {

    private final int COLUMNS = 5;
    private final int ROWS = 6;
    private int currentRow = 0;
    private int currentColumn = 0;
    private final LetterBox[][] gridList;
    private String answer = "";
    private String generatedWord;
    private boolean proceed;
    private GameState gameState;
    boolean won = false;
    private int attempts = 1;

    public CenterPane() throws FileNotFoundException {

        Word word = new Word();
        generatedWord = word.getWord();
        gameState = GameState.PLAY;
        System.out.println("WORD: " + word.getWord());

        gridList = new LetterBox[ROWS][COLUMNS];
        for(int i = 0; i < ROWS; i++) {
            for(int j = 0; j < COLUMNS; j++) {
                LetterBox box = new LetterBox();
                gridList[i][j] = box;
                this.add(box, j, i);
            }
        }
        this.setHgap(10);
        this.setVgap(10);
        this.setAlignment(Pos.CENTER);
    }
    public void registerKey(Stage stage, Scene scene) {
        scene.setOnKeyPressed(event -> {

            if(gameState == GameState.QUIT) {
                return;
            }

            KeyCode eventCode = event.getCode();
            boolean containsLetter = AllowedKey.isLetter(eventCode);

            if(containsLetter && currentColumn <= (COLUMNS - 1)) {
                updateIU(gridList, currentRow, currentColumn, eventCode);
                answer += eventCode.toString();
                currentColumn++;
                System.out.println("Answer: " + answer);
            }

            else if(eventCode == KeyCode.ENTER && currentColumn == COLUMNS) {
                try {
                    GuessOutcome outcome = checkCorrect(currentRow, stage);
                    enterKeyHandler(stage, outcome);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }

            else if(eventCode == KeyCode.BACK_SPACE) {
                if(currentColumn == 0)
                    return;
                currentColumn--;
                answer = answer.substring(0, answer.length() - 1);
                backSpaceHandler(currentRow, currentColumn, gridList);
                System.out.println("Answer: " + answer);
//               set current letterbox label to an empty string & go back to the previous letterbox
            }
        });

    }

    public GuessOutcome checkCorrect(int currentRow, Stage stage) throws FileNotFoundException {

        var letterStatesList = Algorithm.checkAnswerType(generatedWord, answer, gridList, currentRow);

        if(letterStatesList[0] == LetterState.INVALID_WORD) {
            new WordNotExist(stage).showAndWait();
            System.out.println("ANSWER DOES NOT EXIST IN WORD BANK");
            return GuessOutcome.INVALID_WORD;
        }


        else {
            System.out.println(Arrays.toString(letterStatesList));
            for (int i = 0; i < letterStatesList.length; i++) {
                if (letterStatesList[i] == LetterState.CORRECT) {
                    System.out.println("CORRECT!");
                    gridList[currentRow][i].changeLbAppearance(CustomColor.CUSTOM_GREEN, CustomColor.CUSTOM_GREEN, Color.WHITE);
                } else if (letterStatesList[i] == LetterState.PRESENT) {
                    System.out.println("PRESENT!");

                    gridList[currentRow][i].changeLbAppearance(CustomColor.CUSTOM_YELLOW, CustomColor.CUSTOM_YELLOW, Color.WHITE);
                } else {
                    System.out.println("WRONG!");
                    gridList[currentRow][i].changeLbAppearance(CustomColor.INCORRECT_GRAY, CustomColor.INCORRECT_GRAY, Color.WHITE);
                }
            }

        }

        if(checkWinState(letterStatesList)) {
            return GuessOutcome.WIN;
        }

        return GuessOutcome.CONTINUE_GAME;
    }

    public void updateIU(LetterBox[][] gridList, int currentRow, int currentColumn, KeyCode eventCode) {
        // updates entered letter and makes stroke black ( DOES NOT UPDATE BASED OFF ALGORITHM )
        String letter = eventCode.toString();
        gridList[currentRow][currentColumn].getLetter().setText(letter);
        gridList[currentRow][currentColumn].getLetter().setStyle("");
        gridList[currentRow][currentColumn].getRectangle().setStroke(CustomColor.FILLED_GRAY);
    }

    public void enterKeyHandler(Stage stage, GuessOutcome outcome) throws FileNotFoundException {
//        check if we are at the end of column. If so,then check answer then move on to the next rowGuessOutcome outcome = checkCorrect(currentRow, stage);
        if(outcome == GuessOutcome.WIN) {
            won = true;
            System.out.println("YOU WON" + "BOOLEAN WON: " + won);


            System.out.println("Game State: " + gameState);
        }
        else if(outcome == GuessOutcome.INVALID_WORD) {
            proceed = false;
        }
        else {
            if(currentColumn == COLUMNS && currentRow == (ROWS - 1)) {
                won = false;
                System.out.println("WIN BOOLEAN: " + won);
            }


            System.out.println("USER DIDNT WIN YET.");
            proceed = true;
        }

        if(won || (currentColumn == COLUMNS && currentRow == (ROWS - 1))) {
            var winPopUp = new ResultPopUp();
            gameState = winPopUp.showPopUp(stage, generatedWord, gameState, won, attempts);

            if(gameState == GameState.PLAY) {
                restart();
            }
            else {
                stage.close();
            }
        }

        if(proceed) {
            attempts++;
            currentRow++;
            currentColumn = 0;
            answer = "";
            System.out.println("CURRENT ROW: " + currentRow);
        }
    }

    public void backSpaceHandler(int currentRow, int currentColumn, LetterBox[][] gridList) {
        gridList[currentRow][currentColumn].resetLbAppearance();
    }

    public boolean checkWinState(LetterState[] letterStatesList) {
        for(LetterState state : letterStatesList) {
            if(state != LetterState.CORRECT) {
                return false;
            }
        }
        return true;
    }

    public void restart() throws FileNotFoundException {
        attempts = 1;
        currentRow = 0;
        currentColumn = 0;
        generatedWord = Word.generateRandomWord();
        proceed = false;
        won = false;
        answer = "";
        System.out.println("Word: " + generatedWord);

        for (LetterBox[] letterBoxes : gridList) {
            for (LetterBox letterBox : letterBoxes) {
                letterBox.resetLbAppearance();
            }
        }

    }
}