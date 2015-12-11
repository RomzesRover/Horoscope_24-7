package com.BBsRs.horoscopeFullNew.Base;

import java.util.Locale;

import org.holoeverywhere.preference.PreferenceFragment;
import org.holoeverywhere.widget.TextView;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeNewEdition.R;

public class BasePreferenceFragment extends PreferenceFragment {
	
	public void setLocale(String lang) {
		 Locale myLocale;
	     myLocale = new Locale(lang);
	     Resources res = getResources();
	     DisplayMetrics dm = res.getDisplayMetrics();
	     Configuration conf = res.getConfiguration();
	     conf.locale = myLocale;
	     res.updateConfiguration(conf, dm);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
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
