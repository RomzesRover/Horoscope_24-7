package com.BBsRs.horoscopeNewEdition.Base;

import java.util.Locale;

import org.holoeverywhere.preference.PreferenceFragment;
import org.holoeverywhere.preference.SharedPreferences;

import com.BBsRs.horoscopeNewEdition.R;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class BasePreferenceFragment extends PreferenceFragment {
	public void setLocale(SharedPreferences sPref) {
		String lang = "en";
	    switch(sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, getResources().getInteger(R.integer.default_language))){
	    case 1:
	    	lang = "ru";
	    	break;
	    case 0: default:
	    	lang = "en";
	    	break;
	    }
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
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
	}     		
	
    public void setTitle(String title){
    	((BaseActivity) getActivity()).setTitle(title);
    }
}
