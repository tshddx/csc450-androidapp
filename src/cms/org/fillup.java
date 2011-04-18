package cms.org;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class fillup extends Activity
{
	private EditText etOdometer;
	private EditText etGallons;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fillup);
		
		etOdometer = (EditText)findViewById(R.id.txtMiles);
		etGallons = (EditText)findViewById(R.id.txtGallons);
		
		View fillupButton = findViewById(R.id.btnFillUp);
		fillupButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v){
				// TODO Auto-generated method stub
				String baseurl = "http://cars.tshaddox.com/api/fillup";
				String username = "?username=admin";
				String password = "&password=password";
				String vin = "&vin=qwerty";
				String odometer= "&odometer=";
				String gallons ="&gallons=";
				String fullurl ="";
				
				odometer += etOdometer.getText().toString();
				gallons += etGallons.getText().toString();
				
				fullurl = fullurl += baseurl + username + password + vin + odometer + gallons;
				
				System.out.println(fullurl.toString());
				
				try{
					
				
				URL url = new URL(fullurl);
				URLConnection connection = url.openConnection();
				
				BufferedReader in = new BufferedReader(
		                new InputStreamReader(
		                connection.getInputStream()));
				
				String inputLine="";

				while ((in.readLine()) != null)
				{
					inputLine += in.readLine();
				}
					
				in.close();
				
				

				TextView t = (TextView) findViewById(R.id.output);
				t.setText(inputLine);
				}
				catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
				
				
			}
		
		});
	}
	
}
