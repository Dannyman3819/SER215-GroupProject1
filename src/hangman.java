import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Daniel on 6/14/2017.
 */
public class hangman {
	/*public static void main(String[] args) throws  InterruptedException, IOException {
		// Load GUI object with word to display
		HangDictionary dictionaryPool = new HangDictionary();
		hangmanGUI gui = new hangmanGUI("_ _ _ _ _ _ _ _ _ _", "HARD", "5");

		String[] options = {"EASY", "INTERMEDIATE", "HARD"};
		int difficulty = gui.promptUser("Please choose a difficulty", options);
		System.out.println(difficulty);

		// builds the shows GUI
		gui.initGUI();
		gui.updateWord(getBlanks(dictionaryPool.getWordFromFile()));

		while(true) {
			Thread.sleep(100);              // Will cause weird issues if some kind of delay isn't used
			if(gui.isActionPerformed()) {
				// getEventValue returns a string of what the user guessed
				String string = gui.getEventValue();
				System.out.println("Key pressed:" + string);
				gui.drawNextBodyPart();
				gui.updateWord("_ _ C _");
			}
		}
	}*/

	public static void main (String[] args) throws  InterruptedException, IOException {
		HangDictionary dictionaryPool = new HangDictionary();

		System.out.println("Program Booting...");

		// prompt to continue with pre-game procedures
		/*start = JOptionPane.showConfirmDialog(null,"A random word will be generated and the goal of the game is "
				+ "for you to try and guess all of the letters in that word.\n"
				+ "Do you wish to proceed?","GAME PLAY CONFIRMATION",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);

		if (start == answer)
			System.out.println("Enter Difficulty Pane...");
		else{
			System.out.println("Hangman Exiting...");
			answer = JOptionPane.NO_OPTION;
		}
*/

		// confirmed to play
		while (true){
			//int newWordGen = wordGen.nextInt(dictionary.length), won, wrong = 0;
			String word = dictionaryPool.getWordFromFile();

			String[] difficulty = {"BEGINNER", "INTERMEDIATE", "EXPERT"};
			String asterisk = "";
			int maxMisses;
			for(int i = 0; i < word.length(); i++)
				asterisk += '*';

			hangmanGUI gui = new hangmanGUI();

			// choose level
			int choice = gui.promptUser("Please Choose a difficulty", difficulty);
			if (choice == 0) {
				System.out.println("\nInitiating Hangman\nDifficulty: BEGINNER");
				maxMisses = asterisk.length() * 2;
				gui.setDifficulty("BEGINNER");
			}
			else if (choice == 1) {
				System.out.println("\nInitiating Hangman\nDifficulty: INTERMEDIATE");
				maxMisses = asterisk.length() + 5;
				gui.setDifficulty("INTERMEDIATE");
			}
			else{
				System.out.println("\nInitiating Hangman\nDifficulty: HARD");
				maxMisses = asterisk.length();
				gui.setDifficulty("HARD");
			}

			gui.initGUI();
			gui.setWord(asterisk);
			gui.setGuessRemain(String.valueOf(maxMisses));

			int won=1;
			int wrong = 0;
			int guessPerBodyPart = ((maxMisses) / 6);

			// GAME PLAY
			while(true) {
				Thread.sleep(100);              // Will cause weird issues if some kind of delay isn't used
				if (gui.isActionPerformed()) {
					// getEventValue returns a string of what the user guessed
					String string = gui.getEventValue();
					System.out.println("Key pressed:" + string);
					//gui.drawNextBodyPart();
					//gui.updateWord("_ _ C _");

					won = word.length();
					boolean missed = true;
					//System.out.print("\nGuess a letter: ");
					//guess = keyboard.next();
					char guess = string.charAt(0);
					//maxMisses[wrong] = guess.charAt(0);

					System.out.println(word);
					// places asterisks in place of the letters not guessed correctly, yet
					for (int i = 0; i < word.length(); i++) {
						char wordChar = word.charAt(i);
						wordChar = Character.toUpperCase(wordChar);
						guess = Character.toUpperCase(guess);
						if (wordChar == guess) {
							//asterisk = asterisk.replaceAll(guess, )
							asterisk = asterisk.substring(0, i) + word.charAt(i) + asterisk.substring(i + 1);
							//maxMisses[wrong] = '\u0000';
							missed = false;
						}
					}

					if (missed == false) {
						gui.setWord(asterisk);
						System.out.println("Letter " + guess + " IS in the word.");
					} else {
						System.out.println("Letter " + guess + " is NOT in the word.");
						wrong++;
						// TODO Adjust formula here
						int temp = (wrong % (guessPerBodyPart+1));
						if(temp == guessPerBodyPart){
							gui.drawNextBodyPart();
						}

					}

					// display for updated word and remaining incorrect guesses
					System.out.println("Incorrect guesses remaining: " + (maxMisses - wrong));
					gui.setGuessRemain(String.valueOf(maxMisses - wrong));
					System.out.print("HANGMAN WORD: ");
					for (int i = 0; i < asterisk.length(); i++) {
						System.out.print(asterisk.charAt(i));
						if (asterisk.charAt(i) != '*')
							won--;
					}
				}

				if (won == 0) {
					//answer = JOptionPane.showConfirmDialog(null, "YOU ACTUALLY WON.\nWould you like to play again?","AGAIN?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					System.out.println("You Won");
					String [] options = {"Yes", "No"};
					gui.promptUser("You Won!!!\nWould you like to play again??", options);
					break;
				} else if (maxMisses - wrong == 0) {
					//answer = JOptionPane.showConfirmDialog(null, "YOU LOST.\nWould you like to play again?","AGAIN?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
					System.out.println("You Lost");
					String [] options = {"Yes", "No"};
					gui.promptUser("You Lost!!!\nWould you like to play again??", options);
					break;
				}
			}
		}
	}

	protected static String getBlanks(String word){
		String tempStr = "";
		for(int i=0; i<=word.length(); i++){
			tempStr += " _ ";
		}
		return tempStr;
	}
}
