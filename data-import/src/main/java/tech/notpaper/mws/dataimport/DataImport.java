package tech.notpaper.mws.dataimport;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtil;
import org.json.JSONObject;

import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManager;
import tech.notpaper.mws.util.db.connectionmanagement.ConnectionManagerFactory;

public class DataImport {
	
	public static void main(String[] args) throws SQLException, IOException {
		String cardFile = args[0];
		String headersFile = args[1];
		String host = args[2];
		String user = args[3];
		String pass = args[4];
		String db = args[5];
		
		//read the headers JSON
		JSONObject headerData = new JSONObject(IOUtil.toString(new FileReader(headersFile)));
		JSONObject cardHeaders = headerData.getJSONObject("cards");
		JSONObject setHeaders = headerData.getJSONObject("sets");
		Map<String, Integer> cardHeads = new HashMap<>();
		Map<String, Integer> setHeads = new HashMap<>();
		
		for(String key : cardHeaders.keySet()) {
			cardHeads.put(key, cardHeaders.getInt(key));
		}
		
		for(String key : setHeaders.keySet()) {
			setHeads.put(key, setHeaders.getInt(key));
		}
		
		//read the csv and construct all the objects
		Reader in = new FileReader(cardFile);
		Iterable<CSVRecord> records = CSVFormat.newFormat('|').parse(in);//CSVFormat.DEFAULT.parse(in);
		boolean didHeaders = false;
		java.util.Set<Card> cards = new java.util.HashSet<>();
		java.util.Set<Set> sets = new java.util.HashSet<>();
		for(CSVRecord record : records) {
			if(!didHeaders) {
				didHeaders = true;
				continue;
			}
			
			//parse card data
			Map<String,String> cardData = new HashMap<>();
			for(Map.Entry<String, Integer> e : cardHeads.entrySet()) {
				cardData.put(e.getKey(), record.get(e.getValue()));
			}
			
			//parse set data
			Map<String,String> setData = new HashMap<>();
			for(Map.Entry<String, Integer> e : setHeads.entrySet()) {
				setData.put(e.getKey(), record.get(e.getValue()));
			}
			
			//construct the card and set objects and add them to their respective sets
			cards.add(new Card(cardData));
			sets.add(new Set(setData));
		}
		
		//build the connection
		ConnectionManager cManager = ConnectionManagerFactory.getMySqlConnectionManager(
				host, user, pass, db);
		
		//insert all of the objects
		for(Set s : sets) {
			s.insert(cManager.getConnection());
		}
		
		for(Card c : cards) {
			c.insert(cManager.getConnection());
		}
	}
}
