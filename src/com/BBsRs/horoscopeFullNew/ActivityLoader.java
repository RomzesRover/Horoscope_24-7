package com.BBsRs.horoscopeFullNew;

import java.io.IOException;
import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
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
    TextView trialMessage;
    Button errorRetryButton;
    
    //banner 
    int banner = 0;
    
    //alert for trial
	AlertDialog alert = null;	
	
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
        		//1 just load w/o force show
        		//2 load with force show
        		//3 disabled at all
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
        
        setContentView(R.layout.activity_loader);
        
        //init error stuff
    	relativeErrorLayout = (RelativeLayout)this.findViewById(R.id.errorLayout);
    	errorMessage = (TextView)this.findViewById(R.id.errorMessage);
    	errorRetryButton = (Button)this.findViewById(R.id.errorRetryButton);
    	relativeContentLayout = (RelativeLayout)this.findViewById(R.id.contentLayout);
    	trialMessage = (TextView)this.findViewById(R.id.trial_show);
    	
    	setTrialPeriod(sPref.getBoolean("trialSettetUp", false));
    	
    	trialMessage.setText(getResources().getString(R.string.trial_until)+" "+String.valueOf(sPref.getInt("dayBefore", 0))+" "+getResources().getStringArray(R.array.moths_of_year)[sPref.getInt("monthBefore", 0)]+" "+String.valueOf(sPref.getInt("yearBefore", 0)));
    	
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
        
        //check if trial ended
        Calendar currDate = Calendar.getInstance();
        Calendar calSet = Calendar.getInstance();
		calSet.setTimeInMillis(0);
		calSet.set(sPref.getInt("yearBefore", currDate.get(Calendar.YEAR)), sPref.getInt("monthBefore", currDate.get(Calendar.MONTH)), sPref.getInt("dayBefore", currDate.get(Calendar.DAY_OF_MONTH)), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
		
		if (!currDate.after(calSet)){
			//start timer
			CountDownTimer = new timer (3000, 1000);   		//timer to 2 seconds (tick one second)
			CountDownTimer.start();							//start timer
		} else {
			//end of trial try to buy it
 			final Context context = ActivityLoader.this; 								// create context
 			AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		build.setTitle(getResources().getString(R.string.trial_title)); 			// set title

    		LayoutInflater inflater = (LayoutInflater)context.getSystemService
    			      (Context.LAYOUT_INFLATER_SERVICE);
    		
    		View content = inflater.inflate(R.layout.dialog_content, null);
    		
    		RelativeLayout freeShare = (RelativeLayout)content.findViewById(R.id.freeShare);
    		freeShare.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addDaysToTrial(16);
					Intent shareIntent = new Intent(Intent.ACTION_SEND);
					shareIntent.setType("text/plain");
					shareIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.trial_get_share_intent)+"\n"+getResources().getString(R.string.share_content_url));
					activityRefresh();
					startActivity(shareIntent);
					finish();
				}
			});
    		
    		RelativeLayout freeRt = (RelativeLayout)content.findViewById(R.id.freeRt);
    		freeRt.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					addDaysToTrial(31);
					 // show intent market
					Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.BBsRs.horoscopeFullNew"));
	    			marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
	    			activityRefresh();
	    			startActivity(marketIntent);
	    			finish();
				}
			});
    		
    		
    		build.setView(content);
    		
    		alert = build.create();															// show dialog
    		alert.setCanceledOnTouchOutside(false);
    		alert.show();
		}
	}
	
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
    private void setTrialPeriod(boolean trialSettetUp){
    	if (!trialSettetUp){
    		addDaysToTrial(8);
    	}
    }
    
    private void addDaysToTrial(int days){
    	Calendar c = Calendar.getInstance();
    	c.add(Calendar.DATE, +days);
    	//save trial date
		Editor ed = sPref.edit();
		ed.putBoolean("trialSettetUp", true);
		ed.putInt("dayBefore", c.get(Calendar.DAY_OF_MONTH));
		ed.putInt("monthBefore", c.get(Calendar.MONTH));
		ed.putInt("yearBefore", c.get(Calendar.YEAR));
		ed.commit();
    }
    
	private void activityRefresh(){
		Intent refresh;
		if (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13)
        	refresh = new Intent(getApplicationContext(), IntroduceActivityOne.class);
        else 
        	refresh = new Intent(getApplicationContext(), ContentShowActivity.class);
		//restart activity
	    startActivity(refresh);   
	    //set no animation
	    overridePendingTransition(0, 0);
	}

}
