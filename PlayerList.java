package tourney;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlayerList {
	int[] pairings;//Stores ID only
	/**
	 * Maximum number of rounds in human
	 */
	int roundNum;
	ArrayList<Player> tourneyList = new ArrayList<Player>(0);
	/**
	 * Current round in human
	 */
	int curRound = 0;
	//TODO: May consider moving to a queue or array list
	private Player[][] tablePair;
	private ArrayList<Player> dropOuts = new ArrayList<Player>();
	private final int WIN = 2, DRAW = 1, LOSE = 0;
	
	OutputArea output;
	ErrorArea error;
	
	public PlayerList(){
		//*
		tourneyList.add(new Player("Josh Audi", "Benitez"));
		tourneyList.add(new Player("Michael", "Enright"));
		tourneyList.add(new Player("Mitchell", "Ling"));
		tourneyList.add(new Player("Khoi", "Nguyen"));
		tourneyList.add(new Player("Loli", "Con"));
		tourneyList.add(new Player("Andy", "Liu"));
		tourneyList.add(new Player("Alex", "Koreanman"));
		
		//*/
	}
	/**
	 * Sets the rounds, pairings etc
	 */
	public void setRounds(){
		//First round only
		int participants = tourneyList.size();
		int rounds = 0;
		String tempText = "";
		
		if (participants <= 1){
			displayErrorMessage("Not enough participants");
		}
		else if (tablePair == null) {//Which means the pairings have not yet occurred
			rounds =  (int)Math.ceil((Math.log(participants) / Math.log(2)));
			roundNum = rounds;
			tempText += "There will be " + rounds + " round";
			if (rounds > 1){tempText += "s";}
			tempText += ".\n";
			
			curRound = 1;
			ArrayList<Player> copyList = new ArrayList<Player>(participants);
			
			tablePair = new Player[(int)Math.ceil(participants/2.0)][2];//Marker for 2 way weiss
			for (int i = 0; i < tourneyList.size(); i++){
				//Copy the list for randomisation: first time
				copyList.add(tourneyList.get(i));
				tourneyList.get(i).SetRounds(rounds);
			}
			rounds = 0;//Reusing resources.
			while (!copyList.isEmpty()){
				int ranNum = (int)(Math.random() * copyList.size());
				tablePair[rounds][0] = copyList.get(ranNum);
				copyList.remove(ranNum);
				
				if (copyList.size() > 0){//If you can pair the guy
					ranNum = (int)(Math.random() * copyList.size());
					tablePair[rounds][1] = copyList.get(ranNum);
					copyList.remove(ranNum);
				}
				rounds ++;
			}
			//Finished random initial pairings
			displayPairings(tempText);
		}
		
		else {//Participants > 1 and already have tablePairs
			nextRound();
		}
	}
	
	protected int getRounds(){
		return roundNum;
	}
	/**
	 * Get the players for a table
	 * @param tableNum	Table number
	 * @return	Player or 2 Players (array)
	 */
	protected Player[] getTable(int tableNum){
		if (tableNum >= 0 && tableNum < tablePair.length){
			return tablePair[tableNum];
		}
		return null;
	}
	/**
	 * Sets who wins at what table
	 * @param tableNum	Table Number
	 * @param result	1 for p1 win, 2 for p2 win, and 0 for draw
	 */
	protected void setTableWinner(int tableNum, int result){
		Player temp1 = tablePair[tableNum][0];
		Player temp2 = tablePair[tableNum][1];
		switch(result){
			//Did nothing
			case -1:
				break;
			//Player 1 wins
			case 0:
				temp1.setWin(curRound-1, WIN);
				temp2.setWin(curRound-1, LOSE);
				break;
			//Player 2 wins
			case 1:
				temp1.setWin(curRound-1, LOSE);
				temp2.setWin(curRound-1, WIN);
				break;
			//Draw
			case 2:
				temp1.setWin(curRound-1, DRAW);
				temp2.setWin(curRound-1, DRAW);
				break;
		}
		displayPairings();
	}
	/**
	 * For testing purposes only. Clears current round pairings
	 */
	public void clearPairings(){
		//TODO: May need to reset all variables, or create resetFirstRound()
		tablePair = null;
	}
	/**
	 * For testing purposes only. Will reset everything but participants.
	 */
	public void resetAllRounds(){
		clearPairings();
		curRound = 0;
		roundNum = 0;
		//TODO: reset participant results as well
		for (int i = 0; i < dropOuts.size(); i ++){
			tourneyList.add(dropOuts.get(i));
		}
		dropOuts.clear();
		for (int i = 0; i < tourneyList.size(); i ++){
			tourneyList.get(i).resetRecord();
		}
	}
	/**
	 * Sets from round 2 onwards
	 */
	private void nextRound(){//Runs only if canProceed returns true.
		if (canProceed()){//Everybody has finished their rounds
			if (curRound == roundNum){
				tourneyEnd();
				displayErrorMessage("Tournament Finished");
				return;
			}
			/*
			 * calculate max points one can get so far
			 * then randomly put into new list
			 * then add max point - 1 etc
			 * then add to new list randomly
			 * after all has been added,
			 * pair them up following the new list's order
			 */
			int maxPoints = curRound * 2;//Because WIN = 2
			System.out.println(tourneyList.size() + " max point = " + maxPoints);
			int participants = tourneyList.size();
			ArrayList<Player> copyList = new ArrayList<Player>(participants);
			LinkedList<Player> pairingList = new LinkedList<Player>();
			Player temp;
			while (maxPoints >= 0){
				for (int i = 0; i < participants; i ++){
					temp = tourneyList.get(i);
					if (temp.returnPoints(curRound) == maxPoints){
						copyList.add(temp);
					}
				}
				while(copyList.size() > 0){
					int random = (int)(Math.random() * copyList.size());
					pairingList.addLast(copyList.get(random));
					copyList.remove(random);
				}
				maxPoints -= 1;
			}
			clearPairings();
			tablePair = new Player[(int)Math.ceil(participants/2.0)][2];
			int tempNum = 0;
			while (pairingList.peek() != null){
				System.out.println("tempNum = " + tempNum);
				if (tempNum <= participants/2 + 1){
					tablePair[tempNum][0] = pairingList.poll();
					tablePair[tempNum][1] = pairingList.poll();
				}
				tempNum++;
			}
			curRound++;
			displayPairings();
		}
		else{error.setText("Not everyone has finished their rounds");}
	}
	/**
	 * Returns true if all players have completed their rounds
	 * @return
	 */
	private boolean canProceed(){
		//i.e., the last person gets a bye.
		int endIndex = tablePair.length - 1;
		if (tablePair[endIndex][1] == null){
			//Check if all other players finished
			for (int i = 0; i < endIndex; i++){
				//if pairProceed doesn't pass
				if(!pairProceed(tablePair[i])){return false;}
			}
			tablePair[endIndex][0].setWin(curRound - 1, 2);
			tablePair[endIndex][0].receivedBye();
		}
		
		else{//i.e., everybody was paired
			for (int i = 0; i < tourneyList.size(); i++){
				if (!tourneyList.get(i).roundFinished(curRound - 1)){return false;}
			}
		}
		return true;
	}
	/**
	 * This method just checks if both players have finished their rounds or not
	 * @param table Table No.
	 * @return
	 */
	private boolean pairProceed(Player[] table){
		if (table.length == 2){
			return (table[0].roundFinished(curRound-1) && table[1].roundFinished(curRound-1));
		}
		else {
			displayErrorMessage("Error at pairProceed");
		}
		return true;
	}
	/**
	 * Run this to end the tournament.
	 * Both conditions must be satisfied before running this.
	 * I.e., max round reached and all players finished
	 */
	private void tourneyEnd(){
		//TODO: End tourney here. Conditions already met
		int participants = tourneyList.size();
		ArrayList<Player> copyList = new ArrayList<Player>(participants);
		ArrayList<Player> finalList = new ArrayList<Player>(participants);
		copyList = tourneyList;
		int maxPoints = roundNum * 2;
		while (maxPoints >= 0){
			for (int i = 0; i < copyList.size(); i++){
				if (copyList.get(i).returnPoints(roundNum) == maxPoints){
					finalList.add(copyList.get(i));
				}
			}
			maxPoints --;
		}
//		System.out.println("Final List Size = " + finalList.size()); 
//		System.out.println(printRankings(finalList));
		displayMessage(printRankings(finalList));
	}
	/**
	 * Returns a string listing the placement of all the players
	 * @param list The rankings in proper order
	 * @return A String
	 */
	private String printRankings(ArrayList<Player> list){
		String tempString = "Rank";
		System.out.println(tempString + " List: " +list.size());
		Player temp = null;
		int rank = 0;
		//TODO: Sort out bug
		for (int i = 0; i < list.size(); i ++){
			tempString +=   i % 5 == 0 ? "\n" : "";
			temp = list.get(i);
			tempString += (i + 1);
			rank = (i % 10) + 1;
			switch(rank){
			case 1:
				tempString += "st";
				break;
			case 2:
				tempString += "nd";
				break;
			case 3:
				tempString += "rd";
				break;
			default:
				tempString += "th";
				break;
			}
			tempString += ")---" + temp.fullName() + " ";
			for (int j = 0; j < roundNum; j ++){
				tempString += "[" + temp.roundResult(j) + "]";
			}
			tempString += "\n";
		}
		
		System.out.println("Finished loop");
		return tempString;
	}
	/**
	 * Adds a player
	 * @param first	Their first name
	 * @param last	Their last name
	 */
	public void addPlayer(String first, String last){
		/*TODO: Restrict first names and last names
		no weird symbols like !@#$%^&*()=_+{}|[]\;',./:"<>?
		Accept -, spaces (single only), but not for both
		
		Also need to add what happens if rounds are occurring.
		//*/
		Player newEntry = new Player(first, last);
		//TODO: Finish what happens to late signups
		if (inSession()){
			//We haven't added the player yet. We need to see if it exceeds capacity
			int participants = tourneyList.size() + 1;
			if ((int)Math.ceil( Math.log(participants) / Math.log(2) ) > roundNum){
				error.setText("Adding this player will require more rounds."
						+ "Either reset pairings or refuse entry. "
						+ newEntry.fullName() + " has not been added.");
				return;
			}
			newEntry.SetRounds(roundNum);
			for(int i = 0; i < curRound - 1; i++){
				newEntry.setWin(i, 0);
			}
			if (tablePair[tablePair.length -1][1] == null){
				tablePair[tablePair.length -1][1] = newEntry;
			}
			//tablePair[tablePair.length -1][1] != null
			else {
				//Hopefully this won't occur too often
				Player[][] newTable = new Player[tablePair.length + 1][2];
				for (int i = 0; i < tablePair.length; i++){
					newTable[i] = tablePair[i];
				}
				newTable[newTable.length -1][0] = newEntry;
				tablePair = newTable;
			}
			displayPairings();
		}
		tourneyList.add(newEntry);
		//Else do nothing
	}
	/**
	 * 
	 * @return	Number of participants
	 */
	public int getSize(){
		return tourneyList.size();
	}
	/**
	 * Delete a player via their index
	 * @param id	Found in the list of players
	 */
	public void deletePlayer(int id){
		if (tourneyList.size() > 0 ){
			//Just to double check
			if (id < tourneyList.size() && id >= 0){
				try {
					if (curRound > 0){//I.e., tourney underway
						dropOuts.add(tourneyList.get(id));
					}
					//Remove either way
					tourneyList.remove(id);
				}
				catch (Exception e) {
					error.setText("Somehow could not delete. Please review code.");
				}
			}
			else {
				error.setText("Please input a number within the range of the list.");
			}
		}
		else {
			error.setText("No list yet. Start by registering some.");
		}
	}
	
	/**
	 * Print the list of participants and their index
	 * @return	A string
	 */
	public String printStringList(){
		if (tourneyList.size() <= 0){return "No participants yet";}
		else {
			String text = "";
			for (int i = 0; i < tourneyList.size(); i ++){
				text += i + " " + tourneyList.get(i).fullName() + "\n";
			}
			return text;
		}
	}
	
	/**
	 * Print the current pairings, tables along with their results
	 * @return	A string
	 */
	private String printPairings(){
		String tempText2 = "Round " + curRound;
		tempText2 += "\nTable\tPlayer\n";
		for (int i = 0; i < tablePair.length; i ++){
			if (tablePair[i][0] == null){
				//Do nothing if there's nothing
			}
			else if (tablePair[i][1] == null){//Player with no opponent
				Player player3 = tablePair[i][0];
				tempText2 += "This lucky person whose name is " + player3.fullName()
						+ ", gets a bye.";
			}
			else if (tablePair[i][1] != null){//Make sure they're actually paired up
				Player player1 = tablePair[i][0];
				Player player2 = tablePair[i][1];
				tempText2 += i + "-----------" + player1.fullName() + " ["
						+ player1.roundResult(curRound -1)  + "]\n"
						+ "\t" + player2.fullName() + " ["
						+ player2.roundResult(curRound -1)
						+ "]\n";//Check spacing
			}
		}
		return tempText2;
	}
	//For ease of change in case output has errors
	private void displayPairings(){
		output.setText(printPairings());
	}
	private void displayPairings(String additionalText){
		output.setText(additionalText + printPairings());
	}
	private void displayMessage(String message){
		output.setText(message);
	}
	private void displayErrorMessage(String message){
		error.setText(message);
	}
	/**
	 * Set where it will output info
	 * @param area	Output area
	 */
	public void setOutputArea(OutputArea area){
		output = area;
	}
	/**
	 * Set where it will output errors
	 * @param area	Error output
	 */
	public void setErrorArea(ErrorArea area){
		error = area;
	}
	/**
	 * If the tournament is currently underway, return true, else return false
	 * @return
	 */
	public boolean inSession(){
		return (curRound > 0);
	}
}
