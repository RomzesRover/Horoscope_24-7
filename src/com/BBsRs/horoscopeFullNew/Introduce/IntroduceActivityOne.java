package com.BBsRs.horoscopeFullNew.Introduce;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView.BufferType;

import com.BBsRs.horoscopeFullNew.R;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;

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
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityTwo.class);
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
	    if (sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("ru"))
	    locale.setSelection(0);
	    else
	    locale.setSelection(1);
	    
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
	    
	    //set license
	    TextView text = (TextView)this.findViewById(R.id.textAgreement);
	    String sentence = getResources().getString(R.string.clickable_string);


	    text.setMovementMethod(LinkMovementMethod.getInstance());
	    text.setText(addClickablePart(sentence), BufferType.SPANNABLE);
	}
	
	private SpannableStringBuilder addClickablePart(String str) {
	    SpannableStringBuilder ssb = new SpannableStringBuilder(str);

	    int idx1 = str.indexOf("[");
	    int idx2 = 0;
	    while (idx1 != -1) {
	        idx2 = str.indexOf("]", idx1) + 1;

	        final String clickString = str.substring(idx1, idx2);
	        ssb.setSpan(new ClickableSpan() {

	            @Override
	            public void onClick(View widget) {
	            	showLicenseDialog();
	            }
	        }, idx1, idx2, 0);
	        idx1 = str.indexOf("[", idx2);
	    }

	    return ssb;
	 }
	
	
	private void showLicenseDialog(){
		final Context context = IntroduceActivityOne.this; 								// create context
		
		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		build.setTitle(getResources().getString(R.string.agreement_top)); 					// set title
		build.setMessage(getApplicationContext().getResources().getString(R.string.agreement));
		build.setCancelable(true);
		build.setNegativeButton("Ok", null);
		alert = build.create();															// show dialog
		alert.show();
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
