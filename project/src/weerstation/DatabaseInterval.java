/**
 * 
 * Klasse om de database inserts aan te roepen
 * 
 * 
 * @author Mark Nijboer
 * @author Rick van der Poel
 * @author Kevin Haitsema
 * @version 25.9.2015
 * 
 */

package weerstation;

public class DatabaseInterval extends Thread {
	
	/**
	 * Methode die wordt aangeroepen in een losse thread. Vervolgens zal deze methode elke seconde de statische methode getQueue() aanroepen om de gegevens in de database te zetten.
	 */
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
