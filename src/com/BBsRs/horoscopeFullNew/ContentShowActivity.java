package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.slider.SliderMenu;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComYearLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComYesterdayLoaderFragment;
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
import com.BBsRs.horoscopeNewEdition.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

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
        
        //fix for providers
        if ((sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("en")) && ((Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))>3) || (Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))<2)))
        	sPref.edit().putString("preference_provider", getResources().getString(R.string.default_provider)).commit();
        
        if ((sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("ru")) && ((Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))>1) || (Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))<0)))
        	sPref.edit().putString("preference_provider", getResources().getString(R.string.default_provider)).commit();
        
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
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
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
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
        	break;
        case 4:
        	sliderMenu.add(getResources().getString(R.string.de_horoskop_yahoo_com_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.de_horoskop_yahoo_com_horoscopes)[0], DeHoroscopeYahooComYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.de_horoskop_yahoo_com_horoscopes)[1], DeHoroscopeYahooComTodayLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.de_horoskop_yahoo_com_horoscopes)[2], DeHoroscopeYahooComWeekLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.de_horoskop_yahoo_com_horoscopes)[3], DeHoroscopeYahooComMonthLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            sliderMenu.add(getResources().getStringArray(R.array.de_horoskop_yahoo_com_horoscopes)[4], DeHoroscopeYahooComYearLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            pref_id=5;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
        	break;
        }
        sliderMenu.add(getResources().getString(R.string.application_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
        sliderMenu.add(getResources().getStringArray(R.array.application_titles)[0], SettingsFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
        
        //check if user still not set up data
        if ((savedInstanceState == null) && (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13 || sPref.getBoolean("preference_start", false))){
        	//then show settings page
        	sliderMenu.setCurrentPage(pref_id+2);
            if (!sPref.getBoolean("preference_start", false))
        	Toast.makeText(getApplicationContext(), getResources().getString(R.string.pre_use), Toast.LENGTH_LONG).show();
        	Editor ed = sPref.edit();  
    		ed.putBoolean("preference_start", false); 	
    		ed.commit();
    		check=true;
        }
    }
    
	//!----------------------------------AD-----------------------------------------------------!
	/** StartAppAd object declaration */
	private InterstitialAd interstitial;
	private final Handler handler = new Handler();
	
	public void showIntersttial(){
		if (interstitial !=null && interstitial.isLoaded()) {
			interstitial.show();
		}
	}
	//!----------------------------------AD-----------------------------------------------------!
    
	public void showAd(){
		//!----------------------------------AD-----------------------------------------------------!
		new Thread (new Runnable(){
			@Override
			public void run() {
				try {
					
					String AdSource = Jsoup.connect("http://brothers-rovers.3dn.ru/HoroscopeNewEd/adsource_between.txt").timeout(10000).get().text();
					
					if (AdSource.equals(null) || AdSource.length()>50 || AdSource.length()<10){
						Log.i("AD", "Problems with load AD !");
						Log.i("AD", "herec1");
					} else {
						final String AdSourceFinalled = AdSource;
						handler.post(new Runnable(){
							@Override
							public void run() {
								try {
									// Создание межстраничного объявления.
								    interstitial = new InterstitialAd(ContentShowActivity.this);
								    interstitial.setAdUnitId(AdSourceFinalled);
	
								    // Создание запроса объявления.
								    AdRequest adRequest = new AdRequest.Builder().build();
	
								    // Запуск загрузки межстраничного объявления.
								    interstitial.loadAd(adRequest);
								} catch (Exception e){
									Log.i("AD", "Problems with load AD !");
									Log.i("AD", "herec2");
								}
							}
						});
					}
				} catch (Exception e){
					Log.i("AD", "Problems with load AD !");
					Log.i("AD", "herec3");
				}
			}
		}).start();
		//!----------------------------------AD-----------------------------------------------------!
	}
	
	@Override
	public void onBackPressed(){
		showIntersttial();
		super.onBackPressed();
	}
    
    @Override
    protected void onResume(){
    	super.onResume();
    	//set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        //set icon
        TypedArray images = getResources().obtainTypedArray(R.array.zodiac_signs_imgs_whoa);
        getSupportActionBar().setIcon(images.getResourceId(Integer.parseInt(sPref.getString("preference_zodiac_sign", "1")), 1));
        images.recycle();
        //show AD
        showAd();
    }
      
}
