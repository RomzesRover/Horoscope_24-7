
package com.BBsRs.horoscopeFullNew;

import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.DatePreference;
import org.holoeverywhere.preference.DatePreference.OnDateSetListener;
import org.holoeverywhere.preference.ListPreference;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceChangeListener;
import org.holoeverywhere.preference.Preference.OnPreferenceClickListener;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.preference.TimePreference;
import org.holoeverywhere.preference.TimePreference.OnTimeSetListener;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.BBsRs.horoscopeFullNew.Base.BasePreferenceFragment;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class SettingsFragment extends BasePreferenceFragment {
	
	//preferences 
    SharedPreferences sPref;
    
    Preference myTrialPref;
    DatePreference myDatePref;
    TimePreference myTimePref;
    ListPreference myLocaleListPref;
    ListPreference myProviderListPref;
    
    /*--------------------INIT IN APP BILLING-------------------------*/
    //inAppBillingData
    // PRODUCT & SUBSCRIPTION IDS
    private static final String PRODUCT_ID_HIGH = "horoscope_full";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAoOFrLACxS5TNJChRpgGoD3z315y5vm/SDts6uEKIJSXoSB0Q0hWpi7ejYj+5f6WWARqdREhjoKQTe5W2MJV1f6GcY0o+UJR0Ros2dziJm14ffL59wV0W+A/7SCDzu/6u2GDkt6h+5XnDSssT1wbTK+Jfewr0hqQYFrNOtyFhSp52ToZxk9jWLv6OuGgkelfRiKFlqP1LWRK6Wc4nb5yi4iUDV0ZhBGxNQHRt992v6rAMMY+luk8vn/UlXvXEnzvM4NKwsNjXUUQ/rHluhDDf/2HqsdIJy8YPugQmZ4Z/Jaf5nD/Fq3B/c8NaEahJZW218WeuL68/+hQyRMozUfEBYQIDAQAB"; // PUT YOUR MERCHANT KEY HERE;
    
	private BillingProcessor bp;
	private boolean readyToPurchase = false;
	/*--------------------INIT IN APP BILLING-------------------------*/
	
	AlertDialog alert = null;	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        addPreferencesFromResource(R.xml.preferences);
        
        //setting up list zodiac change listener preference, cuz we need update horo if zodiac was changed.
		Editor ed = sPref.edit(); 
		ed.putBoolean("changed_0", true);	
		ed.putBoolean("changed_1", true);	
		ed.putBoolean("changed_2", true);	
		ed.putBoolean("changed_3", true);	
		ed.putBoolean("changed_4", true);	
		ed.putBoolean("changed_5", true);	
		ed.putBoolean("changed_6", true);	
		ed.putBoolean("changed_7", true);	
		ed.putBoolean("changed_8", true);	
		ed.commit();
		
		/*--------------------INIT IN APP BILLING-------------------------*/
        bp = new BillingProcessor(getActivity(), LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
            	//disable ad
            	if (bp.isPurchased(PRODUCT_ID_HIGH)) sPref.edit().putBoolean("agreeWithAd", false).commit();
            	activityRefresh();
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
            	startMainTask();
            }
            @Override
            public void onBillingInitialized() {
                readyToPurchase = true;
                startMainTask();
            }
            @Override
            public void onPurchaseHistoryRestored() {
            }
        });
        /*--------------------INIT IN APP BILLING-------------------------*/
		
        
    }
    
    public void startMainTask(){
		//trial preferences
		myTrialPref = (Preference) findPreference("preference_trial");
		myTrialPref.setSummary(bp.isPurchased(PRODUCT_ID_HIGH) ? getResources().getString(R.string.trial_buyed) : (!sPref.getBoolean("agreeWithAd", false) ? getResources().getString(R.string.trial_until)+" "+String.valueOf(sPref.getInt("dayBefore", 0))+" "+getResources().getStringArray(R.array.moths_of_year)[sPref.getInt("monthBefore", 0)]+" "+String.valueOf(sPref.getInt("yearBefore", 0)) : getResources().getString(R.string.trial_ad)));
		
		myTrialPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				showDialog();
				return false;
			}
		});
        
        //setting up date change preference, cuz we need save date.
        myDatePref = (DatePreference) findPreference("preference_date_born");
        
        Calendar cal=Calendar.getInstance();
        myDatePref.setDay(sPref.getInt("dayBorn", cal.get(Calendar.DAY_OF_MONTH)));
        myDatePref.setMonth(sPref.getInt("monthBorn", cal.get(Calendar.MONTH)));
        myDatePref.setYear(sPref.getInt("yearBorn", cal.get(Calendar.YEAR)));
        
        myDatePref.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public boolean onDateSet(DatePreference preference, long date,
					int year, int month, int day) {
				
				Calendar currDate=Calendar.getInstance();
				Calendar calSet = Calendar.getInstance();
				calSet.setTimeInMillis(0);
				calSet.set(year, month, day, currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));

				if (calSet.getTime().before(currDate.getTime())){
					//save date born
					Editor ed = sPref.edit();   
					ed.putInt("dayBorn", day);				
					ed.putInt("monthBorn", month);				
					ed.putInt("yearBorn", year);	
					ed.putString("preference_zodiac_sign", zodiacNumber(day, month+1));
					Toast.makeText(getActivity(), getResources().getString(R.string.automatic_determined_sign)+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(day, month+1))], Toast.LENGTH_LONG).show();
					ed.commit();
				} else {
					Toast.makeText(getActivity(), getResources().getString(R.string.introduce_date_check), Toast.LENGTH_LONG).show();
				}
				return false;
			}
        });
        
        //setting up date change preference, cuz we need save time.
        myTimePref = (TimePreference) findPreference("preference_time_born");
        
        myTimePref.setHour(sPref.getInt("hourBorn", cal.get(Calendar.HOUR_OF_DAY)));
        myTimePref.setMinute(sPref.getInt("minuteBorn", cal.get(Calendar.MINUTE)));
        
        myTimePref.setOnTimeSetListener(new OnTimeSetListener(){
			@Override
			public boolean onTimeSet(TimePreference preference, long date,
					int hour, int minute) {
				//save time born
				Editor ed = sPref.edit();   
				ed.putInt("minuteBorn", minute); 	
			    ed.putInt("hourBorn", hour);		
				ed.commit();
				return false;
			}
        });
        
        //setting up locale change preference, cuz we need change locale.
        myLocaleListPref = (ListPreference) findPreference("preference_locales");
        
        myLocaleListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				Editor ed = sPref.edit();   
				ed.putString("preference_locales", (String) newValue); 	
				ed.commit();
				setLocale((String) newValue);
				updateProviderToLang();
				activityRefresh();
				return false;
			}
        	
        });
        
        //setting up provider change preference, cuz we need change provider.
        myProviderListPref = (ListPreference) findPreference("preference_provider");
        
        myProviderListPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){

			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				Editor ed = sPref.edit();   
				ed.putString("preference_provider", (String) newValue);
				ed.commit();
				activityRefresh();
				return false;
			}
        	
        });
    }
    
	@Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle(R.string.preference);
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
	
	private void updateProviderToLang(){
		 //change provider to curr lang
	     Editor ed = sPref.edit();  
	     ed.putString("preference_provider", getResources().getString(R.string.default_provider)); 	
	     ed.commit();
	}
	
	private void activityRefresh(){
		Editor ed = sPref.edit();  
		ed.putBoolean("preference_start", true); 	
		ed.commit();
		Intent refresh = new Intent(getActivity(), ContentShowActivity.class);
		//restart activity
	    startActivity(refresh);   
	    //set no animation
	    getActivity().overridePendingTransition(0, 0);
	    // stop curr activity
	    getActivity().finish();
	}
	
	private void showDialog(){
		final Context context = getActivity(); 								// create context
			AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		build.setTitle(getResources().getString(R.string.trial_period)); 			// set title
		build.setIcon(R.drawable.logo_trial);
		
		if (!bp.isPurchased(PRODUCT_ID_HIGH)){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService
			      (Context.LAYOUT_INFLATER_SERVICE);
		
		View content = inflater.inflate(R.layout.dialog_content, null);
		
		RelativeLayout freeAd = (RelativeLayout)content.findViewById(R.id.freeAd);
		if (sPref.getBoolean("agreeWithAd", false))
			freeAd.setVisibility(View.GONE);
		freeAd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sPref.edit().putBoolean("agreeWithAd", true).commit();
				activityRefresh();
			}
		});
		
		RelativeLayout paidRtHigh = (RelativeLayout)content.findViewById(R.id.paidRtHigh);
		if (bp.isPurchased(PRODUCT_ID_HIGH))
			paidRtHigh.setVisibility(View.GONE);
		paidRtHigh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!readyToPurchase) {
		            Toast.makeText(getActivity(), "Billing not initialized.", Toast.LENGTH_LONG).show();
		            return;
		        } else{
		        	bp.purchase(PRODUCT_ID_HIGH);
		        }
			}
		});
		
		build.setView(content);
		} else {
			build.setMessage(getActivity().getResources().getString(R.string.trial_buyed));
		}
		
		alert = build.create();															// show dialog
		alert.show();
	}
}
