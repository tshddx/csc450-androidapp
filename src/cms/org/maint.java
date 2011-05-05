package cms.org;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import cms.org.info.MyOnItemSelectedListener;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class maint extends Activity
{
	String user;
	String password;
	String vin[];
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maint);
		
		// REMOVE - this is test code on how to get a value of the preferences
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		Log.d("BT Discovery", settings.getString("btDeviceList", "NA"));
		
		//BT bt = new BT(getBaseContext(), null, settings.getString("btDeviceList","NA"));
		//bt.startBT();
		//bt.connectBT();
		//bt.connectStreams();
		//Log.d("BT Discovery","Reading BT dongle");
		//bt.read();

		// Get username and password from settings.
		user = settings.getString("username","user" );
		password = settings.getString("userpass", "password");
		//user = "admin";
		//password = "password";
		
		// Build URL to login and get all vehicles.
		String url = "http://cars.tshaddox.com/api/vehiclelist?username=";
		url += user;
		url += "&password=";
		url += password;
		url += "&hax";

		System.out.println(url);
		Log.d("vin", url);

		try {
			Log.d("vin", "Just inside.");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());
			
			// normalize text representation
			doc.getDocumentElement().normalize();
			Log.d("vin", "Root element of the doc is "
					+ doc.getDocumentElement().getNodeName());

			NodeList vehicleList = doc.getElementsByTagName("vehicle");
			int totalVehicles = vehicleList.getLength();
			Log.d("vin", "Total no of vehicles : " + totalVehicles);

			String carName[] = new String[totalVehicles];
			vin = new String[totalVehicles];
			Log.d("vin", "1");
			for (int i = 0; i < vehicleList.getLength(); i++) {

				Node vehicleNode = vehicleList.item(i);
				if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
					Element vehicleElement = (Element) vehicleNode;
					
					// ------ Vin
					NodeList vinList = vehicleElement
							.getElementsByTagName("vin");
					Element vinElement = (Element) vinList.item(0);

					NodeList vinTextList = vinElement.getChildNodes();;
					vin[i] = (String) vinTextList.item(0).getNodeValue()
					.trim();

					// ------ Name
					NodeList nameList = vehicleElement
							.getElementsByTagName("name");
					Element nameElement = (Element) nameList.item(0);

					NodeList nameTextList = nameElement.getChildNodes();
					carName[i] = (String) nameTextList.item(0).getNodeValue()
							.trim();
	
				}
			}
			Spinner spinner = (Spinner) findViewById(R.id.spinner2);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, carName);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(adapter);
		    spinner.setOnItemSelectedListener(new MyOnItemSelectedListener());
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
		
	}// End OnCreate
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	
	    	String maint[][];
	    	String s = "";
	    	
			// Build URL to login and get all vehicles.
			String url = "http://cars.tshaddox.com/api/vehicle?username=";
			url += user;
			url += "&password=";
			url += password;
			url += "&vin=";
			url += vin[pos];
			url += "&hax";

			System.out.println(url);
			Log.d("vin", url);

			try {
				Log.d("vin", "Just inside.");
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new URL(url).openStream());
				
				// normalize text representation
				doc.getDocumentElement().normalize();
				Log.d("vin", "Root element of the doc is "
						+ doc.getDocumentElement().getNodeName());

				NodeList vehicleList = doc.getElementsByTagName("vehicle");
				int totalVehicles = vehicleList.getLength();

				Node vehicleNode = vehicleList.item(0);
				if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
					Element vehicleElement = (Element) vehicleNode;
					
					// ------ Maint
					NodeList maintAlertList = vehicleElement
							.getElementsByTagName("alert");
					int totalAlerts = maintAlertList.getLength();
					maint = new String[totalAlerts][2];
					Log.d("vin", "before for loop " + totalAlerts);
					for( int i = 0; i < totalAlerts; i++ ) {
						Node alertNode = maintAlertList.item(i);
						Log.d("vin", "" + i);
						if (alertNode.getNodeType() == Node.ELEMENT_NODE) {
							Element alertElement = (Element) alertNode;
							Log.d("vin", "" + i);
							// ---- Category
							NodeList catList = alertElement
								.getElementsByTagName("category");
							Element catElement = (Element) catList.item(0);
							NodeList catTextList = catElement.getChildNodes();
							maint[i][0] = (String) catTextList.item(0).getNodeValue()
								.trim();
							Log.d("vin", maint[i][0]);	
							// ---- Due
							NodeList dueList = alertElement
								.getElementsByTagName("due_when_odometer_at");
							Element dueElement = (Element) dueList.item(0);
							NodeList dueTextList = dueElement.getChildNodes();
							maint[i][1] = (String) dueTextList.item(0).getNodeValue()
								.trim();
							
							Log.d("vin", maint[i][1]);
							
							s += maint[i][0] + " is due at " + maint[i][1] + " miles. \n\n";
							Log.d("vin", s);
						}
					}
				}
    	
				TextView maintTextView = (TextView) findViewById(R.id.maintTextField);
				maintTextView.setText(s);
    	
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
	    public void onNothingSelected(AdapterView parent) {
		      // Do nothing.
		}	
	}
}

