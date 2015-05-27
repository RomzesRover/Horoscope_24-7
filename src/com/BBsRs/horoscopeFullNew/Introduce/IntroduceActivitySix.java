package com.BBsRs.horoscopeFullNew.Introduce;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.BBsRs.horoscopeFullNew.ActivityLoader;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;

public class IntroduceActivitySix extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    
	    //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
	    this.setContentView(R.layout.activity_inroduce_six);
	    
	    //init butts
	    Button back = (Button)this.findViewById(R.id.buttonBack);
	    Button next = (Button)this.findViewById(R.id.buttonNext);
	    
	    //on butts 
	    next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), ActivityLoader.class);
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
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityFive.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
			    // stop curr activity
			    finish();
			}
		});
	}

}
