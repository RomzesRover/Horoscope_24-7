package com.BBsRs.horoscopeFullNew.Base;

import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.LinearLayout;
import org.holoeverywhere.widget.TextView;
import org.jsoup.Jsoup;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeNewEdition.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BaseFragment extends Fragment{
	
	//!----------------------------------AD-----------------------------------------------------!
  	private AdView adView;
	private final Handler handler2 = new Handler();
  	//!----------------------------------AD-----------------------------------------------------!
	
	public void showAd(View v, SharedPreferences sPref){
		
		//if user on high exit!
		if (sPref.getBoolean("isOnHigh", false))
			return;
		
		final LinearLayout layout = (LinearLayout) v.findViewById(R.id.mainRtLt);
		layout.setVisibility(View.VISIBLE);
		
		new Thread (new Runnable(){
			@Override
			public void run() {
				try {
					
					String AdSource = "ca-app-pub-6690318766939525/9990722098";
					try {
						AdSource = Jsoup.connect("https://raw.githubusercontent.com/RomzesRover/common_repository_for_static_files/master/Horoscope/horo_files/adsource.txt").timeout(10000).get().text();
					} catch (Exception e) {
						AdSource = "ca-app-pub-6690318766939525/9990722098";
						e.printStackTrace();
					}
					
					if (AdSource.equals(null) || AdSource.length()>50 || AdSource.length()<10){
						Log.i("AD", "Problems with load AD !");
						Log.i("AD", "here1b");
						handler2.post(new Runnable(){
							@Override
							public void run() {
								layout.setVisibility(View.GONE);
							}
						});
					} else {
						final String AdSourceFinalled = AdSource;
						handler2.post(new Runnable(){
							@Override
							public void run() {
								try {
									// INIT adView.
								    adView = new AdView(getActivity());
								    adView.setAdUnitId(AdSourceFinalled);
								    adView.setAdSize(AdSize.BANNER);
								    // adding adView to view.
								    layout.addView(adView);
								    layout.setVisibility(View.VISIBLE);
								    // init base request.
								    AdRequest adRequest = new AdRequest.Builder().build();

								    // download AD.
								    adView.loadAd(adRequest);
								} catch (Exception e){
									Log.i("AD", "Problems with load AD !");
									Log.i("AD", "hereb2");
									layout.setVisibility(View.GONE);
								}
							}
						});
					}
					
				} catch (Exception e){
					Log.i("AD", "Problems with load AD !");
					Log.i("AD", "hereb3");
					handler2.post(new Runnable(){
						@Override
						public void run() {
							layout.setVisibility(View.GONE);
						}
					});
				}
			}
		}).start();
		
		//!----------------------------------AD-----------------------------------------------------!

		//!----------------------------------AD-----------------------------------------------------!
	}
	
	@Override
	  public void onPause() {
		if (adView != null)
	    adView.pause();
	    super.onPause();
	  }
	
	@Override
	public void onResume() {
		super.onResume();
		
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
		
		if (adView != null)
		adView.resume();
		
		//send that new fragment is created!
		getActivity().sendBroadcast(new Intent("fragment_changed"));
	}     		
   		
 	@Override
 	public void onDestroy() {
 		super.onDestroy();
 		if (adView != null)
		adView.destroy();
 	}
	
	public Intent createShareIntent(String text) {
	      Intent shareIntent = new Intent(Intent.ACTION_SEND);
	      shareIntent.setType("text/plain");
	      shareIntent.putExtra(Intent.EXTRA_TEXT, text);
	      return shareIntent;
	}	
	
    public void setTitle(String title){
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowCustomEnabled(true);
		View actionTitle = getLayoutInflater().inflate(R.layout.action_bar, null);
		//set font
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), ((TextView)actionTitle.findViewById(R.id.titleActionBar)));
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), ((TextView)actionTitle.findViewById(R.id.subtitleActionBar)));
		//separate text
		String[] titles = title.split(";");
		if (titles.length==1){
			((TextView)actionTitle.findViewById(R.id.titleActionBar)).setText(titles[0]);
			((TextView)actionTitle.findViewById(R.id.subtitleActionBar)).setVisibility(View.GONE);
		} else {
			((TextView)actionTitle.findViewById(R.id.titleActionBar)).setText(titles[0]);
			((TextView)actionTitle.findViewById(R.id.subtitleActionBar)).setText(titles[1]);
			((TextView)actionTitle.findViewById(R.id.subtitleActionBar)).setVisibility(View.VISIBLE);
		}
		actionBar.setCustomView(actionTitle,
		        new ActionBar.LayoutParams(
		                ActionBar.LayoutParams.WRAP_CONTENT,
		                ActionBar.LayoutParams.MATCH_PARENT,
		                Gravity.CENTER
		        )
		);
    }

}
