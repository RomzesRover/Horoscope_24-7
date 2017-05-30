package com.BBsRs.horoscopeFullNew.Introduce;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.ArrayAdapter;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.RadioButton;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.R;

public class IntroduceNewThemeFirstFragment extends Fragment{
	
	//alert dialog
    AlertDialog alert = null;
    //preferences 
    SharedPreferences sPref;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.introduce_new_theme_first);
		
		//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
		
		//set fonts
		SFUIFonts.ULTRALIGHT.apply(getActivity(), (TextView)contentView.findViewById(R.id.title));
		SFUIFonts.ULTRALIGHT.apply(getActivity(), (TextView)contentView.findViewById(R.id.subTitle));
		SFUIFonts.ULTRALIGHT.apply(getActivity(), (Button)contentView.findViewById(R.id.chooseLanguage));
		
		((Button)contentView.findViewById(R.id.chooseLanguage)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Context context = getActivity(); 								// create context
		 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		    	
		    	LayoutInflater inflater = (LayoutInflater)context.getSystemService
		    		      (Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	//init views
		    	View content = inflater.inflate(R.layout.dialog_content_list, null);
		    	TextView title = (TextView)content.findViewById(R.id.title);
		    	Button cancel = (Button)content.findViewById(R.id.cancel);
		    	Button apply = (Button)content.findViewById(R.id.apply);
		    	ImageView icon = (ImageView)content.findViewById(R.id.icon);
		    	final ListView list = (ListView)content.findViewById(R.id.listView1);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, title);
		    	SFUIFonts.LIGHT.apply(context, cancel);
		    	SFUIFonts.LIGHT.apply(context, apply);
		    	
		    	//view job
		    	title.setText(context.getString(R.string.introduce_two_1));
		    	icon.setImageResource(R.drawable.ic_icon_settings_language);
		    	
		    	list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		    	// custom adapter
		    	final String [] listArray = context.getResources().getStringArray(R.array.locales);
		        list.setAdapter(new ArrayAdapter<String>(context, R.layout.ic_simple_single_choice, listArray){
		            @Override
		            public View getView(final int position, View convertView, ViewGroup parent) {
		            	 View v = super.getView(position, convertView, parent);
		            	 //set font
		            	 SFUIFonts.LIGHT.apply(context, ((TextView)v.findViewById(android.R.id.text1)));
		            	 //set radio
		                 RadioButton radio = (RadioButton) v.findViewById(R.id.radioButton1);
		                 if (list.isItemChecked(position)) {
		                	 radio.setChecked(true);
		                 } else {
		                	 radio.setChecked(false);
		                 }
		                 
		                 View.OnClickListener clickItem = new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								sPref.edit().putString("preference_locales", context.getResources().getStringArray(R.array.locales_entryValues)[position]).commit();
								alert.dismiss();
								getActivity().startActivity(new Intent(getActivity(), IntroduceActivityNewTheme.class));
								getActivity().finish();
							}
						};
		                 
		                 v.setOnClickListener(clickItem);
		                 radio.setOnClickListener(clickItem);
		                 return v;
		            }
		        });
		        
				int index=0;
				for (String summaryValue : getActivity().getResources().getStringArray(R.array.locales_entryValues)){
					if (summaryValue.equals(sPref.getString("preference_locales", getString(R.string.default_locale)))){
						list.setItemChecked(index, true);
						break;
					}
					index++;
				}
		    	
		    	apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				});
		    	
		    	cancel.setVisibility(View.GONE);
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
			}
		});
		
		return contentView;
	}
}
