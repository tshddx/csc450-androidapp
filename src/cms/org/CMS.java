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
    
    View fillupButton = findViewById(R.id.btnFillup);
    View maintButton = findViewById(R.id.btnMaint);
    View infoButton = findViewById(R.id.btnInfo);
    View aboutButton = findViewById(R.id.btnAbout);

    fillupButton.setOnClickListener(this);
    maintButton.setOnClickListener(this);
    infoButton.setOnClickListener(this);
    aboutButton.setOnClickListener(this);
    
    }
    
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    		case R.id.btnFillup:
    			Intent iFillup = new Intent(this, fillup.class);
    			startActivity(iFillup);
    			break;
    			
    		case R.id.btnMaint:
    			Intent iMaint = new Intent(this, maint.class);
    			startActivity(iMaint);
    			break;
    			
    		case R.id.btnAbout:
    			Intent iAbout = new Intent(this, about.class);
    			startActivity(iAbout);
    			break;
    			
    		case R.id.btnInfo:
    			Intent iInfo = new Intent(this, info.class);
    			startActivity(iInfo);
    			break;
    		
    	}    
    }
    

}
