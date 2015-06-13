package com.BBsRs.horoscopeFullNew.Base;

import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.LinearLayout;
import org.jsoup.Jsoup;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.BBsRs.horoscopeNewEdition.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BaseFragment extends Fragment{
	
	//!----------------------------------AD-----------------------------------------------------!
  	private AdView adView;
	private final Handler handler2 = new Handler();
  	//!----------------------------------AD-----------------------------------------------------!
	
	public void showAd(View v){
		
		final LinearLayout layout = (LinearLayout) v.findViewById(R.id.mainRtLt);
		
		new Thread (new Runnable(){
			@Override
			public void run() {
				try {
					
					String AdSource = "ca-app-pub-6690318766939525/9990722098";
					try {
						AdSource = Jsoup.connect("http://brothers-rovers.3dn.ru/HoroscopeNewEd/adsource.txt").timeout(10000).get().text();
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
		if (adView != null)
		adView.resume();
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

}
