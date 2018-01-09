package com.BBsRs.horoscopeNewEdition.Base;

import org.holoeverywhere.app.Fragment;

public class BaseFragment extends Fragment{
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
