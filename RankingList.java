package tourney;

//import java.util.ArrayList;
/**
 * This class is used to calculate rankings only.
 * 
 * @author carterliu
 *
 */
public class RankingList {
	
	int maxRounds, maxTables;
	Player[][][] tableResults;
	
	/*
	 * Store dynamic number of participants
	 * more importantly, number of tables consisting 2 players
	 * which may increase over the game
	 * decrease doesn't matter as it can be left alone
	 * 
	 * Set max size as max. Don't display if empty.
	 * 
	 * AddTableList(tablePair[][], int curRound)
	 * 
	 * round[maxRound] = tableList
	 * tableList is dynamic array that holds 2 players for every element
	 * each element is a player
	 * 
	 * tableList[]
	 * each element
	 */
	/**
	 * This is used to store the results of the matches and
	 * calculate the rankings.
	 * @param maxRoundNum Number of rounds that will occur 
	 */
	public RankingList(int maxRoundNum) {
		tableResults = null;
		this.maxRounds = maxRoundNum;
		int participants = (int) Math.pow(2, maxRounds);
		tableResults = new Player[maxRounds][participants/2][2];
		
	}
	
	public void addResults(int roundNum, Player[][] tableList){
		if (tableList == null){
			System.out.println("Error at addResults()");
		}
		else {//tableList not null
			for (int i = 0; i < tableList.length; i ++){
				
			}
		}
	}
	
}
