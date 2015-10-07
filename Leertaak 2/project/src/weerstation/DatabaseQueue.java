/**
 * 
 * Klasse om gegevens te bufferen en in de database te zetten.
 * 
 * 
 * @author Mark Nijboer
 * @version 25.9.2015
 * 
 */

package weerstation;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DatabaseQueue {
	private static List<Document> dataList = new ArrayList<Document>();
	private static List<Document> buffer = new ArrayList<Document>();
	private static int count = 0;
	private static boolean locked;
	
	/**
	 * Methode om een deel van een query te bufferen en de hoeveelheid queries bij te houden.
	 * 
	 * @param subQuery			Een deel van de query die 10 inserts bevat
	 * @param incommingCount	Het aantal inserts in het deel van de query
	 */
	public static synchronized void addToQueue(List<Document> documents, int incommingCount) {
		if(!locked) {
			if(buffer.size() != 0) {
				count = count + incommingCount;
				dataList.addAll(buffer);
				buffer.clear();
				dataList.addAll(documents);
			} else {
				count = count + incommingCount;
				dataList.addAll(documents);
			}
			
		} else {
			count = count + incommingCount;
			buffer.addAll(documents);
		}
		return;
	}
	
	/**
	 * Methode om de echte query uit de buffer te halen en klaar te zetten.
	 */
	public static synchronized void getQueue() {
		
		locked = true;
		List<Document> data = new ArrayList<Document>(dataList);
		dataList.clear();
		locked = false;
			
		if(data.size() != 0) {
			
			System.out.println(count);
			
			count = 0;
			executeQuery(data);
		}
	}
	
	/**
	 * Methode om connectie te maken met de MySQL database en de query met ongeveer 8000 inserts uit te voeren.
	 * 
	 * @param theQuery		De complete query die uitgevoerd moet worden op de database
	 */
	private static void executeQuery(List<Document> data) {
		
		MongoClient mongoClient = new MongoClient("localhost");
        MongoDatabase db = mongoClient.getDatabase("test");
        MongoCollection<Document> collection = db.getCollection("measurements"); 
        
        collection.insertMany(data);
        
        mongoClient.close();
        
	}
}
