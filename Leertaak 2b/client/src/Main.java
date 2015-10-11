import java.io.File;
import java.util.HashMap;

public class Main {
	public static void main(String[] args) {
		
		File databaseFolder = new File("../../DB");
		if(!databaseFolder.exists()) {
			System.out.println(databaseFolder.getAbsolutePath());
			System.err.println("Database folder not found");
			System.exit(0);
		}
		
		String searchQuery;
		if(args.length == 1) {
			searchQuery = args[0];
		} else {
		    searchQuery = "stn=710750";
		}
		
		
		HashMap<Integer, String> queryMap = new HashMap<>();
		if(searchQuery.contains("&")) {
			String result[] = searchQuery.split("&");
			for(int i = 0; i < result.length; i++) {
				if(result[i].contains("=")) {
					String subQuery[] = result[i].split("=");
					try {
						int number = getParamNumber(subQuery[0]);
						queryMap.put(number, subQuery[1]);
					} catch(Exception e) {}
				}
			}
		} else {
			if(searchQuery.contains("=")) {
				String subQuery[] = searchQuery.split("=");
				try {
					int number = getParamNumber(subQuery[0]);
					queryMap.put(number, subQuery[1]);
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		DBParser.setQuery(queryMap);
		
		int threadCount = 0;
		for (File fileEntry : databaseFolder.listFiles()) {
			
			threadCount++;
			
	        FileLister list = new FileLister(fileEntry);
	        list.start();
	       
	    }
		
		DataCollection.setThreads(threadCount);
	}
	
	private static int getParamNumber(String param) throws Exception {
		if(param.toLowerCase().equals("stn")) {
			return 0;
		}
		if(param.toLowerCase().equals("date")) {
			return 1;
		}
		if(param.toLowerCase().equals("time")) {
			return 2;
		}
		if(param.toLowerCase().equals("dewp")) {
			return 3;
		}
		if(param.toLowerCase().equals("stp")) {
			return 4;
		}
		if(param.toLowerCase().equals("temp")) {
			return 5;
		}
		if(param.toLowerCase().equals("slp")) {
			return 6;
		}
		if(param.toLowerCase().equals("visib")) {
			return 7;
		}
		if(param.toLowerCase().equals("wdsp")) {
			return 8;
		}
		if(param.toLowerCase().equals("prcp")) {
			return 9;
		}
		if(param.toLowerCase().equals("sndp")) {
			return 10;
		}
		if(param.toLowerCase().equals("frshtt")) {
			return 11;
		}
		if(param.toLowerCase().equals("cldc")) {
			return 12;
		}
		if(param.toLowerCase().equals("wnddir")) {
			return 13;
		}
		throw new Exception("Unknown param");
	}
}
