package tourney;

import java.util.Scanner;

public class Main {

	static PlayerList newList = new PlayerList();
	static Scanner in = new Scanner(System.in);
	static String s = "";
	static Display display;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//newList.printList();
		
		Startup();
		//display = new Display(newList);
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		//display = new Display(newList);
            }
        });
	}
	
	public static void Startup(){
		boolean flag = true;
		while (flag){
			System.out.println("\nSelect what you want to do: \n"
					+ "n to enter names \n"
					+ "l to print current list\n"
					+ "d to delete an entry\n"
					+ "q to close program\n"
					+ "r to see how many rounds will run under current number of participants");
			s = in.nextLine();
			switch (s) {
			case "n":
				AddNameFunction();
				break;
			case "l":
				newList.printList();
				break;
			case "d":
				newList.printList();
				DeleteNameFunction();
				break;
			case "q":
				System.out.println("Thank you for using this program");
				//System.exit(0);
				flag = false;
				break;
			case "r":
				newList.SetRounds();
				break;
			default:
				System.out.println("Not recognised");
				//flag = false;
				break;
			}
		}
	}
	
	public static void AddNameFunction(){
		
		String first = "", last = "";
		boolean flag = true;
		while (flag){
			System.out.println("Enter first name please");
			s = in.nextLine();
			first = s;
			System.out.println(first + " accepted. Now enter last name");
			s = in.nextLine();
			last = s;
			System.out.println(last + " accepted.");
			System.out.println("Is " + first + " " + last + " correct?\n"
					+ "y for yes, n for no.");
			
			s = in.nextLine();
			if (s.compareToIgnoreCase("y") == 0){newList.AddPlayer(first, last);flag = false;}
			else if (s.compareToIgnoreCase("n") == 0){}
			else {System.out.println("Please type using one of the options given");}
		}
	}
	
	public static void DeleteNameFunction(){
		if (newList.GetSize() <= 0){
			System.out.println("Try adding some players.");
			return;
		}
		System.out.println("Which entry would you like to delete?");
		int i = -1;
		try {
			i = in.nextInt();
			newList.DeletePlayer(i);
			
		}
		catch (Exception e){
			System.out.println("Input a number please.");
		}
		in.nextLine();
	}
}
