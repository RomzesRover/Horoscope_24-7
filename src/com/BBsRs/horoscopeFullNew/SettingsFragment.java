
package com.BBsRs.horoscopeFullNew;

import java.util.Calendar;

import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.DatePreference;
import org.holoeverywhere.preference.DatePreference.OnDateSetListener;
import org.holoeverywhere.preference.ListPreference;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceChangeListener;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.preference.TimePreference;
import org.holoeverywhere.preference.TimePreference.OnTimeSetListener;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;

import com.BBsRs.horoscopeFullNew.Base.BasePreferenceFragment;
import com.BBsRs.horoscopeNewEdition.R;

public class SettingsFragment extends BasePreferenceFragment {
	
	//preferences 
    SharedPreferences sPref;
    
    DatePreference myDatePref;
    TimePreference myTimePref;
    ListPreference myLocaleListPref;
    ListPreference myProviderListPref;
    
	AlertDialog alert = null;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        addPreferencesFromResource(R.xml.preferences);
        
        //setting up list zodiac change listener preference, cuz we need update horo if zodiac was changed.
		Editor ed = sPref.edit(); 
		ed.putBoolean("changed_0", true);	
		ed.putBoolean("changed_1", true);	
		ed.putBoolean("changed_2", true);	
		ed.putBoolean("changed_3", true);	
		ed.putBoolean("changed_4", true);	
		ed.putBoolean("changed_5", true);	
		ed.putBoolean("changed_6", true);	
		ed.putBoolean("changed_7", true);	
		ed.putBoolean("changed_8", true);	
		ed.commit();
		
		startMainTask();
        
    }
    
    public void startMainTask(){
        //setting up date change preference, cuz we need save date.
        myDatePref = (DatePreference) findPreference("preference_date_born");
        
        Calendar cal=Calendar.getInstance();
        myDatePref.setDay(sPref.getInt("dayBorn", cal.get(Calendar.DAY_OF_MONTH)));
        myDatePref.setMonth(sPref.getInt("monthBorn", cal.get(Calendar.MONTH)));
        myDatePref.setYear(sPref.getInt("yearBorn", cal.get(Calendar.YEAR)));
        
        myDatePref.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public boolean onDateSet(DatePreference preference, long date,
					int year, int month, int day) {
				
				Calendar currDate=Calendar.getInstance();
				Calendar calSet = Calendar.getInstance();
				calSet.setTimeInMillis(0);
				calSet.set(year, month, day, currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));

				if (calSet.getTime().before(currDate.getTime())){
					//save date born
					Editor ed = sPref.edit();   
					ed.putInt("dayBorn", day);				
					ed.putInt("monthBorn", month);				
					ed.putInt("yearBorn", year);	
					ed.putString("preference_zodiac_sign", zodiacNumber(day, month+1));
					Toast.makeText(getActivity(), getResources().getString(R.string.automatic_determined_sign)+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(day, month+1))], Toast.LENGTH_LONG).show();
					ed.commit();
				} else {
					Toast.makeText(getActivity(), getResources().getString(R.string.introduce_date_check), Toast.LENGTH_LONG).show();
				}
				return false;
			}
        });
        
        //setting up date change preference, cuz we need save time.
        myTimePref = (TimePreference) findPreference("preference_time_born");
        
        myTimePref.setHour(sPref.getInt("hourBorn", cal.get(Calendar.HOUR_OF_DAY)));
        myTimePref.setMinute(sPref.getInt("minuteBorn", cal.get(Calendar.MINUTE)));
        
        myTimePref.setOnTimeSetListener(new OnTimeSetListener(){
			@Override
			public boolean onTimeSet(TimePreference preference, long date,
					int hour, int minute) {
				//save time born
				Editor ed = sPref.edit();   
				ed.putInt("minuteBorn", minute); 	
			    ed.putInt("hourBorn", hour);		
				ed.commit();
				return false;
			}
        });
        
        //setting up locale change preference, cuz we need change locale.
        myLocaleListPref = (ListPreference) findPreference("preference_locales");
        
        myLocaleListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				Editor ed = sPref.edit();   
				ed.putString("preference_locales", (String) newValue); 	
				ed.commit();
				setLocale((String) newValue);
				updateProviderToLang();
				activityRefresh();
				return false;
			}
        	
        });
        
        //setting up provider change preference, cuz we need change provider.
        myProviderListPref = (ListPreference) findPreference("preference_provider");
        
        myProviderListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				Editor ed = sPref.edit();   
				ed.putString("preference_provider", (String) newValue);
				ed.commit();
				activityRefresh();
				return false;
			}
        	
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle(R.string.preference);
    }
    
	String zodiacNumber (int day, int month){
		if((month == 1) && (day <= 20) || (month == 12) && (day >= 22)) {
			return "9";
		 } else if((month == 1) || (month == 2) && (day <= 19)) {
			 return "10";
		 } else if((month == 2) || (month == 3) && (day <= 20)) {
			 return "11";
		 } else if((month == 3) || (month == 4) && (day <= 19)) {
			 return "0";
		 } else if((month == 4) || (month == 5) && (day <= 21)) {
			 return "1";
		 } else if((month == 5) || (month == 6) && (day <= 21)) {
			 return "2";
		 } else if((month == 6) || (month == 7) && (day <= 23)) {
			 return "3";
		 } else if((month == 7) || (month == 8) && (day <= 23)) {
			 return "4";
		 } else if((month == 8) || (month == 9) && (day <= 23)) {
			 return "5";
		 } else if((month == 9) || (month == 10) && (day <= 23)) {
			 return "6";
		 } else if((month == 10) || (month == 11) && (day <= 22)) {
			 return "7";
		 } else if(month == 12) {
			 return "8";
		 }
		 return "0";
	}
	
	private void updateProviderToLang(){
		 //change provider to curr lang
	     Editor ed = sPref.edit();  
	     ed.putString("preference_provider", getResources().getString(R.string.default_provider)); 	
	     ed.commit();
	}
	
	private void activityRefresh(){
		Editor ed = sPref.edit();  
		ed.putBoolean("preference_start", true); 	
		ed.commit();
		Intent refresh = new Intent(getActivity(), ContentShowActivity.class);
		//restart activity
	    startActivity(refresh);   
	    //set no animation
	    getActivity().overridePendingTransition(0, 0);
	    // stop curr activity
	    getActivity().finish();
	}
}
