package com.BBsRs.horoscopeNewEdition.Fragments;

import org.holoeverywhere.LayoutInflater;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BaseFragment;

public class ContentFragment extends BaseFragment{
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_content);
		return contentView;
	}
	
	@Override
    public void onResume() {
        super.onResume();
        setTitle("Greka");
	}
}
