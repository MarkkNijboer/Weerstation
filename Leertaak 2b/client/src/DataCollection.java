import java.util.ArrayList;
import java.util.HashMap;


public class DataCollection {
	public static ArrayList<HashMap<String, String>> list = new ArrayList<>();
	public static int threadCount;
	
	public static void collect(ArrayList<HashMap<String, String>> list) {
		DataCollection.list.addAll(list);
		decreaseCount();
	}
	
	public static void setThreads(int threadCount) {
		DataCollection.threadCount = threadCount;
	}
	
	public static synchronized void decreaseCount() {
		DataCollection.threadCount--;
		if(DataCollection.threadCount == 0) {
			System.out.println("Completed!");
			System.out.println("Total return size: " + list.size());
		}
	}
}
