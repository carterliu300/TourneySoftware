package tourney;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("unused")
public class Display extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7569772998089995734L;
	
	public PlayerList tourneyList;
	private int x = 50,
			y = 100,
			width = 500,
			height = 600;
	private int fieldSize = 10;
	JFrame frame = new JFrame();
	
	public Display(PlayerList list){
		tourneyList = list;
		defaultSettings();
		addComponents();
		setCentre();
		frame.pack();
		frame.setVisible(true);
	}
	
	public void defaultSettings(){
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setTitle("Tourney");
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		//frame.setBounds(x, y, width, height);
		//frame.setVisible(true);
		
		/*
		 * 
		JFrame.setDefaultLookAndFeelDecorated(true);
	    JFrame frame = new JFrame();
	    frame.setTitle("My First Swing Application");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JLabel label = new JLabel("Welcome");
	    frame.add(label);
	    frame.pack();
	    frame.setVisible(true);
		 */
	}
	
	public void addComponents(){
		/*
		 * GridLayout first goes down, then rearranges to go across once the vertical
		 * threshold is exceeded
		 */
		
		JPanel centrePanel = new JPanel(new GridLayout(0,2));
		frame.add(centrePanel, BorderLayout.CENTER);
		

		//Output area for the table pairings. In East panel
		OutputArea output = new OutputArea(5, 6*fieldSize, tourneyList);
		output.setEditable(false);
		JScrollPane scrollArea = new JScrollPane(output);
		frame.add(scrollArea, BorderLayout.EAST);
		
		//Output area for the list of participants. In West panel
		JTextArea listOutput = new JTextArea(5, 2*fieldSize);
		listOutput.setEditable(false);
		JScrollPane listArea = new JScrollPane(listOutput);
		frame.add(listArea, BorderLayout.WEST);
		if (tourneyList.getSize() > 0){
			listOutput.setText(tourneyList.printStringList());
		}
		
		
		//Output for errors. In south panel
		JTextField errorPanel = new ErrorArea(tourneyList);
		errorPanel.setEditable(false);
		frame.add(errorPanel, BorderLayout.SOUTH);
		
		
		//Add first name input area and title
		JLabel fName = new JLabel("First Name");
		centrePanel.add(fName);
		JTextField fField = new JTextField(fieldSize);
		centrePanel.add(fField);
		
		//Add last name input area and title
		JLabel lName = new JLabel("Last Name");
		centrePanel.add(lName);
		JTextField lField = new JTextField(fieldSize);
		centrePanel.add(lField);
		

		
		JButton register = new JButton("Register Player");
		centrePanel.add(register);
		JTextField fullName = new JTextField(fieldSize);
		fullName.setEditable(false);
		centrePanel.add(fullName);
		register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstName = fField.getText();
				String lastName = lField.getText();
				if (!firstName.isEmpty() && !lastName.isEmpty()){
					//TODO: Code should be in PlayerList for ease of changes.
					if (tourneyList.inSession()){fullName.setText("You're late!");}
					else{fullName.setText("d(^ ^o)");}
					
					errorPanel.setText(firstName + " " + lastName + " registered.");
					tourneyList.addPlayer(firstName, lastName);
					fField.setText("");
					lField.setText("");
				}
				else {
					fullName.setText("-.-");
					errorPanel.setText("Please enter something for"
							+ " both first and last names, even if it's just a"
							+ " space.");
					
				}
				listOutput.setText(tourneyList.printStringList());
			}
			
		});
		
		//TODO: What happens if a player is deleted during matches?
		JButton delete = new JButton("Delete");
		centrePanel.add(delete);
		JTextField indexArea = new JTextField(fieldSize);
		centrePanel.add(indexArea);
		delete.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					int delIndex = Integer.parseInt(indexArea.getText());
					if (delIndex >= 0 && delIndex < tourneyList.getSize()){
						tourneyList.deletePlayer(delIndex);
						listOutput.setText(tourneyList.printStringList());
					}
					else {
						indexArea.setText("Within range pls");
					}
				}
				catch(Exception e2){
					indexArea.setText("Input player index here");
				}
			}});
		
		
		JButton pair = new JButton("Pair");
		centrePanel.add(pair);
		pair.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				tourneyList.setRounds();
				delete.setText("Drop Out");
			}
			
		});
		
		//Temporary, for testing purposes
		JButton clearPairing = new JButton("Clear Pair");
		centrePanel.add(clearPairing);
		clearPairing.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tourneyList.clearPairings();
				delete.setText("Delete Player");
			}
		});
		
		JButton table = new JButton("Table No.");
		JTextField tableField = new JTextField(fieldSize);
		centrePanel.add(table);
		centrePanel.add(tableField);
		table.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int tableNum = Integer.parseInt(tableField.getText());
					TablePopup popup = new TablePopup(tourneyList.getTable(tableNum));
					//Auto returns -1 if closed
					int result = popup.confirmWinner(frame, tableNum);
					
					tourneyList.setTableWinner(tableNum, result);
					
				}
				catch (Exception tableRoundException){
					tableField.setText("Error");
				}
			}
			
		});
		
		JButton reset = new JButton ("Reset");
		reset.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				tourneyList.resetAllRounds();
			}
			
		});
		centrePanel.add(reset);
		
		JButton manual = new JButton("Manual");
		manual.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				String instructions = "Tournament Software by Carter Liu \n"
						+ "Input first name in text box next to \"First Name\" \n"
						+ "Input last name in text box next to \"Last Name\" \n"
						+ "Click the Register button to register the player when both fields"
						+ " have been filled \n"
						+ "Delete player by inputting their index first, then clicking the button."
						+ "\n\n"
						+ "\"Pair\" will start the tournament. You proceed to the next round "
						+ "using this very button as well.\n"
						+ "\"Clear Pair\" is only there for testing purposes. You may encounter "
						+ "some bugs whilst using this button\n\n"
						+ "Insert table number, then press \"Table No.\" to set the result for"
						+ " that table\n"
						+ "\"Reset\" will clear all pairings and results. This is the button you want"
						+ " to use if you wish to reset all records\n"
						+ "As for \"Manual\" ... do I really have to explain?";
				output.setText(instructions);
			}
		});
		centrePanel.add(manual);
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    //frame.setBounds((int) screenSize.getWidth() - width, 0, width, height);
	    
	}
	
	public void setCentre(){
		Point centre = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		frame.setBounds(centre.x - width/2, centre.y - height/2, width, height);
	}
}
