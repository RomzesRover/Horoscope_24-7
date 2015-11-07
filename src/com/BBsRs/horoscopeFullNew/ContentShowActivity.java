package com.BBsRs.horoscopeFullNew;

import java.util.Calendar;
import java.util.Random;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.addon.AddonSlider;
import org.holoeverywhere.addon.Addons;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.preference.HelvFont;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.slider.SliderMenu;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComMonthLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComTodayLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComWeekLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComYearLoaderFragment;
import com.BBsRs.horoscopeFullNew.De.Horoskop.Yahoo.ComLoaderFragment.DeHoroscopeYahooComYesterdayLoaderFragment;
import com.BBsRs.horoscopeFullNew.Fonts.CustomTypefaceSpan;
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
import com.BBsRs.horoscopeFullNew.MailRuLoaderFragment.MailRuYearTwoLoaderFragment;
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
    boolean firstLaunch = true;
    boolean alreadyShow = false;
    
    //alert dialog
    AlertDialog alert = null;
    
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
            sliderMenu.add(getResources().getStringArray(R.array.mail_ru_horoscopes)[7], MailRuYearTwoLoaderFragment.class, new int[]{R.color.slider_menu_custom_color_black, R.color.slider_menu_custom_color_pink}).setTextAppereanceInverse(1);
            pref_id=8;
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
        
        showDialog();
        
    }
    
	public void showDialog(){
		
		final String shownNotifacation = "SHOWN_NOTIFICATION,HORO";
		final String clickedReview = "CLICKED_REVIEW,HORO";
		
		if (sPref.getBoolean(clickedReview, false))
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
    	HelvFont.HELV_LIGHT.apply(getApplicationContext(), (TextView)content.findViewById(R.id.textTitle));
	    HelvFont.HELV_MEDIUM.apply(getApplicationContext(), (TextView)content.findViewById(R.id.TextView05));
	    HelvFont.HELV_ROMAN.apply(getApplicationContext(), (TextView)content.findViewById(R.id.TextView04));
    	
    	final RelativeLayout makeReview = (RelativeLayout)content.findViewById(R.id.make_review);
    	makeReview.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sPref.edit().putBoolean(clickedReview, true).commit();
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("market://details?id="+getPackageName()));
				startActivity(intent);
				makeReview.setVisibility(View.GONE);
				alert.dismiss();
			}
		});
    	
    	//with font
    	SpannableString sb = new SpannableString(getString(R.string.ok));
        sb.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/HelveticaNeueCyr-Light.otf")), 0, sb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
    	
    	build.setPositiveButton(sb, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				alert.dismiss();
			}
		});
    	build.setView(content);
    	alert = build.create();															// show dialog
    	alert.show();
	}

    
	//!----------------------------------AD-----------------------------------------------------!
	/** StartAppAd object declaration */
	private InterstitialAd interstitial;
	private final Handler handler = new Handler();
	
	public void showIntersttial(){
		if (interstitial !=null && interstitial.isLoaded()) {
			alreadyShow = true;
			interstitial.show();
		}
	}
	//!----------------------------------AD-----------------------------------------------------!
    
	public void showAd(){
		if (alreadyShow)
			return;
		//!----------------------------------AD-----------------------------------------------------!
		new Thread (new Runnable(){
			@Override
			public void run() {
				try {
					
					String AdSource = "ca-app-pub-6690318766939525/2467455298";
					try {
						AdSource = Jsoup.connect("https://raw.githubusercontent.com/RomzesRover/Horoscope_24-7/new_edition/horo_files/adsource_between.txt").timeout(10000).get().text();
					} catch (Exception e) {
						AdSource = "ca-app-pub-6690318766939525/2467455298";
						e.printStackTrace();
					}
					
					if (AdSource.equals(null) || AdSource.length()>50 || AdSource.length()<10){
						Log.i("AD", "Problems with load AD !");
						Log.i("AD", "herec1");
					} else {
						final String AdSourceFinalled = AdSource;
						handler.post(new Runnable(){
							@Override
							public void run() {
								try {
									// �������� �������������� ����������.
								    interstitial = new InterstitialAd(ContentShowActivity.this);
								    interstitial.setAdUnitId(AdSourceFinalled);
	
								    // �������� ������� ����������.
								    AdRequest adRequest = new AdRequest.Builder().build();
	
								    // ������ �������� �������������� ����������.
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
		super.onBackPressed();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		//unregister receiver
        super.unregisterReceiver(fragmentChanged);
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
    	//first launch
    	firstLaunch = true;
    	//set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));
        //set icon
        TypedArray images = getResources().obtainTypedArray(R.array.zodiac_signs_imgs_whoa);
        getSupportActionBar().setIcon(images.getResourceId(Integer.parseInt(sPref.getString("preference_zodiac_sign", "1")), 1));
        images.recycle();
        //show AD
        showAd();
        //register receiver
        super.registerReceiver(fragmentChanged, new IntentFilter("fragment_changed"));
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
      
}
