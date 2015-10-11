import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;


public class FileLister extends Thread {
	private File file;
	
	public FileLister(File file) {
		this.file = file;
	}
	
	
	public void run() {
		 if(file.getName().contains("database")) {
			 ArrayList<HashMap<String, String>> allData = new ArrayList<>();
			 ArrayList<String> lines = new ArrayList<>();
		 	try {
		 		long startTime = System.nanoTime();
		 		
	        	BufferedReader br = new BufferedReader(new FileReader(file));
	        	try {
	        	    String line = br.readLine();
	        	    
	        	    while (line != null) {
	        	    	lines.add(line);
	        	        line = br.readLine();
	        	    }
	        	    
	        	} finally {
	        	    br.close();
	        	}
	        	
	        	long endTime = System.nanoTime();
	        	long duration = (endTime - startTime);
	        	
	        	System.out.println("File opened for " + (duration/1000000) + " ms.");
	        	
	        	for(String line : lines) {
	        		HashMap<String, String> obj = DBParser.parseLine(line);
        	    	
        	    	if(obj != null) {
        	    		allData.add(obj);
        	    	}
	        	}
	        	
	        	DataCollection.collect(allData);
        	} catch(Exception e) {
        		System.out.println(e.getMessage());
        	}
        }
	}
}
