
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Main {
	
	//Dota 2 played with checker
	//Checks your last played match if you have played before with the others within the same match
	//Powered by OpenDota API
	

	public static void main(String[] args) throws IOException, org.json.simple.parser.ParseException {

		// Insert your Dota2 profile ID
		int myAccountId = 71373154;

		long matchId = 0;
		ArrayList<Long> accountIds = new ArrayList<Long>();
		ArrayList<String> newUrls = new ArrayList<String>();
		ArrayList<Long> matchIds = new ArrayList<Long>();
		ArrayList<String> names = new ArrayList<String>();

		ArrayList<Long> whitelistIds = new ArrayList<Long>();
		
		//This list is used to whitelist (e.g your friends) IDs
		whitelistIds.add((long) 84181635);
		whitelistIds.add((long) 70852572);
		whitelistIds.add((long) 52771263);
		whitelistIds.add((long) 16461605);
		whitelistIds.add((long) 71373154);

		JSONParser parser = new JSONParser();
		// Get last match ID
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					"https://api.opendota.com/api/players/" + myAccountId + "/matches").openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				Object obj = parser.parse(inputLine);
				JSONArray jsonArray = (JSONArray) obj;
				JSONObject jsonObject = (JSONObject) jsonArray.get(0);
				matchId = (long) jsonObject.get("match_id");

			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Get players in the match
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(
					"https://api.opendota.com/api/matches/" + matchId).openConnection();
			connection.addRequestProperty("User-Agent", "Mozilla/4.76");
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				Object obj = parser.parse(inputLine);
				JSONObject jsonObject = (JSONObject) obj;
				JSONArray msg = (JSONArray) jsonObject.get("players");

				for (int i = 0; i < msg.size(); i++) {
					JSONObject result = (JSONObject) msg.get(i);
					accountIds.add((Long) result.get("account_id"));
					if (!whitelistIds.contains(result.get("account_id")) && result.get("personaname") != "null"
							&& result.get("personaname") != null) {
						names.add((String) result.get("personaname"));
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Making urls for played with fetching from OpenDota API

		String playedWithUrl = "https://api.opendota.com/api/players/" + myAccountId + "/matches?";
		for (int i = 0; i < accountIds.size(); i++) {
			if (accountIds.get(i) != null && !whitelistIds.contains(accountIds.get(i))) {
				newUrls.add(playedWithUrl + "included_account_id=" + accountIds.get(i));
			}
		}

		// Get played with

		for (int j = 0; j < newUrls.size(); j++) {

			String name = names.get(j);

			try {
				HttpURLConnection connection = (HttpURLConnection) new URL(newUrls.get(j)).openConnection();
				connection.addRequestProperty("User-Agent", "Mozilla/4.76");
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String inputLine;
				
				while ((inputLine = in.readLine()) != null) {
					Object obj = parser.parse(inputLine);
					JSONArray jsonArray = (JSONArray) obj;

					for (int i = 0; i < jsonArray.size(); i++) {
						if (jsonArray.size() != 1) {
							JSONObject result = (JSONObject) jsonArray.get(i);
							matchIds.add((Long) result.get("match_id"));
						}
					}
					System.out.println(name);
					if (jsonArray.size() == 1) {
						System.out.println("No matches found!");
					} else {
						for (int i = 0; i < matchIds.size(); i++) {
							System.out.println("https://www.dotabuff.com/matches/" + matchIds.get(i));
						}
					}
					matchIds.removeAll(matchIds);
				}
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
