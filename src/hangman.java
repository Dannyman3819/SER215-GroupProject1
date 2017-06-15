import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JOptionPane;

/**
 * Created by Daniel on 6/14/2017.
 */
public class hangman {
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
		}*/
		
		int playAgain = 0;
		hangmanGUI gui = new hangmanGUI();
		
		do{
			//int newWordGen = wordGen.nextInt(dictionary.length), won, wrong = 0;
			String word = dictionaryPool.getWordFromFile();

			String[] difficulty = {"BEGINNER", "INTERMEDIATE", "EXPERT"};
			String asterisk = "";
			int maxMisses;
			for(int i = 0; i < word.length(); i++)
				asterisk += '*';

			//hangmanGUI gui = new hangmanGUI();

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
			else if (choice == 2) {
				System.out.println("\nInitiating Hangman\nDifficulty: HARD");
				maxMisses = asterisk.length();
				gui.setDifficulty("HARD");
			}
			else{
				System.out.println("\nMaybe next time then.\nBye!");
				break;
			}
			System.out.println("choice = " + choice);
			
			gui.initGUI();
			gui.setWord(asterisk);
			gui.setGuessRemain(String.valueOf(maxMisses));

			int won = 1, wrong = 0;
			int guessPerBodyPart = ((maxMisses) / 6);
			// GAME PLAY
			while((maxMisses > wrong) && (won > 0)) {
				Thread.sleep(100);              // Will cause weird issues if some kind of delay isn't used
				
				/* 
				 * TODO COMPLETED - HANDLES THE CLOSING OF THE GAME WINDOW BY PRESSING THE X BUTTON
				 */
				gui.f.addWindowListener(new WindowListener(){
					public void windowClosing(WindowEvent we) {
						System.out.println("Closing....");
					    System.exit(0);
					  }
					@Override
					public void windowOpened(WindowEvent e) {}
					@Override
					public void windowClosed(WindowEvent e) {}
					@Override
					public void windowIconified(WindowEvent e) {}
					@Override
					public void windowDeiconified(WindowEvent e) {}
					@Override
					public void windowActivated(WindowEvent e) {}
					@Override
					public void windowDeactivated(WindowEvent e) {}
				});
				
				
				
				if (gui.isActionPerformed()) {
					// getEventValue returns a string of what the user guessed
					String string = gui.getEventValue();
					System.out.println("\nKey pressed: " + string);
					//gui.drawNextBodyPart();
					//gui.updateWord("_ _ C _");

					won = word.length();
					boolean missed = true;
					//System.out.print("\nGuess a letter: ");
					//guess = keyboard.next();
					
					/* 
					 * TODO COMPLETED - IMPLEMENTATION TO SUPPORT FULL WORD GUESSES
					 */
					System.out.println("string.length() = " + string.length());
					System.out.println("string = word is " 
							+ (string.compareToIgnoreCase(word) == 0));
					System.out.println("IF condition = " 
							+ ((string.length()> 1) && (string.compareToIgnoreCase(word) == 0)));
					if((string.length()> 1) && (string.compareToIgnoreCase(word) == 0)){
						won = 0;
						System.out.println("inside WHILE condition to cycle again is "
								+ ((maxMisses > wrong) && (won > 0)));
					}
					else{
						if(string.length() == 1){
							char guess = string.charAt(0);
							//maxMisses[wrong] = guess.charAt(0);

							//System.out.println(word);
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
							System.out.println();
							System.out.println("inside WHILE condition to cycle again is "
									+ ((maxMisses > wrong) || (won > 0)));
						}
					}
				}
			}
			
			if (won == 0) {
				//answer = JOptionPane.showConfirmDialog(null, "YOU ACTUALLY WON.\nWould you like to play again?","AGAIN?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				System.out.println("You Won");
				String [] options = {"Yes", "No"};
				playAgain = gui.promptUser("You Won!!!\nWould you like to play again??", options);
				/* 
				 * TODO COMPLETED - IMPLEMENTATION TO GET YES/NO ANSWER FROM USER
				 */
			} else if (maxMisses - wrong == 0) {
				//answer = JOptionPane.showConfirmDialog(null, "YOU LOST.\nWould you like to play again?","AGAIN?",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
				System.out.println("You Lost");
				String [] options = {"Yes", "No"};
				playAgain = gui.promptUser("You Lost!!!\nWould you like to play again??", options);
				/* 
				 * TODO COMPLETED - IMPLEMENTATION TO GET YES/NO ANSWER FROM USER
				 */
			}
			
			System.out.println("playAgain = " + playAgain);
			System.out.println("JOptionPane.YES_OPTION = " + JOptionPane.YES_OPTION);
			
		}while(playAgain == JOptionPane.YES_OPTION);
		
		System.out.println("THE END");
		System.exit(0);
	}

	protected static String getBlanks(String word){
		String tempStr = "";
		for(int i=0; i<=word.length(); i++){
			tempStr += " _ ";
		}
		return tempStr;
	}
}
