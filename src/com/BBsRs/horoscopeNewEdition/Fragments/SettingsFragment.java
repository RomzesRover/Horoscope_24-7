package com.BBsRs.horoscopeNewEdition.Fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BasePreferenceFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Services.NotificationService;

public class SettingsFragment extends BasePreferenceFragment {
	
	//for retrieve data from activity
    Bundle bundle;
    
    // preferences 
    SharedPreferences sPref; 
    
    Calendar cal;
    
    Preference zodiacSign, language;
    DatePreference dateBorn;
    CheckBoxPreference showNotifications;
    TimePreference notificationTime;
    
	//alert dialog
    AlertDialog alert = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //retrieve bundle
      	bundle = this.getArguments();
      	
      	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        
        //set app lang
        setLocale(sPref);
        
        addPreferencesFromResource(R.xml.settings);
        
        //programming each preferences
        dateBorn = (DatePreference) findPreference ("preference_date_born");
        cal = Calendar.getInstance();
        dateBorn.setDay(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)));
        dateBorn.setMonth(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH)));
        dateBorn.setYear(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20));
        
        dateBorn.setOnDateSetListener(new OnDateSetListener(){
			@Override
			public boolean onDateSet(DatePreference preference, long date, int year, int month, int day) {
				try{
					Calendar currDate=Calendar.getInstance();
					Calendar calSet = Calendar.getInstance();
					calSet.setTimeInMillis(0);
					calSet.set(year, month, day, currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
	
					if (calSet.getTime().before(currDate.getTime())){
						//save date born
						Editor ed = sPref.edit();   
						ed.putInt(Constants.PREFERENCES_DAY_BORN, day);				
						ed.putInt(Constants.PREFERENCES_MONTH_BORN, month);				
						ed.putInt(Constants.PREFERENCES_YEAR_BORN, year);	
						ed.putInt(Constants.PREFERENCES_ZODIAC_SIGN, zodiacNumber(day, month+1));
						int lifePathNumber = personalNumber(day, month+1, year);
						ed.putInt(Constants.PREFERENCES_PERSONAL_NUMBER, lifePathNumber);
						int chineseSignCalculated = chineseSign(day, month+1, year);
						ed.putInt(Constants.PREFERENCES_CHINESE_SIGN, chineseSignCalculated);
						ed.putInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, chineseSignCorrected(chineseSignCalculated));
						
						for (int i = 0; i <= 5; i++){
							ed.putBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+i, true);
						}
						
						ed.commit();
						Toast.makeText(getActivity(), String.format(getResources().getString(R.string.preference_date_set), getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber(day, month+1)], getResources().getStringArray(R.array.chinese_zodiac_signs)[chineseSignCalculated], lifePathNumber), Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getActivity(), getResources().getString(R.string.preference_date_check), Toast.LENGTH_LONG).show();
					}
					
					updateSummary();
				} catch (Exception e){
					e.printStackTrace();
				}
				return false;
			}
        });
        
        zodiacSign = (Preference) findPreference ("preference_zodiac_sign");
        zodiacSign.setOnPreferenceClickListener(new OnPreferenceClickListener(){
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
		    	final ListView list = (ListView)content.findViewById(R.id.listView1);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, title);
		    	SFUIFonts.LIGHT.apply(context, cancel);
		    	SFUIFonts.LIGHT.apply(context, apply);
		    	
		    	//view job
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
		                 final RadioButton radio = (RadioButton) v.findViewById(R.id.radioButton1);
		                 if (list.isItemChecked(position)) {
		                	 radio.setChecked(true);
		                 } else {
		                	 radio.setChecked(false);
		                 }
		                 
		                 View.OnClickListener clickItem = new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								sPref.edit().putInt(Constants.PREFERENCES_ZODIAC_SIGN, position).commit();
								notifyDataSetChanged();
								list.setItemChecked(position, true);
							}
						};
		                 
		                 v.setOnClickListener(clickItem);
		                 radio.setOnClickListener(clickItem);
		                 return v;
		            }
		        });
		        
		        final int indexCalculated = sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0);
		        list.setItemChecked(indexCalculated, true);
				
				apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						Editor ed = sPref.edit();
						for (int i = 0; i <= 5; i++){
							ed.putBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+i, true);
						}
						ed.commit();
						zodiacSign.setSummary(getResources().getStringArray(R.array.zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]);
						alert.dismiss();
					}
				});
		    	
		    	cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sPref.edit().putInt(Constants.PREFERENCES_ZODIAC_SIGN, indexCalculated).commit();
						zodiacSign.setSummary(getResources().getStringArray(R.array.zodiac_signs)[indexCalculated]);
						alert.dismiss();
					}
				});
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
				return false;
			}
        });
        
        language = (Preference) findPreference ("preference_language");
        language.setOnPreferenceClickListener(new OnPreferenceClickListener(){
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
		    	final ListView list = (ListView)content.findViewById(R.id.listView1);
		    	ImageView icon = (ImageView)content.findViewById(R.id.icon);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, title);
		    	SFUIFonts.LIGHT.apply(context, cancel);
		    	SFUIFonts.LIGHT.apply(context, apply);
		    	
		    	//view job
		    	list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		    	title.setText(context.getResources().getString(R.string.preference_language_1));
		    	icon.setImageResource(R.drawable.ic_icon_settings_language);
		    	// custom adapter
		    	final String [] listArray = context.getResources().getStringArray(R.array.languages);
		        list.setAdapter(new ArrayAdapter<String>(context, R.layout.ic_simple_single_choice, listArray){
		            @Override
		            public View getView(final int position, View convertView, ViewGroup parent) {
		            	 View v = super.getView(position, convertView, parent);
		            	 //set font
		            	 SFUIFonts.LIGHT.apply(context, ((TextView)v.findViewById(android.R.id.text1)));
		            	 //set radio
		                 final RadioButton radio = (RadioButton) v.findViewById(R.id.radioButton1);
		                 if (list.isItemChecked(position)) {
		                	 radio.setChecked(true);
		                 } else {
		                	 radio.setChecked(false);
		                 }
		                 
		                 View.OnClickListener clickItem = new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								sPref.edit().putInt(Constants.PREFERENCES_CURRENT_LANGUAGE, position).commit();
								notifyDataSetChanged();
								list.setItemChecked(position, true);
							}
						};
		                 
		                 v.setOnClickListener(clickItem);
		                 radio.setOnClickListener(clickItem);
		                 return v;
		            }
		        });
		        
		        final int indexCalculated = sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, 0);
		        list.setItemChecked(indexCalculated, true);
				
				apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						language.setSummary(getResources().getStringArray(R.array.languages)[sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, 0)]);
						alert.dismiss();
						Editor ed = sPref.edit();  
						ed.putBoolean(Constants.PREFERENCES_OPEN_SETTINGS_FIRST, true); 	
						ed.commit();
						Intent intent = getActivity().getIntent();
						getActivity().finish();
						startActivity(intent);
						getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
					}
				});
		    	
		    	cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sPref.edit().putInt(Constants.PREFERENCES_CURRENT_LANGUAGE, indexCalculated).commit();
						language.setSummary(getResources().getStringArray(R.array.languages)[indexCalculated]);
						alert.dismiss();
					}
				});
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
				return false;
			}
        });
        
        notificationTime = (TimePreference) findPreference ("preference_show_notifications_time");
        notificationTime.setMinute(sPref.getInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_MINUTE, 0));
        notificationTime.setHour(sPref.getInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_HOUR, 8));
        notificationTime.setOnTimeSetListener(new OnTimeSetListener(){
			@Override
			public boolean onTimeSet(TimePreference preference, long date, int hour, int minute) {
				Editor ed = sPref.edit();   
				ed.putInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_MINUTE, minute);				
				ed.putInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_HOUR, hour);				
				ed.commit();
				notificationTime.setSummary(intPlusZero(hour) + ":" + intPlusZero(minute));
				scheduleUpdate(getActivity());
				return false;
			}
        });
        
        showNotifications = (CheckBoxPreference) findPreference ("preference_show_notifications");
        showNotifications.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				showNotifications.setChecked(!showNotifications.isChecked());
				notificationTime.setEnabled(showNotifications.isChecked());
				showNotifications.setSummary(showNotifications.isChecked() ? getResources().getString(R.string.preference_show_notification_2) : getResources().getString(R.string.preference_show_notification_3));
				
				Editor ed = sPref.edit();   
				ed.putBoolean(Constants.PREFERENCES_SHOW_NOTIFICATIONS, showNotifications.isChecked()); 	
				ed.commit();
				scheduleUpdate(getActivity());
				return false;
			}
        });

    }
    
    @Override
    public void onResume() {
        super.onResume();
        setTitle(bundle.getString(Constants.BUNDLE_LIST_TITLE_NAME));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar_color)));
        //setup preferences listview
        getListView().setBackgroundColor(getResources().getColor(R.color.slider_menu_background));
        getListView().setDivider(getResources().getDrawable(R.color.slider_menu_custom_divider));
        getListView().setSelector(getResources().getDrawable(R.drawable.slider_menu_selector));
        
        updateSummary();
    }
    
    public void updateSummary(){
        //update summary
    	SimpleDateFormat format1;
    	if (sPref.getInt(Constants.PREFERENCES_DATE_FORMAT, 0) == 1 && sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, 0) == 0)
    		format1 = new SimpleDateFormat("MM.dd.yyyy");
    	else
    		format1 = new SimpleDateFormat("dd.MM.yyyy");
        cal = Calendar.getInstance();
        cal.set(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20), sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH)), sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)));
        dateBorn.setSummary(format1.format(cal.getTime()));
        zodiacSign.setSummary(getResources().getStringArray(R.array.zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]);
        language.setSummary(getResources().getStringArray(R.array.languages)[sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, 0)]);
        notificationTime.setEnabled(sPref.getBoolean(Constants.PREFERENCES_SHOW_NOTIFICATIONS, true));
        notificationTime.setSummary(intPlusZero(sPref.getInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_HOUR, 8)) + ":" + intPlusZero(sPref.getInt(Constants.PREFERENCES_NOTIFICATIONS_TIME_MINUTE, 0)));
        showNotifications.setSummary(sPref.getBoolean(Constants.PREFERENCES_SHOW_NOTIFICATIONS, true) ? getResources().getString(R.string.preference_show_notification_2) : getResources().getString(R.string.preference_show_notification_3));
    }
    
    String intPlusZero(int s){
    	return s/10==0 ? "0"+s : ""+s;
    }
    
	int zodiacNumber (int day, int month){
		if((month == 1) && (day <= 20) || (month == 12) && (day >= 22)) {
			return 9;
		 } else if((month == 1) || (month == 2) && (day <= 19)) {
			 return 10;
		 } else if((month == 2) || (month == 3) && (day <= 20)) {
			 return 11;
		 } else if((month == 3) || (month == 4) && (day <= 19)) {
			 return 0;
		 } else if((month == 4) || (month == 5) && (day <= 21)) {
			 return 1;
		 } else if((month == 5) || (month == 6) && (day <= 21)) {
			 return 2;
		 } else if((month == 6) || (month == 7) && (day <= 23)) {
			 return 3;
		 } else if((month == 7) || (month == 8) && (day <= 23)) {
			 return 4;
		 } else if((month == 8) || (month == 9) && (day <= 23)) {
			 return 5;
		 } else if((month == 9) || (month == 10) && (day <= 23)) {
			 return 6;
		 } else if((month == 10) || (month == 11) && (day <= 22)) {
			 return 7;
		 } else if(month == 12 || month == 11) {
			 return 8;
		 }
		 return 0;
	}
	
	public int chineseSign(int day, int month, int year){
		int result = -1;
		for (String one : getResources().getStringArray(R.array.chinese_sign_table)){
			String [] current = one.split(",");
			int yyyyStart = Integer.parseInt(current[2]);
			int yyyyEnd = Integer.parseInt(current[5]);
			if ((year>=yyyyStart) && (year<=yyyyEnd)){
				int ddStart = Integer.parseInt(current[1]);
				int ddEnd = Integer.parseInt(current[4]);
				int mmStart = Integer.parseInt(current[0]);
				int mmEnd = Integer.parseInt(current[3]);
				
				Calendar start = Calendar.getInstance();
				start.setTimeInMillis(0);
				start.set(yyyyStart, mmStart, ddStart);
				
				Calendar end = Calendar.getInstance();
				end.setTimeInMillis(0);
				end.set(yyyyEnd, mmEnd, ddEnd);
				
				Calendar curr = Calendar.getInstance();
				curr.setTimeInMillis(0);
				curr.set(year, month, day);
				
				if ((curr.getTimeInMillis()>=start.getTimeInMillis()) && (curr.getTimeInMillis()<=end.getTimeInMillis())){
					result = Integer.parseInt(current[6]);
					break;
				}
			}
		}
		return result;
	}
	
	public int chineseSignCorrected(int chineseSign){
		switch(chineseSign){
		case 0:
			return 3;
		case 1:
			return 1;
		case 2:
			return 6;
		case 3:
			return 7;
		case 4:
			return 5;
		case 5:
			return 4;
		case 6:
			return 8;
		case 7:
			return 2;
		case 8:
			return 9;
		case 9:
			return 10;
		case 10:
			return 11;
		case 11:
			return 12;
		default: 
			return -1;
		}
	}
	
	public int personalNumber(int day, int month, int year){
    	int personalNumberTemp = 0;
    	
    	int daySummTemp = 0;
    	while (day > 9 && day!=11 && day!=22){
    		daySummTemp = day;
    		day = 0;
    		
        	while (daySummTemp > 0) {
        		day += daySummTemp % 10;
        		daySummTemp = daySummTemp / 10;
        	}
    	}
    	personalNumberTemp += day;
    	
    	int monthSummTemp = 0;
    	while (month > 9 && month!=11 && month!=22){
    		monthSummTemp = month;
    		month = 0;
    		
        	while (monthSummTemp > 0) {
        		month += monthSummTemp % 10;
        		monthSummTemp = monthSummTemp / 10;
        	}
    	}
    	personalNumberTemp += month;
    	
    	int yearSummTemp = 0;
    	while (year > 9 && year!=11 && year!=22){
    		yearSummTemp = year;
    		year = 0;
    		
        	while (yearSummTemp > 0) {
        		year += yearSummTemp % 10;
        		yearSummTemp = yearSummTemp / 10;
        	}
    	}
    	personalNumberTemp += year;
    	
    	int personalNumber = personalNumberTemp;
    	while (personalNumber > 9 && personalNumber!=11 && personalNumber!=22){
    		personalNumberTemp = personalNumber;
    		personalNumber = 0;
    		
        	while (personalNumberTemp > 0) {
        		personalNumber += personalNumberTemp % 10;
        		personalNumberTemp = personalNumberTemp / 10;
        	}
    	}
    	
    	return personalNumber;
	}
	
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
