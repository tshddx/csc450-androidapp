package cms.org;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

public class info extends Activity 
{
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info);
		
		//REMOVE - this is test code on how to get a value of the preferences
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);		
		Log.d("BT Discovery", settings.getString("btDeviceList", "NA"));
	}

}
