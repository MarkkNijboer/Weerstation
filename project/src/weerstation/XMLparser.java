package weerstation;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class XMLparser extends Thread {
	
	private String xml;
	
	public XMLparser(String xml) {
		this.xml = xml;
	}
	
	public void run() {
		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			try {
				Document doc = db.parse(is);
				NodeList data = doc.getElementsByTagName("MEASUREMENT");
				
				StringBuilder mb = new StringBuilder();
				
				int length = data.getLength();
				
				for (int i = 0; i < length; i++) {
			        Element measurement = (Element) data.item(i);
			        
			        HashMap<String, String> measurementData = new HashMap<String, String>();
			        
			        measurementData.put("STN", measurement.getElementsByTagName("STN").item(0).getTextContent());
			        measurementData.put("DATE", measurement.getElementsByTagName("DATE").item(0).getTextContent());
			        measurementData.put("TIME", measurement.getElementsByTagName("TIME").item(0).getTextContent());
			        measurementData.put("DEWP", measurement.getElementsByTagName("DEWP").item(0).getTextContent());
			        measurementData.put("STP", measurement.getElementsByTagName("STP").item(0).getTextContent());
			        measurementData.put("TEMP", measurement.getElementsByTagName("TEMP").item(0).getTextContent());
			        measurementData.put("SLP", measurement.getElementsByTagName("SLP").item(0).getTextContent());
			        measurementData.put("VISIB", measurement.getElementsByTagName("VISIB").item(0).getTextContent());
			        measurementData.put("WDSP", measurement.getElementsByTagName("WDSP").item(0).getTextContent());
			        measurementData.put("PRCP", measurement.getElementsByTagName("PRCP").item(0).getTextContent());
			        measurementData.put("SNDP", measurement.getElementsByTagName("SNDP").item(0).getTextContent());
			        measurementData.put("FRSHTT", measurement.getElementsByTagName("FRSHTT").item(0).getTextContent());
			        measurementData.put("CLDC", measurement.getElementsByTagName("CLDC").item(0).getTextContent());
			        measurementData.put("WNDDIR", measurement.getElementsByTagName("WNDDIR").item(0).getTextContent());
			        
			        measurementData = CorrectData.correct(measurementData);
			        
			        StringBuilder sb = new StringBuilder();
			        sb.append("\n(");
			        sb.append(measurementData.get("STN"));
			        sb.append(", '");
			        sb.append(measurementData.get("DATE"));
			        sb.append("', '");
			        sb.append(measurementData.get("TIME"));
			        sb.append("', ");
			        sb.append(measurementData.get("DEWP"));
			        sb.append(", ");
			        sb.append(measurementData.get("STP"));
			        sb.append(", ");
			        sb.append(measurementData.get("TEMP"));
			        sb.append(", ");
			        sb.append(measurementData.get("SLP"));
			        sb.append(", ");
			        sb.append(measurementData.get("VISIB"));
			        sb.append(", ");
			        sb.append(measurementData.get("WDSP"));
			        sb.append(", ");
			        sb.append(measurementData.get("PRCP"));
			        sb.append(", ");
			        sb.append(measurementData.get("SNDP"));
			        sb.append(", '");
			        sb.append(measurementData.get("FRSHTT"));
			        sb.append("', ");
			        sb.append(measurementData.get("CLDC"));
			        sb.append(", ");
			        sb.append(measurementData.get("WNDDIR"));
			        sb.append("),");
			        
			        mb.append(sb);
			    }
				
				DatabaseQueue.addToQueue(mb, length);
				
				
				
			} catch (SAXException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			System.out.println(e1.getMessage());
			e1.printStackTrace();
		}
	}
}
