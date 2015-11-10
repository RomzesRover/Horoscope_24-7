package com.BBsRs.horoscopeNewEdition;

import org.holoeverywhere.app.Activity;

import com.BBsRs.horoscopeFullNew.ContentShowActivity;

import android.content.Intent;
import android.os.Bundle;

public class ActivityRestarter extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    startActivity(new Intent(getApplicationContext(), ContentShowActivity.class));
    	overridePendingTransition(0, 0);
    	finish();
	}

}
