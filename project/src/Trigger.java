import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Trigger {
	public static void main(String[] args) {
		try {
			ServerSocket server = new ServerSocket(7789);
			
			while (true) {
				Socket clientSocket = server.accept();
				BufferedReader in = new BufferedReader (new InputStreamReader (clientSocket.getInputStream ()));
				PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

			    String incommingText = "";
			    try
			    {
			        incommingText = in.readLine ();
			        System.out.println(incommingText);
			    } catch(Exception e) {}
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}

	}
}
