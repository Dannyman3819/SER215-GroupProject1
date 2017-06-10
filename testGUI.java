
public class textGUI {
	public static void main(String[] args) throws  InterruptedException {
		// Load GUI object with word to display
		hangmanGUI gui = new hangmanGUI("_ _ _ _", "HARD", "5");

		String[] options = {"EASY", "INTERMEDIATE", "HARD"};
		int out = gui.promptUser("Please choose a difficulty", options);
		System.out.println(out);

		// builds the shows GUI
		gui.initGUI();

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
	}
}
