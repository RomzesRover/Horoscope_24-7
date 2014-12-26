package com.BBsRs.horoscopeFullNew.Introduce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import android.view.View;

import com.BBsRs.horoscopeFullNew.R;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;

public class IntroduceActivityThree extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
	    
	    //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        this.setContentView(R.layout.activity_inroduce_three);
	    
	    //init butts
	    Button back = (Button)this.findViewById(R.id.buttonBack);
	    final Button next = (Button)this.findViewById(R.id.buttonNext);
	    
	    //on butts 
	    next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityFour.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			    // stop curr activity
			    finish();

			}
		});
	    
	    //on butts 
	    back .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityTwo.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
			    // stop curr activity
			    finish();
			}
		});
	    
	    //init main buttn
	    Button setDate = (Button)this.findViewById(R.id.buttonSetDate);
	    final TextView textDateBorn = (TextView)this.findViewById(R.id.textDateBorn);
	    
	    //dis next bttn
	    next.setEnabled(false);
	    
	    final Calendar currDate = Calendar.getInstance();
	    final DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	    
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
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.automatic_determined_sign)+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(dayOfMonth, monthOfYear+1))], Toast.LENGTH_LONG).show();
					ed.commit();
					next.setEnabled(true);
					textDateBorn.setText(dateFormat.format(calSet.getTime())+" - "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(dayOfMonth, monthOfYear+1))]);
				} else {
					Toast.makeText(getApplicationContext(), getResources().getString(R.string.introduce_date_check), Toast.LENGTH_LONG).show();
					next.setEnabled(false);
				}
			}
	    };
	    final DatePickerDialog dpd = DatePickerDialog.newInstance(lis, sPref.getInt("yearBorn", currDate.get(Calendar.YEAR)), sPref.getInt("monthBorn", currDate.get(Calendar.MONTH)), sPref.getInt("dayBorn", currDate.get(Calendar.DAY_OF_MONTH)));
	    
	    if (sPref.getInt("yearBorn", 0)!=0) {
	    	next.setEnabled(true);
			textDateBorn.setText(monthPlusZero(String.valueOf(sPref.getInt("dayBorn", currDate.get(Calendar.DAY_OF_MONTH))+1))+"/"+monthPlusZero(String.valueOf(sPref.getInt("monthBorn", currDate.get(Calendar.MONTH))))+"/"+String.valueOf(sPref.getInt("yearBorn", currDate.get(Calendar.YEAR))));
	    }

	    setDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dpd.setDate(sPref.getInt("yearBorn", currDate.get(Calendar.YEAR)), sPref.getInt("monthBorn", currDate.get(Calendar.MONTH)), sPref.getInt("dayBorn", currDate.get(Calendar.DAY_OF_MONTH)));
				dpd.show(getSupportFragmentManager());
			}
		});
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
	
	public String monthPlusZero(String arg1){
		if (arg1.length()==1)
		return "0"+arg1;
		else 
		return arg1;
	}

}
