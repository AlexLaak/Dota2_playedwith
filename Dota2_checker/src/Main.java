
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {
	
	//Dota 2 played with checker
	//Checks your last played match if you have played before with the others within the same match
	//Powered by OpenDota API
	

	public static void main(String[] args) throws IOException, ParseException, InterruptedException {
		
		Checker check = new Checker();

		String playerId = args[0];

		if (playerId.length() < 1) {
			System.out.println("Invalid playerID!");
			System.out.println("");
			System.out.println("Example usage: Main 12346321");
			return;
		}

		try {
			System.out.println("Looking for last match for player: " + playerId + "\n");
			check.CheckLastPlayed(Integer.parseInt(playerId));
		}
		catch (NumberFormatException e){
			System.out.println("Invalid playerID!");
			System.out.println("");
			System.out.println("Example usage: Main 12346321");
			return;
		}
	}
}
