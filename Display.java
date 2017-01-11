package tourney;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

@SuppressWarnings("unused")
public class Display extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7569772998089995734L;
	
	public PlayerList tourneyList;
	private int x = 50,
			y = 100,
			width = 300,
			height = 400;

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
		
		frame.setLayout(new GridLayout(3,2));
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
		JLabel label = new JLabel("Text here");
		frame.add(label);
		frame.add(new JTextField());
		
		frame.add(new JLabel("Text There"));
		frame.add(new JTextField());
		
		frame.add(new JButton("Register"));
		
		//Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    //frame.setBounds((int) screenSize.getWidth() - width, 0, width, height);
	    
	}
	
	public void SetCentre(){
		Point centre = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		frame.setBounds(centre.x - width/2, centre.y - height/2, width, height);
	}
}
