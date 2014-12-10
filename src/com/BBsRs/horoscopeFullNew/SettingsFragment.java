
package com.BBsRs.horoscopeFullNew;

import java.util.Calendar;

import org.holoeverywhere.preference.DatePreference;
import org.holoeverywhere.preference.DatePreference.OnDateSetListener;
import org.holoeverywhere.preference.ListPreference;
import org.holoeverywhere.preference.PreferenceFragment;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.preference.TimePreference;
import org.holoeverywhere.preference.TimePreference.OnTimeSetListener;
import org.holoeverywhere.widget.Toast;

import android.os.Bundle;

public class SettingsFragment extends PreferenceFragment {
	
	//preferences 
    SharedPreferences sPref;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        
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
        
        //setting up date change preference, cuz we need save date.
        DatePreference myDatePref = (DatePreference) findPreference("preference_date_born");
        final ListPreference myListPref = (ListPreference) findPreference("preference_zodiac_sign");
        
        Calendar cal=Calendar.getInstance();
        myDatePref.setDay(sPref.getInt("dayBorn", cal.get(Calendar.DAY_OF_MONTH)));
        myDatePref.setMonth(sPref.getInt("monthBorn", cal.get(Calendar.MONTH)));
        myDatePref.setYear(sPref.getInt("yearBorn", cal.get(Calendar.YEAR)));
        
        myDatePref.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public boolean onDateSet(DatePreference preference, long date,
					int year, int month, int day) {
				//save date born
				Editor ed = sPref.edit();   
				ed.putInt("dayBorn", day);				
			    ed.putInt("monthBorn", month);				
			    ed.putInt("yearBorn", year);	
			    ed.putString("preference_zodiac_sign", zodiacNumber(day, month+1));
			    myListPref.setValue(zodiacNumber(day, month+1));
			    Toast.makeText(getActivity(), getResources().getString(R.string.automatic_determined_sign)+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(day, month+1))], Toast.LENGTH_LONG).show();
				ed.commit();
				return false;
			}
        });
        
        //setting up date change preference, cuz we need save time.
        TimePreference myTimePref = (TimePreference) findPreference("preference_time_born");
        
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
}
