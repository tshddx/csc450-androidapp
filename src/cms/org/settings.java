package cms.org;



import java.util.Set;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.widget.Toast;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class settings extends PreferenceActivity
{
	//all BT overhead crap - BT BLOWS FYI
	BluetoothAdapter bt = BluetoothAdapter.getDefaultAdapter();
	public static final int REQUEST_BT_ENABLE = 1;
	private boolean btEnabled = false;	
	IntentFilter btFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.settings);
	 
	    //declaring forms
	    CheckBoxPreference btToggle = (CheckBoxPreference) findPreference("btToggle");
	    //ListPreference btDevices = (ListPreference) findPreference("btDevice");
	    CheckBoxPreference btDevices = (CheckBoxPreference) findPreference("btDevice");
	    Preference btDiscover = (Preference) findPreference("btDiscover");
	    
	    	    
	    //setting click events
	    btToggle.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			public boolean onPreferenceClick(Preference preference) {
				turnOnBT();			
				return true;
			}
		});		
	    
	    btDevices.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			public boolean onPreferenceClick(Preference preference) {
				searchPairedBT();
				return true;
			}
		});
	    
	    btDiscover.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			public boolean onPreferenceClick(Preference preference) {
				discoverBT();
				return true;
			}
		});
	    
	}
	
	private void turnOnBT()
	{
		Toast.makeText(getBaseContext(), "Turning On Bluetooth", Toast.LENGTH_SHORT).show();
		//bt.turnOnBluetooth();

		if(bt == null)
		{
			Toast.makeText(getBaseContext(), "Your phone does not support bluetooth.", Toast.LENGTH_LONG).show();
		}
		
		//bt supported so continue
		if(!bt.isEnabled())
		{
			Log.d("info","Attempting to start bt");
			Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBT,REQUEST_BT_ENABLE);					
		}
	}
	
	
	private void searchPairedBT()
	{
		Set<BluetoothDevice> paired = bt.getBondedDevices();
		if(paired.size() > 0)
		{
			for(BluetoothDevice d : paired)
			{
				Log.d("BT Paired",d.getName() + "  " + d.getAddress());
			}
		}
	}
	
	private void discoverBT()
	{
		if(bt.startDiscovery())
		{
			registerReceiver(btReceiver, btFilter);
			Toast.makeText(getBaseContext(), "Starting Bluetooth Discovery...Please wait", Toast.LENGTH_LONG).show();
		}
	}
	
	
	//used for general intent signal received (filter by case)
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode)
		{
			case REQUEST_BT_ENABLE:
				if(resultCode == Activity.RESULT_CANCELED)
				{//BUG: for HTC phones when BT is turned on it will not return Activity.RESULT_OK but RESULT_CANCELED
					Toast.makeText(getBaseContext(), "Bluetooth Turned On",Toast.LENGTH_SHORT).show();
					btEnabled = true;
				}
				else
				{
					Toast.makeText(getBaseContext(), "Bluetooth Not Turned On", Toast.LENGTH_SHORT).show();
					btEnabled = false;
				}
				break;
		}
	}
	
	//used to capture BT discovery process
	private final BroadcastReceiver btReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			
			if(BluetoothDevice.ACTION_FOUND.equals(action))
			{
				BluetoothDevice d = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.d("BT Discovery",d.getName() + "  " + d.getAddress());
			}
			
		}
	};
}

	