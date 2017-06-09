import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static java.lang.System.exit;

public class hangmanGUI extends JPanel {
	// User set Variables
	private String word = "";
	private int drawBodyStatus = 0;

	// Variables set and used in this class
	private JFrame f;                           // Form window
	private JButton button;                     // A-Z buttons
	private GridBagConstraints c;               // General constraints for Panels and Forms
	private JPanel drawingPanel;                // Panel for Images
	private BufferedImage image;                // Holds what image to load
	private boolean myActionPerformed = false;  // Variable that can be constantly checked for an action
	private String eventValue = "";             // Hold value of user input
	private JLabel labelImages = new JLabel();  // Used to hold value of image currently being displaying
	private JTextArea wordTextPanel;            // Panel for displaying word

	// Pictures used for drawing body
	private String path = "./GroupProject1/src/resources/";
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

	hangmanGUI() {
	}

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
		f.setSize(570, 690);
		f.setVisible(true);
	}

	public void updateWord(String word){
		this.word = word;
		wordTextPanel.setText(word);
		f.revalidate();
	}

	public boolean isActionPerformed(){
		return myActionPerformed;
	}

	public String getEventValue(){
		if(myActionPerformed){
			myActionPerformed = false;
			return eventValue;
		} else {
			return null;
		}
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
		JPanel optionPanel;                 // Panel for displaying Data to user
		optionPanel = new JPanel();
		c = new GridBagConstraints();
		optionPanel.setLayout(new FlowLayout());
		optionPanel.setBackground(Color.gray);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 4;
		c.weightx = 0.0;
		c.gridx = 9;
		c.gridy = 0;
		c.weighty = 0;
		f.add(optionPanel, c);
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
			button = new JButton(String.valueOf(currentChar));
			currentChar++;
			c.fill = GridBagConstraints.HORIZONTAL;
			if (i < 13) {
				c.gridx = i;
				c.gridy = 2;
			} else {
				c.gridx = i % 13;
				c.gridy = 3;
			}

			f.add(button, c);
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					myActionPerformed = true;
					eventValue = e.getActionCommand();
					//System.out.println(e);
				}
			});
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
