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
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Introduce.IntroduceActivityOne;
import com.BBsRs.horoscopeNewEdition.R;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

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
    
    //alert for trial
	AlertDialog alert = null;	
	
    /*--------------------INIT IN APP BILLING-------------------------*/
    //inAppBillingData
    // PRODUCT & SUBSCRIPTION IDS
	private BillingProcessor bp;
	private boolean readyToPurchase = false;
	/*--------------------INIT IN APP BILLING-------------------------*/
	
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
				banner=Integer.parseInt(Jsoup.connect("http://brothers-rovers.3dn.ru/banner2.txt").userAgent(getResources().getString(R.string.user_agent)).timeout(getResources().getInteger(R.integer.user_timeout)).get().text());
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
		    //set  animation
		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
        
        /*--------------------INIT IN APP BILLING-------------------------*/
        bp = new BillingProcessor(this, Constants.LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
            	Intent refresh = new Intent(getApplicationContext(), ActivityLoader.class);
    			//restart activity
    		    startActivity(refresh);   
    		    //set  animation
    		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    		    // stop curr activity
    		    finish();
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
            	relativeContentLayout.setVisibility(View.GONE);
            	relativeErrorLayout.setVisibility(View.VISIBLE);
            	errorMessage.setVisibility(View.VISIBLE);
            	errorRetryButton.setVisibility(View.VISIBLE);
            }
            @Override
            public void onBillingInitialized() {
                readyToPurchase = true;
                setTrialPeriod(sPref.getBoolean("trialSettetUp", false));
                startMainTask();
            }
            @Override
            public void onPurchaseHistoryRestored() {
            }
        });
        /*--------------------INIT IN APP BILLING-------------------------*/
	}
	
	public void startMainTask(){
		//check if trial ended
        Calendar currDate = Calendar.getInstance();
        Calendar calSet = Calendar.getInstance();
		calSet.setTimeInMillis(0);
		calSet.set(sPref.getInt("yearBefore", currDate.get(Calendar.YEAR)), sPref.getInt("monthBefore", currDate.get(Calendar.MONTH)), sPref.getInt("dayBefore", currDate.get(Calendar.DAY_OF_MONTH)), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
		
		if (!currDate.after(calSet) || bp.isPurchased(Constants.PRODUCT_ID_HIGH)){
			//disable ad
			if (bp.isPurchased(Constants.PRODUCT_ID_HIGH)) sPref.edit().putBoolean("agreeWithAd", false).commit();
			//start timer
			CountDownTimer = new timer (3000, 1000);   		//timer to 2 seconds (tick one second)
			CountDownTimer.start();							//start timer
		} else {
			//end of trial try to buy it
 			final Context context = ActivityLoader.this; 								// create context
 			AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		build.setTitle(getResources().getString(R.string.trial_title)); 			// set title
    		build.setIcon(R.drawable.logo_trial);
    		
    		LayoutInflater inflater = (LayoutInflater)context.getSystemService
    			      (Context.LAYOUT_INFLATER_SERVICE);
    		
    		View content = inflater.inflate(R.layout.dialog_content, null);
    		
    		RelativeLayout freeAd = (RelativeLayout)content.findViewById(R.id.freeAd);
    		freeAd.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					sPref.edit().putBoolean("agreeWithAd", true).commit();
					//adding days to use
					addDaysToTrial(8);
					Intent refresh;
			        refresh = new Intent(getApplicationContext(), ActivityLoader.class);
					//restart activity
				    startActivity(refresh);   
				    //set  animation
				    overridePendingTransition(0, 0);
				    //stopping
				    finish();
				}
			});
    		
    		RelativeLayout paidRtHigh = (RelativeLayout)content.findViewById(R.id.paidRtHigh);
    		if (bp.isPurchased(Constants.PRODUCT_ID_HIGH))
    			paidRtHigh.setVisibility(View.GONE);
    		paidRtHigh.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (!readyToPurchase) {
			            Toast.makeText(getApplicationContext(), "Billing not initialized.", Toast.LENGTH_LONG).show();
			            return;
			        } else{
			        	bp.purchase(Constants.PRODUCT_ID_HIGH);
			        }
				}
			});
    		
    		build.setView(content);
    		
    		alert = build.create();															// show dialog
    		alert.setCanceledOnTouchOutside(false);
    		alert.show();
		}
	}
	
	@Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }
	
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
    private void setTrialPeriod(boolean trialSettetUp){
    		if (!trialSettetUp){
        		addDaysToTrial(4);
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

}
