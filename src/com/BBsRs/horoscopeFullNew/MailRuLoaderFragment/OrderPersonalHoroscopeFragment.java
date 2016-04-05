
package com.BBsRs.horoscopeFullNew.MailRuLoaderFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.EditText;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.horoscopeFullNew.Base.BaseFragment;
import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeNewEdition.R;

public class OrderPersonalHoroscopeFragment extends BaseFragment {
	
	EditText username, userbirthdate, userbirthtime, userbirthplace, useremail;
	TextView name, birthdate, birthtime, birthplace, email;
	TextView username_mask, userbirthdate_mask, userbirthtime_mask, userbirthplace_mask, useremail_mask; 
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
    	
    	username_mask = (TextView)contentView.findViewById(R.id.textMaskUsername);
    	userbirthdate_mask = (TextView)contentView.findViewById(R.id.textMaskUserbirthdate);
    	userbirthtime_mask = (TextView)contentView.findViewById(R.id.textMaskUserbirthtime);
    	userbirthplace_mask = (TextView)contentView.findViewById(R.id.textMaskUserbirthplace);
    	useremail_mask = (TextView)contentView.findViewById(R.id.textMaskUseremail);
    	
    	name = (TextView)contentView.findViewById(R.id.name);
    	birthdate = (TextView)contentView.findViewById(R.id.birthdate);
    	birthtime = (TextView)contentView.findViewById(R.id.birthtime);
    	birthplace = (TextView)contentView.findViewById(R.id.birthplace);
    	email = (TextView)contentView.findViewById(R.id.email);
    	
    	order = (Button)contentView.findViewById(R.id.order);
    	
		//set text
		final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Calendar calSet = Calendar.getInstance();
		Calendar currDate = Calendar.getInstance();
		calSet.setTimeInMillis(0);
		calSet.set(sPref.getInt("yearBorn", 1995), sPref.getInt("monthBorn", 4), sPref.getInt("dayBorn", 10), currDate.get(Calendar.HOUR_OF_DAY), currDate.get(Calendar.MINUTE), currDate.get(Calendar.SECOND));
		userbirthdate.setText(dateFormat.format(calSet.getTime()));
		userbirthdate_mask.setVisibility(View.GONE);
    	
    	
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
    	
    	//order button
    	order.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((username.length() > 2) && (userbirthdate.length() > 2) && (userbirthtime.length() > 2) && (userbirthplace.length() > 2) && (useremail.length() > 2)){
					Log.i("aza", "sendnormal");
					checkServerStatus();
				} else {
					Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.order_personal_horoscope_info_3), Toast.LENGTH_LONG).show();
				}
			}
		});
    	
    	
        //set fonts
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
  		
  		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), order);
    	
        return contentView;
    }
	
	public void checkServerStatus(){
		
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
					
					String status = "";
					try {
						status = Jsoup.connect("http://brothers-rovers.ru/application_horoscope_order_feature/check_status.txt").timeout(10000).get().text();
					} catch (Exception e) {
						status = "";
						e.printStackTrace();
					}
					
					Thread.sleep(500);
					
					if (status.equals("all_is_ok")){
						handler.post(new Runnable(){
							@Override
							public void run() {
								//show buy dialog
								Intent intent = new Intent("request_order_horo");
								intent.putExtra("name", String.valueOf(username.getText()));
								intent.putExtra("datebirth", String.valueOf(userbirthdate.getText()));
								intent.putExtra("timebirth", String.valueOf(userbirthtime.getText()));
								intent.putExtra("email", String.valueOf(useremail.getText()));
								intent.putExtra("placebirth", String.valueOf(userbirthplace.getText()));
								getActivity().sendBroadcast(intent);
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
	
    @Override
    public void onResume() {
        super.onResume();
        //set subtitle for a current fragment with custom font
        setTitle(getResources().getString(R.string.order_personal_horoscope_title));
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



