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
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	String year;
	String make;
	String model;
	String name;
	String mpg;
	String carbFp;
	String desc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		// REMOVE - this is test code on how to get a value of the preferences
		SharedPreferences settings = PreferenceManager
				.getDefaultSharedPreferences(this);
		Log.d("BT Discovery", settings.getString("btDeviceList", "NA"));

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
			Log.d("info", "Just inside.");
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

			String s[] = new String[totalVehicles];
			
			for (int i = 0; i < vehicleList.getLength(); i++) {

				Node vehicleNode = vehicleList.item(i);
				if (vehicleNode.getNodeType() == Node.ELEMENT_NODE) {
					Element vehicleElement = (Element) vehicleNode;

					// ------ Make
					NodeList makeList = vehicleElement
							.getElementsByTagName("make");
					Element makeElement = (Element) makeList.item(0);

					NodeList makeTextList = makeElement.getChildNodes();
					make = (String) makeTextList.item(0).getNodeValue()
							.trim();
					Log.d("info", "Make : " + make);
					// ------ Make
					NodeList modelList = vehicleElement
							.getElementsByTagName("model");
					Element modelElement = (Element) modelList.item(0);

					NodeList modelTextList = modelElement.getChildNodes();
					model = (String) modelTextList.item(0)
							.getNodeValue().trim();
					Log.d("info", "Model : " + model);
					// ------ Make
					NodeList yearList = vehicleElement
							.getElementsByTagName("year");
					Element yearElement = (Element) yearList.item(0);

					NodeList yearTextList = yearElement.getChildNodes();
					year = (String) yearTextList.item(0).getNodeValue()
							.trim();
					
					// ------ Name
					NodeList nameList = vehicleElement
							.getElementsByTagName("name");
					Element nameElement = (Element) nameList.item(0);

					NodeList nameTextList = nameElement.getChildNodes();
					name = (String) nameTextList.item(0).getNodeValue()
							.trim();
					
					// ------ MPG
					NodeList mpgList = vehicleElement
							.getElementsByTagName("mileage");
					Element mpgElement = (Element) mpgList.item(0);

					NodeList mpgTextList = mpgElement.getChildNodes();
					mpg = (String) mpgTextList.item(0).getNodeValue()
							.trim();
					
					// ------ Carbon Footprint
					NodeList cfpList = vehicleElement
							.getElementsByTagName("carbon");
					Element cfpElement = (Element) cfpList.item(0);

					NodeList cfpTextList = cfpElement.getChildNodes();
					carbFp = (String) cfpTextList.item(0).getNodeValue()
							.trim();
					
					// ------ Desc
					NodeList descList = vehicleElement
							.getElementsByTagName("description");
					Element descElement = (Element) descList.item(0);

					NodeList descTextList = descElement.getChildNodes();
					desc = (String) descTextList.item(0).getNodeValue()
							.trim();

					
					// ------ Make drop down.
					Log.d("info", "Year : " + year);

					Log.d("info", year + " " + make + " " + model);
					
					s[i] = name;
					
				}
			}
			Spinner spinner = (Spinner) findViewById(R.id.spinner1);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, s);
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
	     // Toast.makeText(parent.getContext(), "The planet is " +
	     //     parent.getItemAtPosition(pos).toString(), Toast.LENGTH_LONG).show();
	    	TextView vinTextView = (TextView) findViewById(R.id.vinTextField);
	    	TextView makeTextView = (TextView) findViewById(R.id.makeTextField);
	    	TextView modelTextView = (TextView) findViewById(R.id.modelTextField);
	    	TextView yearTextView = (TextView) findViewById(R.id.yearTextField);
	    	TextView mpgTextView = (TextView) findViewById(R.id.mpgTextField);
	    	TextView cfpTextView = (TextView) findViewById(R.id.cfpTextField);
	    	TextView descTextView = (TextView) findViewById(R.id.descTextField);

	    	
	    	vinTextView.setText(vin);
	    	makeTextView.setText(make);
	    	modelTextView.setText(model);
	    	yearTextView.setText(year);
	    	mpgTextView.setText(mpg);
	    	cfpTextView.setText(carbFp);
	    	descTextView.setText(desc);
	    	
	    	
	    	
	    }

	    public void onNothingSelected(AdapterView parent) {
	      // Do nothing.
	    }

	}
}
