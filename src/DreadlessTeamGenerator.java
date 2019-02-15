import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class DreadlessTeamGenerator {
	// The Random object used for the generation of the teams
	private static final Random RAND = new Random();
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		// Create and add entries to the allPlayers list
		List<String> allPlayers = new ArrayList<>();
		
		System.out.println("Please enter the name of every player");
		System.out.println("Press enter after each entry, and add a blank entry once finished:");
		while(sc.hasNextLine()) {
			String s = sc.nextLine().trim();
			if(s.length() == 0)
				break;
			
			allPlayers.add(s);
		}
		
		// For debug purposes
		if(allPlayers.isEmpty())
			allPlayers = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
		
		System.out.println();
		
		// Create and add entries to the illegal list
		List<String> illegal = new ArrayList<>();
		
		System.out.println("Please enter the list of players that should not be in the same team (if possible)");
		System.out.println("Press enter after each entry, and add a blank entry once finished:");
		while(sc.hasNextLine()) {
			String s = sc.nextLine().trim();
			if(s.length() == 0)
				break;
			
			// Make sure the illegal player is also in the allPlayers list
			if(!allPlayers.contains(s)) {
				System.out.println("That player isn't in the main player list!");
				continue;
			}
			
			illegal.add(s);
		}
		
		// For debug purposes
		if(illegal.isEmpty())
			illegal = Arrays.asList("a", "b", "c");
		
		// Create and shuffle the teams
		List<String> a = new ArrayList<>();
		List<String> b = new ArrayList<>();
		shuffleTeams(allPlayers, illegal, a, b);
		
		// Whether or not team A starts as CT
		boolean teamACT = RAND.nextBoolean();
		
		// Print the teams
		System.out.println();
		System.out.println("Team A:");
		printTeam(a);
		System.out.println("Team A begins as " + (teamACT ? "CT" : "T") + "!");
		
		System.out.println();
		System.out.println("Team B:");
		printTeam(b);
		System.out.println("Team B begins as " + (teamACT ? "T" : "CT") + "!");
		
		sc.close();
	}
	
	/**
	 * Shuffles the players in allPlayers into teamA and teamB.
	 * Players in the illegal list will be separated from each other as much as possible.
	 * This method does not alter the allPlayers and illegal lists.
	 * 
	 * @param allPlayers All players to shuffle into team A and team B.
	 * @param illegal The players to separate as much as possible. All these players should be in the allPlayers list.
	 * @param teamA Team A array to put the players in.
	 * @param teamB Team B array to put the players in.
	 */
	public static void shuffleTeams(List<String> allPlayers, List<String> illegal, List<String> teamA, List<String> teamB) {
		allPlayers = new ArrayList<>(allPlayers);
		illegal = new ArrayList<>(illegal);
		
		Collections.shuffle(allPlayers, RAND);
		Collections.shuffle(illegal, RAND);
		
		if(illegal.size() % 2 != 0) {
			String player;
			do {
				player = allPlayers.get(RAND.nextInt(allPlayers.size()));
			}while(illegal.contains(player));
			illegal.add(player);
		}
		
		for(int i = 0; i < illegal.size(); i++) {
			String player = illegal.get(i);
			List<String> team = i % 2 == 0 ? teamA : teamB;
			team.add(player);
			
			allPlayers.remove(player);
		}
		
		for(int i = 0; i < allPlayers.size(); i++) {
			String player = allPlayers.get(i);
			List<String> team = (i+1) % 2 == 0 ? teamA : teamB;
			team.add(player);
		}
	}
	
	/**
	 * Prints the team to the console.
	 * 
	 * @param team The team to print.
	 */
	public static void printTeam(List<String> team) {
		for(String player : team) {
			System.out.println(player);
		}
	}
}
