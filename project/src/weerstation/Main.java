/**
 * 
 * Main klasse om server en SQL interval te starten.
 * 
 * 
 * @author Mark Nijboer
 * @author Rick van der Poel
 * @author Kevin Haitsema
 * @version 25.9.2015
 * 
 */

package weerstation;

import java.net.ServerSocket;
import java.net.Socket;


public class Main {
	
	/**
	 * 
	 * main methode om de servers te starten en socketconnecties af te handelen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			ServerSocket server = new ServerSocket(7789);
			
			(new DatabaseInterval()).start();
			
			while (true) {
				Socket clientSocket = server.accept();
				(new ClientConnection(clientSocket)).start();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
