package com.BBsRs.horoscopeNewEdition;

import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.slider.SliderMenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.View;

import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Fragments.AboutFragment;
import com.BBsRs.horoscopeNewEdition.Fragments.ContentFragment;
import com.BBsRs.horoscopeNewEdition.Fragments.SettingsFragment;


@Addons(AddonSlider.class)
public class ContentActivity extends BaseActivity {
	public AddonSlider.AddonSliderA addonSlider() {
		return addon(AddonSlider.class);
	}
	
	// preferences 
    SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(this);
        
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
        
	    switch(sPref.getInt(Constants.PREFERENCES_CURRENT_LANGUAGE, 0)){
	    case 1:
	        yesterday.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[0]);
	        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[1]);
	        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[2]);
	        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[3]);
	        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[4]);
	        yearly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.mail_ru_horoscopes)[5]);
			
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[0], ContentFragment.class, yesterday, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[1], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[2], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[3], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[4], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[5], ContentFragment.class, yearly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
	    	break;
	    case 0: default:
	        yesterday.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[0]);
	        today.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[1]);
	        tomorrow.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[2]);
	        weekly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[3]);
	        monthly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[4]);
	        yearly.putString(Constants.BUNDLE_LIST_TITLE_NAME, getResources().getStringArray(R.array.horoscope_com_horoscopes)[5]);
			
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[0], ContentFragment.class, yesterday, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[1], ContentFragment.class, today, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[2], ContentFragment.class, tomorrow, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[3], ContentFragment.class, weekly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[4], ContentFragment.class, monthly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
			sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[5], ContentFragment.class, yearly, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
	    	break;
	    }
        

		//init common
		sliderMenu.add(getResources().getString(R.string.settings), SettingsFragment.class, settings, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_settings).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.application_to_upper_case)).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
		sliderMenu.add(getResources().getString(R.string.about), AboutFragment.class, about, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_about).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
		
		//set curent page to mainFragment only if fisrt launch Activity
		if ((savedInstanceState == null))
			sliderMenu.setCurrentPage(2);
    }
    
    @Override
	public void onResume(){
    	super.onResume();
    	
        //register receiver
        try {
	        super.registerReceiver(openMenuDrawer, new IntentFilter(Constants.INTENT_OPEN_DRAWER_MENU));
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
    @Override
	public void onPause(){
		//unregister receiver
        try {
            super.unregisterReceiver(openMenuDrawer);
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        super.onPause();
    }
    
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
