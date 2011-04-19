package cms.org;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
				String password = "&password=passwo";
				String vin = "&vin=qwerty";
				String odometer= "&odometer=";
				String gallons ="&gallons=";
				String fullurl ="";
				
				odometer += etOdometer.getText().toString();
				gallons += etGallons.getText().toString();
				
				if(odometer.length() != 0 && gallons.length() != 0)
				{
		
					fullurl = baseurl + username + password + vin + odometer + gallons;
				
					System.out.println(fullurl.toString());
				
					try{
					
				
						HttpClient client = new DefaultHttpClient();
						HttpGet getmethod = new HttpGet(fullurl);
						HttpResponse response = client.execute(getmethod);
				
		
						BufferedReader in = new BufferedReader( new InputStreamReader(response.getEntity().getContent()));
						StringBuilder str = new StringBuilder();
						String line="";
				

						while ((line = in.readLine()) != null)
						{
							str.append(line);
						}
				
						System.out.println("Returned = " + str.toString());
					
						in.close();
						
//						Context mContext = this;
//						AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
//						builder.setMessage(str.toString())
//						       .setCancelable(false)
//						       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//						           public void onClick(DialogInterface dialog, int id) {
//						                dialog.cancel();
//						           }
//						       });
//						builder.show();
						
						
						TextView t = (TextView) findViewById(R.id.output);
						t.setText(str.toString());
						
					}
					catch(Exception ex)
					{
						System.out.println("error " + ex.getMessage());
					}
					
				}//end if
				
				
			}
		
		});
	}
	
}
