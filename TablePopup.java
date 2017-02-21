package tourney;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JOptionPane;

public class TablePopup extends JOptionPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3727158159534032956L;

	public TablePopup() {
	}

	public TablePopup(Object message) {
		super(message);
	}

	public TablePopup(Object message, int messageType) {
		super(message, messageType);
	}

	public TablePopup(Object message, int messageType, int optionType) {
		super(message, messageType, optionType);
	}

	public TablePopup(Object message, int messageType, int optionType, Icon icon) {
		super(message, messageType, optionType, icon);
	}

	public TablePopup(Object message, int messageType, int optionType, Icon icon, Object[] options) {
		super(message, messageType, optionType, icon, options);
	}

	public TablePopup(Object message, int messageType, int optionType, Icon icon, Object[] options,
			Object initialValue) {
		super(message, messageType, optionType, icon, options, initialValue);
	}
	
	Object[] options = {"Draw",
			"player 2 wins",
			"player 1 wins"};
	
	public TablePopup (Player [] table){
		if (table.length > 0 && table.length < 3){
			options[2] = table[0].fullName();
			options[1] = table[1].fullName();
		}
	}
	
	
	public int confirmWinner(Component parentComponent, int tableNum){
		int n = TablePopup.showOptionDialog(parentComponent,
				"Who wins?",
				"Table " + tableNum,
				YES_NO_OPTION,
				QUESTION_MESSAGE,
				null,
				options,
				null);
		/*Options are presented in reverse order
		 * 2 = player 1
		 * 1 = player 2
		 * 0 = draw
		 * 
		 * we want 0 = player 1
		 * 1 = player 2
		 * 2 = draw
		 */
		
		switch (n){
			case 2:
				return 0;
			case 0:
				return 2;
		}
		return n;
	}
	/*
	public static int showOptionDialog(Component parentComponent,
            Object message,
            String title,
            int optionType,
            int messageType,
            Icon icon,
            Object[] options,
            Object initialValue)
     throws HeadlessException
	//*/
}
