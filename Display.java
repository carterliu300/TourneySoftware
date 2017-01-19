package tourney;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	JFrame frame = new JFrame();
	
	public Display(PlayerList list){
		tourneyList = list;
		DefaultSettings();
		AddComponents();
		SetCentre();
		frame.pack();
		frame.setVisible(true);
	}
	
	public void DefaultSettings(){
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
	
	public void AddComponents(){
		/*
		 * GridLayout first goes down, then rearranges to go across once the vertical
		 * threshold is exceeded
		 */
		
		JPanel centrePanel = new JPanel(new GridLayout(3,2));
		frame.add(centrePanel, BorderLayout.CENTER);
		
		//Add first name input area and title
		JLabel fName = new JLabel("First Name");
		centrePanel.add(fName);
		JTextField fField = new JTextField(10);
		centrePanel.add(fField);
		
		//Add last name input area and title
		JLabel lName = new JLabel("Last Name");
		centrePanel.add(lName);
		JTextField lField = new JTextField(10);
		centrePanel.add(lField);
		
		//Output area for the tourney list
		JTextArea output = new JTextArea(5, 15);
		output.setEditable(false);
		JScrollPane scrollArea = new JScrollPane(output);
		frame.add(scrollArea, BorderLayout.EAST);
		
		//TODO: fix display area problem?
		JButton register = new JButton("Register Player");
		centrePanel.add(register);
		JTextArea fullName = new JTextArea();
		fullName.setEditable(false);
		centrePanel.add(fullName);
		register.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstName = fField.getText();
				String lastName = lField.getText();
				if (!firstName.isEmpty() && !lastName.isEmpty()){
					fullName.setText(firstName + " " + lastName + " registered.");
					tourneyList.AddPlayer(firstName, lastName);
				}
				else {
					fullName.setText("Please enter something for first and last names, even"
							+ " if it\'s just an empty space");
				}
			}
			
		});
		
		
//		JLabel label = new JLabel("Text here");
//		frame.add(label);
//		frame.add(new JTextField());
//		
//		frame.add(new JLabel("Text There"));
//		frame.add(new JTextField());
//		
//		frame.add(new JButton("Register"));
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    //frame.setBounds((int) screenSize.getWidth() - width, 0, width, height);
	    
	}
	
	public void SetCentre(){
		Point centre = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		frame.setBounds(centre.x - width/2, centre.y - height/2, width, height);
	}
}
