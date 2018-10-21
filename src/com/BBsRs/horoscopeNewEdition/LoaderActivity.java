package com.BBsRs.horoscopeNewEdition;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog;
import org.holoeverywhere.widget.datetimepicker.date.DatePickerDialog.OnDateSetListener;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Base.Connectivity;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Services.NotificationService;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class LoaderActivity extends BaseActivity {
	
    RelativeLayout relativeContentLayout;
    LinearLayout IntroLayout;
    ImageView stars, sign, lines;
    Button language, dateBorn, zodiacSign, start;
    TextView commSign;
 
    //handler
    Handler handler = new Handler();
    
    // preferences 
    SharedPreferences sPref; 
    
    //alert dialog
    AlertDialog alert = null;
    
    boolean isUserStillInApp = true;


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
	    
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        
        //set date format
        sPref.edit().putInt(Constants.PREFERENCES_DATE_FORMAT, getResources().getInteger(R.integer.date_format)).commit();
	  
        //set app lang
        setLocale(sPref);
	    
        //start allother
        setContentView(R.layout.activity_loader);
        
        //init views
    	relativeContentLayout = (RelativeLayout)this.findViewById(R.id.contentLayout);
    	IntroLayout = (LinearLayout)findViewById(R.id.mainIntroLayout);
        stars = (ImageView)findViewById(R.id.stars);
        sign = (ImageView)findViewById(R.id.sign);
        lines = (ImageView)findViewById(R.id.lines);
        language = (Button)findViewById(R.id.language);
        dateBorn = (Button)findViewById(R.id.date_born);
        zodiacSign = (Button)findViewById(R.id.zodiac_sign);
        start = (Button)findViewById(R.id.start);
        commSign = (TextView)findViewById(R.id.subTitle_3);

		//set fonts
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.title)));
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.subTitle)));
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.title_2)));
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.subTitle_2)));
		SFUIFonts.ULTRALIGHT.apply(this, commSign);
		SFUIFonts.ULTRALIGHT.apply(this, language);
		SFUIFonts.ULTRALIGHT.apply(this, dateBorn);
		SFUIFonts.ULTRALIGHT.apply(this, zodiacSign);
		SFUIFonts.ULTRALIGHT.apply(this, start);
		
		//prog buttons intro
		language.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final Context context = LoaderActivity.this; 								// create context
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
		        
		        final int indexCalculated = sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, getResources().getInteger(R.integer.default_language));
		        list.setItemChecked(indexCalculated, true);
				
				apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
						handler.postDelayed(new Runnable(){
							@Override
							public void run() {
								Intent intent = getIntent();
								finish();
								startActivity(intent);
								overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
							}
						}, 500);
					}
				});
		    	
		    	cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sPref.edit().putInt(Constants.PREFERENCES_CURRENT_LANGUAGE, indexCalculated).commit();
						alert.dismiss();
					}
				});
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
			}
		});
		
		dateBorn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//init date dialog
				final Calendar currDate = Calendar.getInstance();
			    //date change listener
			    OnDateSetListener lis = new OnDateSetListener(){
					@Override
					public void onDateSet(DatePickerDialog dialog, int year,
							int month, int day) {
						
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
							
							for (int i = 0; i <= 7; i++){
								ed.putBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+i, true);
							}
							
							ed.commit();
							commSign.setText(String.format(getResources().getString(R.string.preference_date_set), getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber(day, month+1)], getResources().getStringArray(R.array.chinese_zodiac_signs)[chineseSignCalculated], lifePathNumber));
							updateSummary();
							
							handler.postDelayed(showStartButton, 500);
						} else {
							Toast.makeText(LoaderActivity.this, getResources().getString(R.string.preference_date_check), Toast.LENGTH_LONG).show();
						}
					}
			    };
			    final DatePickerDialog dpd = DatePickerDialog.newInstance(lis, sPref.getInt(Constants.PREFERENCES_YEAR_BORN, currDate.get(Calendar.YEAR)-20), sPref.getInt(Constants.PREFERENCES_MONTH_BORN, currDate.get(Calendar.MONTH)), sPref.getInt(Constants.PREFERENCES_DAY_BORN, currDate.get(Calendar.DAY_OF_MONTH)));
				dpd.show(LoaderActivity.this.getSupportFragmentManager());
			}
		});
		
		zodiacSign.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final Context context = LoaderActivity.this; 								// create context
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
						for (int i = 0; i <= 7; i++){
							ed.putBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+i, true);
						}
						ed.commit();
						zodiacSign.setText(getResources().getStringArray(R.array.zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]);
						alert.dismiss();
					}
				});
		    	
		    	cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sPref.edit().putInt(Constants.PREFERENCES_ZODIAC_SIGN, indexCalculated).commit();
						zodiacSign.setText(getResources().getStringArray(R.array.zodiac_signs)[indexCalculated]);
						alert.dismiss();
					}
				});
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
			}
		});
		
		start.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				handler.postDelayed(hideIntro, 500);
			}
		});
	}
	
	@Override
	public void onPause(){
		//stopp all delayed stuff
		handler.removeCallbacks(mainTask);
		handler.removeCallbacks(showStars);
		handler.removeCallbacks(showLines);
		handler.removeCallbacks(showSign);
		handler.removeCallbacks(startApp);
		handler.removeCallbacks(hideStartTitle);
		handler.removeCallbacks(showStartButton);
		handler.removeCallbacks(hideIntro);
		
		isUserStillInApp = false;
		
		super.onPause();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		isUserStillInApp = true;
		
		//delete old image
		stars.setImageResource(android.R.color.transparent);
		sign.setImageResource(android.R.color.transparent);
		lines.setImageResource(android.R.color.transparent);
		
		relativeContentLayout.setVisibility(View.VISIBLE);
		IntroLayout.setVisibility(View.GONE);
		
		//stopp all delayed stuff
		handler.postDelayed(mainTask, 750);
	}
	
    public void updateSummary(){
        //update summary
    	SimpleDateFormat format1;
    	if (sPref.getInt(Constants.PREFERENCES_DATE_FORMAT, 0) == 1 && sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, getResources().getInteger(R.integer.default_language)) == 0)
    		format1 = new SimpleDateFormat("MM.dd.yyyy");
    	else
    		format1 = new SimpleDateFormat("dd.MM.yyyy");
    	Calendar cal = Calendar.getInstance();
        cal.set(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20), sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH)), sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)));
        dateBorn.setText(format1.format(cal.getTime()));
        zodiacSign.setText(getResources().getStringArray(R.array.zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]);
    }
    
	Runnable mainTask = new Runnable(){
		@Override
		public void run() {
			if (sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, -1) == -1){
				//start intro
				handler.postDelayed(hideStartTitle, 500);
			} else {
				handler.postDelayed(showStars, 500);
			}
		}
	};
	
	Runnable hideStartTitle = new Runnable(){
		@Override
		public void run() {
			try{
				language.setText(getResources().getStringArray(R.array.languages)[sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, getResources().getInteger(R.integer.default_language))]);
				
            	Animation flyUpAnimationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_up_anim_out);
            	relativeContentLayout.startAnimation(flyUpAnimationOut);
            	flyUpAnimationOut.setAnimationListener(new AnimationListener(){
					@Override
					public void onAnimationEnd(Animation arg0) {
						relativeContentLayout.setVisibility(View.GONE);
					}
					@Override
					public void onAnimationRepeat(Animation arg0) { }
					@Override
					public void onAnimationStart(Animation arg0) { }
            	});
            	IntroLayout.setVisibility(View.VISIBLE);
            	//with fly up animation
            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_up_anim);
            	IntroLayout.startAnimation(flyUpAnimation);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	};
	
	Runnable hideIntro = new Runnable(){
		@Override
		public void run() {
			try{
            	Animation flyUpAnimationOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_up_anim_out);
            	IntroLayout.startAnimation(flyUpAnimationOut);
            	flyUpAnimationOut.setAnimationListener(new AnimationListener(){
					@Override
					public void onAnimationEnd(Animation arg0) {
						IntroLayout.setVisibility(View.GONE);
					}
					@Override
					public void onAnimationRepeat(Animation arg0) { }
					@Override
					public void onAnimationStart(Animation arg0) { }
            	});
            	relativeContentLayout.setVisibility(View.VISIBLE);
            	//with fly up animation
            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_up_anim);
            	relativeContentLayout.startAnimation(flyUpAnimation);
            	
            	//finally start!!
            	handler.postDelayed(showStars, 500);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	};
	
	Runnable showStartButton = new Runnable(){
		@Override
		public void run() {
			try{
				if (start.getVisibility() != View.VISIBLE){
					start.setVisibility(View.VISIBLE);
					commSign.setVisibility(View.VISIBLE);
	            	//with fly up animation
	            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fly_up_anim);
	            	start.startAnimation(flyUpAnimation);
	            	commSign.startAnimation(flyUpAnimation);
				}
			}
			catch(Exception e){
				e.printStackTrace();
			}
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
//			loadAndSetupInterstitialAD();
			if (sPref.getBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, true) && isGooglePlayServicesAvailable(getApplicationContext()) && Connectivity.isConnectedFast(getApplicationContext())){
				loadAndSetupInterstitialAD();
			} else {
				sPref.edit().putInt(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT_COUNT, 0).commit();
				showContentActivity();
			}
		}
	};
	
	public boolean isGooglePlayServicesAvailable(Context context){
		try {
		    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		    return resultCode == ConnectionResult.SUCCESS;  
		} catch (Exception e){
			e.printStackTrace();
			return true;
		}
	}
	
	private InterstitialAd interstitial;
	public void loadAndSetupInterstitialAD(){
		try {
		    interstitial = new InterstitialAd(this);
		    interstitial.setAdUnitId("ca-app-pub-6690318766939525/2467455298");

		    AdRequest.Builder builder = new AdRequest.Builder();
		    
			Calendar birthday = Calendar.getInstance();
			birthday.setTimeInMillis(System.currentTimeMillis());
			
			try {
				birthday.set(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, 1995), sPref.getInt(Constants.PREFERENCES_MONTH_BORN, 4), sPref.getInt(Constants.PREFERENCES_DAY_BORN, 10));
			} catch (Exception e){
				e.printStackTrace();
				birthday.set(2000, 04, 10);
			}
			builder.setBirthday(new Date(birthday.getTimeInMillis()));
			
			try {
				final LocationManager mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
				Location loc = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				if (loc!=null)
					builder.setLocation(loc);
			}
			catch (Exception e){
				e.printStackTrace();
			}
			
			AdRequest adRequest = builder.build();
			
			interstitial.setAdListener(new AdListener() {
				@Override
				public void onAdClosed() {
					int count = sPref.getInt(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT_COUNT, 0);
					count++;
					sPref.edit().putInt(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT_COUNT, count).commit();
					showContentActivity();
				}
		        @Override
		        public void onAdLoaded() {
		        	if (interstitial !=null && interstitial.isLoaded() && isUserStillInApp) {
		    			interstitial.show();
		    		}
		        }
		        @Override
		        public void onAdFailedToLoad(int errorCode) {
		        	showContentActivity();
		        }
		    });

		    interstitial.loadAd(adRequest);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void showContentActivity(){
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

}
