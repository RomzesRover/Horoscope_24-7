package com.BBsRs.horoscopeFullNew.Introduce;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;

public class IntroduceActivityTwo extends BaseActivity {
	
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
        
        this.setContentView(R.layout.activity_inroduce_two);
	    
	    //init butts
	    Button back = (Button)this.findViewById(R.id.buttonBack);
	    final Button next = (Button)this.findViewById(R.id.buttonNext);
	    
	    //on butts 
	    next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityThree.class);
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
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityOne.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
			    // stop curr activity
			    finish();
			}
		});
	    
	    //dis next bttn
	    next.setEnabled(false);
	    
	    //init edit text name
	    final TextView textMask = (TextView)this.findViewById(R.id.textMask);
	    final EditText editTextName = (EditText)this.findViewById(R.id.editTextName);

	    //on ed text
	    editTextName.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   textMask.setVisibility(View.VISIBLE);
		    		   next.setEnabled(false);
	    		   } else {
	    			   next.setEnabled(true);
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   Editor ed = sPref.edit();   
	    		   ed.putString("preference_name", String.valueOf(s)); 	
	    		   ed.commit();
	    		   textMask.setVisibility(View.GONE);
	    	   }
	    	  });
	    
	    //set curr name
	    editTextName.setText(sPref.getString("preference_name", ""));
	}

}
