
package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.preference.Preference;
import org.holoeverywhere.preference.Preference.OnPreferenceClickListener;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.BBsRs.horoscopeFullNew.Base.BasePreferenceFragment;

public class AboutFragment extends BasePreferenceFragment {
	
	//preferences 
    SharedPreferences sPref;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        
        addPreferencesFromResource(R.xml.about);
        
        Preference myPref = (Preference) findPreference("open_vk");
		myPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.contacts_vk_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref2 = (Preference) findPreference("open_gmail");
		myPref2.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.contacts_gmail_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref3 = (Preference) findPreference("open_jsoup");
		myPref3.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.libraries_jsoup_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref4 = (Preference) findPreference("open_holoeverywhere");
		myPref4.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.libraries_holoeverywhere_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref5 = (Preference) findPreference("open_smoothprogressbar");
		myPref5.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.libraries_smoothprogressbar_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref6 = (Preference) findPreference("open_pulltorefresh");
		myPref6.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.libraries_pulltorefresh_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref7 = (Preference) findPreference("open_github");
		myPref7.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.open_source_base_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref8 = (Preference) findPreference("open_billing");
		myPref8.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.libraries_billing_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref9 = (Preference) findPreference("open_mail");
		myPref9.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.providers_mail_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref10 = (Preference) findPreference("open_goroskop");
		myPref10.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.providers_goroskop_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref11 = (Preference) findPreference("open_horoscope");
		myPref11.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.providers_horoscope_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
		
        Preference myPref12 = (Preference) findPreference("open_tarot");
		myPref12.setOnPreferenceClickListener(new OnPreferenceClickListener() {
			public boolean onPreferenceClick(Preference preference) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri
						.parse(getResources().getString(R.string.providers_tarot_url)));
				startActivity(intent);
				// open browser or intent here
				return false;
			}
		});
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle(R.string.about);
    }
}
