package com.BBsRs.horoscopeFullNew.Introduce;

import java.util.Calendar;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.datetimepicker.time.RadialPickerLayout;
import org.holoeverywhere.widget.datetimepicker.time.TimePickerDialog;
import org.holoeverywhere.widget.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;

public class IntroduceActivityFour extends BaseActivity {
	
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
        
        
        
        this.setContentView(R.layout.activity_inroduce_four);
	    
	    //init butts
	    Button back = (Button)this.findViewById(R.id.buttonBack);
	    final Button next = (Button)this.findViewById(R.id.buttonNext);
	    
	    //on butts 
	    next.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityFive.class);
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
				Intent refresh = new Intent(getApplicationContext(), IntroduceActivityThree.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
			    // stop curr activity
			    finish();
			}
		});
	    
	    //init main buttn
	    Button setTime = (Button)this.findViewById(R.id.buttonSetTime);
	    final TextView textTimeBorn = (TextView)this.findViewById(R.id.textTimeBorn);
	    
	    //dis next bttn
	    next.setEnabled(false);
	    
	    final Calendar currDate = Calendar.getInstance();
	    
	    //time change listener
	    OnTimeSetListener lis = new OnTimeSetListener(){
			@Override
			public void onTimeSet(RadialPickerLayout view, int hourOfDay,
					int minute) {
				//save time born
				Editor ed = sPref.edit();   
				ed.putInt("minuteBorn", minute); 	
			    ed.putInt("hourBorn", hourOfDay);		
				ed.commit();
				next.setEnabled(true);
				textTimeBorn.setText(String.valueOf(hourOfDay)+":"+String.valueOf(minute));
			}
	    };
	    final TimePickerDialog tpd = TimePickerDialog.newInstance(lis, sPref.getInt("hourBorn", currDate.get(Calendar.YEAR)), sPref.getInt("minuteBorn", currDate.get(Calendar.MONTH)), true);
	    
	    if (sPref.getInt("hourBorn", 123)!=123) {
	    	next.setEnabled(true);
			textTimeBorn.setText(sPref.getInt("hourBorn", currDate.get(Calendar.YEAR))+":"+sPref.getInt("minuteBorn", currDate.get(Calendar.MONTH)));
	    }

	    setTime.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tpd.setTime(sPref.getInt("hourBorn", currDate.get(Calendar.HOUR_OF_DAY)), sPref.getInt("minuteBorn", currDate.get(Calendar.MINUTE)));
				tpd.show(getSupportFragmentManager());
			}
		});
	}
	

}
