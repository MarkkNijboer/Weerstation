package weerstation;

import java.net.ServerSocket;
import java.net.Socket;


public class Main {
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
