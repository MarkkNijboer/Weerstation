import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class DatabaseQueue {
	private static StringBuilder query = new StringBuilder();
	private static int count = 0;
	
	public static synchronized void addToQueue(StringBuilder subQuery, int incommingCount) {
		count = count + incommingCount;
		query.append(subQuery);
		return;
	}
	
	public static void getQueue() {
		
		if(query.length() != 0) {
			StringBuilder builder = new StringBuilder("INSERT INTO `measurements` (`stn`, `date`, `time`, `dewp`, `stp`, `temp`, `slp`, `visib`, `wdsp`, `prcp`, `sndp`, `frshtt`, `cldc`, `wnddir`) VALUES ");
			query.setLength(Math.max(query.length() - 1, 0));
			
			builder.append(query);
			String mainQuery = query.toString();
			
			PrintWriter writer;
			try {
				writer = new PrintWriter("/home/mark/Desktop/file.txt", "UTF-8");
				writer.print(mainQuery);
				writer.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println(count);
			
			count = 0;
			
			System.exit(0);
			
			//executeQuery(mainQuery);
			
			query.setLength(0);
		}
	}
	
	private static void executeQuery(String theQuery) {
		try {
			String host = "";
			String username = "";
			String password = "";
			
			Connection conn = DriverManager.getConnection(host, username, password);
			Statement stmt = conn.createStatement();
			stmt.executeQuery(theQuery);
			
			conn.close();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
