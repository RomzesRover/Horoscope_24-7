package com.BBsRs.horoscopeFullNew.Base;

import java.util.Locale;

import org.holoeverywhere.app.Activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class BaseActivity extends Activity {
	
	public void setLocale(String lang) {
		 Locale myLocale;
	     myLocale = new Locale(lang);
	     Resources res = getResources();
	     DisplayMetrics dm = res.getDisplayMetrics();
	     Configuration conf = res.getConfiguration();
	     conf.locale = myLocale;
	     res.updateConfiguration(conf, dm);
	}

}
