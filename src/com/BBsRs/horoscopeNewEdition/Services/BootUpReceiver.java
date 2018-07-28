package com.BBsRs.horoscopeNewEdition.Services;

import java.util.Calendar;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.BBsRs.horoscopeNewEdition.Base.Constants;

public class BootUpReceiver extends BroadcastReceiver {
	
	//preferences 
    SharedPreferences sPref;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			//set up preferences
	        sPref = PreferenceManager.getDefaultSharedPreferences(context);
			scheduleUpdate(context);
		} catch (Exception e){
			e.printStackTrace();
		}
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
