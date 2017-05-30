package com.BBsRs.horoscopeFullNew;

import java.util.Calendar;
import java.util.Date;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Introduce.IntroduceActivityNewTheme;
import com.BBsRs.horoscopeNewEdition.NotificationService;
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
    ImageView stars, sign, lines;
 
    //handler
    Handler handler = new Handler();
    
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
	        	Intent refresh = new Intent(getApplicationContext(), IntroduceActivityNewTheme.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			    // stop curr activity
			    finish();
	        } else {
	        	//start service
	        	scheduleUpdate(getApplicationContext());
	        	//create intent
	        	Intent refresh = new Intent(getApplicationContext(), ContentShowActivity.class);
				//restart activity
			    startActivity(refresh);   
			    //set  animation
			    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			    // stop curr activity
			    finish();
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
        //grant ad show
        sPref.edit().putBoolean("grantAdShow", true).commit();
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        setContentView(R.layout.activity_loader);
        
        //init error stuff
    	relativeErrorLayout = (RelativeLayout)this.findViewById(R.id.errorLayout);
    	errorMessage = (TextView)this.findViewById(R.id.errorMessage);
    	errorRetryButton = (Button)this.findViewById(R.id.errorRetryButton);
    	relativeContentLayout = (RelativeLayout)this.findViewById(R.id.contentLayout);
        stars = (ImageView)findViewById(R.id.stars);
        sign = (ImageView)findViewById(R.id.sign);
        lines = (ImageView)findViewById(R.id.lines);
    	
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
        
		if (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13){
			Intent refresh = new Intent(getApplicationContext(), IntroduceActivityNewTheme.class);
			//restart activity
		    startActivity(refresh);   
		    //set  animation
		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		    // stop curr activity
		    finish();
		} else {
			//start timer
			CountDownTimer = new timer (4000, 1000);   		//timer to 2 seconds (tick one second)
			CountDownTimer.start();							//start timer
			//start animation
			//delete old image
			stars.setImageResource(android.R.color.transparent);
			sign.setImageResource(android.R.color.transparent);
			lines.setImageResource(android.R.color.transparent);
			
			handler.postDelayed(new Runnable(){
				@Override
				public void run() {
					try{
						//start stars animation
						//set icon
				        TypedArray imagesStars = getResources().obtainTypedArray(R.array.zodiac_stars_imgs);
				        stars.setImageResource(imagesStars.getResourceId(Integer.parseInt(sPref.getString("preference_zodiac_sign", "1")), 1));
				        imagesStars.recycle();
		            	Animation flyUpAnimationStars = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_stars);
		            	stars.startAnimation(flyUpAnimationStars);
					}
					catch(Exception e){
						e.printStackTrace();
					}
					
	            	handler.postDelayed(new Runnable(){
						@Override
						public void run() {
							try{
								//start lines animation
								//set icon
						        TypedArray imagesLines = getResources().obtainTypedArray(R.array.zodiac_lines_imgs);
						        lines.setImageResource(imagesLines.getResourceId(Integer.parseInt(sPref.getString("preference_zodiac_sign", "1")), 1));
						        imagesLines.recycle();
				            	Animation flyUpAnimationLines = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_stars);
				            	lines.startAnimation(flyUpAnimationLines);
							}
							catch(Exception e){
								e.printStackTrace();
							}
			            	
			            	handler.postDelayed(new Runnable(){
								@Override
								public void run() {
									try{
										//start sign animation
										//set icon
								        TypedArray images = getResources().obtainTypedArray(R.array.zodiac_signs_imgs);
								        sign.setImageResource(images.getResourceId(Integer.parseInt(sPref.getString("preference_zodiac_sign", "1")), 1));
								        images.recycle();
						            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_sign);
						            	sign.startAnimation(flyUpAnimation);
									}
									catch(Exception e){
										e.printStackTrace();
									}
								}
			            	}, 500);
						}
	            	}, 500);
				}
			}, 500);
		}
		
		//set fonts
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.title)));
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.subTitle)));
		SFUIFonts.ULTRALIGHT.apply(this, errorMessage);
		SFUIFonts.ULTRALIGHT.apply(this, errorRetryButton);
	}

    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
    private void scheduleUpdate(Context context) {
    	cancelUpdates(context);
    	
    	if (!sPref.getBoolean("preference_show_notifications", true))
    		return;
    	
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeInMillis(System.currentTimeMillis());
		currentDate.add(Calendar.SECOND, +30);
		
        Calendar workDate = Calendar.getInstance();
        workDate.setTimeInMillis(System.currentTimeMillis());
		
		//send notification everyday at morning
        workDate.set(Calendar.HOUR_OF_DAY, sPref.getInt("preference_show_notifications_time_hour", 8));
        workDate.set(Calendar.MINUTE, sPref.getInt("preference_show_notifications_time_minute", 0));
        workDate.set(Calendar.SECOND, 0);
		
        if (workDate.before(currentDate)){
        	workDate.add(Calendar.DATE, +1);
        }

        Log.i("ACTIVITY_LOADER", "Scheduling next update at " + new Date(workDate.getTimeInMillis()));
        am.set(AlarmManager.RTC_WAKEUP, workDate .getTimeInMillis(), getUpdateIntent(context));
    }
    
    public static PendingIntent getUpdateIntent(Context context) {
        Intent i = new Intent(context, NotificationService.class);
        return PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    
    public static void cancelUpdates(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getUpdateIntent(context));
    }
}
