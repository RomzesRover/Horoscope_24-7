package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;

import com.BBsRs.horoscopeFullNew.R;
import com.actionbarsherlock.app.ActionBar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;

public class ActivityAgreement extends Activity {
	
	SharedPreferences sPref;    // ��� ��� �������� ��
	 Editor ed;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_agreement);
	    final ActionBar ab = getSupportActionBar();
		ab.setSubtitle(getResources().getString(R.string.agreement_top).replace(":", ""));
		ab.setLogo(getResources().getDrawable(R.drawable.ic_launcher));
	    // TODO Auto-generated method stub
	    
	    final Button DisAgree = (Button)findViewById(R.id.buttonStepOne);
	    final Button Agree = (Button)findViewById(R.id.buttonStepThree);
	    sPref = getSharedPreferences("T", 1);
											//������� � 1�� ����
	    DisAgree.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
			    ed.putBoolean("agree", false);						//����, �� ������
		        ed.commit();
		        overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
				finish();
			}
		});
	    									//������� � 3�� ����
	    Agree.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
			    ed.putBoolean("agree", true);						//����, �� ������
		        ed.commit();
				
		        Intent step1 = new Intent(getApplicationContext(), ActivityLoader.class);
				startActivity(step1);		//��������
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
			}
		});
	}
	
	@Override
	public void onBackPressed(){
		overridePendingTransition(R.anim.push_left_out,R.anim.push_left_in);
		finish();
	}

}
