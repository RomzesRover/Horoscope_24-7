package com.BBsRs.horoscopefree;

import java.io.IOException;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ProgressBar;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.BBsRs.horoscopefree.R;
import com.actionbarsherlock.app.ActionBar;

public class ActivityLoaderVersion extends Activity {
	SharedPreferences sPref;
	Thread thr;
	boolean animation=false;
	String tag = "ActivityLoaderVersion";
	private RelativeLayout errorLayout, allNormalLayout;
	private ProgressBar progressBar;
	private final Handler handler = new Handler();
	 WebView webV;
	 Document doc;
	 int delayForThread;
	 String html;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_loader_version);
	    webV = (WebView)findViewById(R.id.webView1);
	    errorLayout = (RelativeLayout)findViewById(R.id.errorLayout);
	    allNormalLayout =(RelativeLayout)findViewById(R.id.content);
	    progressBar = (ProgressBar)findViewById(R.id.progressBar1);
	    
	    delayForThread=getResources().getInteger(R.integer.DelayForThreads);
	    
	    final ActionBar ab = getSupportActionBar();
	    ab.setLogo(getResources().getDrawable(R.drawable.ic_launcher));			//for miui and other
	    sPref = getSharedPreferences("T", 1);
	    if (sPref.getString("help or info", "info").equals("info")){
	    	if(savedInstanceState != null){
	    		newloading(savedInstanceState.getString("html"));
	    	html=savedInstanceState.getString("html");}
	    	else 
	    	 Load(getResources().getString(R.string.infoUrl));
	    	 ab.setSubtitle(getResources().getString(R.string.version));
	    } else {
	    	if(savedInstanceState != null && savedInstanceState.getString("html").length()>1){
	    		newloading(savedInstanceState.getString("html"));
	    		html=savedInstanceState.getString("html");
	    	}
	    	else 
	    	Load(getResources().getString(R.string.helpUrl));
	    	ab.setSubtitle(getResources().getString(R.string.about));
	    }
	   
	    
	    
	    final Button start = (Button)findViewById(R.id.start);
	    start.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (sPref.getString("help or info", "info").equals("info")){
				// TODO Auto-generated method stub
				sPref = getSharedPreferences("T", 1);
				String prpr = sPref.getString("name", "");
		        if (prpr.length()<1){
		        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
					startActivity(step1);
					overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
					finish();
		        } else {
		        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
					startActivity(step1);
					overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
					finish();
		        }
			}else {
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
			}
			}
			
		});
	    // TODO Auto-generated method stub
	}
	public void newloading(final String html){
		final Runnable updaterText = new Runnable() {
	        public void run() {
	        	webV.setBackgroundColor(Color.TRANSPARENT);
	        	webV.loadDataWithBaseURL(null,html
	                    ,"text/html", "utf-8",null); // ��������� �������� ��������
	        	allNormalLayout.setVisibility(View.VISIBLE);
	        	progressBar.setVisibility(View.GONE);
	        	errorLayout.setVisibility(View.GONE);
	        }
	    };
	    handler.post(updaterText);
	}
	
	public void Error(){
		final Runnable updaterText = new Runnable() {
	        public void run() {
	        	allNormalLayout.setVisibility(View.GONE);
	        	progressBar.setVisibility(View.GONE);
	        	errorLayout.setVisibility(View.VISIBLE);
	        	
	        	Button retry = (Button)errorLayout.findViewById(R.id.retry);
	        	retry.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Intent step1 = new Intent(getApplicationContext(), ActivityLoaderVersion.class);
						startActivity(step1);
						animation=true;
						finish();
					}
				});
	        }
	    };
	    handler.post(updaterText);
	}
	
	
	public void Load(final String url){
		thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
	        	try {							//�������� �������
	        		Thread.sleep(delayForThread);
					doc = Jsoup.connect(url).userAgent("Opera").get();
					html=doc.html().replaceAll("src=\"/", "src=\"http://brothers-rovers.3dn.ru/");
					newloading(html);
					Log.i(tag, "succes: "+url);
				}catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(tag, "Load Error "+url);
					Error();
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					Log.e(tag, "Load Error "+url);
					Error();
					e.printStackTrace();
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					Log.e(tag, "Load Error "+url);
					Error();
					e.printStackTrace();
				}}
        });
	thr.start();
	}
	
	@Override
	public void onPause(){
		super.onPause();
		if (!animation){
			overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			sPref = getSharedPreferences("T", 1);
			if (sPref.getBoolean("canBack", false)){
				overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
				Editor ed=sPref.edit();
				ed.putBoolean("canBack", false);
			    ed.commit();
			}
			}
		else 
			overridePendingTransition(R.anim.nulling_animation, R.anim.nulling_animation);
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		 outState.putString("html", html);
	}
}
