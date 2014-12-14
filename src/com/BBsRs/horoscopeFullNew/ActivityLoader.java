package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Introduce.IntroduceActivityOne;

public class ActivityLoader extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;
    
    // for timer
	private timer CountDownTimer;					
	
	public class timer extends CountDownTimer{
	public timer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);}

	@Override
	public void onFinish() {
		//check if user still not set up data
        if (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13){
        	Intent refresh = new Intent(getApplicationContext(), IntroduceActivityOne.class);
			//restart activity
		    startActivity(refresh);   
		    //set  animation
		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		    // stop curr activity
		    finish();
        } else {
        	Intent refresh = new Intent(getApplicationContext(), ContentShowActivity.class);
			//restart activity
		    startActivity(refresh);   
		    // stop curr activity
		    finish();
        }
	}

	@Override
	public void onTick(long arg0) {
	}
}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        this.setContentView(R.layout.activity_loader);
        
	    //start timer
   		CountDownTimer = new timer (3000, 1000);   		//timer to 2 seconds (tick one second)
        CountDownTimer.start();							//start timer
	}

}
