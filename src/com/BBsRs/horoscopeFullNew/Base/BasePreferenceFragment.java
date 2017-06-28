package com.BBsRs.horoscopeFullNew.Base;

import java.util.Locale;

import org.holoeverywhere.preference.PreferenceFragment;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.BBsRs.horoscopeFullNew.ContentShowActivity;

public class BasePreferenceFragment extends PreferenceFragment {
	
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
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
	}  
	
    public void setTitle(String title){
    	((ContentShowActivity) getActivity()).setTitle(title);
    }

}
