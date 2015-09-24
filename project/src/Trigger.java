import java.net.ServerSocket;
import java.net.Socket;


public class Trigger {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(7789);
			
			while (true) {
				Socket clientSocket = server.accept();
				(new XMLparser(clientSocket)).start();
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
