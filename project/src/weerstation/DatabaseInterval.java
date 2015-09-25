package weerstation;

public class DatabaseInterval extends Thread {
	public void run() {
		while(true) {
			try {
				DatabaseQueue.getQueue();
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
