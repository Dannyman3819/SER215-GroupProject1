/**---------------------------------------
 //
 // Project: SER215 Group Project
 //
 // hangmanGUI class
 // @author: Daniel Blevins
 // @date: 2017-6-9
 //
 ---------------------------------------*/

/** ---USAGE:---
hangmanGUI(String word, String difficulty, String guessRemain)
    options:
    	word - string to display to user in text box
		difficulty - string to display to user in options box
		guessRemain - string to display to user in option box

void initGUI()
    desc:
        Calls necessary functions to build and show the GUI

void drawNextBodyPart()
    desc:
        Loads the next body part

int promptUser(String message, String[] options)
    desc:
        create a message box and return the selected option (zero-indexed)
        returns '-1' if no option was selected
    options:
        message - the message to display to the user
        options - simple array of strings to display to the user

void updateWord(String word)
	desc:
        Updates the string show to the user without rebuilding the form
    options:
        word - word to be updated

boolean isActionPerformed()
	desc:
        returns true when a action happened

string getEventValue()
    desc:
        returns string of event value (could be word or one character) or NULL if no new event

void closeGUI()
    desc:
        closes the GUI


// OPTIONAL FUNCTIONS:

void setGuessedLetters(char character)
    desc:
        manually set guessed letter (useful before building or rebuilding GUI)
    options:
        character - the letter A-Z to set

void disableButton(char character)
    desc:
        manually disable a button
    options:
        character - the letter A-Z to disable

*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import static java.lang.System.exit;

public class hangmanGUI extends JPanel {
	// User set Variables
	private String word = "";
	private int drawBodyStatus = 0;
	private String difficulty = "";
	private String guessRemain = "";
	private boolean [] guessedLetters = new boolean[26];

	// Variables set and used in this class
	private JFrame f;                           // Form window
	private JButton[] button = new JButton[26]; // A-Z buttons
	private GridBagConstraints c;               // General constraints for Panels and Forms
	private JPanel drawingPanel;                // Panel for Images
	private BufferedImage image;                // Holds what image to load
	private boolean myActionPerformed = false;  // Variable that can be constantly checked for an action
	private String eventValue = "";             // Hold value of user input
	private JLabel labelImages = new JLabel();  // Used to hold value of image currently being displaying
	private JTextArea wordTextPanel;            // Panel for displaying word
	JTextArea guessRemainArea;                  // Panel for displaying Data to user

	// Pictures used for drawing body
	private String path = "./HangmanDictionary/resources/";
	private File resourceHanger = new File(path + "Hanger.png");
	private File resourceHead = new File(path + "Head.png");
	private File resourceBody = new File(path + "Body.png");
	private File resourceLeg1 = new File(path + "Leg1.png");
	private File resourceLeg2 = new File(path + "Leg2.png");
	private File resourceArm1 = new File(path + "Arm1.png");
	private File resourceArm2 = new File(path + "Arm2.png");

	hangmanGUI(String word) {
		this.word = word;
	}

	hangmanGUI(String word, String difficultyDisplay) {
		this.word = word;
		this.difficulty = difficultyDisplay;
	}

	hangmanGUI(String word, String difficultyDisplay, String guessRemain) {
		this.word = word;
		this.difficulty = difficultyDisplay;
		this.guessRemain = guessRemain;
	}

	hangmanGUI() {}

	public void initGUI(){
		// Init main window
		_mainForm();

		// Set Drawing Panel
		_drawingPanel();

		// Panel to show data
		_optionPanel();

		// Panel for displaying Word to guess
		_wordLine();

		// Add buttons A-Z
		_buttons();

		// Add user input line
		_textInputLine();

		// Set size of window then display
		f.setSize(575, 690);
		f.setResizable(false);
		f.setVisible(true);
	}

	public void updateWord(String word){
		this.word = word;
		wordTextPanel.setText(word);
		f.revalidate();
	}

	public void updateGuessRemain(String guessRemain){
		this.guessRemain = guessRemain;
		guessRemainArea.setText(guessRemain);
		f.revalidate();
	}

	public boolean isActionPerformed(){
		return myActionPerformed;
	}

	public String getEventValue(){
		if(myActionPerformed){
			myActionPerformed = false;
			if(eventValue.length() == 1){
				char letter = eventValue.charAt(0);
				letter = Character.toUpperCase(letter);
				setGuessedLetters(letter);
				disableButton(letter);
			}
			return eventValue;
		} else {
			return null;
		}
	}

	public void setGuessedLetters(char letter){
		letter = Character.toUpperCase(letter);
		int numLetter = (int)letter - 65;
		guessedLetters[numLetter] = true;
	}

	public void disableButton(char letter){
		letter = Character.toUpperCase(letter);
		int numLetter = (int)letter - 65;
		button[numLetter].setEnabled(false);
	}

	public void drawNextBodyPart() {
		File file;
		drawBodyStatus++;
		switch (drawBodyStatus) {
			case 1: file = resourceHead;
				break;
			case 2: file = resourceBody;
				break;
			case 3: file = resourceLeg1;
				break;
			case 4: file = resourceLeg2;
				break;
			case 5: file = resourceArm1;
				break;
			case 6: file = resourceArm2;
				break;
			default: file = resourceHanger;
				break;
		}
		// Try to get file
		try {
			image = ImageIO.read(file);
			/*
			int w = image.getWidth(null);
			int h = image.getHeight(null);
			backgroundImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			overlayImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			*/
		} catch (IOException ex) {
			//cant find file
			exit(1);
		}
		drawingPanel.remove(labelImages);
		labelImages = new JLabel(new ImageIcon(image));
		drawingPanel.add(labelImages);
		drawingPanel.revalidate();

	}

	public int promptUser(String message, String [] options) {
		Object defaultChoice = options[0];
		return JOptionPane.showOptionDialog(this, message, "Hang Man",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, defaultChoice);
	}

	public void closeGUI(){
		f.dispatchEvent(new WindowEvent(f, WindowEvent.WINDOW_CLOSING));
	}

	/*protected String _getBlanks(String word){
		String tempStr = "";
		for(int i=0; i<=word.length(); i++){
			tempStr += " _ ";
		}
		return tempStr;
	}*/

	protected void _mainForm(){
		f = new JFrame();
		f.setLayout(new GridBagLayout());
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	protected void _drawingPanel(){
		// Setup drawing panel
		drawingPanel = new JPanel();
		drawingPanel.setLayout(new FlowLayout());
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 9;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty = 0;
		drawingPanel.setBackground(Color.black);
		c.insets = new Insets(3,3,3,3);
		// Read image file and put into drawingPanel
		try {
			image = ImageIO.read(resourceHanger);
		} catch (IOException ex) {
			//cant find file
			exit(1);
		}
		labelImages = new JLabel(new ImageIcon(image));
		drawingPanel.add(labelImages);
		f.add(drawingPanel, c);
	}

	protected void _optionPanel(){
		// Add option panel where the data is showed
		JPanel optionPanel = new JPanel();
		guessRemainArea = new JTextArea();
		JTextArea difficultyArea = new JTextArea();
		//optionPanel = new JPanel();
		c = new GridBagConstraints();
		optionPanel.setLayout(new GridBagLayout());
		optionPanel.setBackground(Color.gray);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.weightx = 0.0;
		c.gridx = 9;
		c.gridy = 0;
		c.weighty = 0;
		c.insets = new Insets(10,2,10,2);
		optionPanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		optionPanel.setBackground(Color.white);
		c.anchor = GridBagConstraints.NORTH;
		f.add(optionPanel, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = 0;
		c.insets = new Insets(5,5,5,5);
		guessRemainArea.setText("Guesses Remaining: " + guessRemain);
		guessRemainArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		guessRemainArea.setEditable(false);
		optionPanel.add(guessRemainArea, c);

		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.gridwidth = 4;
		c.gridx = 0;
		c.gridy = -1;
		c.insets = new Insets(5,5,5,5);
		difficultyArea.setText("Difficulty: " + difficulty);
		difficultyArea.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		difficultyArea.setEditable(false);
		optionPanel.add(difficultyArea, c);
		//updateOptionPanel(); // TODO
	}

	protected void _wordLine(){
		// Add text line
		JPanel wordPanel;

		wordPanel = new JPanel();
		c = new GridBagConstraints();
		wordTextPanel = new JTextArea();

		wordTextPanel.setText(word);
		wordTextPanel.setEditable(false);
		wordTextPanel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));

		wordPanel.setLayout(new FlowLayout());
		wordPanel.setBorder(BorderFactory.createLineBorder(Color.black, 4));
		wordPanel.setBackground(Color.white);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 13;
		c.gridheight = 1;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty = 0;
		c.insets = new Insets(5,5,5,5);
		wordPanel.add(wordTextPanel);
		f.add(wordPanel, c);
	}

	protected void _buttons(){
		// Add buttons A-Z
		c = new GridBagConstraints();
		char currentChar = 'A';
		for (int i = 0; i < 26; i++) {
			button[i] = new JButton(String.valueOf(currentChar));
			currentChar++;
			c.fill = GridBagConstraints.HORIZONTAL;
			if (i < 13) {
				c.gridx = i;
				c.gridy = 2;
			} else {
				c.gridx = i % 13;
				c.gridy = 3;
			}

			f.add(button[i], c);
			//tButton = button[i];
			button[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					//tButton.setEnabled(false);
					myActionPerformed = true;
					eventValue = e.getActionCommand();
					//System.out.println(e);
				}
			});

			//Decide whether to enable or disable
			if(guessedLetters[i]){
				button[i].setEnabled(false);
			}
		}
	}

	protected void _textInputLine(){
		// Add text Line

		c = new GridBagConstraints();
		JTextField textField = new JTextField();
		JButton guessButton = new JButton("Guess");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 11;
		c.weightx = 0.0;
		c.gridx = 0;
		c.gridy = 4;
		c.weighty = 1;
		drawingPanel.setBackground(Color.black);
		c.insets = new Insets(3,3,3,3);
		f.add(textField, c);
		c.gridwidth=2;
		c.gridx=11;
		c.insets = new Insets(3,3,3,3);
		f.add(guessButton, c);
		guessButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myActionPerformed = true;
				eventValue = textField.getText();
				textField.setText("");
			}
		});

		// Action specifically for pressing Enter
		textField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myActionPerformed = true;
				eventValue = textField.getText();
				textField.setText("");
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null) {
			g.drawImage(image, 0, 0, null);
		}
	}

}
