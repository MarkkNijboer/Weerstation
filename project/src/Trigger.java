import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Trigger {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(7789);
			
			while (true) {
				Socket clientSocket = server.accept();
				BufferedReader in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()));
		    	StringBuilder xml = new StringBuilder();
			    try
			    {	
			    	int t = 0;
			    	String line;
			    	Boolean recording = false;
			    	while ((line = in.readLine()) != null) {
			    		if(line.contains("<?xml version=\"1.0\"?>")) {
			    			recording = true;
			    		}
			    		
			    		if(recording) {
			    			xml.append(line);
			    		}
			    		
			    		if(line.contains("</WEATHERDATA>")) {
			    			recording = false;
			    			String xmlString = xml.toString();
			    			xml.setLength(0);
			    		}
			    	}
			    	String xmlData = xml.toString();
			    	System.out.println(xmlData);
			    } catch(Exception e) {
			    	System.out.println(e.getMessage());
			    }
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
