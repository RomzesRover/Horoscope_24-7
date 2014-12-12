
package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;

import android.os.Bundle;

import com.BBsRs.horoscopeFullNew.Base.BasePreferenceFragment;

public class AboutFragment extends BasePreferenceFragment {
	
	//preferences 
    SharedPreferences sPref;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        addPreferencesFromResource(R.xml.about);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle(R.string.about);
    }
}
