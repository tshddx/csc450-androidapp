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
    aboutButton.setOnClickListener(this);
    
    }
    
    public void onClick(View v)
    {
    	switch(v.getId())
    	{
    	
    	case R.id.btnAbout:
    		Intent i = new Intent(this, about.class);
    		startActivity(i);
    		break;
    	}    
    }
    

}
