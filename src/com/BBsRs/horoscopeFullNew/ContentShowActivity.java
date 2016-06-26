package com.BBsRs.horoscopeFullNew;

import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Random;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.ProgressDialog;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.slider.SliderMenu;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment.GoAstroDeMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment.GoAstroDePersonalLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment.GoAstroDeTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment.GoAstroDeTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment.GoAstroDeWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment.GoAstroDeYesterdayLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComMoneyLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComPersonalLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComYearLoaderFragment;
import com.BBsRs.horoscopeFullNew.HoroscopeComLoaderFragment.HoroscopeComYesterdayLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuPersonalLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuYearTwoLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuYesterdayLoaderFragment;
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.OrderPersonalHoroscopeFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComMonthLoaderFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComPersonalLoaderFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComTodayLoaderFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComTomorrowLoaderFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComWeekLoaderFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComYearLoaderFragment;
import com.BBsRs.horoscopeFullNewHoroscopoComLoaderFragment.HoroscopoComYesterdayLoaderFragment;
import com.BBsRs.horoscopeNewEdition.ActivityRestarter;
import com.BBsRs.horoscopeNewEdition.R;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

@Addons(AddonSlider.class)
public class ContentShowActivity extends BaseActivity implements BillingProcessor.IBillingHandler {
	public AddonSlider.AddonSliderA addonSlider() {
	      return addon(AddonSlider.class);
	}
	
	//!----------------------------------BILLING-----------------------------------------------------!
	private Activity mCurrentActivity = null;
	// PRODUCT & SUBSCRIPTION IDS
//	private static final String PRODUCT_ID_ORDER = "android.test.purchased";
//	private static final String PRODUCT_ID_AD = "android.test.purchased";
//  private static final String LICENSE_KEY = null;
	
	private static final String PRODUCT_ID_ORDER = "order_horo";
    private static final String PRODUCT_ID_AD = "ad_disabler";
    private static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmn5/MyJmRJkvKCLyD4BUvpOrK2Yv6Sk9GNQjiv7VvKPNnzSwrWERbfmQjgbCfxgqkuyOP5lailx769HfGDJWmPcHqknvcZGX7C369rGbMQubAfIg146f8mKjLY63YabY9Gx6O+8mScHLvsJCVzTcGVttKDReChA7/X5UxbIljZ/HZGd57nUUSp5xWuaw+Vh1cA49x5tftx7gbBkWKKWMb34sWAqdtd7kSulj/a8l9Kd1mm3AH6zvcarrxbs6+wnf602lWJNlTP9YeMxDFeUQTbSWM62PVkDpapiK6EH3HbvbMCCxeUWolMPkqTHLtBEzP/Y7CLExZ7kuEfYoI4pTWQIDAQAB"; // PUT YOUR MERCHANT KEY HERE;
    
    ProgressDialog prDialog = null;

	private BillingProcessor bp;
	private boolean readyToPurchase = false;
	//!----------------------------------BILLING-----------------------------------------------------!
	
	// some data to slider menu
	SliderMenu sliderMenu;
	int pref_id = 0;
	
	//preferences 
    SharedPreferences sPref;
    
    boolean check = false;
    boolean firstLaunch = true;
    
    //alert dialog
    AlertDialog alert = null;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        
        //prDialog
        prDialog = new ProgressDialog(this);
        
	    //!----------------------------------BILLING-----------------------------------------------------!
        mCurrentActivity = this;
        bp = new BillingProcessor(this, LICENSE_KEY, this);
	    //!----------------------------------BILLING-----------------------------------------------------!
        
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));

        //init slider menu
        sliderMenu = addonSlider().obtainDefaultSliderMenu(R.layout.menu);
        sliderMenu.setInverseTextColorWhenSelected(false);
        addonSlider().setOverlayActionBar(true);
        
        //fix for providers
        if ((sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("en")) && ((Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))>3) || (Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))<2)))
        	sPref.edit().putString("preference_provider", getResources().getString(R.string.default_provider)).commit();
        
        if ((sPref.getString("preference_locales", getResources().getString(R.string.default_locale)).equals("ru")) && ((Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))>1) || (Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))<0)))
        	sPref.edit().putString("preference_provider", getResources().getString(R.string.default_provider)).commit();
        
        //adding tabs as prooved provider
        switch (Integer.parseInt(sPref.getString("preference_provider", getResources().getString(R.string.default_provider)))){
        case 0:
        	sliderMenu.add(getResources().getString(R.string.mail_ru_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[0], MailRuYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[1], MailRuTodayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[2], MailRuTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[3], MailRuPersonalLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_personal).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[4], MailRuWeekLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[5], MailRuMonthLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[6], MailRuYearTwoLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[7], OrderPersonalHoroscopeFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            pref_id=8;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
            showDialogNewFeature();
            checkOrderStatus();
        	break;
        case 2:
        	sliderMenu.add(getResources().getString(R.string.horoscope_com_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[0], HoroscopeComYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[1], HoroscopeComTodayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[2], HoroscopeComTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[3], HoroscopeComPersonalLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_personal).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[4], HoroscopeComWeekLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[5], HoroscopeComMoneyLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[6], HoroscopeComMonthLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[7], HoroscopeComYearLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscope_com_horoscopes)[8], OrderPersonalHoroscopeFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            pref_id=9;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
            showDialogNewFeature();
            checkOrderStatus();
        	break;
        case 4:
        	sliderMenu.add(getResources().getString(R.string.go_astro_de_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[0], GoAstroDeYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[1], GoAstroDeTodayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[2], GoAstroDeTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[3], GoAstroDeWeekLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[4], GoAstroDeMonthLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.go_astro_de_horoscopes)[5], GoAstroDePersonalLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_personal).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            pref_id=6;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
        	break;
        case 6:
        	sliderMenu.add(getResources().getString(R.string.horoscopo_com_title).toUpperCase()).setCustomLayout(R.layout.custom_slider_menu_item).clickable(false).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[0], HoroscopoComYesterdayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_yesterday).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[1], HoroscopoComTodayLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_today).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[2], HoroscopoComTomorrowLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_tomorrow).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[3], HoroscopoComPersonalLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_personal).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4], HoroscopoComWeekLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_week).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[5], HoroscopoComMonthLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_month).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            sliderMenu.add(getResources().getStringArray(R.array.horoscopo_com_horoscopes)[6], HoroscopoComYearLoaderFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_all_other).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
            pref_id=7;
            if((savedInstanceState == null) && !(Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13) && !sPref.getBoolean("preference_start", false) )
            sliderMenu.setCurrentPage(2);
        	break;
        }
        sliderMenu.add(getResources().getStringArray(R.array.application_titles)[0], SettingsFragment.class, new int[]{R.color.slider_menu_selected_color, R.color.slider_menu_selected_color}).setIcon(R.drawable.ic_icon_settings).setCustomLayout(R.layout.custom_slider_menu_item_selectable).setTextAppereance(1);
        
        //check if user still not set up data
        if ((savedInstanceState == null) && (Integer.parseInt(sPref.getString("preference_zodiac_sign", "13"))==13 || sPref.getBoolean("preference_start", false))){
        	//then show settings page
        	sliderMenu.setCurrentPage(pref_id+1);
            if (!sPref.getBoolean("preference_start", false))
        	Toast.makeText(getApplicationContext(), getResources().getString(R.string.pre_use), Toast.LENGTH_LONG).show();
        	Editor ed = sPref.edit();  
    		ed.putBoolean("preference_start", false); 	
    		ed.commit();
    		check=true;
        }
        
        showDialog();
        //show AD
        showAd();
    }
    
	public void showDialog(){
		
		final String shownNotifacation = "SHOWN_NOTIFICATION,HORO";
		final String clickedReview = "CLICKED_REVIEW,HORO";
		final String clickedCancel = "CLICKED_CANCEL,HORO";
		final String clickedVK = "CLICKED_VK,HORO";
		final String clickedShare = "CLICKED_SHARE,HORO";
		
		if (sPref.getBoolean(clickedReview, false) && sPref.getBoolean(clickedVK, false) && sPref.getBoolean(clickedShare, false))
			return;
		
		if (sPref.getBoolean(clickedCancel, false))
			return;
		
		//calendar job
		if (sPref.getLong(shownNotifacation, -1)==-1){
			sPref.edit().putLong(shownNotifacation, System.currentTimeMillis()).commit();
		}
		
		//init all dates
		Calendar shownNotification = Calendar.getInstance();
		shownNotification.setTimeInMillis(sPref.getLong(shownNotifacation, System.currentTimeMillis()));
				
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeInMillis(System.currentTimeMillis());
				
		//add 3 days to shown notification
		shownNotification.add(Calendar.DATE, +2);
		
		if (currentDate.before(shownNotification))
			return;
		
		sPref.edit().putLong(shownNotifacation, System.currentTimeMillis()).commit();
		
 		final Context context = ContentShowActivity.this; 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	View content = inflater.inflate(R.layout.dialog_content_sponsor, null);
    	
    	//set fonts
    	SFUIDisplayFont.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView04));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView15));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView14));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView25));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView24));
    	
    	
    	final RelativeLayout makeReview = (RelativeLayout)content.findViewById(R.id.make_review);
    	if (sPref.getBoolean(clickedReview, false))
    		makeReview.setVisibility(View.GONE);
    	makeReview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sPref.edit().putBoolean(clickedReview, true).commit();
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id="+getPackageName()));
				startActivity(intent);
				makeReview.setVisibility(View.GONE);
			}
		});
    	
    	final RelativeLayout share = (RelativeLayout)content.findViewById(R.id.make_share);
    	share.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try { 
					sPref.edit().putBoolean(clickedShare, true).commit();
					Intent i = new Intent(Intent.ACTION_SEND);  
					i.setType("text/plain");
					i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
					String sAux = "\n"+getString(R.string.share_text)+"\n\n";
					sAux = sAux + getString(R.string.share_content_url)+" \n\n";
					i.putExtra(Intent.EXTRA_TEXT, sAux);  
					startActivity(Intent.createChooser(i, "Share with"));
				} catch(Exception e) {}   
			}
		});
    	
    	final RelativeLayout vk = (RelativeLayout)content.findViewById(R.id.make_vk);
    	vk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try { 
					sPref.edit().putBoolean(clickedVK, true).commit();
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.dialog_sponsor_vk_link)));
					startActivity(browserIntent);
				} catch(Exception e) {}   
			}
		});
    	
    	((Button)content.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
    	
    	((Button)content.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sPref.edit().putBoolean(clickedCancel, true).commit();
				alert.dismiss();
			}
		});
    	
    	build.setView(content);
    	alert = build.create();															// show dialog
    	alert.show();
	}
	
	public void showDialogNewFeature(){
		
		final String shownNotifacationNewFeatureDate = "SHOWN_NOTIFICATION_NEW_FEATURE_DATE,HORO";
		
		if (sPref.getBoolean("SHOWN_NOTIFICATION_NEW_FEATURE,HORO", false)){
			return;
		}
		
		//calendar job		
		//init all dates
		Calendar shownNotification = Calendar.getInstance();
		shownNotification.setTimeInMillis(sPref.getLong(shownNotifacationNewFeatureDate, System.currentTimeMillis()));
				
		Calendar currentDate = Calendar.getInstance();
		currentDate.setTimeInMillis(System.currentTimeMillis());
				
		//add 9 days to shown notification
		shownNotification.add(Calendar.DATE, +9);
		
		if (currentDate.before(shownNotification) && !(sPref.getLong(shownNotifacationNewFeatureDate, -1)==-1))
			return;
		
		sPref.edit().putLong(shownNotifacationNewFeatureDate, System.currentTimeMillis()).commit();
		
 		final Context context = this; 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	View content = inflater.inflate(R.layout.dialog_content_purchase, null);
    	
    	//set fonts
    	SFUIDisplayFont.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
    	
    	((TextView)content.findViewById(R.id.title)).setText(context.getString(R.string.order_personal_horoscope_info_9));
    	((TextView)content.findViewById(R.id.TextView05)).setText(context.getString(R.string.order_personal_horoscope_info));
    	
    	((Button)content.findViewById(R.id.apply)).setText(context.getString(R.string.roulette));
    	((Button)content.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sliderMenu.setCurrentPage(pref_id);
				alert.dismiss();
			}
		});
    	
    	((Button)content.findViewById(R.id.cancel)).setText(context.getString(R.string.ok));
    	((Button)content.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
    	
    	build.setView(content);
    	alert = build.create();															// show dialog
    	alert.show();
	}
	
	public void showDialogInvite(){
		
 		final Context context = ContentShowActivity.this; 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	View content = inflater.inflate(R.layout.dialog_content_purchase, null);
    	
    	//set fonts
    	SFUIDisplayFont.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
    	
    	((Button)content.findViewById(R.id.apply)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendBroadcast(new Intent("request_disable_ad"));
				alert.dismiss();
			}
		});
    	
    	((Button)content.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alert.dismiss();
			}
		});
    	
    	build.setView(content);
    	alert = build.create();															// show dialog
    	alert.show();
	}
	
	public void checkOrderStatus(){
		
		if (sPref.getString("ordered_id", "-1").equals("-1")){
			return;
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					
					Thread.sleep(500);
					
					Document doc = Jsoup.connect("http://brothers-rovers.ru/application_horoscope_order_feature/order_status_new.php" +
							"?id="+URLEncoder.encode(sPref.getString("ordered_id", "-1"), "UTF-8")).timeout(10000).get();
					
					Thread.sleep(500);
					
					if (doc.text().equals("-1") || doc.text().equals("-2")){
						handler.post(new Runnable(){
							@Override
							public void run() {
								//stop check horo
								sPref.edit().putString("ordered_id", "-1").commit();
								showDialogConfirmPayment();
							}
						});
					}
					
					if (doc.text().equals("4")){
						handler.post(new Runnable(){
							@Override
							public void run() {
								//show dialog that horoscope is available
								showDialogHoroscopeIsAvailable();
							}
						});
					}
				} catch (Exception e) {} 
			}
		}).start();
	}
	
	public void sendOrderToServer(){
		
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
					
					Document doc = Jsoup.connect("http://brothers-rovers.ru/application_horoscope_order_feature/add_to_database_new.php" +
							"?name="+URLEncoder.encode(sPref.getString("name", "Roman"), "UTF-8")+
							"&birth_date=" + URLEncoder.encode(sPref.getString("datebirth", "10.05.1995"), "UTF-8")+
							"&birth_place=" + URLEncoder.encode(sPref.getString("placebirth", "Baymak"), "UTF-8")+
							"&birth_time=" + URLEncoder.encode(sPref.getString("timebirth", "22.10"), "UTF-8")+
							"&email=" + URLEncoder.encode(sPref.getString("email", "admin@brothers-rovers.ru"), "UTF-8")+
							"&tel_skype=" + ""+
							"&horoscope_type=" + URLEncoder.encode(getString(R.string.order_personal_horoscope_type_1), "UTF-8")+
							"&partner_name=" + ""+
							"&partner_birth_date=" + ""+
							"&partner_birth_place=" + ""+
							"&partner_birth_time=" + bp.getPurchaseTransactionDetails(PRODUCT_ID_ORDER).orderId+
							"&status=2").timeout(30000).get();
					
					Thread.sleep(500);
					
					if (doc.text().contains("request_accepted")){
						//save id 
						sPref.edit().putString("ordered_id", doc.text().split("=")[1]).commit();
						handler.post(new Runnable(){
							@Override
							public void run() {
								//consume purchase so user can buy it again
								bp.consumePurchase(PRODUCT_ID_ORDER);
								//you can not send more than one request per minute
								sPref.edit().putLong("order_send_time", System.currentTimeMillis()).commit();
								showDialogSuccess();
							}
						});
					} else {
						handler.post(new Runnable(){
							@Override
							public void run() {
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.order_personal_horoscope_info_5), Toast.LENGTH_LONG).show();
							}
						});
					}
				} catch (Exception e) {
					handler.post(new Runnable(){
						@Override
						public void run() {
							Toast.makeText(getApplicationContext(), getResources().getString(R.string.order_personal_horoscope_info_5), Toast.LENGTH_LONG).show();
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
		
		sPref.edit().putBoolean("SHOWN_NOTIFICATION_NEW_FEATURE,HORO", true).commit();
		
 		final Context context = this; 								// create context
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
	
	public void showDialogConfirmPayment(){
		
 		final Context context = this; 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	View content = inflater.inflate(R.layout.dialog_content_purchase, null);
    	
    	//set fonts
    	SFUIDisplayFont.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
    	
    	((TextView)content.findViewById(R.id.title)).setText(context.getString(R.string.order_personal_horoscope_info_8));
    	((TextView)content.findViewById(R.id.TextView05)).setText(context.getString(R.string.order_personal_horoscope_info_11));
    	
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
	
	public void showDialogHoroscopeIsAvailable(){
		
 		final Context context = this; 								// create context
 		AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
    		
    	LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	
    	View content = inflater.inflate(R.layout.dialog_content_purchase, null);
    	
    	//set fonts
    	SFUIDisplayFont.MEDIUM.apply(context, (TextView)content.findViewById(R.id.title));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.cancel));
    	SFUIDisplayFont.LIGHT.apply(context, (Button)content.findViewById(R.id.apply));
    	SFUIDisplayFont.LIGHT.apply(context, (TextView)content.findViewById(R.id.TextView05));
    	
    	((TextView)content.findViewById(R.id.title)).setText(context.getString(R.string.order_personal_horoscope_info_8));
    	((TextView)content.findViewById(R.id.TextView05)).setText(String.format(context.getString(R.string.order_personal_horoscope_info_10), "http://brothers-rovers.ru/application_horoscope_order_feature/horos/result.php?id="+sPref.getString("ordered_id", "-1")+"&lang="+getString(R.string.order_personal_horoscope_lang)));
    	//stop check horo
    	sPref.edit().putString("ordered_id", "-1").commit();
    	
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

    
	//!----------------------------------AD-----------------------------------------------------!
	private final Handler handler = new Handler();
	
	public void showIntersttial(){
		if (!sPref.getBoolean("isOnHigh", false)){
			showDialogInvite();
			sPref.edit().putBoolean("grantAdShow", false).commit();
			//TODO
		}
	}
	//!----------------------------------AD-----------------------------------------------------!
    
	public void showAd(){
		//if ad already shown exit!
		if (!sPref.getBoolean("grantAdShow", true))
			return;
		//if user on high exit!
		if (sPref.getBoolean("isOnHigh", false))
			return;
		
		//TODO
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//unregister receiver
        try {
        	super.unregisterReceiver(fragmentChanged);
            super.unregisterReceiver(requestDisableAd);
            super.unregisterReceiver(openMenuDrawer);
            super.unregisterReceiver(requestOrderHoro);
        } catch (Exception e){
        	e.printStackTrace();
        }
        //setting up list zodiac change listener preference, cuz we need update horo if zodiac was changed.
		Editor ed = sPref.edit(); 
		ed.putBoolean("changed_0", true);	
		ed.putBoolean("changed_1", true);	
		ed.putBoolean("changed_2", true);	
		ed.putBoolean("changed_3", true);	
		ed.putBoolean("changed_4", true);	
		ed.putBoolean("changed_5", true);	
		ed.putBoolean("changed_6", true);	
		ed.putBoolean("changed_7", true);	
		ed.putBoolean("changed_8", true);	
		ed.commit();
	}
    
    @Override
    protected void onResume(){
    	super.onResume();
        //delete notification is exist
        sendBroadcast(new Intent("MP_DELETE_INTENT"));
    	//first launch
    	firstLaunch = true;
    	//set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        //set icon
        getSupportActionBar().setIcon(R.drawable.ic_menu);
        //register receiver
        try {
	        super.registerReceiver(fragmentChanged, new IntentFilter("fragment_changed"));
	        super.registerReceiver(openMenuDrawer, new IntentFilter("horo_open_menu_drawer"));
	        super.registerReceiver(requestDisableAd, new IntentFilter("request_disable_ad"));
	        super.registerReceiver(requestOrderHoro, new IntentFilter("request_order_horo"));
        } catch (Exception e){
        	e.printStackTrace();
        }
    }
    
	private BroadcastReceiver fragmentChanged = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (firstLaunch){
	    		firstLaunch = false;
	    		return;
	    	}
	    	if (((new Random(System.currentTimeMillis())).nextInt(3) + 1) == 2)
	    		showIntersttial();
	    }
	};
	
	private BroadcastReceiver requestDisableAd = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (!readyToPurchase) {
	            Toast.makeText(getApplication(), "Billing not initialized.", Toast.LENGTH_LONG).show();
	        } else{
	        	bp.purchase(mCurrentActivity, PRODUCT_ID_AD);
	        }
	    }
	};
	
	private BroadcastReceiver requestOrderHoro = new BroadcastReceiver() {
	    @Override
	    public void onReceive(Context context, Intent intent) {
	    	if (!readyToPurchase) {
	            Toast.makeText(getApplication(), "Billing not initialized.", Toast.LENGTH_LONG).show();
	        } else{
	        	bp.purchase(mCurrentActivity, PRODUCT_ID_ORDER);
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
	
	@Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (!bp.handleActivityResult(requestCode, resultCode, data))
			super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onBillingError(int arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBillingInitialized() {
		readyToPurchase = true;
        sPref.edit().putBoolean("isOnHigh", bp.isPurchased(PRODUCT_ID_AD)).commit();
        
    	if (bp.isPurchased(PRODUCT_ID_ORDER)){
    		//send horoscope request to server
    		sendOrderToServer();
    	}
	}

	@Override
	public void onProductPurchased(String productId, TransactionDetails arg1) {
    	if (productId.equals(PRODUCT_ID_AD)){
        	sPref.edit().putBoolean("isOnHigh", true).commit();
        	startActivity(new Intent(getApplicationContext(), ActivityRestarter.class));
        	overridePendingTransition(0, 0);
        	finish();
    	}
    	if (productId.equals(PRODUCT_ID_ORDER)){
    		//send horoscope request to server
    		sendOrderToServer();
    	}
	}

	@Override
	public void onPurchaseHistoryRestored() {
		// TODO Auto-generated method stub
		
	}
      
}
