package com.BBsRs.horoscopeFullNew.Base;

import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.LinearLayout;

import android.content.Intent;
import android.view.View;

import com.BBsRs.horoscopeFullNew.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class BaseFragment extends Fragment{
	
	//!----------------------------------AD-----------------------------------------------------!
  	private AdView adView;
  	//!----------------------------------AD-----------------------------------------------------!
	
	public void showAd(View v, SharedPreferences sPref){
		
		//!----------------------------------AD-----------------------------------------------------!
		if (sPref.getBoolean("agreeWithAd", false)){
		// INIT adView.
	    adView = new AdView(getActivity());
	    adView.setAdUnitId("ca-app-pub-0799144907631986/7771742972");
	    adView.setAdSize(AdSize.BANNER);

	    LinearLayout layout = (LinearLayout) v.findViewById(R.id.mainRtLt);

	    // adding adView to view.
	    layout.addView(adView);
	    layout.setVisibility(View.VISIBLE);
	    // init base request.
	    AdRequest adRequest = new AdRequest.Builder().build();

	    // download AD.
	    adView.loadAd(adRequest);
		} else {
			LinearLayout layout = (LinearLayout)v.findViewById(R.id.mainRtLt);
			layout.setVisibility(View.GONE);
		}
		//!----------------------------------AD-----------------------------------------------------!
	}
	
//	@Override
//	  public void onPause() {
//		if (adView != null)
//	    adView.pause();
//	    super.onPause();
//	  }
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		if (adView != null)
//		adView.resume();
//	}     		
   		
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
