package tourney;

import java.util.ArrayList;

public class PlayerList {
	int[] pairings;//Stores ID only
	int roundNum;
	ArrayList<Player> tourneyList = new ArrayList<Player>(0);
	int curRound;
	Player[][] tablePair;
	
	public PlayerList(){
	}
	
	public void SetRounds(){
		int participants = 0;
		int rounds = 0;
		participants = tourneyList.size();
		if (participants <= 1){
			System.out.println("There are not enough participants.");
		}
		else {
			roundNum = 0;
			for (int i = 0; i < 10; i++){
				if (Math.pow(2, i) >= participants){
					rounds = i;
					System.out.println("Number of rounds = " + rounds);
					break;
				}
				else {System.out.println("Number " + i + " skipped");}
			}
			
			ArrayList<Player> copyList = new ArrayList<Player>(0);
			tablePair = new Player[tourneyList.size()/2 + 1][2];//Sets reference point only.
			for (int i = 0; i < tourneyList.size(); i ++){
				copyList.add(tourneyList.get(i));
			}
			
			for (int i = 0; i < copyList.size(); i ++){
				System.out.println(copyList.get(i).firstName + " " + copyList.get(i).lastName);
			}
			
			int i = 0;
			while (!copyList.isEmpty()){
				int ranNum = (int)(Math.random() * copyList.size());
				tablePair[i][0] = copyList.get(ranNum);
				copyList.remove(ranNum);
				
				if (copyList.size() > 0){
					ranNum = (int)(Math.random() * copyList.size());
					tablePair[i][1] = copyList.get(ranNum);
					copyList.remove(ranNum);
				}
				
				i ++;
			}
			System.out.println("Finished Pairings ");
			for (i = 0; i < tablePair.length; i ++){
				if (tablePair[i][0] == null){}
				else if (tablePair[i][1] == null){
					System.out.println(tablePair[i][0] + " gets a bye");
				}
				else if (tablePair[i][1] != null){
					System.out.println("At table " + (i + 1) + ",  " 
							+ tablePair[i][0].firstName + " and " + tablePair[i][1].firstName
							+ " will face each other.");
				}
			}
		}
	}
	
	public void AddPlayer(String first, String last){
		/*TODO: Restrict first names and last names
		no weird symbols like !@#$%^&*()=_+{}|[]\;',./:"<>?
		Accept -, spaces (single only)
		//*/
		tourneyList.add(new Player(first, last));
	}
	
	public int GetSize(){
		return tourneyList.size();
	}
	
	public void DeletePlayer(int id){
		if (tourneyList.size() > 0 ){
			if (id < tourneyList.size() && id >= 0){
				try {
					System.out.println(tourneyList.get(id).firstName + " "
							+ tourneyList.get(id).lastName + " will now be deleted. ");
					tourneyList.remove(id);
				}
				catch (Exception e) {
					System.out.println("Somehow could not delete. Please review code.");
				}
			}
			else {
				System.out.println("Please input a number within the range of the list.");
			}
		}
		else {
			System.out.println("No list yet. Start by registering some.");
		}
	}
	
	public void printList(){
		try{
			if (tourneyList.size() <= 0){System.out.println("No entries yet");}
			else {
				for(int i = 0; i < tourneyList.size(); i ++){
				System.out.println(i + " " + tourneyList.get(i).firstName + " "
						+ tourneyList.get(i).lastName);
				}
			}
		}
		catch (Exception e){
			System.out.println("No entries yet(error version)");
		}
		System.out.println();
	}
}
