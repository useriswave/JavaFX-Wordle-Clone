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

    private int currentRow = 0;
    private int currentColumn = 0;
    private final LetterBox[][] gridList;
    private String answer = "";
    private final Word word;
    private String generatedWord;
    private boolean procceed;
    private GameState gameState;
    boolean won = false;
    private static int attempts = 1;

    public CenterPane() throws FileNotFoundException {

        word = new Word();
        generatedWord = word.getWord();
        gameState = GameState.PLAY;
        System.out.println("WORD: " + word.getWord());
        int COLUMNS = 5;
        int ROWS = 6;
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

            if(containsLetter && currentColumn <= 4) {
                updateIU(gridList, currentRow, currentColumn, eventCode);
                answer += eventCode.toString();
                currentColumn++;
                System.out.println("ANSWER STRING : " + answer + " COLUMN: " + currentColumn);

            }

            else if(eventCode == KeyCode.ENTER && currentColumn == 5) {
                enterKeyHandler(stage);
            }

            else if(eventCode == KeyCode.BACK_SPACE) {
                if(currentColumn == 0)
                    return;
                currentColumn--;
                answer = answer.substring(0, answer.length() - 1);
                backSpaceHandler(currentRow, currentColumn, gridList);
                System.out.println(answer);
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
                    gridList[currentRow][i].getRectangle().setFill(CustomColor.CUSTOM_GREEN);
                    gridList[currentRow][i].getRectangle().setStroke(CustomColor.CUSTOM_GREEN);
                    gridList[currentRow][i].getLetter().setStyle("-fx-text-fill: white;");
                } else if (letterStatesList[i] == LetterState.PRESENT) {
                    System.out.println("PRESENT!");
                    gridList[currentRow][i].getRectangle().setFill(CustomColor.CUSTOM_YELLOW);
                    gridList[currentRow][i].getRectangle().setStroke(CustomColor.CUSTOM_YELLOW);
                    gridList[currentRow][i].getLetter().setStyle("-fx-text-fill: white;");
                } else {
                    System.out.println("WRONG!");
                    gridList[currentRow][i].getRectangle().setFill(CustomColor.INCORRECT_GRAY);
                    gridList[currentRow][i].getRectangle().setStroke(CustomColor.INCORRECT_GRAY);
                    gridList[currentRow][i].getLetter().setStyle("-fx-text-fill: white;");

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

    public void enterKeyHandler(Stage stage) {
//               check if we are at the end of column. If so,then check answer then move on to the next row
        try {
            GuessOutcome outcome = checkCorrect(currentRow, stage);



            if(outcome == GuessOutcome.WIN) {
                won = true;
                System.out.println("YOU WON" + "BOOLEAN WON: " + won);


                System.out.println("Game State: " + gameState);
            }
            else if(outcome == GuessOutcome.INVALID_WORD) {
                procceed = false;
            }
            else {
                if(currentColumn == 5 && currentRow == 5) {
                    won = false;
                    System.out.println("WIN BOOLEAN: " + won);
                }


                System.out.println("USER DIDNT WIN YET.");
                procceed = true;
            }

            if(won || (currentColumn == 5 && currentRow == 5)) {
                var winPopUp = new ResultPopUp();
                gameState = winPopUp.showPopUp(stage, generatedWord, gameState, won, attempts);

                if(gameState == GameState.PLAY) {
                    restart();
                }
                else {
                    stage.close();
                }
            }


            if(procceed) {
                attempts++;
                currentRow++;
                currentColumn = 0;
                answer = "";
                System.out.println("CURRENT ROW: " + currentRow);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void backSpaceHandler(int currentRow, int currentColumn, LetterBox[][] gridList) {
        gridList[currentRow][currentColumn].getLetter().setText("");
        gridList[currentRow][currentColumn].getRectangle().setStroke(CustomColor.DEFAULT_GRAY);
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
        procceed = false;
        won = false;
        answer = "";
        System.out.println(generatedWord);

        for(int i = 0; i < gridList.length; i++) {
            for (int j = 0; j < gridList[i].length; j++) {
                gridList[i][j].getLetter().setText("");
                gridList[i][j].getRectangle().setStroke(CustomColor.DEFAULT_GRAY);
                gridList[i][j].getRectangle().setFill(Color.WHITE);
            }
        }

    }
}
