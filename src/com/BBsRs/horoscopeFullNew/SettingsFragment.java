
package com.BBsRs.horoscopeFullNew;

import java.util.Calendar;
import java.util.Date;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.drawable.ColorDrawable;
import org.holoeverywhere.preference.CheckBoxPreference;
import org.holoeverywhere.preference.DatePreference;
import org.holoeverywhere.preference.DatePreference.OnDateSetListener;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceChangeListener;
import org.holoeverywhere.preference.Preference.OnPreferenceClickListener;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.preference.TimePreference;
import org.holoeverywhere.preference.TimePreference.OnTimeSetListener;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeFullNew.Base.BasePreferenceFragment;
import com.BBsRs.horoscopeNewEdition.ActivityRestarter;
import com.BBsRs.horoscopeNewEdition.NotificationService;
import com.BBsRs.horoscopeNewEdition.R;

public class SettingsFragment extends BasePreferenceFragment {
	
	//preferences 
    SharedPreferences sPref; 
    
    DatePreference myDatePref;
    CheckBoxPreference myNotificationsPref;
    TimePreference myNotificationsTimePref;
    Preference myDisableAdPreference, myLocaleListPref, myZodiacSignPref, mAboutPref;
    
	AlertDialog alert = null;	
	Calendar cal;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        addPreferencesFromResource(R.xml.preferences);
        
		startMainTask();
        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.setBackgroundColor(getResources().getColor(R.color.slider_menu_background));
        return view;
    }
    
    public void forceUpdateContent(){
		// setting up list zodiac change listener preference, cuz we need update
		// horo if zodiac was changed.
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
    }
    
    public void startMainTask(){
    	//setting up about pref
    	mAboutPref = (Preference) findPreference("preference_ad_about");
    	mAboutPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
		 		final Context context = getActivity(); 								// create context
		 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		    		
		    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	View content = inflater.inflate(R.layout.dialog_content_sponsor, null);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
		    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
		    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView04));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView15));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView14));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView25));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView24));
		    	
		    	
		    	final RelativeLayout makeReview = (RelativeLayout)content.findViewById(R.id.make_review);
		    	makeReview.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("market://details?id="+getActivity().getPackageName()));
						startActivity(intent);
					}
				});
		    	
		    	final RelativeLayout share = (RelativeLayout)content.findViewById(R.id.make_share);
		    	share.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try { 
							Intent i = new Intent(Intent.ACTION_SEND);  
							i.setType("text/plain");
							i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
							String sAux = "\n"+getString(R.string.share_text)+"\n\n";
							sAux = sAux + getString(R.string.share_content_url)+" \n\n";
							i.putExtra(Intent.EXTRA_TEXT, sAux);  
							startActivity(Intent.createChooser(i, "Share with"));
						} catch(Exception e) {}   
					}
				});
		    	
		    	final RelativeLayout vk = (RelativeLayout)content.findViewById(R.id.make_vk);
		    	vk.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try { 
							Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dialog_sponsor_vk_link)));
							startActivity(browserIntent);
						} catch(Exception e) {}   
					}
				});
		    	
		    	((Button)content.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				});
		    	
		    	((Button)content.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				});
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
				return false;
			}
    	});
    	
        //setting up date change preference, cuz we need save date.
        myDatePref = (DatePreference) findPreference("preference_date_born");
        
        cal = Calendar.getInstance();
        myDatePref.setDay(sPref.getInt("dayBorn", cal.get(Calendar.DAY_OF_MONTH)));
        myDatePref.setMonth(sPref.getInt("monthBorn", cal.get(Calendar.MONTH)));
        myDatePref.setYear(sPref.getInt("yearBorn", cal.get(Calendar.YEAR)));
        
        myDatePref.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public boolean onDateSet(DatePreference preference, long date,
					int year, int month, int day) {
					try{
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
							ed.commit();
							Toast.makeText(getActivity(), getResources().getString(R.string.automatic_determined_sign)+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(zodiacNumber(day, month+1))], Toast.LENGTH_LONG).show();
							forceUpdateContent();
							activityRefresh();
						} else {
							Toast.makeText(getActivity(), getResources().getString(R.string.introduce_date_check), Toast.LENGTH_LONG).show();
						}
						
						updateSummary();
					} catch (Exception e){
						//
					}
					return false;
			}
        });
        
        //setting up locale change preference, cuz we need change locale.
        myLocaleListPref = (Preference) findPreference("preference_locales");
        myLocaleListPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				final Context context = getActivity(); 								// create context
		 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		    	
		    	LayoutInflater inflater = (LayoutInflater)context.getSystemService
		    		      (Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	//init views
		    	View content = inflater.inflate(R.layout.dialog_content_list, null);
		    	TextView title = (TextView)content.findViewById(R.id.title);
		    	Button cancel = (Button)content.findViewById(R.id.cancel);
		    	Button apply = (Button)content.findViewById(R.id.apply);
		    	ImageView icon = (ImageView)content.findViewById(R.id.icon);
		    	final ListView list = (ListView)content.findViewById(R.id.listView1);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, title);
		    	SFUIFonts.LIGHT.apply(context, cancel);
		    	SFUIFonts.LIGHT.apply(context, apply);
		    	
		    	//view job
		    	title.setText(context.getString(R.string.introduce_two_1));
		    	icon.setImageResource(R.drawable.ic_icon_settings_language);
		    	
		    	list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		    	// custom adapter
		    	final String [] listArray = context.getResources().getStringArray(R.array.locales);
		        list.setAdapter(new ArrayAdapter<String>(context, R.layout.ic_simple_single_choice, listArray){
		            @Override
		            public View getView(final int position, View convertView, ViewGroup parent) {
		            	 View v = super.getView(position, convertView, parent);
		            	 //set font
		            	 SFUIFonts.LIGHT.apply(context, ((TextView)v.findViewById(android.R.id.text1)));
		            	 //set radio
		                 RadioButton radio = (RadioButton) v.findViewById(R.id.radioButton1);
		                 if (list.isItemChecked(position)) {
		                	 radio.setChecked(true);
		                 } else {
		                	 radio.setChecked(false);
		                 }
		                 
		                 View.OnClickListener clickItem = new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								sPref.edit().putString("preference_locales", context.getResources().getStringArray(R.array.locales_entryValues)[position]).commit();
								forceUpdateContent();
								alert.dismiss();
								setLocale(context.getResources().getStringArray(R.array.locales_entryValues)[position]);
								updateProviderToLang();
								activityRefresh();
							}
						};
		                 
		                 v.setOnClickListener(clickItem);
		                 radio.setOnClickListener(clickItem);
		                 return v;
		            }
		        });
		        
				int index=0;
				for (String summaryValue : getActivity().getResources().getStringArray(R.array.locales_entryValues)){
					if (summaryValue.equals(sPref.getString("preference_locales", getString(R.string.default_locale)))){
						list.setItemChecked(index, true);
						break;
					}
					index++;
				}
		    	
		    	apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				});
		    	
		    	cancel.setVisibility(View.GONE);
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
				return false;
			}
        });
        
        myZodiacSignPref = (Preference) findPreference("preference_zodiac_sign");
        myZodiacSignPref.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				final Context context = getActivity(); 								// create context
		 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		    	
		    	LayoutInflater inflater = (LayoutInflater)context.getSystemService
		    		      (Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	//init views
		    	View content = inflater.inflate(R.layout.dialog_content_list, null);
		    	TextView title = (TextView)content.findViewById(R.id.title);
		    	Button cancel = (Button)content.findViewById(R.id.cancel);
		    	Button apply = (Button)content.findViewById(R.id.apply);
		    	ImageView icon = (ImageView)content.findViewById(R.id.icon);
		    	final ListView list = (ListView)content.findViewById(R.id.listView1);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, title);
		    	SFUIFonts.LIGHT.apply(context, cancel);
		    	SFUIFonts.LIGHT.apply(context, apply);
		    	
		    	//view job
		    	title.setText(context.getString(R.string.preference_zodiac_signs_1));
		    	icon.setImageResource(R.drawable.ic_icon_settings_sign);
		    	
		    	list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		    	// custom adapter
		    	final String [] listArray = context.getResources().getStringArray(R.array.zodiac_signs);
		        list.setAdapter(new ArrayAdapter<String>(context, R.layout.ic_simple_single_choice, listArray){
		            @Override
		            public View getView(final int position, View convertView, ViewGroup parent) {
		            	 View v = super.getView(position, convertView, parent);
		            	 //set font
		            	 SFUIFonts.LIGHT.apply(context, ((TextView)v.findViewById(android.R.id.text1)));
		            	 //set radio
		                 RadioButton radio = (RadioButton) v.findViewById(R.id.radioButton1);
		                 if (list.isItemChecked(position)) {
		                	 radio.setChecked(true);
		                 } else {
		                	 radio.setChecked(false);
		                 }
		                 
		                 View.OnClickListener clickItem = new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								sPref.edit().putString("preference_zodiac_sign", context.getResources().getStringArray(R.array.zodiac_signs_entryValues)[position]).commit();
								forceUpdateContent();
								alert.dismiss();
								updateSummary();
							}
						};
		                 
		                 v.setOnClickListener(clickItem);
		                 radio.setOnClickListener(clickItem);
		                 return v;
		            }
		        });
		        
				int index=0;
				for (String summaryValue : getActivity().getResources().getStringArray(R.array.zodiac_signs_entryValues)){
					if (summaryValue.equals(sPref.getString("preference_zodiac_sign", getString(R.string.default_sign)))){
						list.setItemChecked(index, true);
						break;
					}
					index++;
				}
		    	
		    	apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				});
		    	
		    	cancel.setVisibility(View.GONE);
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
				return false;
			}
        });
        
        //setting up locale change preference, cuz we need change notifications.
        myNotificationsPref = (CheckBoxPreference) findPreference("preference_show_notifications");
        myNotificationsPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference,
					Object newValue) {
				myNotificationsPref.setChecked(!myNotificationsPref.isChecked());
				Editor ed = sPref.edit();   
				ed.putBoolean("preference_show_notifications", myNotificationsPref.isChecked()); 	
				ed.commit();
				
				if (sPref.getBoolean("preference_show_notifications", myNotificationsPref.isChecked())){
					scheduleUpdate(getActivity());
				} else {
					cancelUpdates(getActivity());
				}
				
				updateSummary();
				return false;
			}
        });
        
        myNotificationsTimePref = (TimePreference) findPreference("preference_show_notifications_time");
        myNotificationsTimePref.setMinute(sPref.getInt("preference_show_notifications_time_minute", 0));
        myNotificationsTimePref.setHour(sPref.getInt("preference_show_notifications_time_hour", 8));

        myNotificationsTimePref.setOnTimeSetListener(new OnTimeSetListener(){
			@Override
			public boolean onTimeSet(TimePreference preference, long date,
					int hour, int minute) {
				Editor ed = sPref.edit();   
				ed.putInt("preference_show_notifications_time_minute", minute);				
				ed.putInt("preference_show_notifications_time_hour", hour);				
				ed.commit();
				
				scheduleUpdate(getActivity());
				
				updateSummary();
				return false;
			}
        });
        
        myDisableAdPreference = (Preference) findPreference("preference_ad_disabler");
        
        myDisableAdPreference.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				getActivity().sendBroadcast(new Intent("request_disable_ad"));
				updateSummary();
				return false;
			}
        });
    }
    
    public void updateSummary(){
    	myNotificationsTimePref.setEnabled(sPref.getBoolean("preference_show_notifications", true));
    	myNotificationsTimePref.setSummary(intPlusZero(sPref.getInt("preference_show_notifications_time_hour", 8)) + ":" + intPlusZero(sPref.getInt("preference_show_notifications_time_minute", 0)));
    	myDatePref.setSummary(intPlusZero(sPref.getInt("dayBorn", cal.get(Calendar.DAY_OF_MONTH))) + "." + intPlusZero(sPref.getInt("monthBorn", cal.get(Calendar.MONTH))+1) + "." + intPlusZero(sPref.getInt("yearBorn", cal.get(Calendar.YEAR))));
    	myZodiacSignPref.setSummary(getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]);
    	myDisableAdPreference.setEnabled(!sPref.getBoolean("isOnHigh", false));
    	myDisableAdPreference.setTitle((!sPref.getBoolean("isOnHigh", false)) ? getActivity().getResources().getString(R.string.preference_ad_disable) : getActivity().getResources().getString(R.string.preference_ad_disabled_succesfully));
    }

    @Override
    public void onResume() {
        super.onResume();
        //set titile for action bar with custom font
        setTitle(getString(R.string.preference));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar_color)));
        
        updateSummary();
    }
    
    String intPlusZero(int s){
    	return s/10==0 ? "0"+s : ""+s;
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
		 } else if(month == 12 || month == 11) {
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
		Intent refresh = new Intent(getActivity(), ActivityRestarter.class);
		//restart activity
	    startActivity(refresh);   
	    //set no animation
	    getActivity().overridePendingTransition(0, 0);
	    // stop curr activity
	    getActivity().finish();
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

        Log.i("FROM_SETTINGS", "Scheduling next update at " + new Date(workDate.getTimeInMillis()));
        am.set(AlarmManager.RTC_WAKEUP, workDate .getTimeInMillis(), getUpdateIntent(context));
    }
    
    public static PendingIntent getUpdateIntent(Context context) {
        Intent i = new Intent(context, NotificationService.class);
        return PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }
    
    public static void cancelUpdates(Context context) {
    	Log.i("FROM_SETTINGS", "Cancel all notifications updates");
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        am.cancel(getUpdateIntent(context));
    }
}
