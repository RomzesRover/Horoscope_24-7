package com.BBsRs.horoscopeFullNew.Introduce;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.BBsRs.horoscopeFullNew.ContentShowActivity;
import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeNewEdition.NotificationService;
import com.BBsRs.horoscopeNewEdition.R;

public class IntroduceNewThemeThirdFragment extends Fragment{
	
	//alert dialog
    AlertDialog alert = null;
    //preferences 
    SharedPreferences sPref;
    //views
    TextView title, subtitle;
    Button start;
    ImageView stars, sign, lines;
    
    //handler
    Handler handler = new Handler();

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.introduce_new_theme_third);
		
		//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
        //init views
        title = (TextView)contentView.findViewById(R.id.title);
        subtitle = (TextView)contentView.findViewById(R.id.subTitle);
        start = (Button)contentView.findViewById(R.id.start);
        stars = (ImageView)contentView.findViewById(R.id.stars);
        sign = (ImageView)contentView.findViewById(R.id.sign);
        lines = (ImageView)contentView.findViewById(R.id.lines);
        
		//set fonts
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), title);
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), subtitle);
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), start);
		return contentView;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//turn up  receiver
		getActivity().registerReceiver(allIsNormal, new IntentFilter("horo_inro_all_is_normal"));
		getActivity().registerReceiver(otherScreen, new IntentFilter("horo_inro_other_screen"));
	}
    
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(allIsNormal);
		getActivity().unregisterReceiver(otherScreen);
	}
	
	private BroadcastReceiver otherScreen = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        try {
	        	handler.postDelayed(new Runnable(){
					@Override
					public void run() {
						//delete old image
						stars.setImageResource(android.R.color.transparent);
						sign.setImageResource(android.R.color.transparent);
						lines.setImageResource(android.R.color.transparent);
					}
	        	}, 25);
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    }
	};
	
	private BroadcastReceiver allIsNormal = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	        try {
	        	//enable start button
				start.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						//start service
			        	scheduleUpdate(getActivity());
			        	//create intent
						Intent refresh = new Intent(getActivity(), ContentShowActivity.class);
						//restart activity
					    startActivity(refresh);   
					    //set  animation
					    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
					    // stop curr activity
					    getActivity().finish();
					}
				});
				
				//set text
				final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
				Calendar calSet = Calendar.getInstance();
				Calendar currDate = Calendar.getInstance();
				calSet.setTimeInMillis(0);
				calSet.set(sPref.getInt("yearBorn", 1995), sPref.getInt("monthBorn", 4), sPref.getInt("dayBorn", 10), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
				
				title.setText(getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))]);
				subtitle.setText(dateFormat.format(calSet.getTime()));
				
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
			            	Animation flyUpAnimationStars = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_stars);
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
					            	Animation flyUpAnimationLines = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_stars);
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
							            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_sign);
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
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    }
	};
	
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