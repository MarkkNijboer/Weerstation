package weerstation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;


public class CorrectData {
	private static HashMap<String, ArrayList<HashMap<String, String>>> dataStorage = new HashMap<String, ArrayList<HashMap<String, String>>>();
	private static final int MAX_COUNTER = 30;
	
	public static void init() {
		
	}
	
	public static synchronized HashMap<String, String> correct(HashMap<String, String> data) {
		
		String stationnr = "0";
		ArrayList<String> missingValue = new ArrayList<String>();
		for(Entry<String, String> entry : data.entrySet()) {
			
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
			if(key == "stn") {
				stationnr = value;
			}
		    
		    if(value == "") {
		    	missingValue.add(key);
		    }

		}
		
		ArrayList<HashMap<String, String>> list = dataStorage.get(stationnr);
		
		if(list != null) {
			if(list.size() == MAX_COUNTER) {
				
				for(String key : missingValue) {
					data.put(key, "0.0");
				}
				
				list.add(data);
				list.remove(0);
				list.trimToSize();
			} else {
				
				for(String key : missingValue) {
					data.put(key, "0.0");
				}
				
				list.add(data);
			}
		} else {
			
			for(String key : missingValue) {
				data.put(key, "0.0");
			}
			
			list = new ArrayList<HashMap<String, String>>();
			list.add(data);
		}
		
		dataStorage.put(stationnr, list);
		
		
		return data;
	}
}
