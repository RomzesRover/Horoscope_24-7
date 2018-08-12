package com.BBsRs.horoscopeNewEdition.Fragments;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.drawable.ColorDrawable;
import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceClickListener;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.CheckBox;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BasePreferenceFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;

public class AboutFragment extends BasePreferenceFragment {
	
	//alert dialog
    AlertDialog alert = null;

    // preferences 
    SharedPreferences sPref; 
	
	//for retrieve data from activity
    Bundle bundle;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //retrieve bundle
      	bundle = this.getArguments();
      	
      	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
      	
        addPreferencesFromResource(R.xml.about);
        
        //programming about buttons
        Preference googlePlay = (Preference) findPreference ("preference_google_play");
        googlePlay.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				try { 
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://details?id="+getActivity().getPackageName()));
					startActivity(intent);
				} catch(Exception e) {}   
				return false;
			}
        });
        Preference facebook = (Preference) findPreference ("preference_facebook");
        facebook.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				try { 
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebook_link)));
					startActivity(browserIntent);
				} catch(Exception e) {}   
				return false;
			}
        });
        Preference vkontakte = (Preference) findPreference ("preference_vk");
        vkontakte.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				try { 
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.vk_link)));
					startActivity(browserIntent);
				} catch(Exception e) {}   
				return false;
			}
        });
        Preference instagram = (Preference) findPreference ("preference_instagram");
        instagram.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				try { 
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.instagram_link)));
					startActivity(browserIntent);
				} catch(Exception e) {}   
				return false;
			}
        });
        Preference brothersrovers = (Preference) findPreference ("preference_brothersrovers");
        brothersrovers.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
				try { 
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.brothersrovers_link)));
					startActivity(browserIntent);
				} catch(Exception e) {}   
				return false;
			}
        });
        Preference shareApp = (Preference) findPreference ("preference_share");
        shareApp.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
		 		final Context context = getActivity(); 								// create context
		 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		    		
		    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	//init views
		    	View content = inflater.inflate(R.layout.dialog_content_share, null);
		    	Button cancel = (Button)content.findViewById(R.id.cancel);
		    	Button apply = (Button)content.findViewById(R.id.apply);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
		    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
		    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView04));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView051));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView041));
		    	
		    	//view job
		    	final RelativeLayout makeShareGplay = (RelativeLayout)content.findViewById(R.id.make_share_gplay);
		    	makeShareGplay.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try { 
							Intent i = new Intent(Intent.ACTION_SEND);  
							i.setType("text/plain");
							i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
							String sAux = "\n"+getString(R.string.share_comment)+" ";
							sAux = sAux + getString(R.string.share_link_google_play)+"\n";
							i.putExtra(Intent.EXTRA_TEXT, sAux);  
							startActivity(Intent.createChooser(i, "Share with"));
						} catch(Exception e) {}   
						alert.dismiss();
					}
				});
		    	
		    	final RelativeLayout makeShareLanding = (RelativeLayout)content.findViewById(R.id.make_share_landing);
		    	makeShareLanding.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						try { 
							Intent i = new Intent(Intent.ACTION_SEND);  
							i.setType("text/plain");
							i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
							String sAux = "\n"+getString(R.string.share_comment)+"\n\n";
							sAux = sAux + getString(R.string.share_link_landing)+"\n";
							i.putExtra(Intent.EXTRA_TEXT, sAux);  
							startActivity(Intent.createChooser(i, "Share with"));
						} catch(Exception e) {}   
						alert.dismiss();
					}
				});
		    	
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
				return false;
			}
        });
        
        Preference advertisement = (Preference) findPreference ("preference_advertisement");
        advertisement.setOnPreferenceClickListener(new OnPreferenceClickListener(){
			@Override
			public boolean onPreferenceClick(Preference preference) {
		 		final Context context = getActivity(); 								// create context
		 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
		    		
		    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		    	
		    	//init views
		    	View content = inflater.inflate(R.layout.dialog_content_advertisement, null);
		    	Button cancel = (Button)content.findViewById(R.id.cancel);
		    	Button apply = (Button)content.findViewById(R.id.apply);
		    	final TextView intAdsSummary = (TextView)content.findViewById(R.id.TextView04);
		    	
		    	final CheckBox intAds = (CheckBox)content.findViewById(R.id.TextView06);
		    	
		    	//set fonts
		    	SFUIFonts.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
		    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
		    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
		    	SFUIFonts.LIGHT.apply(context, intAdsSummary);
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView051));
		    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView041));
		    	
		    	//set check boxs
		    	intAds.setOnCheckedChangeListener(new OnCheckedChangeListener(){
					@Override
					public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
						intAdsSummary.setText(arg1 ? R.string.advertisement_interstitials_summary : R.string.advertisement_interstitials_disabled_summary);
					}
		    	});
		    	
		    	intAds.setChecked(sPref.getBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, true));
		    	
		    	//view job
		    	final RelativeLayout makeToggleInterstitials = (RelativeLayout)content.findViewById(R.id.make_toggle_intestitials);
		    	makeToggleInterstitials.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						intAds.setChecked(!intAds.isChecked());
					}
				});
		    	
		    	final RelativeLayout makeBuyNoAd = (RelativeLayout)content.findViewById(R.id.make_buy_no_ad);
		    	makeBuyNoAd.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
//						TODO getActivity().sendBroadcast(new Intent(Constants.INTENT_NAME_SHOW_BUY_DIALOG));
					}
				});
		    	
		    	if (sPref.getBoolean(Constants.PREFERENCES_SHOW_BANNER_ADVERTISEMENT, true)){
		    		((TextView)content.findViewById(R.id.TextView041)).setText(getString(R.string.advertisement_banners_summary));
		    	} else {
		    		intAdsSummary.setTextColor(getActivity().getResources().getColor(R.color.dialog_summary_text_color_disabled));
		    		((TextView)content.findViewById(R.id.TextView041)).setTextColor(getActivity().getResources().getColor(R.color.dialog_summary_text_color_disabled));
		    		((TextView)content.findViewById(R.id.TextView041)).setText(getString(R.string.advertisement_banners_disabled_summary));
		    		((TextView)content.findViewById(R.id.TextView051)).setTextColor(getActivity().getResources().getColor(R.color.dialog_summary_text_color));
		    		((TextView)content.findViewById(R.id.TextView05)).setTextColor(getActivity().getResources().getColor(R.color.dialog_summary_text_color));
		    		intAds.setEnabled(false);
		    		makeToggleInterstitials.setClickable(false);
		    		makeBuyNoAd.setClickable(false);
		    		
		    		cancel.setVisibility(View.GONE);
		    		apply.setText(cancel.getText());
		    	}
		    	
		    	apply.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, intAds.isChecked()).commit();
						alert.dismiss();
					}
				});
		    	
		    	cancel.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						alert.dismiss();
					}
				});
		    	
		    	build.setView(content);
		    	alert = build.create();															// show dialog
		    	alert.show();
				return false;
			}
        });



    }
    
    @Override
    public void onResume() {
        super.onResume();
        setTitle(bundle.getString(Constants.BUNDLE_LIST_TITLE_NAME));
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.action_bar_color)));
        //setup preferences listview
        getListView().setBackgroundColor(getResources().getColor(R.color.slider_menu_background));
        getListView().setDivider(getResources().getDrawable(R.color.slider_menu_custom_divider));
        getListView().setSelector(getResources().getDrawable(R.drawable.slider_menu_selector));
    }

}
