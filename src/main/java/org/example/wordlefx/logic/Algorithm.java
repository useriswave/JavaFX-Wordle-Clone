package org.example.wordlefx.logic;

import org.example.wordlefx.enums.LetterState;
import org.example.wordlefx.external.Word;
import org.example.wordlefx.shapes.LetterBox;

import java.io.FileNotFoundException;
import java.util.HashMap;

public class Algorithm {
    private static HashMap<Character, Integer> letterMap;

    public static LetterState[] checkAnswerType(String word, String answer, LetterBox[][] grid, int currentRow) throws FileNotFoundException {
        LetterState[] letterStateList = new LetterState[5];
        answer = answer.toUpperCase();
        word = word.toUpperCase();
        letterMap = Word.addLettersToList(word);
        boolean checkWord = Word.checkWordExists(answer);

        if(!checkWord) {
            letterStateList[0] = LetterState.INVALID_WORD;
            return letterStateList;
        }
        for(int i = 0; i < answer.length(); i++) {
            if(answer.charAt(i) == word.charAt(i)) {
                letterStateList[i] = LetterState.CORRECT;
                letterMap.replace(word.charAt(i), letterMap.get(word.charAt(i)) - 1);
            }
        }

        for(int i = 0; i < answer.length(); i++) {
            if(letterStateList[i] != LetterState.CORRECT) {
                // handle yellow

                if(letterMap.getOrDefault(answer.charAt(i), 0) > 0) {
                    letterStateList[i] = LetterState.PRESENT;
                    letterMap.replace(answer.charAt(i), letterMap.get(answer.charAt(i)) - 1);
                }
                else {
                    letterStateList[i] = LetterState.ABSENT;
                }

            }
        }
        // if none
        return letterStateList;
    }
}
