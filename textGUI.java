
public class textGUI {
	public static void main(String[] args) throws  InterruptedException {
		// Load GUI object with word to display
		hangmanGUI gui = new hangmanGUI("_ _ _ _");

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