package org.example.wordlefx.external;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Word {

    private static String path = "C:/Users/user/IdeaProjects/WordleFX/src/main/resources/org/example/wordlefx//files/words.txt";
    private static File wordsFile = new File(path);
    private static Random random;
    private static long randomIndex;
    private String word;
    public Word() throws FileNotFoundException {this.word = generateRandomWord();}

    public static boolean checkWords(String answer) throws FileNotFoundException {
        String word;
        Scanner sc = new Scanner(wordsFile);
        while(sc.hasNextLine()) {
            word = sc.nextLine();
            if(answer.equalsIgnoreCase(word)) {
                System.out.println("Word correct!");
                sc.close();
                return true;
            }
        }
        sc.close();
        return false;
    }

    public static String generateRandomWord() throws FileNotFoundException {
        int length = getFileContentSize();
        random = new Random();
        randomIndex = random.nextLong(0, length);
        String randomWord =  getRandomWord(randomIndex);

        if(randomWord == null) {
            System.out.println("Word is null");
        }
        return randomWord;

    }

    private static String getRandomWord(long randomIndex) throws FileNotFoundException {
        Scanner sc = new Scanner(wordsFile);
        String randomWord;
        int index = 0;
        while(sc.hasNextLine()) {
            if(index == randomIndex) {
                randomWord = sc.nextLine();
                sc.close();
                return randomWord;
            }
            randomWord = sc.nextLine();
            index++;
        }
        sc.close();
        return null;
    }

    private static int getFileContentSize() throws FileNotFoundException {
        Scanner sc = new Scanner(wordsFile);
        int length = 0;
        while(sc.hasNextLine()) {
            sc.nextLine();
            length++;
        }
        sc.close();
        return length;
    }

    public static boolean checkWordExists(String answer) throws FileNotFoundException{
        Scanner sc = new Scanner(wordsFile);
        String word;
        while(sc.hasNextLine()) {
            word = sc.nextLine();
            if(answer.equalsIgnoreCase(word)) {
                System.out.println("WORD EXISTS: " + word);
                return true;
            }
        }
        return false;
    }

    public static HashMap<Character, Integer> addLettersToList(String word) {
        word = word.toUpperCase();
        var wordLetters = new HashMap<Character, Integer>();
        for(int i = 0; i < word.length(); i++) {

            if(!wordLetters.containsKey(word.charAt(i))) {
                wordLetters.put(word.charAt(i), 1);
            }
            else {
                wordLetters.put(word.charAt(i),  wordLetters.get(word.charAt(i)) + 1);
            }
        }
        return wordLetters;
    }

    public String getWord() {
        return word;
    }


}
