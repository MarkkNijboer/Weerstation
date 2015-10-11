/**
 * 
 * Klasse om clientconnectie af te handelen
 * 
 * 
 * @author Mark Nijboer
 * @version 25.9.2015
 * 
 */

package weerstation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;


public class ClientConnection extends Thread{
	private Socket clientSocket;
	
	
	/**
	 * Constructor. Dit wordt gebruikt om de clientSocket op te slaan in de instantie.
	 * 
	 * @param clientSocket		De clientSocket
	 */
	public ClientConnection(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}
	
	
	/**
	 * 
	 * Methode om de connectie van de client af te handelen. De inkomende berichtenstroom wordt opgedeeld in volledige xml bestanden en doorgestuurd naar een apparte thread.
	 * 
	 */
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
