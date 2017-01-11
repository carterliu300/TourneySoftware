package tourney;

import java.util.ArrayList;

public class Player {
	public String firstName;
	public String lastName;
	public ArrayList<Boolean> winStreak;
	
	public Player(String first, String last){
		firstName = first;
		lastName = last;
	}
	
	public void SetRounds(int num){
		winStreak = new ArrayList<Boolean>(num);
	}
}
