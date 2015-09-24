

public class XMLparser extends Thread {
	
	private String xml;
	
	public XMLparser(String xml) {
		this.xml = xml;
	}
	
	public void run() {
		System.out.println(xml);
		// Magic
	}
}
