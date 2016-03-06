
package com.BBsRs.horoscopeFullNew.MailRuLoaderFragment;

import org.holoeverywhere.LayoutInflater;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.horoscopeFullNew.Base.BaseFragment;
import com.BBsRs.horoscopeNewEdition.R;

public class OrderPersonalHoroscopeFragment extends BaseFragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View contentView = inflater.inflate(R.layout.fragment_content_show);
    	
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	      case android.R.id.home:
	    	  Intent i = new Intent("horo_open_menu_drawer");
	    	  getActivity().sendBroadcast(i);
	    	  break;
    	}
		return true;
    	
    }
}



