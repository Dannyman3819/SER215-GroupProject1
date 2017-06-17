
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
		int playAgain = 0;
		
		System.out.println("Program Booting...");
		
		do{
			hangmanGUI gui = new hangmanGUI();
			String word = dictionaryPool.getWordFromFile();
			String[] difficulty = {"BEGINNER", "INTERMEDIATE", "EXPERT"};
			String currentDifficulty = "BEGINNER";
			String asterisk = "";
			int maxMisses;
			
			for(int i = 0; i < word.length(); i++)
				asterisk += '*';

			//choose level
			int choice = gui.promptUser("Please Choose a difficulty", difficulty);
			
			//a switch statement would not have worked here, because the break in the else
			//statement needs to exit the do-loop instead of just the switch :)
			if (choice == 0) {
				//maxMisses = asterisk.length() * 2;
				maxMisses = 16;
				gui.setDifficulty("BEGINNER");
				currentDifficulty = "BEGINNER";
			}
			else if (choice == 1) {
				//maxMisses = asterisk.length() + (int)(asterisk.length()/2);
				maxMisses = 12;
				gui.setDifficulty("INTERMEDIATE");
				currentDifficulty = "INTERMEDIATE";
			}
			else if (choice == 2) {
				//maxMisses = asterisk.length();
				maxMisses = 6;
				gui.setDifficulty("HARD");
				currentDifficulty = "HARD";
			}
			else
				break;
			
			gui.initGUI();
			gui.setWord(asterisk);
			gui.setGuessRemain(String.valueOf(maxMisses));

			int won = 1, wrong = 0;
			//Double guessPerBodyPart = Math.ceil(((maxMisses) / 6.0));
			
			// GAME PLAY
			while((maxMisses > wrong) && (won > 0)) {
				//will cause weird issues if some kind of delay isn't used
				Thread.sleep(100);

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
					String string = gui.getEventValue();
					//gui.drawNextBodyPart();

					won = word.length();
					boolean missed = true;
					//guess = keyboard.next();
					
					if((string.length()> 1) && (string.compareToIgnoreCase(word) == 0))
						won = 0;
					else{
						if(string.length() == 1){
							char guess = string.charAt(0);
							//maxMisses[wrong] = guess.charAt(0);

							//places asterisks in place of the letters not guessed correctly, yet
							for (int i = 0; i < word.length(); i++) {
								char wordChar = word.charAt(i);
								wordChar = Character.toUpperCase(wordChar);
								guess = Character.toUpperCase(guess);
								if (wordChar == guess) {
									asterisk = asterisk.substring(0, i) + word.charAt(i) + asterisk.substring(i + 1);
									missed = false;
								}
							}

							if (missed == false)
								gui.setWord(asterisk);
							else {
								wrong++;
								
								// TODO Adjust formula here
								/*
								int temp = (wrong % (guessPerBodyPart+1));
								if(temp == guessPerBodyPart){
									gui.drawNextBodyPart();
								}*/

								/*if(currentDifficulty == "BEGINNER"){

								} else if (currentDifficulty == "INTERMEDIATE") {

								} else if(currentDifficulty == "HARD") {

								}*/

								/*if(((wrong % guessPerBodyPart) == 0)
										&& (wrong != 0)) {
									gui.drawNextBodyPart();
								}*/
								gui.drawNextBodyPart();
							}

							//update GUI with updated word and remaining incorrect guesses
							gui.setGuessRemain(String.valueOf(maxMisses - wrong));
							for (int i = 0; i < asterisk.length(); i++) {
								if (asterisk.charAt(i) != '*')
									won--;
							}
						}
					}
				}
			}
			
			if (won == 0) {
				String [] options = {"Yes", "No"};
				playAgain = gui.promptUser("You Won!!!\nWould you like to play again??", options);
				gui.closeGUI();
			} else if (maxMisses - wrong == 0) {
				String [] options = {"Yes", "No"};
				playAgain = gui.promptUser("You Lost!!!\nWould you like to play again??", options);
				gui.closeGUI();
			}
			
		}while(playAgain == JOptionPane.YES_OPTION);
		
		System.out.println("THE END");
		System.exit(0);
	}
	
	/*
	protected static String getBlanks(String word){
		String tempStr = "";
		for(int i=0; i<=word.length(); i++){
			tempStr += " _ ";
		}
		return tempStr;
	}
 	*/
}
