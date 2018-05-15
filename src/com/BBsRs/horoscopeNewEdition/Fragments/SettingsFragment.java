package com.BBsRs.horoscopeNewEdition.Fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.holoeverywhere.drawable.ColorDrawable;
import org.holoeverywhere.preference.DatePreference;
import org.holoeverywhere.preference.DatePreference.OnDateSetListener;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.Toast;

import android.os.Bundle;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BasePreferenceFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;

public class SettingsFragment extends BasePreferenceFragment {
	
	//for retrieve data from activity
    Bundle bundle;
    
    // preferences 
    SharedPreferences sPref; 
    
    Calendar cal;
    
    Preference zodiacSign;
    DatePreference dateBorn;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //retrieve bundle
      	bundle = this.getArguments();
      	
      	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        
        addPreferencesFromResource(R.xml.settings);
        
        //programming each preferences
        dateBorn = (DatePreference) findPreference ("preference_date_born");
        cal = Calendar.getInstance();
        dateBorn.setDay(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)));
        dateBorn.setMonth(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH)));
        dateBorn.setYear(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20));
        
        dateBorn.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public boolean onDateSet(DatePreference preference, long date, int year, int month, int day) {
				try{
					Calendar currDate=Calendar.getInstance();
					Calendar calSet = Calendar.getInstance();
					calSet.setTimeInMillis(0);
					calSet.set(year, month, day, currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
	
					if (calSet.getTime().before(currDate.getTime())){
						//save date born
						Editor ed = sPref.edit();   
						ed.putInt(Constants.PREFERENCES_DAY_BORN, day);				
						ed.putInt(Constants.PREFERENCES_MONTH_BORN, month);				
						ed.putInt(Constants.PREFERENCES_YEAR_BORN, year);	
						ed.putString(Constants.PREFERENCES_ZODIAC_SIGN, zodiacNumber(day, month+1));
						ed.commit();
						Toast.makeText(getActivity(), String.format(getResources().getString(R.string.preference_date_set), getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(day, month+1))]), Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.preference_date_check), Toast.LENGTH_LONG).show();
					}
					
					updateSummary();
				} catch (Exception e){
					//
				}
				return false;
			}
        });

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
        
        updateSummary();
    }
    
    public void updateSummary(){
        //update summary
    	SimpleDateFormat format1;
        cal = Calendar.getInstance();
        cal.set(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20), sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH)), sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)));
        switch(getResources().getInteger(R.integer.date_format)){
        case 0: 
        	format1 = new SimpleDateFormat("dd.MM.yyyy");
        	break;
        case 1:
        	format1 = new SimpleDateFormat("MM.dd.yyyy");
        	break;
        default:
        	format1 = new SimpleDateFormat("dd.MM.yyyy");
        	break;
        }
        dateBorn.setSummary(format1.format(cal.getTime()));
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
		 } else if(month == 12 || month == 11) {
			 return "8";
		 }
		 return "0";
	}
}
