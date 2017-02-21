package tourney;

import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class OutputArea extends JTextArea {
	public PlayerList tourneyList;
	
	public OutputArea(PlayerList list){
		tourneyList = list;
		setOutput();
	}
	public OutputArea(int a, int b, PlayerList list){
		super(a,b);
		tourneyList = list;
		setOutput();
	}
	
	public void setOutput(){
		tourneyList.setOutputArea(this);
	}
	
	public void refresh(){
	}
}
