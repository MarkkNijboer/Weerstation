package weerstation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientConnection extends Thread{
	private Socket clientSocket;
	
	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()));
	    	StringBuilder xml = new StringBuilder();
		    try
		    {	
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
		    			
		    			(new XMLparser(xml.toString())).start();
		    			
		    			xml.setLength(0);
		    		}
		    	}
		    } catch(Exception e) {
		    	System.out.println(e.getMessage());
		    }
		} catch(Exception e) {}
	}
}
