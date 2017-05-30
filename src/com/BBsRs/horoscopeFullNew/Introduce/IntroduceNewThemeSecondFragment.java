package com.BBsRs.horoscopeFullNew.Introduce;

import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog.OnDateSetListener;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.R;

public class IntroduceNewThemeSecondFragment extends Fragment{
	
	//alert dialog
    AlertDialog alert = null;
    //preferences 
    SharedPreferences sPref;
    //handler
    Handler handler = new Handler();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.introduce_new_theme_second);
		
		//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		//set fonts
		SFUIFonts.ULTRALIGHT.apply(getActivity(), (TextView)contentView.findViewById(R.id.title));
		SFUIFonts.ULTRALIGHT.apply(getActivity(), (TextView)contentView.findViewById(R.id.subTitle));
		SFUIFonts.ULTRALIGHT.apply(getActivity(), (Button)contentView.findViewById(R.id.chooseLanguage));
		
		//init date dialog
		final Calendar currDate = Calendar.getInstance();
	    //date change listener
	    OnDateSetListener lis = new OnDateSetListener(){
			@Override
			public void onDateSet(DatePickerDialog dialog, int year,
					int monthOfYear, int dayOfMonth) {
				
				Calendar calSet = Calendar.getInstance();
				calSet.setTimeInMillis(0);
				calSet.set(year, monthOfYear, dayOfMonth, currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));

				if (calSet.getTime().before(currDate.getTime())){
					//save date born
					Editor ed = sPref.edit();   
					ed.putInt("dayBorn", dayOfMonth);				
					ed.putInt("monthBorn", monthOfYear);				
					ed.putInt("yearBorn", year);	
					ed.putString("preference_zodiac_sign", zodiacNumber(dayOfMonth, monthOfYear+1));
					ed.commit();
		        	handler.postDelayed(new Runnable(){
						@Override
						public void run() {
							//delete old image
							getActivity().sendBroadcast(new Intent("horo_intro_next_page"));
						}
		        	}, 666);
				} else {
					Toast.makeText(getActivity(), getResources().getString(R.string.introduce_date_check), Toast.LENGTH_LONG).show();
				}
			}
	    };
	    final DatePickerDialog dpd = DatePickerDialog.newInstance(lis, sPref.getInt("yearBorn", currDate.get(Calendar.YEAR)), sPref.getInt("monthBorn", currDate.get(Calendar.MONTH)), sPref.getInt("dayBorn", currDate.get(Calendar.DAY_OF_MONTH)));
		
		((Button)contentView.findViewById(R.id.chooseLanguage)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dpd.setDate(sPref.getInt("yearBorn", currDate.get(Calendar.YEAR)-20), sPref.getInt("monthBorn", currDate.get(Calendar.MONTH)), sPref.getInt("dayBorn", currDate.get(Calendar.DAY_OF_MONTH)));
				dpd.show(getActivity().getSupportFragmentManager());
			}
		});
		
		return contentView;
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
