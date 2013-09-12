package com.BBsRs.horoscopefree;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ListView;

import adapter.ListViewAdapterForProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.BBsRs.horoscopefree.R;
import com.actionbarsherlock.app.ActionBar;

public class ActivityHoroSettings3 extends Activity {
	 SharedPreferences sPref;    // для стр настроек жж
	 ListViewAdapterForProviders listViewAdapterForProviders;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings3);
	    sPref =getSharedPreferences("T", 1);
	    final ActionBar ab = getSupportActionBar();
		ab.setTitle(getResources().getString(R.string.menu_settings));
		ab.setSubtitle(getResources().getString(R.string.step3));
        
	    // TODO Auto-generated method stub
	    ListView listProvider = (ListView)findViewById(R.id.listView1);
        listViewAdapterForProviders = new ListViewAdapterForProviders(getApplicationContext(),getResources().getStringArray(R.array.providers));
	    listProvider.setAdapter(listViewAdapterForProviders);
	    listViewAdapterForProviders.onSetChecked(sPref.getInt("providerNumber",Integer.parseInt(getResources().getString(R.string.Lg))));
	    if (sPref.getInt("providerNumber",25)==25){
	    	Editor ed = sPref.edit();   // пока ничего не сохраняем, делаем всё как надо :)
			ed.putInt("providerNumber", Integer.parseInt(getResources().getString(R.string.Lg)));						//знак, по номеру
			ed.commit();
	    }
	    listProvider.setSelectionFromTop(sPref.getInt("providerNumber",Integer.parseInt(getResources().getString(R.string.Lg))),sPref.getInt("providerNumber",Integer.parseInt(getResources().getString(R.string.Lg))));	//листаем вьюху к совему знаку
//	    
	    listProvider.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				listViewAdapterForProviders.onSetChecked(arg2);
				Editor ed = sPref.edit();   // пока ничего не сохраняем, делаем всё как надо :)
				ed.putInt("providerNumber", arg2);						//знак, по номеру
				ed.commit();
			}
	    });
	    final Button step2 = (Button)findViewById(R.id.buttonStepTwo);
	    final Button stepEnd = (Button)findViewById(R.id.buttonStepEnd);
	    
	    step2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings2.class);
				startActivity(step1);
				overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
				finish();
			}
		});
	    
	    stepEnd.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent stepEnd = new Intent(getApplicationContext(), ActivityResultPage.class);
				startActivity(stepEnd);
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
			}
		});
	}
	@Override
	public void onBackPressed(){
		if (sPref.getBoolean("canBack", false)){
			Intent step2 = new Intent(getApplicationContext(), ActivityResultPage.class);
			startActivity(step2);		//анимация перехода
			overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
			Editor ed=sPref.edit();
			ed.putBoolean("canBack", false);
		    ed.commit();
		    finish();
		}
		else {
			Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings2.class);
			startActivity(step1);		//анимация
			overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
			finish();
		}
	}
	

	@Override
	public void onStop(){
		super.onStop();
		Editor ed=sPref.edit();
		ed.putBoolean("canBack", false);
	    ed.commit();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}
}
