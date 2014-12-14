package com.BBsRs.horoscopeFullNew;

import java.io.IOException;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.jsoup.Jsoup;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Introduce.IntroduceActivityOne;

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
		if (isNetworkAvailable())
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
        	
        	try {
				banner=Integer.parseInt(Jsoup.connect("http://brothers-rovers.3dn.ru/banner.txt").get().text());
        	} catch (NotFoundException e) {
        		banner = 0;
				e.printStackTrace();
			} catch (IOException e) {
				banner = 0;
				e.printStackTrace();
			} catch (NullPointerException e) {
				banner = 0;
				e.printStackTrace();
			} catch (Exception e) {
				banner = 0;
				e.printStackTrace();
			}
        	Editor ed = sPref.edit();   
    		ed.putInt("banner", banner); 	
    		ed.commit();
        	
        	Intent refresh = new Intent(getApplicationContext(), ContentShowActivity.class);
			//restart activity
		    startActivity(refresh);   
		    // stop curr activity
		    finish();
		        }
        	});
		    thr.start();
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
        
        this.setContentView(R.layout.activity_loader);
        
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
	}
	
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}

}
