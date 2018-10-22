package com.BBsRs.horoscopeNewEdition;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.slider.SliderMenu;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Fragments.AboutFragment;
import com.BBsRs.horoscopeNewEdition.Fragments.ContentFragment;
import com.BBsRs.horoscopeNewEdition.Fragments.SettingsFragment;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;


@Addons(AddonSlider.class)
public class ContentActivity extends BaseActivity {
	public AddonSlider.AddonSliderA addonSlider() {
		return addon(AddonSlider.class);
	}
	
	private final Handler handler = new Handler();
	
	//!--------------------------------------------------------BILLING-----------------------------------------------------!
//	private static final String PRODUCT_ID = "android.test.purchased";
//	private static final String LICENSE_KEY = null;
    private static final String PRODUCT_ID = "ad_disabler";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmn5/MyJmRJkvKCLyD4BUvpOrK2Yv6Sk9GNQjiv7VvKPNnzSwrWERbfmQjgbCfxgqkuyOP5lailx769HfGDJWmPcHqknvcZGX7C369rGbMQubAfIg146f8mKjLY63YabY9Gx6O+8mScHLvsJCVzTcGVttKDReChA7/X5UxbIljZ/HZGd57nUUSp5xWuaw+Vh1cA49x5tftx7gbBkWKKWMb34sWAqdtd7kSulj/a8l9Kd1mm3AH6zvcarrxbs6+wnf602lWJNlTP9YeMxDFeUQTbSWM62PVkDpapiK6EH3HbvbMCCxeUWolMPkqTHLtBEzP/Y7CLExZ7kuEfYoI4pTWQIDAQAB"; // PUT YOUR MERCHANT KEY HERE;
    

																																																																																																															// YOUR
	private BillingProcessor bp;
	private boolean readyToPurchase = false;
	//!--------------------------------------------------------BILLING-----------------------------------------------------!
	
	// preferences 
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        
        switch (sPref.getInt(Constants.PREFERENCES_BACKGROUND_TEXT_COLOR, 0)){
		 case 1:
			 setTheme(R.style.ContentThemeDarkFill);
			 break;
		 case 2:
			 setTheme(R.style.ContentThemeFoggyForest);
			 break;
		 case 3:
			 setTheme(R.style.ContentThemeLightFill);
			 break;
		 case 0: default:
			 setTheme(R.style.ContentTheme);
			 break;
        }
    	
        super.onCreate(savedInstanceState);
        
        //set app lang
        setLocale(sPref);
        
        getSupportActionBar().setIcon(R.drawable.ic_menu);
        
        //init slider menu
		final SliderMenu sliderMenu = addonSlider().obtainDefaultSliderMenu(R.layout.menu);
		sliderMenu.setInverseTextColorWhenSelected(false);
        addonSlider().setOverlayActionBar(true);
        try {
        	addonSlider().setDrawerListener(new DrawerListener(){
        		@Override
        		public void onDrawerClosed(View arg0) {
        			Intent i = new Intent(Constants.INTENT_FORCE_SHOW_UPDATE_LINE);
        			sendBroadcast(i);
        		}
        		@Override
        		public void onDrawerOpened(View arg0) {
        			Intent i3 = new Intent(Constants.INTENT_FORCE_HIDE_UPDATE_LINE);
        			sendBroadcast(i3);
        		}
        		@Override
        		public void onDrawerSlide(View arg0, float arg1) { }
        		@Override
        		public void onDrawerStateChanged(int arg0) {}
        	});
        } catch (Exception e){
        	e.printStackTrace();
        	//Error on tablets !!
        }
        
        //init common
        Bundle settings  = new Bundle();
        settings.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getString(R.string.settings));
        Bundle about  = new Bundle();
        about.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getString(R.string.about));
        sliderMenu.add(getResources().getString(R.string.app_name_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
        Bundle yesterday  = new Bundle();
        yesterday.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_YESTERDAY);
        Bundle today  = new Bundle();
        today.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_TODAY);
        Bundle tomorrow  = new Bundle();
        tomorrow.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_TOMORROW);
        Bundle weekly  = new Bundle();
        weekly.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_WEEKLY);
        Bundle monthly  = new Bundle();
        monthly.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_MONTHLY);
        Bundle yearly  = new Bundle();
        yearly.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_YEARLY);
        Bundle yearlyChina  = new Bundle();
        yearlyChina.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_YEARLY_CHINA);
        Bundle compatibility  = new Bundle();
        compatibility.putInt(Constants.BUNDLE_LIST_TYPE, Constants.BUNDLE_LIST_TYPE_COMPATIBILITY);
        
        int prefId=0;
        int startId=2;
	    switch(sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, getResources().getInteger(R.integer.default_language))){
	    case 3:
	        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[0]);
	        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[1]);
	        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[2]);
	        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[3]);
	        yearly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]);
	        yearlyChina.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[5]);
	        compatibility.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscopo_com_horoscopes)[6]);
	        
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[0], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[1], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[2], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[3], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4], ContentFragment.class, yearly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[5], ContentFragment.class, yearlyChina, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[6], ContentFragment.class, compatibility, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_compatibility).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			prefId=8;
			startId=1;
	    	break;
	    case 2:
	        yesterday.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.go_astro_de_horoscopes)[0]);
	        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.go_astro_de_horoscopes)[1]);
	        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.go_astro_de_horoscopes)[2]);
	        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.go_astro_de_horoscopes)[3]);
	        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.go_astro_de_horoscopes)[4]);
			
			sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[0], ContentFragment.class, yesterday, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[1], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[2], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[3], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[4], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			prefId=6;
			startId=2;
	    	break;
	    case 1:
	        yesterday.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[0]);
	        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[1]);
	        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[2]);
	        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[3]);
	        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[4]);
	        yearly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[5]);
	        compatibility.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[6]);
			
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[0], ContentFragment.class, yesterday, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[1], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[2], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[3], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[4], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[5], ContentFragment.class, yearly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[6], ContentFragment.class, compatibility, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_compatibility).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			prefId=8;
			startId=2;
	    	break;
	    case 0: default:
	        yesterday.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[0]);
	        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[1]);
	        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[2]);
	        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[3]);
	        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[4]);
	        yearly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[5]);
	        yearlyChina.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[6]);
	        compatibility.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[7]);
			
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[0], ContentFragment.class, yesterday, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[1], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[2], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[3], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[4], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[5], ContentFragment.class, yearly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[6], ContentFragment.class, yearlyChina, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[7], ContentFragment.class, compatibility, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_compatibility).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			prefId=9;
			startId=2;
	    	break;
	    }
        

		//init common
		sliderMenu.add(getResources().getString(R.string.settings), SettingsFragment.class, settings, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_settings).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.application_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.about), AboutFragment.class, about, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_about).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		
		//set curent page to mainFragment only if fisrt launch Activity
		if ((savedInstanceState == null))
			if (sPref.getBoolean(Constants.PREFERENCES_OPEN_SETTINGS_FIRST, false)){
				Editor ed = sPref.edit();  
				ed.putBoolean(Constants.PREFERENCES_OPEN_SETTINGS_FIRST, false); 	
				ed.commit();
				sliderMenu.setCurrentPage(prefId);
			} else 
				sliderMenu.setCurrentPage(startId);
		
		//show start toast!
		if (sPref.getBoolean(Constants.PREFERENCES_IS_IT_FIRST_LAUNCH, true)){
			sPref.edit().putBoolean(Constants.PREFERENCES_IS_IT_FIRST_LAUNCH, false).commit();
			Toast.makeText(this, getResources().getString(R.string.first_launch_message), Toast.LENGTH_LONG).show();
		}
		
		initBilling();
    	handler.postDelayed(showDisableADDialogHandler, 500);
    }
    
	Runnable showDisableADDialogHandler = new Runnable(){
		@Override
		public void run() {
			showDisableADDialog();
		}
	};
    
    AlertDialog alert = null;
    public void showDisableADDialog(){
    	if (!sPref.getBoolean(Constants.PREFERENCES_SHOW_ADVERTISEMENT_DIALOG_GLOBAL, true)){
			return;
    	}
    	int count = sPref.getInt(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT_COUNT, 0);
    	if (count == 0 || count % 5 != 0 || !sPref.getBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, true)){
			return;
		}
    	sPref.edit().putInt(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT_COUNT, 0).commit();
    	
    	if (!sPref.getBoolean(Constants.PREFERENCES_SHOW_ADVERTISEMENT_DIALOG, true)){
			sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_ADVERTISEMENT_DIALOG, true).commit();
			return;
    	}
    	sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_ADVERTISEMENT_DIALOG, false).commit();
    	
    	final Context context = this; 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	//init views
    	View content = inflater.inflate(R.layout.dialog_content_promote, null);
    	Button cancel = (Button)content.findViewById(R.id.cancel);
    	Button apply = (Button)content.findViewById(R.id.apply);
    	
    	//set fonts
    	SFUIFonts.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIFonts.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView051));
    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView041));
    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView0051));
    	SFUIFonts.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView0041));
    	
    	//view job
    	final RelativeLayout makeBuyNoAd = (RelativeLayout)content.findViewById(R.id.make_buy_no_ad);
    	makeBuyNoAd.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				context.sendBroadcast(new Intent(Constants.INTENT_NAME_SHOW_BUY_DIALOG));
				alert.dismiss();
			}
		});
    	final RelativeLayout googlePlay = (RelativeLayout)content.findViewById(R.id.make_google_review);
    	googlePlay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try { 
					Intent intent = new Intent(Intent.ACTION_VIEW);
					intent.setData(Uri.parse("market://details?id="+context.getPackageName()));
					startActivity(intent);
				} catch(Exception e) {}   
				alert.dismiss();
			}
		});
    	
    	if (sPref.getBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, true)){
    		((TextView)content.findViewById(R.id.TextView041)).setText(getString(R.string.advertisement_interstitials_summary));
    	} else {
    		((TextView)content.findViewById(R.id.TextView041)).setTextColor(context.getResources().getColor(R.color.dialog_summary_text_color_disabled));
    		((TextView)content.findViewById(R.id.TextView041)).setText(getString(R.string.advertisement_interstitials_disabled_summary));
    		((TextView)content.findViewById(R.id.TextView051)).setTextColor(context.getResources().getColor(R.color.dialog_summary_text_color));
    		makeBuyNoAd.setClickable(false);
    	}
    	
    	apply.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
    	
    	cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_ADVERTISEMENT_DIALOG_GLOBAL, false).commit();
				alert.dismiss();
			}
		});
    	
    	build.setView(content);
    	alert = build.create();															// show dialog
    	alert.show();
    }
    
	public void initBilling(){
		bp = new BillingProcessor(ContentActivity.this, LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) { 
            	sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, false).commit();
            	handler.postDelayed(new Runnable(){
					@Override
					public void run() {
						sendBroadcast(new Intent(Constants.INTENT_NAME_HIDE_ANY_DIALOG));
					}
            	}, 500);
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) { }
            @Override
            public void onBillingInitialized() {
            	readyToPurchase = true;
            	if (bp.isPurchased(PRODUCT_ID)){
            		sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, false).commit();
            	} else {
            		sPref.edit().putBoolean(Constants.PREFERENCES_SHOW_INTERSTITIAL_ADVERTISEMENT, true).commit();
            	}
            }
            @Override
            public void onPurchaseHistoryRestored() { }
		});
	}
	
	@Override
	public void onDestroy() {
		if (bp != null)
			bp.release();
		super.onDestroy();
	}
    
    @Override
	public void onResume(){
    	super.onResume();
    	
        //register receiver
        try {
	        super.registerReceiver(openMenuDrawer, new IntentFilter(Constants.INTENT_OPEN_DRAWER_MENU));
	        super.registerReceiver(showBuyDialog, new IntentFilter(Constants.INTENT_NAME_SHOW_BUY_DIALOG));
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    @Override
	public void onPause(){
    	//stopp all delayed stuff
    	handler.removeCallbacks(showDisableADDialogHandler);
    	
		//unregister receiver
        try {
            super.unregisterReceiver(openMenuDrawer);
            super.unregisterReceiver(showBuyDialog);
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        super.onPause();
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!bp.handleActivityResult(requestCode, resultCode, data))
			super.onActivityResult(requestCode, resultCode, data);
	}
	
	private BroadcastReceiver showBuyDialog = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (!readyToPurchase) {
	            Toast.makeText(getApplication(), "Billing not initialized.", Toast.LENGTH_LONG).show();
	        } else{
	        	bp.purchase(ContentActivity.this, PRODUCT_ID);
	        }
	    }
	};
    
	private BroadcastReceiver openMenuDrawer = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (addonSlider() == null)
	    		return;
	    	if (addonSlider().isDrawerOpen(addonSlider().getLeftView()))
	    		  addonSlider().closeLeftView();
	    	  else 
	    		  addonSlider().openLeftView();
	    }
	};
}
