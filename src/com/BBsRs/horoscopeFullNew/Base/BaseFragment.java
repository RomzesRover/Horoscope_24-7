package com.BBsRs.horoscopeFullNew.Base;

import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;

import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeNewEdition.R;

public class BaseFragment extends Fragment{
	
	
	public void showAd(View v, SharedPreferences sPref){
		
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
		
		//send that new fragment is created!
		getActivity().sendBroadcast(new Intent("fragment_changed"));
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
