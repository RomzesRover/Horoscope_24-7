package com.BBsRs.horoscopeNewEdition;

import java.util.Calendar;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Services.NotificationService;

public class LoaderActivity extends BaseActivity {
	
    RelativeLayout relativeContentLayout;
    ImageView stars, sign, lines;
 
    //handler
    Handler handler = new Handler();
    
    // preferences 
    SharedPreferences sPref; 


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    if (!isTaskRoot()) {
	        // Android launched another instance of the root activity into an existing task
	        //  so just quietly finish and go away, dropping the user back into the activity
	        //  at the top of the stack (ie: the last state of this task)
	        finish();
	        return;
	    }
	    
        setContentView(R.layout.activity_loader);
        
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(this);

        //init views
    	relativeContentLayout = (RelativeLayout)this.findViewById(R.id.contentLayout);
        stars = (ImageView)findViewById(R.id.stars);
        sign = (ImageView)findViewById(R.id.sign);
        lines = (ImageView)findViewById(R.id.lines);

		//set fonts
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.title)));
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.subTitle)));
	}
	
	@Override
	public void onPause(){
		//stopp all delayed stuff
		handler.removeCallbacks(mainTask);
		handler.removeCallbacks(showStars);
		handler.removeCallbacks(showLines);
		handler.removeCallbacks(showSign);
		handler.removeCallbacks(startApp);
		
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		//delete old image
		stars.setImageResource(android.R.color.transparent);
		sign.setImageResource(android.R.color.transparent);
		lines.setImageResource(android.R.color.transparent);
		
		//stopp all delayed stuff
		handler.postDelayed(mainTask, 750);
	}
	
	Runnable mainTask = new Runnable(){
		@Override
		public void run() {
			handler.postDelayed(showStars, 500);
		}
	};
	
	Runnable showStars = new Runnable(){
		@Override
		public void run() {
			try{
				//start stars animation
				//set icon
		        TypedArray imagesStars = getResources().obtainTypedArray(R.array.zodiac_stars_imgs);
		        stars.setImageResource(imagesStars.getResourceId(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 1), 1));
		        imagesStars.recycle();
            	Animation flyUpAnimationStars = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_stars);
            	stars.startAnimation(flyUpAnimationStars);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.postDelayed(showLines, 500);
		}
	};
	
	Runnable showLines = new Runnable(){
		@Override
		public void run() {
			try{
				//start lines animation
				//set icon
		        TypedArray imagesLines = getResources().obtainTypedArray(R.array.zodiac_lines_imgs);
		        lines.setImageResource(imagesLines.getResourceId(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 1), 1));
		        imagesLines.recycle();
            	Animation flyUpAnimationLines = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_stars);
            	lines.startAnimation(flyUpAnimationLines);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.postDelayed(showSign, 500);
		}
	};
	
	Runnable showSign = new Runnable(){
		@Override
		public void run() {
			try{
				//start sign animation
				//set icon
		        TypedArray images = getResources().obtainTypedArray(R.array.zodiac_signs_imgs);
		        sign.setImageResource(images.getResourceId(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 1), 1));
		        images.recycle();
            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_sign);
            	sign.startAnimation(flyUpAnimation);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.postDelayed(startApp, 1500);
		}
	};
	
	Runnable startApp = new Runnable(){
		@Override
		public void run() {
			//update notif time al
			scheduleUpdate(getApplicationContext());
        	//create intent
        	Intent refresh = new Intent(getApplicationContext(), ContentActivity.class);
			//restart activity
		    startActivity(refresh);   
		    //set  animation
		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		    // stop curr activity
		    finish();
		}
	};
	
    private void scheduleUpdate(Context context) {
    	cancelUpdates(context);
    	
    	if (!sPref.getBoolean(Constants.PREFERENCES_SHOW_NOTIFICATIONS, true))
    		return;
    	
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeInMillis(System.currentTimeMillis());
		currentDate.add(Calendar.SECOND, +30);
		
        Calendar workDate = Calendar.getInstance();
        workDate.setTimeInMillis(System.currentTimeMillis());
		
		//send notification everyday at morning
        workDate.set(Calendar.HOUR_OF_DAY, sPref.getInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_HOUR, 8));
        workDate.set(Calendar.MINUTE, sPref.getInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_MINUTE, 0));
        workDate.set(Calendar.SECOND, 0);
		
        if (workDate.before(currentDate)){
        	workDate.add(Calendar.DATE, +1);
        }
        
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
