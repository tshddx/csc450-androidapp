package cms.org;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class info extends Activity {

	String user = "admin";
	String password = "password";
	String vin = "123abc";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);

		
		
		// REMOVE - this is test code on how to get a value of the preferences
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		Log.d("BT Discovery", settings.getString("btDeviceList", "NA"));
		
		BT bt = new BT(getBaseContext(), null, settings.getString("btDeviceList","NA"));
		bt.startBT();
		bt.connectBT();
		bt.connectStreams();
		Log.d("BT Discovery","Reading BT dongle");
		bt.read();

		String url = "http://cars.tshaddox.com/api/vehiclelist?username=";
		url += user;
		url += "&password=";
		url += password;
		// url += "&vin=";
		// url += vin;
		url += "&hax";

		System.out.println(url);
		Log.d("info", url);

		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());

			// normalize text representation
			doc.getDocumentElement().normalize();
			Log.d("info", "Root element of the doc is "
					+ doc.getDocumentElement().getNodeName());

			NodeList vehicleList = doc.getElementsByTagName("vehicles");
			int totalVehicles = vehicleList.getLength();
			Log.d("info", "Total no of vehicles : " + totalVehicles);

			for (int i = 0; i < vehicleList.getLength(); i++) {

				Node vehicleNode = vehicleList.item(i);
				if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
					Element vehicleElement = (Element) vehicleNode;

					// ------ Make
					NodeList makeList = vehicleElement
							.getElementsByTagName("make");
					Element makeElement = (Element) makeList.item(0);

					NodeList makeTextList = makeElement.getChildNodes();
					String make = (String) makeTextList.item(0).getNodeValue()
							.trim();
					Log.d("info", "Make : " + make);
					// ------ Make
					NodeList modelList = vehicleElement
							.getElementsByTagName("model");
					Element modelElement = (Element) modelList.item(0);

					NodeList modelTextList = modelElement.getChildNodes();
					String model = (String) modelTextList.item(0)
							.getNodeValue().trim();
					Log.d("info", "Model : " + model);
					// ------ Make
					NodeList yearList = vehicleElement
							.getElementsByTagName("year");
					Element yearElement = (Element) yearList.item(0);

					NodeList yearTextList = yearElement.getChildNodes();
					String year = (String) yearTextList.item(0).getNodeValue()
							.trim();
					Log.d("info", "Year : " + year);

					Log.d("info", year + " " + make + " " + model);
				}
			}
		} catch (SAXParseException err) {
			System.out.println("** Parsing error" + ", line "
					+ err.getLineNumber() + ", uri " + err.getSystemId());
			System.out.println(" " + err.getMessage());

		} catch (SAXException e) {
			Exception x = e.getException();
			((x == null) ? e : x).printStackTrace();

		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
}
