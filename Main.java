package tourney;

//import java.util.Scanner;
/*
 * Things to implement
 * Manual Pairing
 * Check current standings
 * 
 */
public class Main {

	static PlayerList newList = new PlayerList();
//	static Scanner in = new Scanner(System.in);
//	static String s = "";
	static Display display;
	
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
        		display = new Display(newList);
            }
        });
	}
	
}
