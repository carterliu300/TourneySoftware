package tourney;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public class ActionComponent extends JPanel implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 859776156175244636L;

	public PlayerList list;
	
	public ActionComponent(PlayerList pList){
		super();
		list = pList;
		Components();
	}
	
	private void Components(){
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
