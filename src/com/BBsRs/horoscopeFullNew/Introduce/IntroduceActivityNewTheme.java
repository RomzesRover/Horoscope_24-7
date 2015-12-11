package com.BBsRs.horoscopeFullNew.Introduce;

import java.util.Locale;

import org.holoeverywhere.app.TabSwipeActivity;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import com.BBsRs.horoscopeNewEdition.R;

public class IntroduceActivityNewTheme extends TabSwipeActivity {
	
	ImageView one,two,three;
	
    //preferences 
    SharedPreferences sPref;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
	    
	    //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
	    setContentView(R.layout.activity_introduce_new_theme);
	    
	    //hide ab
	    getSupportActionBar().hide();
	    
	    //init circles
	    one = (ImageView)findViewById(R.id.circle_one);
	    two = (ImageView)findViewById(R.id.circle_two);
	    three = (ImageView)findViewById(R.id.circle_three);
	}

	@Override
	public void onHandleTabs() {
		addTab("#1", IntroduceNewThemeFirstFragment.class);
		addTab("#2", IntroduceNewThemeSecondFragment.class);
		addTab("#3", IntroduceNewThemeThirdFragment.class);
	    
		//to select new circle
	    this.setOnTabSelectedListener(new OnTabSelectedListener(){
			@Override
			public void onTabSelected(int position) {
				switch (position){
				case 0:
					sendBroadcast(new Intent("horo_inro_other_screen"));
					one.setImageResource(R.drawable.ic_circle_selected);
					two.setImageResource(R.drawable.ic_circle_unselected);
					three.setImageResource(R.drawable.ic_circle_unselected);
					break;
				case 1:
					sendBroadcast(new Intent("horo_inro_other_screen"));
					one.setImageResource(R.drawable.ic_circle_unselected);
					two.setImageResource(R.drawable.ic_circle_selected);
					three.setImageResource(R.drawable.ic_circle_unselected);
					break;
				case 2:
					//if user still doesn't entered date of born
					if (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13){
						sendBroadcast(new Intent("horo_intro_prev_page"));
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.set_date_first), Toast.LENGTH_LONG).show();
						break;
					}
					sendBroadcast(new Intent("horo_inro_all_is_normal"));
					one.setImageResource(R.drawable.ic_circle_unselected);
					two.setImageResource(R.drawable.ic_circle_unselected);
					three.setImageResource(R.drawable.ic_circle_selected);
					break;
				default:
					one.setImageResource(R.drawable.ic_circle_selected);
					two.setImageResource(R.drawable.ic_circle_unselected);
					three.setImageResource(R.drawable.ic_circle_unselected);
					break;
				}
			}
	    });
	}
	
	public void setLocale(String lang) {
		 Locale myLocale;
	     myLocale = new Locale(lang);
	     Resources res = getResources();
	     DisplayMetrics dm = res.getDisplayMetrics();
	     Configuration conf = res.getConfiguration();
	     conf.locale = myLocale;
	     res.updateConfiguration(conf, dm);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//turn up download receiver
		registerReceiver(nextPage, new IntentFilter("horo_intro_next_page"));
		registerReceiver(prevPage, new IntentFilter("horo_intro_prev_page"));
	}
    
	public void onPause() {
		super.onPause();
		unregisterReceiver(nextPage);
		unregisterReceiver(prevPage);
	}
    
	private BroadcastReceiver nextPage = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        try {
	        	setCurrentTab(getCurrentTab()+1);
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    }
	};
    
	private BroadcastReceiver prevPage = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        try {
	        	setCurrentTab(getCurrentTab()-1);
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    }
	};
}
