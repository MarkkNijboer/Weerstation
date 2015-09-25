package weerstation;

import java.util.HashMap;
import java.util.Map.Entry;


public class CorrectData {
	public static HashMap<String, String> correct(HashMap<String, String> data) {
		for(Entry<String, String> entry : data.entrySet()) {
		    String key = entry.getKey();
		    String value = entry.getValue();
		    
		    if(value == "") {
		    	data.put(key, "0.0");
		    }

		}
		return data;
	}
}
