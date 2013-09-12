package com.BBsRs.horoscopefree;

import java.util.Calendar;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.DatePicker;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.TimePicker;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnFocusChangeListener;

import com.BBsRs.horoscopefree.R;
import com.actionbarsherlock.app.ActionBar;

public class ActivityHoroSettings1 extends Activity {

	/** Called when the activity is first created. */
	
	SharedPreferences sPref;    // для стр настроек жж
	boolean textFr=true;		//для текста имени
	EditText edName;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.settings1);
	    final ActionBar ab = getSupportActionBar();
		ab.setTitle(getResources().getString(R.string.menu_settings));
		ab.setSubtitle(getResources().getString(R.string.step1));
	    // TODO Auto-generated method stub
	    									//анимация появления элеметнов первого шага
	    final Button step2 = (Button)findViewById(R.id.buttonStepTwo);
		//обьекты с которых ерем данные
	    edName = (EditText)findViewById(R.id.editTextName);
		final DatePicker edDateBirth = (DatePicker)findViewById(R.id.datePicker1);
		final TimePicker edTimeBirth = (TimePicker)findViewById(R.id.timePicker1);
		final TextView txt = (TextView)findViewById(R.id.TextView01);
	    									//едит текст делаем как надо. черным при вводе и убираем херню всякую
		loadData(edName, edDateBirth, edTimeBirth,txt);				//загружаем данные, если они есть :)
		
	    edName.setOnFocusChangeListener(new OnFocusChangeListener(){
			public void onFocusChange(View v, boolean hasFocus) {
					if (textFr && hasFocus){
					edName.setTextColor(Color.BLACK);
					edName.setText("");
					textFr=false;
					}
					if (!hasFocus && String.valueOf(edName.getText()).length()<=0){
						edName.setText(String.valueOf(getResources().getString(R.string.noname)));
						textFr=true;
						edName.setTextColor(Color.DKGRAY);
					}
			}
	    	
	    });
	    									//при переходе ко второму шагу
	    step2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
											//очищаем фокус, чтобы избежать ошибок!
				edDateBirth.clearFocus();
				edName.clearFocus();
				edTimeBirth.clearFocus();
				sPref = getSharedPreferences("T", 1);
		        Editor ed = sPref.edit();   // пока ничего не сохраняем, делаем всё как надо :)
		        ed.putString("name", String.valueOf(edName.getText()));  		//имя
			    ed.putInt("dayBorn", edDateBirth.getDayOfMonth());				//день
			    ed.putInt("monthBorn", edDateBirth.getMonth());				//месяц
			    ed.putInt("yearBorn", edDateBirth.getYear());					//год
			    ed.putInt("minuteBorn", edTimeBirth.getCurrentMinute()); 		//минуты
			    ed.putInt("hourBorn", edTimeBirth.getCurrentHour());			//часы
			    																//знак от дня и месяца
			    ed.putInt("zodiacNumber", zodiacNumber(edDateBirth.getDayOfMonth(),edDateBirth.getMonth()+1));						//знак, по номеру
			    //Toast.makeText(getApplicationContext(), getResources().getString(R.string.Automatic)+"\n"+getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber(edDateBirth.getDayOfMonth(),edDateBirth.getMonth())], Toast.LENGTH_LONG).show();
		        ed.commit();
		        							//стартуем второй шаг
		        Intent step2 = new Intent(getApplicationContext(), ActivityHoroSettings2.class);
				startActivity(step2);		//анимация перехода
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
			}
		});
	}
	void loadData(EditText name, DatePicker date, TimePicker time, TextView txt){
		
		sPref = getSharedPreferences("T", 1);
		
		name.setText(sPref.getString("name", getResources().getString(R.string.noname)));
		
		Calendar cal=Calendar.getInstance();
		date.updateDate(sPref.getInt("yearBorn", cal.get(Calendar.YEAR)), sPref.getInt("monthBorn", cal.get(Calendar.MONTH)), sPref.getInt("dayBorn", cal.get(Calendar.DAY_OF_MONTH)));
		
		time.setCurrentHour(sPref.getInt("hourBorn", cal.get(Calendar.HOUR_OF_DAY)));
		time.setCurrentMinute(sPref.getInt("minuteBorn", cal.get(Calendar.MINUTE)));
		
	}
	int zodiacNumber (int day, int month){					//функция определения знака зодиака по дате рождения
		if((month == 1) && (day <= 20) || (month == 12) && (day >= 22)) {
			return 10;
		 } else if((month == 1) || (month == 2) && (day <= 19)) {
			 return 11;
		 } else if((month == 2) || (month == 3) && (day <= 20)) {
			 return 12;
		 } else if((month == 3) || (month == 4) && (day <= 19)) {
			 return 1;
		 } else if((month == 4) || (month == 5) && (day <= 21)) {
			 return 2;
		 } else if((month == 5) || (month == 6) && (day <= 21)) {
			 return 3;
		 } else if((month == 6) || (month == 7) && (day <= 23)) {
			 return 4;
		 } else if((month == 7) || (month == 8) && (day <= 23)) {
			 return 5;
		 } else if((month == 8) || (month == 9) && (day <= 23)) {
			 return 6;
		 } else if((month == 9) || (month == 10) && (day <= 23)) {
			 return 7;
		 } else if((month == 10) || (month == 11) && (day <= 22)) {
			 return 8;
		 } else if(month == 12) {
			 return 9;
		 }
		 return 1;
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
