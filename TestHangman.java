/**
 * Problem Statement:
 * This program generate a random word and prompt the user to play a game of hangman by asking them
 * to enter letters to try to guess what the word is. When the user fills in all of the letters the
 * program will display how many misses they had, and it will ask if they want to play again.
 * If yes the program will generate a new word and the process will restart.
 *
 * @author: Alec Castaneda, Marco Rosa
 * @version: 1.1
 */

import java.util.Scanner;
import java.util.Random;
import javax.swing.JOptionPane;

public class TestHangman
{
    private static Random wordGen = new Random();
    private static String[] dictionary = {"hello","code","program","silicon valley","mr. robot","unity","intellij","binary","hexadecimal"};
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        int start, answer = JOptionPane.YES_OPTION;

        System.out.println("Program Booting...");

        // prompt to continue with pre-game procedures
        start = JOptionPane.showConfirmDialog(null,"A random word will be generated and the goal of the game is "
                + "for you to try and guess all of the letters in that word.\n"
                + "Do you wish to proceed?","GAME PLAY CONFIRMATION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

        if (start == answer)
            System.out.println("Enter Difficulty Pane...");
        else{
            System.out.println("Hangman Exiting...");
            answer = JOptionPane.NO_OPTION;
        }

        // confirmed to play
        while (start != JOptionPane.NO_OPTION && answer == JOptionPane.YES_OPTION){
            int newWordGen = wordGen.nextInt(dictionary.length), won, wrong = 0;;

            String[] difficulty = {"BEGINNER", "INTERMEDIATE", "EXPERT"};
            char[] asterisk = new char[dictionary[newWordGen].length()], maxMisses;
            for(int i = 0; i < asterisk.length; i++)
                asterisk[i] = '*';

            // choose level
            String choice = (String)JOptionPane.showInputDialog(null,"Please Choose a difficulty: ","DIFFICULTY",JOptionPane.PLAIN_MESSAGE,null,difficulty,difficulty[0]);
            if (choice == difficulty[0]) {
                System.out.println("\nInitiating Hangman\nDifficulty: BEGINNER");
                maxMisses = new char[asterisk.length * 2];
            }
            else if (choice == difficulty[1]) {
                System.out.println("\nInitiating Hangman\nDifficulty: INTERMEDIATE");
                maxMisses = new char[asterisk.length + 5];
            }
            else{
                System.out.println("\nInitiating Hangman\nDifficulty: HARD");
                maxMisses = new char[asterisk.length];
            }

            // GAME PLAY
            String guess;
            do {
                won = dictionary[newWordGen].length();
                boolean missed = true;
                System.out.print("\nGuess a letter: ");
                guess = keyboard.next();
                maxMisses[wrong] = guess.charAt(0);

                // places asterisks in place of the letters not guessed correctly, yet
                for(int i = 0; i < dictionary[newWordGen].length(); i++) {
                    if (dictionary[newWordGen].charAt(i) == maxMisses[wrong]) {
                        asterisk[i] = dictionary[newWordGen].charAt(i);
                        maxMisses[wrong] = '\u0000';
                        missed = false;
                    }
                }
                if(missed == false)
                    System.out.println("Letter " + guess + " IS in the word.");
                else {
                    System.out.println("Letter " + guess + " is NOT in the word.");
                    wrong++;
                }

                // display for updated word and remaining incorrect guesses
                System.out.println("Incorrect guesses remaining: " + (maxMisses.length - wrong));
                System.out.print("HANGMAN WORD: ");
                for(char letter : asterisk) {
                    System.out.print(letter);
                    if(letter != '*')
                        won--;
                }
            }while (won > 0 && wrong < maxMisses.length);

            if(won == 0)
                answer = JOptionPane.showConfirmDialog(null, "YOU ACTUALLY WON.\nWould you like to play again?","AGAIN?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            else
                answer = JOptionPane.showConfirmDialog(null, "YOU LOST.\nWould you like to play again?","AGAIN?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
        }
    }
}