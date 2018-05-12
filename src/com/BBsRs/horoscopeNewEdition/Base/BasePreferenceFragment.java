package com.BBsRs.horoscopeNewEdition.Base;

import org.holoeverywhere.preference.PreferenceFragment;

public class BasePreferenceFragment extends PreferenceFragment {
	@Override
	public void onResume() {
		super.onResume();
		getSupportActionBar().setSubtitle(null);
		getSupportActionBar().setTitle(null);
	}     		
	
    public void setTitle(String title){
    	((BaseActivity) getActivity()).setTitle(title);
    }
}
