package com.BBsRs.horoscopeFullNew.Base;

import org.holoeverywhere.app.Fragment;

import android.content.Intent;

import com.BBsRs.horoscopeFullNew.ContentShowActivity;

public class BaseFragment extends Fragment{
	
	
	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
	}     		
	
	public Intent createShareIntent(String text) {
	      Intent shareIntent = new Intent(Intent.ACTION_SEND);
	      shareIntent.setType("text/plain");
	      shareIntent.putExtra(Intent.EXTRA_TEXT, text);
	      return shareIntent;
	}	
	
    public void setTitle(String title){
    	((ContentShowActivity) getActivity()).setTitle(title);
    }

}
