package cms.org;


import android.os.Bundle;
import android.preference.Preference;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;


public class settings extends PreferenceActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.settings);
	 
	    CheckBoxPreference btToggle = (CheckBoxPreference) findPreference("bttogle");
    
	    btToggle.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			
			public boolean onPreferenceClick(Preference preference) {
				Toast.makeText(getBaseContext(), "You turned me on", Toast.LENGTH_LONG);
				return false;
			}
		});			
	    
	}
	    
	    
	 /*   CheckPreferenceClickListener clicker = new CheckPreferenceClickListener();
	    CheckBoxPreference btToggle = (CheckBoxPreference) findPreference("bttoggle");
	    
	    btToggle.setOnPreferenceClickListener(clicker);
	}
	
	class CheckPreferenceClickListener implements OnPreferenceClickListener {

		public boolean onPreferenceClick(Preference preference) {
			Toast.makeText(getBaseContext(), "You turned me on", Toast.LENGTH_LONG);
			return false;
		}

	}
*/
		
}


	