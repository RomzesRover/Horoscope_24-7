package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.slider.SliderMenu;
import org.holoeverywhere.widget.Toast;

import android.os.Bundle;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.CelebrutyYahooComLoaderFragment.CelebrityYahooComMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.CelebrutyYahooComLoaderFragment.CelebrityYahooComTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.CelebrutyYahooComLoaderFragment.CelebrityYahooComTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.CelebrutyYahooComLoaderFragment.CelebrityYahooComWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.CelebrutyYahooComLoaderFragment.CelebrityYahooComYesterdayLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoroskopRuLoaderFragment.GoroskopRuLoveLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoroskopRuLoaderFragment.GoroskopRuMoneyLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoroskopRuLoaderFragment.GoroskopRuPersonalLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoroskopRuLoaderFragment.GoroskopRuTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoroskopRuLoaderFragment.GoroskopRuTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoroskopRuLoaderFragment.GoroskopRuWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComMoneyLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComPersonalLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComYesterdayLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuPersonalLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuYearLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuYesterdayLoaderFragment;

@Addons(AddonSlider.class)
public class ContentShowActivity extends BaseActivity {
	public AddonSlider.AddonSliderA addonSlider() {
	      return addon(AddonSlider.class);
	}
	
	// some data to slider menu
	SliderMenu sliderMenu;
	int pref_id = 0;
	
	//preferences 
    SharedPreferences sPref;
    
    boolean check = false;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));

        //init slider menu
        sliderMenu = addonSlider().obtainDefaultSliderMenu(R.layout.menu);
        addonSlider().setOverlayActionBar(false);
        
        //adding tabs as prooved provider
        switch (Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))){
        case 0:
        	sliderMenu.add(getResources().getString(R.string.mail_ru_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[0], MailRuYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[1], MailRuTodayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[2], MailRuTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[3], MailRuPersonalLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[4], MailRuWeekLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[5], MailRuMonthLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[6], MailRuYearLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            pref_id=7;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) && (sPref.getInt("banner", 0)!=1))
            sliderMenu.setCurrentPage(2);
        	break;
        case 1:
        	sliderMenu.add(getResources().getString(R.string.goroskop_ru_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.goroskop_ru_horoscopes)[0], GoroskopRuTodayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.goroskop_ru_horoscopes)[1], GoroskopRuTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.goroskop_ru_horoscopes)[2], GoroskopRuPersonalLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.goroskop_ru_horoscopes)[3], GoroskopRuLoveLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.goroskop_ru_horoscopes)[4], GoroskopRuWeekLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.goroskop_ru_horoscopes)[5], GoroskopRuMoneyLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            pref_id=6;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) && (sPref.getInt("banner", 0)!=1))
            sliderMenu.setCurrentPage(1);
            break;
        case 2:
        	sliderMenu.add(getResources().getString(R.string.horoscope_com_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[0], HoroscopeComYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[1], HoroscopeComTodayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[2], HoroscopeComTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[3], HoroscopeComPersonalLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[4], HoroscopeComWeekLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[5], HoroscopeComMoneyLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[6], HoroscopeComMonthLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            pref_id=7;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) && (sPref.getInt("banner", 0)!=1))
            sliderMenu.setCurrentPage(2);
        	break;
        case 3:
        	sliderMenu.add(getResources().getString(R.string.celebrity_yahoo_com_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.celebrity_yahoo_com_horoscopes)[0], CelebrityYahooComYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.celebrity_yahoo_com_horoscopes)[1], CelebrityYahooComTodayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.celebrity_yahoo_com_horoscopes)[2], CelebrityYahooComTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.celebrity_yahoo_com_horoscopes)[3], CelebrityYahooComWeekLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.celebrity_yahoo_com_horoscopes)[4], CelebrityYahooComMonthLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            pref_id=5;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) && (sPref.getInt("banner", 0)!=1))
            sliderMenu.setCurrentPage(2);
        	break;
        }
        sliderMenu.add(getResources().getString(R.string.application_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
        sliderMenu.add(getResources().getStringArray(R.array.application_titles)[0], SettingsFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
        sliderMenu.add(getResources().getStringArray(R.array.application_titles)[1], AboutFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
        
        //check if user still not set up data
        if (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13 || sPref.getBoolean("preference_start", false)){
        	//then show settings page
        	sliderMenu.setCurrentPage(pref_id+2);
            if (!sPref.getBoolean("preference_start", false))
        	Toast.makeText(getApplicationContext(), getResources().getString(R.string.pre_use), Toast.LENGTH_LONG).show();
        	Editor ed = sPref.edit();  
    		ed.putBoolean("preference_start", false); 	
    		ed.commit();
    		check=true;
        }
        
        if (sPref.getInt("banner", 0)==1 ){
        	sliderMenu.add(getResources().getStringArray(R.array.application_titles)[2], BannerFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
        	if (!check)
        	sliderMenu.setCurrentPage(pref_id+4);
        }
    }
    
    @Override
    protected void onResume(){
    	super.onResume();
    	//set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
    }
      
}
