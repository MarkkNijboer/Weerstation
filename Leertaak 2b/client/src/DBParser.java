import java.util.HashMap;
import java.util.Map.Entry;


public class DBParser {
	private static HashMap<Integer, String> query;
	
	public static void setQuery(HashMap<Integer, String> searchQuery) {
		query = searchQuery;
		System.out.println(query.toString());
	}
	
	public static HashMap<String, String> parseLine(String line) {
		int count = line.length() - line.replace(",", "").length();
		if(count == 13) {
			String elems[] = line.split(",");
			if(elems.length == 14) {
				HashMap<String, String> temp = new HashMap<String, String>();
				
				for(Entry<Integer, String> e : query.entrySet()) {
					if(!elems[e.getKey()].trim().equals(e.getValue())) {
						return null;
					}
				}
				
				temp.put("STN", elems[0].trim());
		        temp.put("DATE", elems[1].trim());
		        temp.put("TIME", elems[2].trim());
		        temp.put("DEWP", elems[3].trim());
		        temp.put("STP", elems[4].trim());
		        temp.put("TEMP", elems[5].trim());
		        temp.put("SLP", elems[6].trim());
		        temp.put("VISIB", elems[7].trim());
		        temp.put("WDSP", elems[8].trim());
		        temp.put("PRCP", elems[9].trim());
		        temp.put("SNDP", elems[10].trim());
		        temp.put("FRSHTT", elems[11].trim());
		        temp.put("CLDC", elems[12].trim());
		        temp.put("WNDDIR", elems[13].trim());
				
		        
		        
				return temp;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}
}
