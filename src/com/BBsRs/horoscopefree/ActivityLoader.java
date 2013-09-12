package com.BBsRs.horoscopefree;

import java.io.IOException;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.jsoup.Jsoup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class ActivityLoader extends Activity {
	SharedPreferences sPref;    // ��� ��� �������� ��
	 Editor ed;
	 boolean animation=false;
	 int currentVersion;
	private timer CountDownTimer;					// for timer
	  RelativeLayout  layoutErrorView ;
 	 	ImageView logo;
 	 	Button reconnect ;
 	 	private final Handler handler = new Handler();
	public class timer extends CountDownTimer{

		public timer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// extendedClass
		}

		@Override
		public void onFinish() {
			Thread thr=new Thread(new Runnable() {				//������ � ����� ������
		        public void run() {
		        	sPref = getSharedPreferences("T", 1);
		        	
		        	if (!sPref.getBoolean("agree", false)){
		        		Intent step1 = new Intent(getApplicationContext(), ActivityAgreement.class);
						startActivity(step1);
						finish();
		        	} else {
		        	
			if (isNetworkAvailable()){
				//
				 	ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
				    ed.putBoolean("canBack", false);						//����, �� ������
			        ed.commit();
				try {
					currentVersion=Integer.parseInt(Jsoup.connect("http://brothers-rovers.3dn.ru/currentInfo5.0.txt").get().text());
					sPref = getSharedPreferences("T", 1);
					if (currentVersion==sPref.getInt("currentVersion", 0000 )){
						// onFinish
				        String prpr = sPref.getString("name", "");
				        if (prpr.length()<1){
				        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
							startActivity(step1);
							finish();
				        } else {
				        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
							startActivity(step1);
							finish();
				        }
					} else {
						sPref = getSharedPreferences("T", 1);
						ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
						if (currentVersion != 1120)
					    ed.putInt("currentVersion", currentVersion);						//����, �� ������
					    ed.putString("help or info", "info");
				        ed.commit();
				        Intent step1 = new Intent(getApplicationContext(), ActivityLoaderVersion.class);
						startActivity(step1);
						finish();
					}
				} catch (NumberFormatException e) {
					String prpr = sPref.getString("name", "");
			        if (prpr.length()<1){
			        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
						startActivity(step1);
						finish();
			        } else {
			        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
						startActivity(step1);
						finish();
			        }
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					String prpr = sPref.getString("name", "");
			        if (prpr.length()<1){
			        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
						startActivity(step1);
						finish();
			        } else {
			        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
						startActivity(step1);
						finish();
			        }
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					String prpr = sPref.getString("name", "");
			        if (prpr.length()<1){
			        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
						startActivity(step1);
						finish();
			        } else {
			        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
						startActivity(step1);
						finish();
			        }
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				//���� ��� ���� !!!
				connectionError(layoutErrorView,logo,reconnect);
			}
		        	}
		        }
	        });
		thr.start();
		}

		@Override
		public void onTick(long arg0) {
			// on Tick
			
		}
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        //final ActionBar ab = getSupportActionBar();
        //ab.setLogo(getResources().getDrawable(R.drawable.ic_launcher));
        layoutErrorView = (RelativeLayout)findViewById(R.id.errorLayout);
 	 	logo = (ImageView)findViewById(R.id.logo);
 	 	reconnect = (Button)findViewById(R.id.retry);
   	
   		 CountDownTimer = new timer (2000, 1000);   		//timer to 2 seconds (tick one second)
         CountDownTimer.start();							//start timer 
   	 
        
       
       
    }
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
    @Override
	public void onPause(){
		super.onPause();
		if (!animation)
	      	overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
    
	private void connectionError(final RelativeLayout arg1, final ImageView arg2, final Button arg3){
		final Runnable updaterText = new Runnable() {
	        public void run() {

	    		arg1.setVisibility(View.VISIBLE);
	    		arg2.setVisibility(View.GONE);
	    		
	    		arg3.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				animation=true;
	    				Intent step1 = new Intent(getApplicationContext(), ActivityLoader.class);
	    				startActivity(step1);
	    				overridePendingTransition(R.anim.nulling_animation, R.anim.nulling_animation);
	    				finish();
	    			}
	    		});
	        }
	    };
	    handler.post(updaterText);
	}
}
