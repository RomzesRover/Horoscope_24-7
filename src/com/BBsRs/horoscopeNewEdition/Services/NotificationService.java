package com.BBsRs.horoscopeNewEdition.Services;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.BBsRs.horoscopeNewEdition.LoaderActivity;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;

public class NotificationService extends Service {
	
	NotificationCompat.Builder mBuilder;
	Notification notification;
	NotificationManager mNotificationManager;
	PendingIntent contentIntent, PendingDeleteIntent;
	
    // preferences 
    SharedPreferences sPref; 

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	
    	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
		registerReceiver(startActivity, new IntentFilter(Constants.BROADCAST_INTENT_OPEN_APP));
		registerReceiver(deleteNotification, new IntentFilter(Constants.BROADCAST_INTENT_CLOSE_NOTIF));
    	
    	//create notification manager
    	mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(Constants.BROADCAST_INTENT_OPEN_APP), 0);
    	PendingDeleteIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent(Constants.BROADCAST_INTENT_CLOSE_NOTIF), 0);
    	
    	scheduleUpdate(getApplicationContext());
    	
    	showNotif();
    	
		return super.onStartCommand(intent, flags, startId);
    }
    
    public void onDestroy() {
    	
		try {
			unregisterReceiver(startActivity);
			unregisterReceiver(deleteNotification);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		super.onDestroy();
	}
    
    public void showNotif(){
		try {
			Random rand = new Random();
			int randomNum  = rand.nextInt((3 - 0) + 1) + 0;
			
			//create new
			mBuilder = new NotificationCompat.Builder(NotificationService.this);
			
			// define sound URI, the sound to be played when there's a notification
			Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
			
			mBuilder.setContentTitle(NotificationService.this.getString(R.string.app_name))
			.setContentText(NotificationService.this.getResources().getStringArray(R.array.notification_messages)[randomNum])
			.setSmallIcon(R.drawable.ic_notification)
			.setContentIntent(contentIntent)
			.setOngoing(false)
			.setAutoCancel(true)
			.setProgress(0, 0, false)
			.setSound(soundUri)
			.setPriority(NotificationCompat.PRIORITY_HIGH);
			
			//show not
			notification = mBuilder.build();
			//set ticker
			notification.tickerText = NotificationService.this.getString(R.string.app_name)+" "+ NotificationService.this.getResources().getStringArray(R.array.notification_messages)[randomNum];
			
			notification.deleteIntent = PendingDeleteIntent;
			
			mNotificationManager.notify(25, notification);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	private BroadcastReceiver startActivity= new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	//user decide to open player activity
	    	startActivity(new Intent(getApplicationContext(), LoaderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
	    	//user decide to kill the service
	    	stopSelf();
	    }
	};
	
	private BroadcastReceiver deleteNotification = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	//user decide to kill the service
	    	stopSelf();
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
