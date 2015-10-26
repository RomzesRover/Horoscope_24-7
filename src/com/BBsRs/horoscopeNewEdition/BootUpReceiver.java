package com.BBsRs.horoscopeNewEdition;

import java.util.Calendar;
import java.util.Date;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

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
    	
    	if (!sPref.getBoolean("preference_show_notifications", true))
    		return;
    	
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        
        Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeInMillis(System.currentTimeMillis());
		
        Calendar workDate = Calendar.getInstance();
        workDate.setTimeInMillis(System.currentTimeMillis());
		
		//send notification everyday at morning
        workDate.set(Calendar.HOUR_OF_DAY, sPref.getInt("preference_show_notifications_time_hour", 8));
        workDate.set(Calendar.MINUTE, sPref.getInt("preference_show_notifications_time_minute", 0));
        workDate.set(Calendar.SECOND, 0);
		
        if (workDate.before(currentDate)){
        	workDate.add(Calendar.DATE, +1);
        }
        
        Log.i("From_BootUpReceiver", "Scheduling next update at " + new Date(workDate.getTimeInMillis()));
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
