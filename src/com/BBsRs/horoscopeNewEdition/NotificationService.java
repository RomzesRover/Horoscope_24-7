package com.BBsRs.horoscopeNewEdition;

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
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.BBsRs.horoscopeFullNew.ActivityLoader;

public class NotificationService extends Service {
	
	private static String LOG_TAG = "Notification Service Horoscope";
	
	private MainMusicListUpdateTask mTask;
	
	//preferences 
    SharedPreferences sPref;
    
	NotificationCompat.Builder mBuilder;
	Notification notification;
	NotificationManager mNotificationManager;
	PendingIntent contentIntent, PendingDeleteIntent;


	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
    	Log.i(LOG_TAG, "start command");
    	
    	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	
    	boolean active = mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED;
    	
		if (active) {
			Log.i(LOG_TAG, "task is already running");
			return super.onStartCommand(intent, flags, startId);
		}
		
		registerReceiver(startActivity, new IntentFilter("MP_START_ACTIVITY_INTENT"));
		registerReceiver(deleteNotification, new IntentFilter("MP_DELETE_INTENT"));
		
		//create notification manager
		mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		contentIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("MP_START_ACTIVITY_INTENT"), 0);
		PendingDeleteIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, new Intent("MP_DELETE_INTENT"), 0);
    	
    	mTask = new MainMusicListUpdateTask();
        mTask.execute();
    	
		return super.onStartCommand(intent, flags, startId);
    }
    
    public void onDestroy() {
    	Log.i(LOG_TAG, "destroy");
		if (mTask != null && mTask.getStatus() != AsyncTask.Status.FINISHED) {
			mTask.cancel(true);
			mTask = null;
		}
		
		try {
			unregisterReceiver(startActivity);
			unregisterReceiver(deleteNotification);
		} catch (Exception e){
			e.printStackTrace();
		}
		super.onDestroy();
	}
    
    private class MainMusicListUpdateTask extends AsyncTask<Void, Void, Void> {
        private WakeLock mWakeLock;
        private Context mContext;

        public MainMusicListUpdateTask() {
            Log.i(LOG_TAG, "Starting notification update task");
            PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
            mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, LOG_TAG);
            mWakeLock.setReferenceCounted(false);
            mContext = NotificationService.this;
        }

        @Override
        protected void onPreExecute() {
        	Log.i(LOG_TAG, "ACQUIRING WAKELOCK");
            mWakeLock.acquire();
        }

		@SuppressWarnings("unchecked")
		@Override
		protected Void doInBackground(Void... params) {
			Log.i(LOG_TAG, "load and check notification");
			
			try {
				
				Random rand = new Random();
				int randomNum  = rand.nextInt((3 - 0) + 1) + 0;
				
				//create new
				mBuilder = new NotificationCompat.Builder(mContext);
				
				// define sound URI, the sound to be played when there's a notification
				Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
				
				mBuilder.setContentTitle(mContext.getString(R.string.app_name))
				.setContentText(mContext.getResources().getStringArray(R.array.notification_messages)[randomNum])
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
				notification.tickerText = mContext.getString(R.string.app_name)+" "+mContext.getResources().getStringArray(R.array.notification_messages)[randomNum];
				
				notification.deleteIntent = PendingDeleteIntent;
				
				mNotificationManager.notify(25, notification);
			} catch (Exception e) {
				this.cancel(false);
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			Log.i(LOG_TAG, "successfully doed stuff");
			finish(true);
		}

		@Override
		protected void onCancelled() {
			Log.i(LOG_TAG, "error in stuff, cancel");
			finish(false);
		}

		private void finish(boolean success) {
			if (success) {
				Log.i(LOG_TAG, "notification successfully updated");
			} else {
				// failure, schedule next download in 30 minutes
				Log.i(LOG_TAG, "notification refresh failed, scheduling update in 30 minutes");
			}
			//set new update
			scheduleUpdate(mContext);

			Log.i(LOG_TAG, "RELEASING WAKELOCK");
			mWakeLock.release();
		}
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

        Log.i(LOG_TAG, "Scheduling next update at " + new Date(workDate.getTimeInMillis()));
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
    
	private BroadcastReceiver startActivity= new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	//user decide to open player activity
	    	startActivity(new Intent(getApplicationContext(), ActivityLoader.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
}
