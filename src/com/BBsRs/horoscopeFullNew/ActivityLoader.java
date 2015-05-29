package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Fonts.HelvFont;
import com.BBsRs.horoscopeFullNew.Introduce.IntroduceActivityOne;
import com.BBsRs.horoscopeNewEdition.R;

public class ActivityLoader extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;
    
    // for timer
	private timer CountDownTimer;	
	
    RelativeLayout relativeErrorLayout;
    RelativeLayout relativeContentLayout;
    TextView errorMessage;
    Button errorRetryButton;
    
    //banner 
    int banner = 0;
    
	public class timer extends CountDownTimer{
	public timer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);}

	@Override
	public void onFinish() {
		if (isNetworkAvailable()) {
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
	        	Thread thr=new Thread(new Runnable() {			
			        public void run() {
			        	Intent refresh = new Intent(getApplicationContext(), ContentShowActivity.class);
						//restart activity
					    startActivity(refresh);   
					    //set  animation
					    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
					    // stop curr activity
					    finish();
			        }
	        	});
			    thr.start();
	        } 
        } else {
        	relativeContentLayout.setVisibility(View.GONE);
        	relativeErrorLayout.setVisibility(View.VISIBLE);
        	errorMessage.setVisibility(View.VISIBLE);
        	errorRetryButton.setVisibility(View.VISIBLE);
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
        
        setContentView(R.layout.activity_loader);
        
        //init error stuff
    	relativeErrorLayout = (RelativeLayout)this.findViewById(R.id.errorLayout);
    	errorMessage = (TextView)this.findViewById(R.id.errorMessage);
    	errorRetryButton = (Button)this.findViewById(R.id.errorRetryButton);
    	relativeContentLayout = (RelativeLayout)this.findViewById(R.id.contentLayout);
    	
    	//programing error button
        errorRetryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent refresh = new Intent(getApplicationContext(), ActivityLoader.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(0, 0);
			    // stop curr activity
			    finish();
			}
		});
        
		//start timer
		CountDownTimer = new timer (3000, 1000);   		//timer to 2 seconds (tick one second)
		CountDownTimer.start();							//start timer
		
		//set fonts
	    HelvFont.HELV_LIGHT.apply(this, ((TextView)this.findViewById(R.id.title)));
	    HelvFont.HELV_LIGHT.apply(this, errorMessage);
	    HelvFont.HELV_ROMAN.apply(this, errorRetryButton);
	    
	    
        //set icon
        TypedArray images = getResources().obtainTypedArray(R.array.zodiac_signs_imgs_whoa_logos);
        ((ImageView)this.findViewById(R.id.logo)).setImageResource(images.getResourceId(Integer.parseInt(sPref.getString("preference_zodiac_sign", "1")), 1));
        images.recycle();
	}

    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
}
