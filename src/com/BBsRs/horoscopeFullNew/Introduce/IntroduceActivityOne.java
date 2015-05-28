package com.BBsRs.horoscopeFullNew.Introduce;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.CheckedTextView;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Fonts.HelvFont;
import com.BBsRs.horoscopeNewEdition.R;

public class IntroduceActivityOne extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;
    
    int check=0;
    
    AlertDialog alert = null;	
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    
	    //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
	    this.setContentView(R.layout.activity_inroduce_one);
	    
	    //init butts
	    Button back = (Button)this.findViewById(R.id.buttonBack);
	    Button next = (Button)this.findViewById(R.id.buttonNext);
	    
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
			    // stop curr activity
			    finish();
			}
		});
	    
	    final Spinner locale = (Spinner)findViewById(R.id.spinnerLocale);
	    
	    //set fonts for spinner
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.locales)) {

			public View getView(int position, View convertView, ViewGroup parent) {
				View v = super.getView(position, convertView, parent);
				HelvFont.HELV_ROMAN.apply(getApplicationContext(), (CheckedTextView) v);
				return v;
			}

			public View getDropDownView(int position, View convertView,
					ViewGroup parent) {
				View v = super.getDropDownView(position, convertView, parent);
				HelvFont.HELV_ROMAN.apply(getApplicationContext(), (CheckedTextView) v);
				return v;
			}
		};
		locale.setAdapter(adapter);
	    
	    if (sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("ru"))
	    locale.setSelection(0);
	    if (sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("en"))
	    locale.setSelection(1);
	    if (sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("de"))
		locale.setSelection(2);
	    
	    locale.setOnItemSelectedListener(new OnItemSelectedListener(){    
	    	@Override
	    	public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
	    		check++;
	    		if(check>1){
	    		Editor ed = sPref.edit();   
	    		ed.putString("preference_locales", getResources().getStringArray(R.array.locales_entryValues)[i]); 	
	    		ed.commit();
	    		setLocale(getResources().getStringArray(R.array.locales_entryValues)[i]);
	    		updateProviderToLang();
	    		activityRefresh();}
	    	} 
	    	@Override     
	    	public void onNothingSelected(AdapterView<?> parentView) {}
	    }); 
	    
	    //set fonts
	    HelvFont.HELV_LIGHT.apply(getApplicationContext(), next);
	    HelvFont.HELV_LIGHT.apply(getApplicationContext(), back);
	    HelvFont.HELV_MEDIUM.apply(getApplicationContext(), (TextView)findViewById(R.id.textView1));
	    HelvFont.HELV_ROMAN.apply(getApplicationContext(), (TextView)findViewById(R.id.textView2));
	    
	    
	}
	
	private void activityRefresh(){
		Intent refresh = new Intent(this, IntroduceActivityOne.class);
		//restart activity
	    startActivity(refresh);   
	    //set no animation
	    overridePendingTransition(0, 0);
	    // stop curr activity
	    finish();
	}
	
	private void updateProviderToLang(){
		 //change provider to curr lang
	     Editor ed = sPref.edit();  
	     ed.putString("preference_provider", getResources().getString(R.string.default_provider)); 	
	     ed.commit();
	}

}
