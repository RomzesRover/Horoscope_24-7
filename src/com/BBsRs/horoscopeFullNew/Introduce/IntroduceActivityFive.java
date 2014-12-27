package com.BBsRs.horoscopeFullNew.Introduce;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Spinner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.BBsRs.horoscopeFullNew.R;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;

public class IntroduceActivityFive extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;
    
    int check=0;
    
    //
    int k=0;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    
	    //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        if (sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("en"))
        	k=2; 
        else 
        	k=0;
        
	    this.setContentView(R.layout.activity_inroduce_five);
	    
	    //init butts
	    Button back = (Button)this.findViewById(R.id.buttonBack);
	    Button next = (Button)this.findViewById(R.id.buttonNext);
	    
	    //on butts 
	    next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivitySix.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			    // stop curr activity
			    finish();

			}
		});
	    
	    //on butts 
	    back .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityFour.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
			    // stop curr activity
			    finish();
			}
		});
	    
	    final Spinner provider = (Spinner)findViewById(R.id.spinnerProvider);
	    provider.setSelection(Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))-k);
	    
	    provider.setOnItemSelectedListener(new OnItemSelectedListener(){    
	    	@Override
	    	public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
	    		check++;
	    		if(check>1){
	    		Editor ed = sPref.edit();   
	    		ed.putString("preference_provider", String.valueOf(i+k)); 	
	    		ed.commit();
	    		}
	    	} 
	    	@Override     
	    	public void onNothingSelected(AdapterView<?> parentView) {}
	    }); 
	    
	}

}
