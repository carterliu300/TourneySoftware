package tourney;

import javax.swing.JTextField;
import javax.swing.text.Document;

public class ErrorArea extends JTextField {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8045733078012430694L;
	PlayerList tourneyList;
	public ErrorArea() {
	}
	
	public ErrorArea(PlayerList list){
		tourneyList = list;
		tourneyList.setErrorArea(this);
	}
	
	public ErrorArea(String text) {
		super(text);
	}

	public ErrorArea(int columns) {
		super(columns);
	}

	public ErrorArea(String text, int columns) {
		super(text, columns);
	}

	public ErrorArea(Document doc, String text, int columns) {
		super(doc, text, columns);
	}

}
