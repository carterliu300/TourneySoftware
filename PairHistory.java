package tourney;

import java.util.ArrayList;

public class PairHistory {
	
	ArrayList<Player[][]> tableHistory;
	Player[][] curHistory;
			
	public PairHistory(int num) {
		// TODO Auto-generated constructor stub
		tableHistory = new ArrayList<Player[][]>(num);
	}
	
	//Copies address only, not data.
	public void saveHistory(Player[][] dataHistory, int roundNum){
		if (dataHistory == null){
			System.out.println("Empty dataHistory when calling PairHistory.saveHistory(/)");
			return;
		}
		int tableNum = dataHistory.length;
		for (int i = 0; i < tableNum; i++){
			tableHistory.get(roundNum)[i][0] = dataHistory[i][0];
			if (dataHistory[i][1] != null){
				tableHistory.get(roundNum)[i][1] = dataHistory[i][1];
			}
		}
	}
	
	public Player[][] recallHistory(int targetRound){
		try {
			return tableHistory.get(targetRound);
		}
		catch (Exception e){
			System.out.println("Threw exception in PairHistory.recallHistory() " + targetRound);
			return null;
		}
	}
	
}
