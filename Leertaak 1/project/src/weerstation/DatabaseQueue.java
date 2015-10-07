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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DatabaseQueue {
	private static StringBuilder query = new StringBuilder();
	private static StringBuilder buffer = new StringBuilder();
	private static int count = 0;
	private static boolean locked;
	
	/**
	 * Methode om een deel van een query te bufferen en de hoeveelheid queries bij te houden.
	 * 
	 * @param subQuery			Een deel van de query die 10 inserts bevat
	 * @param incommingCount	Het aantal inserts in het deel van de query
	 */
	public static synchronized void addToQueue(StringBuilder subQuery, int incommingCount) {
		if(!locked) {
			if(buffer.length() != 0) {
				count = count + incommingCount;
				query.append(buffer);
				buffer.setLength(0);
				query.append(subQuery);
			} else {
				count = count + incommingCount;
				query.append(subQuery);
			}
			
		} else {
			count = count + incommingCount;
			buffer.append(subQuery);
		}
		return;
	}
	
	/**
	 * Methode om de echte query uit de buffer te halen en klaar te zetten.
	 */
	public static synchronized void getQueue() {
		
		locked = true;
		StringBuilder q = new StringBuilder(query);
		query.setLength(0);
		locked = false;
			
		if(q.length() != 0) {
			StringBuilder builder = new StringBuilder();
			q.setLength(Math.max(q.length() - 1, 0));
			
			builder.append(q);
			String mainQuery = "INSERT INTO measurements (stn, date, time, dewp, stp, temp, slp, visib, wdsp, prcp, sndp, frshtt, cldc, wnddir) VALUES " + q.toString();
			
			System.out.println(count);
			
			count = 0;
			
			executeQuery(mainQuery);
		}
	}
	
	/**
	 * Methode om connectie te maken met de MySQL database en de query met ongeveer 8000 inserts uit te voeren.
	 * 
	 * @param theQuery		De complete query die uitgevoerd moet worden op de database
	 */
	private static void executeQuery(String theQuery) {
		try {
			String host = "jdbc:mysql://localhost:3306/weerstation";
			String username = "root";
			String password = "";
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = DriverManager.getConnection(host, username, password);
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(theQuery);
			
			conn.close();
		} catch(Exception e) {
			
			if(e.getMessage().contains("You have an error in your SQL syntax")) {
				System.out.println(e.getMessage());
				try {
					PrintWriter writer = new PrintWriter("/home/mark/Desktop/file.txt", "UTF-8");
					writer.println(theQuery);
					writer.close();
				} catch(Exception e1) {}
			} else {
				System.out.println(e.getMessage());
			}
		}
	}
}
