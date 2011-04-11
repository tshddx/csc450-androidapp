package cms.org;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;


public class CMS extends Activity  implements OnClickListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);    
    
    View aboutButton = findViewById(R.id.btnAbout);
    View btButton = findViewById(R.id.btnBT); 
    View webButton = findViewById(R.id.btnWeb);
    View infoButton = findViewById(R.id.btnAbout);
    
    aboutButton.setOnClickListener(this);
    btButton.setOnClickListener(this);
    webButton.setOnClickListener(this);
    infoButton.setOnClickListener(this);
    
    }
    
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.btnAbout:
    			Intent iAbout = new Intent(this, about.class);
    			startActivity(iAbout);
    			break;
    			
    		case R.id.btnInfo:
    			Intent iInfo = new Intent(this, info.class);
    			startActivity(iInfo);
    			break;
    			
    		case R.id.btnBT:
    			break;
    	
    		case R.id.btnWeb:
    			break;
    		
    		
    	}    
    }
    

}
