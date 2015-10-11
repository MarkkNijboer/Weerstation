/**
 * 
 * Main klasse om server en SQL interval te starten.
 * 
 * 
 * @author Mark Nijboer
 * @version 25.9.2015
 * 
 */

package weerstation;

import java.net.ServerSocket;
import java.net.Socket;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Main {
	
	/**
	 * 
	 * main methode om de servers te starten en socketconnecties af te handelen
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("measurements"); 
        
        DatabaseQueue.setCollection(collection);
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
