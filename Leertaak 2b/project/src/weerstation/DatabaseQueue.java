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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class DatabaseQueue {
	private static StringBuilder dataList = new StringBuilder();
	private static StringBuilder buffer = new StringBuilder();
	private static String path;
	private static int count = 0;
	private static boolean locked;
	
	/**
	 * Methode om een deel van een query te bufferen en de hoeveelheid queries bij te houden.
	 * 
	 * @param subQuery			Een deel van de query die 10 inserts bevat
	 * @param incommingCount	Het aantal inserts in het deel van de query
	 */
	public static synchronized void addToQueue(StringBuilder documents, int incommingCount) {
		if(!locked) {
			if(buffer.length() != 0) {
				count = count + incommingCount;
				dataList.append(buffer);
				buffer.setLength(0);
				dataList.append(documents);
			} else {
				count = count + incommingCount;
				dataList.append(documents);
			}
			
		} else {
			count = count + incommingCount;
			buffer.append(documents);
		}
		return;
	}
	
	/**
	 * Methode om de echte query uit de buffer te halen en klaar te zetten.
	 */
	public static synchronized void getQueue() {
		
		locked = true;
		StringBuilder data = new StringBuilder(dataList);
		dataList.setLength(0);
		locked = false;
			
		if(data.length() != 0) {
			
			System.out.println(count);
			
			count = 0;
			executeQuery(data.toString());
		}
	}
	
	public static void setDBFolder(String folder) {
		path = folder;
	}
	
	/**
	 * Methode om connectie te maken met de MySQL database en de query met ongeveer 8000 inserts uit te voeren.
	 * 
	 * @param theQuery		De complete query die uitgevoerd moet worden op de database
	 */
	private static void executeQuery(String data) {
		long startTime = System.nanoTime();
		boolean fileExists = false;
		int number = 0;
		String endPath = "";
		while(!fileExists) {
			String filePath = path + "/database" + number;
			File file = new File(filePath);
			if(!file.exists()) {
				endPath = filePath;
				fileExists = true;
				try {
					if (!file.isFile() && !file.createNewFile()) {
				        throw new IOException("Error creating new file: " + file.getAbsolutePath());
				    }
				} catch(Exception e) {
					System.out.println(e.getMessage());
				}
			} else {
				if(file.length() < 50000000){
					endPath = filePath;
					fileExists = true;
				} else {
					number++;
				}
			}
		}
		
		try {
		    Files.write(Paths.get(endPath), data.getBytes(), StandardOpenOption.APPEND);
		}catch (IOException e) {
			
			System.out.println("ER" + e.getMessage());
		    //exception handling left as an exercise for the reader
		}
		
		long endTime = System.nanoTime();
		long duration = (endTime - startTime) / 1000000;
		
		System.out.println("Duration: " + duration + "ms");
	}
}
