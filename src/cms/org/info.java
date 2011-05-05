package cms.org;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class info extends Activity {

	String user;
	String password;
	String info[][];


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
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
		Log.d("info", url);

		try {
			Log.d("info", "Just inside.");
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new URL(url).openStream());
			
			// normalize text representation
			doc.getDocumentElement().normalize();
			Log.d("info", "Root element of the doc is "
					+ doc.getDocumentElement().getNodeName());

			NodeList vehicleList = doc.getElementsByTagName("vehicle");
			int totalVehicles = vehicleList.getLength();
			Log.d("info", "Total no of vehicles : " + totalVehicles);

			String carName[] = new String[totalVehicles];
			info = new String[totalVehicles][7];
			
			for (int i = 0; i < vehicleList.getLength(); i++) {

				Node vehicleNode = vehicleList.item(i);
				if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
					Element vehicleElement = (Element) vehicleNode;
					
					// ------ Vin
					NodeList vinList = vehicleElement
							.getElementsByTagName("vin");
					Element vinElement = (Element) vinList.item(0);

					NodeList vinTextList = vinElement.getChildNodes();
					info[i][0]= (String) vinTextList.item(0).getNodeValue()
					.trim();

					// ------ Make
					NodeList makeList = vehicleElement
							.getElementsByTagName("make");
					Element makeElement = (Element) makeList.item(0);

					NodeList makeTextList = makeElement.getChildNodes();
					info[i][1]= (String) makeTextList.item(0).getNodeValue()
						.trim();
					
					// ------ Model
					NodeList modelList = vehicleElement
							.getElementsByTagName("model");
					Element modelElement = (Element) modelList.item(0);

					NodeList modelTextList = modelElement.getChildNodes();
					info[i][2] = (String) modelTextList.item(0)
						.getNodeValue().trim();
					
					// ------ Year
					NodeList yearList = vehicleElement
							.getElementsByTagName("year");
					Element yearElement = (Element) yearList.item(0);

					NodeList yearTextList = yearElement.getChildNodes();
					info[i][3] = (String) yearTextList.item(0).getNodeValue()
					.trim();
					
					// ------ Name
					NodeList nameList = vehicleElement
							.getElementsByTagName("name");
					Element nameElement = (Element) nameList.item(0);

					NodeList nameTextList = nameElement.getChildNodes();
					carName[i] = (String) nameTextList.item(0).getNodeValue()
							.trim();
					
					// ------ MPG
					NodeList mpgList = vehicleElement
							.getElementsByTagName("mileage");
					Element mpgElement = (Element) mpgList.item(0);

					NodeList mpgTextList = mpgElement.getChildNodes();
					info[i][4] = (String) mpgTextList.item(0).getNodeValue()
					.trim();
					
					// ------ Carbon Footprint
					NodeList cfpList = vehicleElement
							.getElementsByTagName("carbon");
					Element cfpElement = (Element) cfpList.item(0);

					NodeList cfpTextList = cfpElement.getChildNodes();
					info[i][5] = (String) cfpTextList.item(0).getNodeValue()
					.trim();
					
					// ------ Desc
					NodeList descList = vehicleElement
							.getElementsByTagName("description");
						
					Element descElement = (Element) descList.item(0);
						
					NodeList descTextList = descElement.getChildNodes();
					Log.d("info", "This: " + descTextList.item(0));
					if (descTextList.item(0) != null) {	
						info[i][6] = (String) descTextList.item(0).getNodeValue()
							.trim();
					}						
				}
			}
			Spinner spinner = (Spinner) findViewById(R.id.spinner1);
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
		
	}
	
	
	public class MyOnItemSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	TextView vinTextView = (TextView) findViewById(R.id.vinTextField);
	    	TextView makeTextView = (TextView) findViewById(R.id.makeTextField);
	    	TextView modelTextView = (TextView) findViewById(R.id.modelTextField);
	    	TextView yearTextView = (TextView) findViewById(R.id.yearTextField);
	    	TextView mpgTextView = (TextView) findViewById(R.id.mpgTextField);
	    	TextView cfpTextView = (TextView) findViewById(R.id.cfpTextField);
	    	TextView descTextView = (TextView) findViewById(R.id.descTextField);


	    	vinTextView.setText(info[pos][0]);
	    	makeTextView.setText(info[pos][1]);
	    	modelTextView.setText(info[pos][2]);
	    	yearTextView.setText(info[pos][3]);
	    	mpgTextView.setText(info[pos][4]);
	    	cfpTextView.setText(info[pos][5]);
	    	descTextView.setText(info[pos][6]);
	    	
	    	
	    	
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }

	}
}
