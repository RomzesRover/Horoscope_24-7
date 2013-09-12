package com.BBsRs.horoscopefree;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ListView;

import adapter.ListViewAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.BBsRs.horoscopefree.R;
import com.actionbarsherlock.app.ActionBar;

public class ActivityHoroSettings2 extends Activity {
	SharedPreferences sPref;    // ��� ��� �������� ��
	ListViewAdapter listViewAdapter;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings2);
	    final ActionBar ab = getSupportActionBar();
		ab.setTitle(getResources().getString(R.string.menu_settings));
		ab.setSubtitle(getResources().getString(R.string.step2));
		ab.setLogo(getResources().getDrawable(R.drawable.ic_launcher));			//for miui and other
	    // TODO Auto-generated method stub
	    final ListView listSign = (ListView)findViewById(R.id.listView1);
	    									//��� ����� ����� �����, ����� ����� �������� ������ ����
	    sPref = getSharedPreferences("T", 1);
	    									//�� ������ �������� ������ list view
//        //�� ������ �������� ������ list view
        listViewAdapter = new ListViewAdapter(getApplicationContext(),getResources().getStringArray(R.array.zodiac_signs));
	    listSign.setAdapter(listViewAdapter);
	    if (sPref.getInt("zodiacNumber", 1)>0 && sPref.getInt("zodiacNumber", 1)<13){
	    listViewAdapter.onSetChecked(sPref.getInt("zodiacNumber", 1)-1);
	    listSign.setSelectionFromTop(sPref.getInt("zodiacNumber", 1)-1, sPref.getInt("zodiacNumber", 1)-1);	}//������� ����� � ������ �����
	    listSign.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				listViewAdapter.onSetChecked(arg2);
				Editor ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
				ed.putInt("zodiacNumber", arg2+1);						//����, �� ������
				ed.commit();
			}
	    });
	   
	    									//���������!
	    final Button step1 = (Button)findViewById(R.id.buttonStepOne);
	    final Button step3 = (Button)findViewById(R.id.buttonStepThree);
	    
											//������� � 1�� ����
	    step1.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
				startActivity(step1);		//��������
				overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
				finish();
			}
		});
	    									//������� � 3�� ����
	    step3.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
		        Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings3.class);
				startActivity(step1);		//��������
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed(){
		Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
		startActivity(step1);		//��������
		overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
		finish();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
