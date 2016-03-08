
package com.BBsRs.horoscopeFullNew.MailRuLoaderFragment;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.BBsRs.horoscopeFullNew.Base.BaseFragment;
import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeNewEdition.R;

public class OrderPersonalHoroscopeFragment extends BaseFragment {
	
	EditText username, userbirthdate, userbirthtime, userbirthplace, useremail, usertel_skype;
	TextView name, birthdate, birthtime, birthplace, email, tel_skype;
	TextView username_mask, userbirthdate_mask, userbirthtime_mask, userbirthplace_mask, useremail_mask, usertel_skype_mask;
	EditText partner_username, partner_userbirthdate, partner_userbirthtime, partner_userbirthplace;
	TextView partner_name, partner_birthdate, partner_birthtime, partner_birthplace;
	TextView partner_username_mask, partner_userbirthdate_mask, partner_userbirthtime_mask, partner_userbirthplace_mask;
	RadioButton natal, sovm, forecast, kid;
	Button order;
	
    //preferences 
    SharedPreferences sPref;
    
    private final Handler handler = new Handler();
    
    //alert dialog
    Dialog alert = null;
    ProgressDialog prDialog = null;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View contentView = inflater.inflate(R.layout.fragment_order_personal_horoscope);
    	
    	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        
        prDialog = new ProgressDialog(getActivity());
        
    	//enable menu
    	setHasOptionsMenu(true);
    	
    	//init view
    	username = (EditText)contentView.findViewById(R.id.username);
    	userbirthdate = (EditText)contentView.findViewById(R.id.userbirthdate);
    	userbirthtime = (EditText)contentView.findViewById(R.id.userbirthtime);
    	userbirthplace = (EditText)contentView.findViewById(R.id.userbirthplace);
    	useremail = (EditText)contentView.findViewById(R.id.useremail);
    	usertel_skype = (EditText)contentView.findViewById(R.id.usertel_skype);
    	
    	username_mask = (TextView)contentView.findViewById(R.id.textMaskUsername);
    	userbirthdate_mask = (TextView)contentView.findViewById(R.id.textMaskUserbirthdate);
    	userbirthtime_mask = (TextView)contentView.findViewById(R.id.textMaskUserbirthtime);
    	userbirthplace_mask = (TextView)contentView.findViewById(R.id.textMaskUserbirthplace);
    	useremail_mask = (TextView)contentView.findViewById(R.id.textMaskUseremail);
    	usertel_skype_mask = (TextView)contentView.findViewById(R.id.textMaskUsertel_skype);
    	
    	name = (TextView)contentView.findViewById(R.id.name);
    	birthdate = (TextView)contentView.findViewById(R.id.birthdate);
    	birthtime = (TextView)contentView.findViewById(R.id.birthtime);
    	birthplace = (TextView)contentView.findViewById(R.id.birthplace);
    	email = (TextView)contentView.findViewById(R.id.email);
    	tel_skype = (TextView)contentView.findViewById(R.id.tel_skype);
    	
    	partner_username = (EditText)contentView.findViewById(R.id.partner_username);
    	partner_userbirthdate = (EditText)contentView.findViewById(R.id.partner_userbirthdate);
    	partner_userbirthtime = (EditText)contentView.findViewById(R.id.partner_userbirthtime);
    	partner_userbirthplace = (EditText)contentView.findViewById(R.id.partner_userbirthplace);
    	
    	partner_username_mask = (TextView)contentView.findViewById(R.id.partner_textMaskUsername);
    	partner_userbirthdate_mask = (TextView)contentView.findViewById(R.id.partner_textMaskUserbirthdate);
    	partner_userbirthtime_mask = (TextView)contentView.findViewById(R.id.partner_textMaskUserbirthtime);
    	partner_userbirthplace_mask = (TextView)contentView.findViewById(R.id.partner_textMaskUserbirthplace);
    	
    	partner_name = (TextView)contentView.findViewById(R.id.partner_name);
    	partner_birthdate = (TextView)contentView.findViewById(R.id.partner_birthdate);
    	partner_birthtime = (TextView)contentView.findViewById(R.id.partner_birthtime);
    	partner_birthplace = (TextView)contentView.findViewById(R.id.partner_birthplace);
    	
    	natal = (RadioButton)contentView.findViewById(R.id.natal);
    	sovm = (RadioButton)contentView.findViewById(R.id.sovm);
    	forecast = (RadioButton)contentView.findViewById(R.id.forecast);
    	kid  = (RadioButton)contentView.findViewById(R.id.kid);
    	
    	order = (Button)contentView.findViewById(R.id.order);
    	
		//set text
		final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Calendar calSet = Calendar.getInstance();
		Calendar currDate = Calendar.getInstance();
		calSet.setTimeInMillis(0);
		calSet.set(sPref.getInt("yearBorn", 1995), sPref.getInt("monthBorn", 4), sPref.getInt("dayBorn", 10), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
		userbirthdate.setText(dateFormat.format(calSet.getTime()));
		userbirthdate_mask.setVisibility(View.GONE);
    	
//    	//on radio button click
    	natal.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1){
					name.setText(getActivity().getString(R.string.order_personal_horoscope_name));
					birthdate.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_date));
					birthtime.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_time));
					birthplace.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_place));
					//set text
					final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					Calendar calSet = Calendar.getInstance();
					Calendar currDate = Calendar.getInstance();
					calSet.setTimeInMillis(0);
					calSet.set(sPref.getInt("yearBorn", 1995), sPref.getInt("monthBorn", 4), sPref.getInt("dayBorn", 10), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
					userbirthdate.setText(dateFormat.format(calSet.getTime()));
					userbirthdate_mask.setVisibility(View.GONE);
					
					//partner
					partner_username.setText("");
			    	partner_userbirthdate.setText("");
			    	partner_userbirthtime.setText("");
			    	partner_userbirthplace.setText("");
			    	
			    	partner_username.setVisibility(View.GONE);
			    	partner_userbirthdate.setVisibility(View.GONE);
			    	partner_userbirthtime.setVisibility(View.GONE);
			    	partner_userbirthplace.setVisibility(View.GONE);
			    	
			    	partner_username_mask.setVisibility(View.GONE);
			    	partner_userbirthdate_mask.setVisibility(View.GONE);
			    	partner_userbirthtime_mask.setVisibility(View.GONE);
			    	partner_userbirthplace_mask.setVisibility(View.GONE);
			    	
			    	partner_name.setVisibility(View.GONE);
			    	partner_birthdate.setVisibility(View.GONE);
			    	partner_birthtime.setVisibility(View.GONE);
			    	partner_birthplace.setVisibility(View.GONE);
				}
			}
    	});
    	sovm.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1){
					name.setText(getActivity().getString(R.string.order_personal_horoscope_name));
					birthdate.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_date));
					birthtime.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_time));
					birthplace.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_place));
					//set text
					final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					Calendar calSet = Calendar.getInstance();
					Calendar currDate = Calendar.getInstance();
					calSet.setTimeInMillis(0);
					calSet.set(sPref.getInt("yearBorn", 1995), sPref.getInt("monthBorn", 4), sPref.getInt("dayBorn", 10), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
					userbirthdate.setText(dateFormat.format(calSet.getTime()));
					userbirthdate_mask.setVisibility(View.GONE);
					
					//partner
			    	partner_username.setVisibility(View.VISIBLE);
			    	partner_userbirthdate.setVisibility(View.VISIBLE);
			    	partner_userbirthtime.setVisibility(View.VISIBLE);
			    	partner_userbirthplace.setVisibility(View.VISIBLE);
			    	
			    	partner_username_mask.setVisibility(View.VISIBLE);
			    	partner_userbirthdate_mask.setVisibility(View.VISIBLE);
			    	partner_userbirthtime_mask.setVisibility(View.VISIBLE);
			    	partner_userbirthplace_mask.setVisibility(View.VISIBLE);
			    	
			    	partner_name.setVisibility(View.VISIBLE);
			    	partner_birthdate.setVisibility(View.VISIBLE);
			    	partner_birthtime.setVisibility(View.VISIBLE);
			    	partner_birthplace.setVisibility(View.VISIBLE);
			    	
			    	partner_username.setText("");
			    	partner_userbirthdate.setText("");
			    	partner_userbirthtime.setText("");
			    	partner_userbirthplace.setText("");
				}
			}
    	});
    	forecast.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1){
					name.setText(getActivity().getString(R.string.order_personal_horoscope_name));
					birthdate.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_date));
					birthtime.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_time));
					birthplace.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_place));
					//set text
					final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					Calendar calSet = Calendar.getInstance();
					Calendar currDate = Calendar.getInstance();
					calSet.setTimeInMillis(0);
					calSet.set(sPref.getInt("yearBorn", 1995), sPref.getInt("monthBorn", 4), sPref.getInt("dayBorn", 10), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
					userbirthdate.setText(dateFormat.format(calSet.getTime()));
					userbirthdate_mask.setVisibility(View.GONE);
					
					//partner
					partner_username.setText("");
			    	partner_userbirthdate.setText("");
			    	partner_userbirthtime.setText("");
			    	partner_userbirthplace.setText("");
			    	
			    	partner_username.setVisibility(View.GONE);
			    	partner_userbirthdate.setVisibility(View.GONE);
			    	partner_userbirthtime.setVisibility(View.GONE);
			    	partner_userbirthplace.setVisibility(View.GONE);
			    	
			    	partner_username_mask.setVisibility(View.GONE);
			    	partner_userbirthdate_mask.setVisibility(View.GONE);
			    	partner_userbirthtime_mask.setVisibility(View.GONE);
			    	partner_userbirthplace_mask.setVisibility(View.GONE);
			    	
			    	partner_name.setVisibility(View.GONE);
			    	partner_birthdate.setVisibility(View.GONE);
			    	partner_birthtime.setVisibility(View.GONE);
			    	partner_birthplace.setVisibility(View.GONE);
				}
			}
    	});
    	kid.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1){
					name.setText(getActivity().getString(R.string.order_personal_horoscope_name_child));
					birthdate.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_date_child));
					birthtime.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_time_child));
					birthplace.setText(getActivity().getString(R.string.order_personal_horoscope_birthday_place_child));
					//set text
					userbirthdate.setText("");
					userbirthdate_mask.setVisibility(View.VISIBLE);
					
					//partner
					partner_username.setText("");
			    	partner_userbirthdate.setText("");
			    	partner_userbirthtime.setText("");
			    	partner_userbirthplace.setText("");
			    	
			    	partner_username.setVisibility(View.GONE);
			    	partner_userbirthdate.setVisibility(View.GONE);
			    	partner_userbirthtime.setVisibility(View.GONE);
			    	partner_userbirthplace.setVisibility(View.GONE);
			    	
			    	partner_username_mask.setVisibility(View.GONE);
			    	partner_userbirthdate_mask.setVisibility(View.GONE);
			    	partner_userbirthtime_mask.setVisibility(View.GONE);
			    	partner_userbirthplace_mask.setVisibility(View.GONE);
			    	
			    	partner_name.setVisibility(View.GONE);
			    	partner_birthdate.setVisibility(View.GONE);
			    	partner_birthtime.setVisibility(View.GONE);
			    	partner_birthplace.setVisibility(View.GONE);
				}
			}
    	});
    	
    	//on edit text
    	username.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   username_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   username_mask.setVisibility(View.GONE);
	    	   }
	    });
    	userbirthdate.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   userbirthdate_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   userbirthdate_mask.setVisibility(View.GONE);
	    	   }
	    });
    	userbirthtime.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   userbirthtime_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   userbirthtime_mask.setVisibility(View.GONE);
	    	   }
	    });
    	userbirthplace.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   userbirthplace_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   userbirthplace_mask.setVisibility(View.GONE);
	    	   }
	    });
    	useremail.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   useremail_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   useremail_mask.setVisibility(View.GONE);
	    	   }
	    });
    	usertel_skype.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   usertel_skype_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   usertel_skype_mask.setVisibility(View.GONE);
	    	   }
	    });
    	
    	partner_username.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   partner_username_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   partner_username_mask.setVisibility(View.GONE);
	    	   }
	    });
    	partner_userbirthdate.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   partner_userbirthdate_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   partner_userbirthdate_mask.setVisibility(View.GONE);
	    	   }
	    });
    	partner_userbirthtime.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   partner_userbirthtime_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   partner_userbirthtime_mask.setVisibility(View.GONE);
	    	   }
	    });
    	partner_userbirthplace.addTextChangedListener(new TextWatcher() {
	    	   public void afterTextChanged(Editable s) {
	    		   if (s.length()<=0){
	    			   partner_userbirthplace_mask.setVisibility(View.VISIBLE);
	    		   } else {
	    		   }
	    	   }
	    	   public void beforeTextChanged(CharSequence s, int start, 
	    	     int count, int after) {
	    	   }
	    	   public void onTextChanged(CharSequence s, int start, 
	    	     int before, int count) {
	    		   partner_userbirthplace_mask.setVisibility(View.GONE);
	    	   }
	    });
    	
    	//order button
    	order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((username.length() > 2) && (userbirthdate.length() > 2) && (userbirthtime.length() > 2) && (userbirthplace.length() > 2) && (useremail.length() > 2) && (usertel_skype.length() > 2)){
					if (sovm.isChecked()){
						if((partner_username.length() > 2) && (partner_userbirthdate.length() > 2) && (partner_userbirthtime.length() > 2) && (partner_userbirthplace.length() > 2)){
							Log.i("aza", "sendsovm");
							sendOrderToServer();
						} else {
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.order_personal_horoscope_info_3), Toast.LENGTH_LONG).show();
						}
					} else {
						Log.i("aza", "sendnormal");
						sendOrderToServer();
					}
					
				} else {
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.order_personal_horoscope_info_3), Toast.LENGTH_LONG).show();
				}
			}
		});
    	
    	
        //set fonts
    	SFUIDisplayFont.MEDIUM.apply(getActivity(), (TextView)contentView.findViewById(R.id.type));
    	SFUIDisplayFont.LIGHT.apply(getActivity(), natal);
    	SFUIDisplayFont.LIGHT.apply(getActivity(), sovm);
    	SFUIDisplayFont.LIGHT.apply(getActivity(), kid);
    	SFUIDisplayFont.LIGHT.apply(getActivity(), forecast);
    	
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), (TextView)contentView.findViewById(R.id.info));
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), (TextView)contentView.findViewById(R.id.info_order));
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), name);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), username_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), username);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), birthdate);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(),  userbirthdate_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), userbirthdate);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), birthtime);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), userbirthtime_mask );
  		SFUIDisplayFont.LIGHT.apply(getActivity(), userbirthtime);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), birthplace);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), userbirthplace_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), userbirthplace);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), email);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), useremail_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), useremail);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), tel_skype);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), usertel_skype_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), usertel_skype);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), partner_name);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), partner_username_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), partner_username);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), partner_birthdate);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(),  partner_userbirthdate_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), partner_userbirthdate);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), partner_birthtime);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), partner_userbirthtime_mask );
  		SFUIDisplayFont.LIGHT.apply(getActivity(), partner_userbirthtime);
  		
  		SFUIDisplayFont.MEDIUM.apply(getActivity(), partner_birthplace);
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), partner_userbirthplace_mask);
  		SFUIDisplayFont.LIGHT.apply(getActivity(), partner_userbirthplace);
  		
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), order);
    	
        return contentView;
    }
	
	public void sendOrderToServer(){
		
		if (sPref.getLong("order_send_time", -1)==-1){
			sPref.edit().putLong("order_send_time", System.currentTimeMillis()).commit();
		} else {
			Calendar currentTime = Calendar.getInstance();
			currentTime.setTimeInMillis(System.currentTimeMillis());
			
			Calendar orderSendTime = Calendar.getInstance();
			orderSendTime.setTimeInMillis(sPref.getLong("order_send_time", System.currentTimeMillis()));
			
			//add 1 minite to shown notification
			orderSendTime.add(Calendar.MINUTE, +1);
			
			if (currentTime.before(orderSendTime)){
				Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.order_personal_horoscope_info_7), Toast.LENGTH_LONG).show();
				return;
			}
		}
			
		
		
		//show an dialog intermediate 
        prDialog.setIndeterminate(true);
        prDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        prDialog.setMessage(getText(R.string.wait));
        prDialog.setCancelable(false);
        prDialog.setCanceledOnTouchOutside(false);
        try {
        	prDialog.show();
    	} catch (Exception e){
    		e.printStackTrace();
    	}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					Thread.sleep(500);
					
					Document doc = Jsoup.connect("http://brothers-rovers.ru/application_horoscope_order_feature/add_to_database.php" +
							"?name="+URLEncoder.encode(String.valueOf(username.getText()), "UTF-8")+
							"&birth_date=" + URLEncoder.encode(String.valueOf(userbirthdate.getText()), "UTF-8")+
							"&birth_place=" + URLEncoder.encode(String.valueOf(userbirthplace.getText()), "UTF-8")+
							"&birth_time=" + URLEncoder.encode(String.valueOf(userbirthtime.getText()), "UTF-8")+
							"&email=" + URLEncoder.encode(String.valueOf(useremail.getText()), "UTF-8")+
							"&tel_skype=" + URLEncoder.encode(String.valueOf(usertel_skype.getText()), "UTF-8")+
							"&horoscope_type=" + (natal.isChecked() ? getString(R.string.order_personal_horoscope_type_1) : sovm.isChecked() ? getString(R.string.order_personal_horoscope_type_2) : forecast.isChecked() ? getString(R.string.order_personal_horoscope_type_3) : kid.isChecked() ? getString(R.string.order_personal_horoscope_type_4) : "Error in determ") +
							"&partner_name=" + URLEncoder.encode(String.valueOf(partner_username.getText()), "UTF-8")+
							"&partner_birth_date=" + URLEncoder.encode(String.valueOf(partner_userbirthdate.getText()), "UTF-8")+
							"&partner_birth_place=" + URLEncoder.encode(String.valueOf(partner_userbirthplace.getText()), "UTF-8")+
							"&partner_birth_time=" + URLEncoder.encode(String.valueOf(partner_userbirthtime.getText()), "UTF-8")+
							"&status=0").timeout(10000).get();
					
					Thread.sleep(500);
					
					if (doc.text().equals("request_accepted")){
						handler.post(new Runnable(){
							@Override
							public void run() {
								sPref.edit().putLong("order_send_time", System.currentTimeMillis()).commit();
								showDialogSuccess();
							}
						});
					} else {
						handler.post(new Runnable(){
							@Override
							public void run() {
								Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.order_personal_horoscope_info_5), Toast.LENGTH_LONG).show();
							}
						});
					}
				} catch (Exception e) {
					handler.post(new Runnable(){
						@Override
						public void run() {
							Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.order_personal_horoscope_info_5), Toast.LENGTH_LONG).show();
						}
					});
					e.printStackTrace();
				} 
				
				handler.post(new Runnable(){
					@Override
					public void run() {
				        try {
				        	prDialog.dismiss();
				    	} catch (Exception e){
				    		e.printStackTrace();
				    	}
					}
				});
				
			}
		}).start();
	}
	
	public void showDialogSuccess(){
		
 		final Context context = getActivity(); 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	View content = inflater.inflate(R.layout.dialog_content_purchase, null);
    	
    	//set fonts
    	SFUIDisplayFont.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
    	
    	((TextView)content.findViewById(R.id.title)).setText(context.getString(R.string.order_personal_horoscope_info_8));
    	((TextView)content.findViewById(R.id.TextView05)).setText(context.getString(R.string.order_personal_horoscope_info_4));
    	
    	((Button)content.findViewById(R.id.apply)).setText(context.getString(R.string.ok));
    	((Button)content.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
    	
    	((Button)content.findViewById(R.id.cancel)).setVisibility(View.GONE);
    	
    	build.setView(content);
    	alert = build.create();															// show dialog
    	alert.show();
	}

    @Override
    public void onResume() {
        super.onResume();
        //set subtitle for a current fragment with custom font
        setTitle(getResources().getStringArray(R.array.mail_ru_horoscopes)[7]);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_bar_background));
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



