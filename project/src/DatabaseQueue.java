
public class DatabaseQueue {
	private static StringBuilder query = new StringBuilder();
	
	public static synchronized void addToQueue(StringBuilder subQuery) {
		query.append(subQuery);
		return;
	}
	
	public static void getQueue() {
		StringBuilder builder = new StringBuilder("INSERT INTO `database` (`STN`, `DATE`, `TIME`, `DEWP`, `STP`, `TEMP`, `SLP`, `VISIB`, `WDSP`, `PRCP`, `SNDP`, `SNDP`, `FRSHTT`, `NDDIR`, `CLDC`, `WNDDIR`) VALUES ");
		query.setLength(Math.max(query.length() - 1, 0));
		
		builder.append(query);
		String mainQuery = query.toString();
		
		// System.out.println(mainQuery);
		
		query.setLength(0);
	}
}
