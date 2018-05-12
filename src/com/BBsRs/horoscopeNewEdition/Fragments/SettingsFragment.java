package com.BBsRs.horoscopeNewEdition.Fragments;

import org.holoeverywhere.drawable.ColorDrawable;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.SharedPreferences;

import android.os.Bundle;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BasePreferenceFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;

public class SettingsFragment extends BasePreferenceFragment {
	
	//for retrieve data from activity
    Bundle bundle;
    
    // preferences 
    SharedPreferences sPref; 
    
    Preference dateBorn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //retrieve bundle
      	bundle = this.getArguments();
        
        addPreferencesFromResource(R.xml.settings);
        
        //programming each preferences
        dateBorn = (Preference) findPreference ("preference_date_born");

    }
    
    @Override
    public void onResume() {
        super.onResume();
        setTitle(bundle.getString(Constants.BUNDLE_LIST_TITLE_NAME));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar_color)));
        //setup preferences listview
        getListView().setBackgroundColor(getResources().getColor(R.color.slider_menu_background));
        getListView().setDivider(getResources().getDrawable(R.color.slider_menu_custom_divider));
        getListView().setSelector(getResources().getDrawable(R.drawable.slider_menu_selector));
        
        //update summary
        dateBorn.setSummary("10.05.1995");
    }
}
