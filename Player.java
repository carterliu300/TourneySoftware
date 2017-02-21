package tourney;

import java.util.ArrayList;

public class Player {
	private String firstName;
	private String lastName;
	public ArrayList<Integer> winStreak;
	private int uniqueID;//In case there's a need to use it
	private boolean dropout = false;
	private boolean bye;
	
	//Should make something happen if they don't accept proper names
	public Player(String first, String last){
		firstName = first;
		lastName = last;
	}
	public String fullName(){
		return firstName + " " + lastName;
	}
	/**
	 * Spits out the current round result for player
	 * @param roundNum Current round in code
	 * @return Win, Lose or Draw for the round
	 */
	public String roundResult(int roundNum){
		//TODO: possible number problem
		if (roundNum >= 0 && roundNum < winStreak.size()){
			switch (winStreak.get(roundNum)){
			case -1:
				break;
			case 0:
				return "Lose";
			case 1:
				return "Draw";
			case 2:
				return "Win";
			}
		}
		return " ";
	}
	
	/**
	 * Sets the capacity of rounds a player will play.
	 * Don't forget to actually set the round results afterwards!
	 * @param num The max num of rounds
	 */
	public void SetRounds(int num){
		this.winStreak = new ArrayList<Integer>(num);
		for (int i = 0; i < num; i++){
			winStreak.add(-1);
		}
	}
	
	//Accepts WIN,LOSE or DRAW
	/**
	 * Sets the result of that round
	 * @param roundNum	Current round in code
	 * @param result	Whether they won, lost or drew.
	 */
	public void setWin(int roundNum, int result){
		if (result >= 0 && result <= 3){
			winStreak.set(roundNum, result);
		}
	}
	/**
	 * See if they've finished for that round or not
	 * @param roundNum	Current round in code
	 * @return			true if they played that round, else false
	 */
	public boolean roundFinished(int roundNum){
		if (roundNum <= winStreak.size()){//Either 0 for draw, or 1 for victory
			if(winStreak.get(roundNum) > -1){
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param curRound	Points they have accumulated until this round
	 * @return	Current round in code
	 */
	public int returnPoints(int curRound){
		int tempNum = 0;
		for (int i = 0; i < curRound; i ++){
			tempNum += winStreak.get(i);
		}
		return tempNum;
	}
	/**
	 * If someone drops out, use this method
	 */
	public void dropOut(){
		dropout = true;
	}
	/**
	 * If someone received a bye, use this method
	 */
	public void receivedBye(){
		bye = true;
	}
	/**
	 * @return If player received a bye or not.
	 */
	public boolean ifBye(){
		return bye;
	}
	/**
	 * Sets a latecomer's streak to all losses until current round.
	 * Don't forget to set the number of rounds the player will play beforehand!
	 * @param curRound	Current round in human
	 */
	public void lateComer(int curRound){
		for (int i = 0; i < curRound-1; i++){
			setWin(i, 0);
		}
	}
}
