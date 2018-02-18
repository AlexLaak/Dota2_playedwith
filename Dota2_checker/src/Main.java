
import java.io.IOException;

import org.json.simple.parser.ParseException;

public class Main {
	
	//Dota 2 played with checker
	//Checks your last played match if you have played before with the others within the same match
	//Powered by OpenDota API
	

	public static void main(String[] args) throws IOException, ParseException, InterruptedException {
		
		Checker check = new Checker();
		
		// Insert your Dota2 profile ID
		check.CheckLastPlayed(16461605);

	}
}
