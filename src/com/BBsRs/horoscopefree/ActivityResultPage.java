package com.BBsRs.horoscopefree;

import java.util.ArrayList;
import java.util.List;

import loader.HoroDeYahooComLoader;
import loader.HoroEnYahooComLoader;
import loader.HoroFrYahooComLoader;
import loader.HoroGoroskopRuLoader;
import loader.HoroMailRuLoader;
import loader.HoroscopeComLoader;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.ProgressBar;
import org.holoeverywhere.widget.Toast;

import adapter.NewPagerAdapter;
import adapter.SlidingMenuAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;
import com.actionbarsherlock.widget.ShareActionProvider.OnShareTargetSelectedListener;
import com.viewpagerindicator.TabPageIndicator;

public class ActivityResultPage extends Activity {
	
	LayoutInflater inflater;
	View page ;
	List<View> views;
	NewPagerAdapter adapter;
	ViewPager pager ;
	TabPageIndicator indicator;

	int zodiacNumber, providerNumber;
	
	Menu mainMenu; // local variable for menu
	
	SharedPreferences sPref; 			// ��� ��� �������� ��
	
	private WebView[] webViews;
	private RelativeLayout[] errorLayout, allNormalLayout;
	private ProgressBar[] progressBar;
	
	HoroscopeComLoader horoscopeComLoader;
	HoroMailRuLoader horoMailRuLoader;
	HoroGoroskopRuLoader horoGoroskopRuLoader;
	HoroFrYahooComLoader horoFrYahooComLoader;
	HoroDeYahooComLoader horoDeYahooComLoader;
	HoroEnYahooComLoader horoEnYahooComLoader;
	
	ShareActionProvider actionProvider;
	
	ListView list;
	SlidingMenuAdapter adapterListMenu;
	int currentSelected=1;
	String[] titles;
	boolean[] checked ;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_result_page);
	    final ActionBar ab = getSupportActionBar();
	    ab.setLogo(getResources().getDrawable(R.drawable.ic_launcher));			//for miui and other
	    
	    sPref = getSharedPreferences("T", 1);
	    zodiacNumber=sPref.getInt("zodiacNumber", 13);		//����� �������
	    providerNumber=sPref.getInt("providerNumber",7);	//����� ����������
	    Log.d("ActivityResultPage", "zdNb:"+String.valueOf(zodiacNumber)+" prNb:"+String.valueOf(providerNumber));
	    if (zodiacNumber>=13 || providerNumber>=7 || zodiacNumber<=0 || providerNumber<0){
	    	Intent aka = new Intent(this, ActivityHoroSettings1.class);
	    	startActivity(aka);
	    	Toast.makeText(getApplicationContext(), getResources().getString(R.string.nosett), Toast.LENGTH_LONG).show();
	    	finish();
	    } else{
	    ab.setTitle(sPref.getString("name", getResources().getString(R.string.noname)));
	    ab.setSubtitle(getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+" - "+getResources().getStringArray(R.array.providers)[providerNumber]);
	    
	    list = (ListView)findViewById(R.id.List);
	    
	    
	    switch(providerNumber){
	    case 0:
	    	//horoscope com
	    	webViews = new WebView[7];
	    	errorLayout = new RelativeLayout[7];
	    	allNormalLayout = new RelativeLayout[7];
	    	progressBar = new ProgressBar[7];
	    			
	    	inflater = LayoutInflater.from(this);
	        views = new ArrayList<View>();
	        
	        for (int i=0; i<7; i++){
	        	 page = inflater.inflate(R.layout.simple_text_tab_element, null);
	 	        webViews[i]=(WebView)page.findViewById(R.id.simpleHoroText);
	 	        errorLayout[i] = (RelativeLayout)page.findViewById(R.id.errorLayout);
	 	        allNormalLayout[i] = (RelativeLayout)page.findViewById(R.id.allNormalLayout);
	 	        progressBar[i] = (ProgressBar)page.findViewById(R.id.progressBar1);
	 	        webViews[i].setBackgroundColor(Color.TRANSPARENT);
	 	        views.add(page);
	        }
	       

	        horoscopeComLoader = new HoroscopeComLoader(getApplicationContext(), webViews, errorLayout, allNormalLayout, progressBar);
	        horoscopeComLoader.load(1);
	        
	        adapter = new NewPagerAdapter(views, getResources().getStringArray(R.array.horoscope_com_horoscopes));
	        pager = (ViewPager)findViewById( R.id.viewpager );
	        indicator = (TabPageIndicator)findViewById( R.id.indicator );
	        pager.setAdapter( adapter );
	        pager.setCurrentItem(1);
	        indicator.setViewPager( pager );
	        indicator.setCurrentItem(1);
	        
	        titles = getResources().getStringArray(R.array.horoscope_com_horoscopes);
	    	checked = new boolean[titles.length];
	    	checked[1]=true;
	    	adapterListMenu = new SlidingMenuAdapter(getApplicationContext(), titles,checked); list.setAdapter(adapterListMenu); list.setAdapter(adapterListMenu);
	    	
	        pager.setOnPageChangeListener(new OnPageChangeListener(){
				@Override
				public void onPageScrollStateChanged(int arg0) {}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageSelected(int arg0) {
					indicator.setCurrentItem(arg0);
					
					adapterListMenu.onSetChecked(arg0, currentSelected);
					currentSelected=arg0;
						 horoscopeComLoader.load(arg0);
				}
	        	
	        });
	        
	        
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					adapterListMenu.onSetChecked(arg2, currentSelected);
					pager.setCurrentItem(arg2);
					currentSelected=arg2;
				}
				
			});
	        break;
	    case 4:
	    	//horo mail ru
	    	webViews = new WebView[7];
	    	errorLayout = new RelativeLayout[7];
	    	allNormalLayout = new RelativeLayout[7];
	    	progressBar = new ProgressBar[7];
	    			
	    	inflater = LayoutInflater.from(this);
	        views = new ArrayList<View>();
	        
	        for (int i=0; i<7; i++){
	        	 page = inflater.inflate(R.layout.simple_text_tab_element, null);
	 	        webViews[i]=(WebView)page.findViewById(R.id.simpleHoroText);
	 	        errorLayout[i] = (RelativeLayout)page.findViewById(R.id.errorLayout);
	 	        allNormalLayout[i] = (RelativeLayout)page.findViewById(R.id.allNormalLayout);
	 	        progressBar[i] = (ProgressBar)page.findViewById(R.id.progressBar1);
	 	        webViews[i].setBackgroundColor(Color.TRANSPARENT);
	 	        views.add(page);
	        }
	       

	        horoMailRuLoader = new HoroMailRuLoader(getApplicationContext(), webViews, errorLayout, allNormalLayout, progressBar);
	        horoMailRuLoader.load(1);
	        
	        adapter = new NewPagerAdapter(views, getResources().getStringArray(R.array.mail_ru_horoscopes));
	        pager = (ViewPager)findViewById( R.id.viewpager );
	        indicator = (TabPageIndicator)findViewById( R.id.indicator );
	        pager.setAdapter( adapter );
	        pager.setCurrentItem(1);
	        indicator.setViewPager( pager );
	        indicator.setCurrentItem(1);
	        
	        titles = getResources().getStringArray(R.array.mail_ru_horoscopes);
	    	checked = new boolean[titles.length];
	    	checked[1]=true;
	    	adapterListMenu = new SlidingMenuAdapter(getApplicationContext(), titles,checked); list.setAdapter(adapterListMenu);
	    	
	        pager.setOnPageChangeListener(new OnPageChangeListener(){
				@Override
				public void onPageScrollStateChanged(int arg0) {}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageSelected(int arg0) {
					indicator.setCurrentItem(arg0);
					
					adapterListMenu.onSetChecked(arg0, currentSelected);
					currentSelected=arg0;
						 horoMailRuLoader.load(arg0);
				}
	        	
	        });
	        
	        
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					adapterListMenu.onSetChecked(arg2, currentSelected);
					pager.setCurrentItem(arg2);
					currentSelected=arg2;
				}
				
			});
	    	break;
	    case 5:
	    	//horo goroskop ru
	    	webViews = new WebView[6];
	    	errorLayout = new RelativeLayout[6];
	    	allNormalLayout = new RelativeLayout[6];
	    	progressBar = new ProgressBar[6];
	    			
	    	inflater = LayoutInflater.from(this);
	        views = new ArrayList<View>();
	        
	        for (int i=0; i<6; i++){
	        	 page = inflater.inflate(R.layout.simple_text_tab_element, null);
	 	        webViews[i]=(WebView)page.findViewById(R.id.simpleHoroText);
	 	        errorLayout[i] = (RelativeLayout)page.findViewById(R.id.errorLayout);
	 	        allNormalLayout[i] = (RelativeLayout)page.findViewById(R.id.allNormalLayout);
	 	        progressBar[i] = (ProgressBar)page.findViewById(R.id.progressBar1);
	 	        webViews[i].setBackgroundColor(Color.TRANSPARENT);
	 	        views.add(page);
	        }
	       

	        horoGoroskopRuLoader = new HoroGoroskopRuLoader(getApplicationContext(), webViews, errorLayout, allNormalLayout, progressBar);
	        horoGoroskopRuLoader.load(0);
	        
	        adapter = new NewPagerAdapter(views, getResources().getStringArray(R.array.goroskop_ru_horoscopes));
	        pager = (ViewPager)findViewById( R.id.viewpager );
	        indicator = (TabPageIndicator)findViewById( R.id.indicator );
	        pager.setAdapter( adapter );
	        pager.setCurrentItem(0);
	        indicator.setViewPager( pager );
	        indicator.setCurrentItem(0);
	        
	        titles = getResources().getStringArray(R.array.goroskop_ru_horoscopes);
	    	checked = new boolean[titles.length];
	    	checked[0]=true;
	    	currentSelected=0;
	    	adapterListMenu = new SlidingMenuAdapter(getApplicationContext(), titles,checked); list.setAdapter(adapterListMenu);
	    	
	        pager.setOnPageChangeListener(new OnPageChangeListener(){
				@Override
				public void onPageScrollStateChanged(int arg0) {}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageSelected(int arg0) {
					indicator.setCurrentItem(arg0);
					
					adapterListMenu.onSetChecked(arg0, currentSelected);
					currentSelected=arg0;
						 horoGoroskopRuLoader.load(arg0);
				}
	        	
	        });
	        
	        
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					adapterListMenu.onSetChecked(arg2, currentSelected);
					pager.setCurrentItem(arg2);
					currentSelected=arg2;
				}
				
			});
	    	break;
	    case 2:
	    	//fr.astrology.yahoo.com
	    	webViews = new WebView[5];
	    	errorLayout = new RelativeLayout[5];
	    	allNormalLayout = new RelativeLayout[5];
	    	progressBar = new ProgressBar[5];
	    			
	    	inflater = LayoutInflater.from(this);
	        views = new ArrayList<View>();
	        
	        for (int i=0; i<5; i++){
	        	 page = inflater.inflate(R.layout.simple_text_tab_element, null);
	 	        webViews[i]=(WebView)page.findViewById(R.id.simpleHoroText);
	 	        errorLayout[i] = (RelativeLayout)page.findViewById(R.id.errorLayout);
	 	        allNormalLayout[i] = (RelativeLayout)page.findViewById(R.id.allNormalLayout);
	 	        progressBar[i] = (ProgressBar)page.findViewById(R.id.progressBar1);
	 	        webViews[i].setBackgroundColor(Color.TRANSPARENT);
	 	        views.add(page);
	        }
	       

	        horoFrYahooComLoader = new HoroFrYahooComLoader(getApplicationContext(), webViews, errorLayout, allNormalLayout, progressBar);
	        horoFrYahooComLoader.load(1);
	        
	        adapter = new NewPagerAdapter(views, getResources().getStringArray(R.array.fr_yahoo_com_horoscopes));
	        pager = (ViewPager)findViewById( R.id.viewpager );
	        indicator = (TabPageIndicator)findViewById( R.id.indicator );
	        pager.setAdapter( adapter );
	        pager.setCurrentItem(1);
	        indicator.setViewPager( pager );
	        indicator.setCurrentItem(1);
	        
	        titles = getResources().getStringArray(R.array.fr_yahoo_com_horoscopes);
	    	checked = new boolean[titles.length];
	    	checked[1]=true;
	    	adapterListMenu = new SlidingMenuAdapter(getApplicationContext(), titles,checked); list.setAdapter(adapterListMenu);
	    	
	        pager.setOnPageChangeListener(new OnPageChangeListener(){
				@Override
				public void onPageScrollStateChanged(int arg0) {}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageSelected(int arg0) {
					indicator.setCurrentItem(arg0);
					
					adapterListMenu.onSetChecked(arg0, currentSelected);
					currentSelected=arg0;
						 horoFrYahooComLoader.load(arg0);
				}
	        	
	        });
	        
	        
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					adapterListMenu.onSetChecked(arg2, currentSelected);
					pager.setCurrentItem(arg2);
					currentSelected=arg2;
				}
				
			});
	        break;
	    case 3:
	    	//de.astrology.yahoo.com
	    	webViews = new WebView[5];
	    	errorLayout = new RelativeLayout[5];
	    	allNormalLayout = new RelativeLayout[5];
	    	progressBar = new ProgressBar[5];
	    			
	    	inflater = LayoutInflater.from(this);
	        views = new ArrayList<View>();
	        
	        for (int i=0; i<5; i++){
	        	 page = inflater.inflate(R.layout.simple_text_tab_element, null);
	 	        webViews[i]=(WebView)page.findViewById(R.id.simpleHoroText);
	 	        errorLayout[i] = (RelativeLayout)page.findViewById(R.id.errorLayout);
	 	        allNormalLayout[i] = (RelativeLayout)page.findViewById(R.id.allNormalLayout);
	 	        progressBar[i] = (ProgressBar)page.findViewById(R.id.progressBar1);
	 	        webViews[i].setBackgroundColor(Color.TRANSPARENT);
	 	        views.add(page);
	        }
	       

	        horoDeYahooComLoader = new HoroDeYahooComLoader(getApplicationContext(), webViews, errorLayout, allNormalLayout, progressBar);
	        horoDeYahooComLoader.load(1);
	        
	        adapter = new NewPagerAdapter(views, getResources().getStringArray(R.array.de_yahoo_com_horoscopes));
	        pager = (ViewPager)findViewById( R.id.viewpager );
	        indicator = (TabPageIndicator)findViewById( R.id.indicator );
	        pager.setAdapter( adapter );
	        pager.setCurrentItem(1);
	        indicator.setViewPager( pager );
	        indicator.setCurrentItem(1);
	        
	        titles = getResources().getStringArray(R.array.de_yahoo_com_horoscopes);
	    	checked = new boolean[titles.length];
	    	checked[1]=true;
	    	adapterListMenu = new SlidingMenuAdapter(getApplicationContext(), titles,checked); list.setAdapter(adapterListMenu);
	    	
	    	pager.setOnPageChangeListener(new OnPageChangeListener(){
				@Override
				public void onPageScrollStateChanged(int arg0) {}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageSelected(int arg0) {
					indicator.setCurrentItem(arg0);
					
					adapterListMenu.onSetChecked(arg0, currentSelected);
					currentSelected=arg0;
					
						 horoDeYahooComLoader.load(arg0);
				}
	        	
	        });
	        
	        
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					adapterListMenu.onSetChecked(arg2, currentSelected);
					pager.setCurrentItem(arg2);
					currentSelected=arg2;
				}
				
			});
	        break;
	    case 1:
	    	//en.astrology.yahoo.com
	    	webViews = new WebView[8];
	    	errorLayout = new RelativeLayout[8];
	    	allNormalLayout = new RelativeLayout[8];
	    	progressBar = new ProgressBar[8];
	    			
	    	inflater = LayoutInflater.from(this);
	        views = new ArrayList<View>();
	        
	        for (int i=0; i<8; i++){
	        	 page = inflater.inflate(R.layout.simple_text_tab_element, null);
	 	        webViews[i]=(WebView)page.findViewById(R.id.simpleHoroText);
	 	        errorLayout[i] = (RelativeLayout)page.findViewById(R.id.errorLayout);
	 	        allNormalLayout[i] = (RelativeLayout)page.findViewById(R.id.allNormalLayout);
	 	        progressBar[i] = (ProgressBar)page.findViewById(R.id.progressBar1);
	 	        webViews[i].setBackgroundColor(Color.TRANSPARENT);
	 	        views.add(page);
	        }
	       

	        horoEnYahooComLoader = new HoroEnYahooComLoader(getApplicationContext(), webViews, errorLayout, allNormalLayout, progressBar);
	        horoEnYahooComLoader.load(1);
	        
	        adapter = new NewPagerAdapter(views, getResources().getStringArray(R.array.en_yahoo_com_horoscopes));
	        pager = (ViewPager)findViewById( R.id.viewpager );
	        indicator = (TabPageIndicator)findViewById( R.id.indicator );
	        pager.setAdapter( adapter );
	        pager.setCurrentItem(1);
	        indicator.setViewPager( pager );
	        indicator.setCurrentItem(1);
	        
	        titles = getResources().getStringArray(R.array.en_yahoo_com_horoscopes);
	    	checked = new boolean[titles.length];
	    	checked[1]=true;
	    	adapterListMenu = new SlidingMenuAdapter(getApplicationContext(), titles,checked); list.setAdapter(adapterListMenu);
	    	
	        pager.setOnPageChangeListener(new OnPageChangeListener(){
				@Override
				public void onPageScrollStateChanged(int arg0) {}
				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {}
				@Override
				public void onPageSelected(int arg0) {
					indicator.setCurrentItem(arg0);
					
					adapterListMenu.onSetChecked(arg0, currentSelected);
					currentSelected=arg0;
						 horoEnYahooComLoader.load(arg0);
				}
	        	
	        });
	        
	        
			list.setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					adapterListMenu.onSetChecked(arg2, currentSelected);
					pager.setCurrentItem(arg2);
					currentSelected=arg2;
				}
				
			});
	        break;
	    }
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
      getSupportMenuInflater().inflate(R.menu.activity_result_page_manu, menu);
      mainMenu=menu;
      MenuItem actionItem = menu.findItem(R.id.menu_share);
      actionProvider = (ShareActionProvider) actionItem.getActionProvider();
      actionProvider.setShareIntent(createShareIntent(""));
      actionProvider.setOnShareTargetSelectedListener(new OnShareTargetSelectedListener(){
		@Override
		 public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
      	  Intent shareIntent = intent;
      	  String toShare="";
      	  switch(providerNumber){
      	  case 0:
      		  toShare=horoscopeComLoader.getHoroscopesForShare()[pager.getCurrentItem()];
      		  break;
      	  case 4:
      		  toShare=horoMailRuLoader.getHoroscopesForShare()[pager.getCurrentItem()];
      		  break;
      	  case 5:
      		  toShare=horoGoroskopRuLoader.getHoroscopesForShare()[pager.getCurrentItem()];
      		  break;
      	  case 2:
    		  toShare=horoFrYahooComLoader.getHoroscopesForShare()[pager.getCurrentItem()];
    		  break;
      	  case 3:
      		  toShare=horoDeYahooComLoader.getHoroscopesForShare()[pager.getCurrentItem()];
      		  break;
      	  case 1:
     		  toShare=horoEnYahooComLoader.getHoroscopesForShare()[pager.getCurrentItem()];
     		  break;
      	  default:
	    	Intent aka = new Intent(ActivityResultPage.this, ActivityHoroSettings1.class);
	    	startActivity(aka);
	    	Toast.makeText(getApplicationContext(), getResources().getString(R.string.nosett), Toast.LENGTH_LONG).show();
	    	finish();
	       	break;
      	  }
      	 
      	  if (toShare!=null)
      		  shareIntent.putExtra(Intent.EXTRA_TEXT, (toShare+
						"\n\n#"+getResources().getStringArray(R.array.providers)[providerNumber]+
    		  			"\n\n"+getResources().getText(R.string.send_from)+" �"+getResources().getText(R.string.app_name)+"�"+"\nPowered by: #BigBrothersRovers"));
			else {
				Toast.makeText(getApplicationContext(), getResources().getString(R.string.null_to_share), Toast.LENGTH_LONG).show();
				return true;	
			}
            startActivity(shareIntent);
            return true;
        }});
      return true;
  }
	
	public boolean  onKeyUp(int keyCode, KeyEvent event) {
		if(event.getAction() == KeyEvent.ACTION_UP){
		    switch(keyCode) {
		    case KeyEvent.KEYCODE_MENU:

		        mainMenu.performIdentifierAction(R.id.submenu, 0);

		        return true;  
		    }
		}
		return super.onKeyUp(keyCode, event);
		}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		sPref = getSharedPreferences("T", 1);
		Editor ed=sPref.edit();
	      switch (item.getItemId()) {
	      case R.id.update:
	    	  switch(providerNumber){
	      	  case 0:
	      		  horoscopeComLoader.update(pager.getCurrentItem());
	      		  break;
	      	  case 4:
	      		  horoMailRuLoader.update(pager.getCurrentItem());
	      		  break;
	      	  case 5:
	      		  horoGoroskopRuLoader.update(pager.getCurrentItem());
	      		  break;
	      	  case 2:
	    		  horoFrYahooComLoader.update(pager.getCurrentItem());
	    		  break;
	      	  case 3:
	      		  horoDeYahooComLoader.update(pager.getCurrentItem());
	      		  break;
	      	  case 1:
	     		  horoEnYahooComLoader.update(pager.getCurrentItem());
	     		  break;
	      	  }
	    	  break;
	      case R.id.settings:
	    	  ed.putBoolean("canBack", true);
	    	  ed.commit();
	    	  Intent step12 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
				startActivity(step12);
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
	    	  break;
	      case R.id.changeProviderQ:
	    	  ed.putBoolean("canBack", true);
	    	  ed.commit();
	    	  Intent step122 = new Intent(getApplicationContext(), ActivityHoroSettings3.class);
				startActivity(step122);
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
				finish();
	    	  break;
	      case R.id.about:
			    ed.putString("help or info", "help");
			    ed.putBoolean("canBack", true);
			    ed.commit();
		        Intent step1 = new Intent(getApplicationContext(), ActivityLoaderVersion.class);
				startActivity(step1);
				overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	    	  break;
	      }
		return true;
	}
	
	private Intent createShareIntent(String text) {
	      Intent shareIntent = new Intent(Intent.ACTION_SEND);
	      shareIntent.setType("text/plain");
	      shareIntent.putExtra(Intent.EXTRA_TEXT, text);
	      return shareIntent;
	}	
	/*
	public void onBackPressed(){
		sPref = getSharedPreferences("T", 1);
        if (sPref.getBoolean("itsFake", true)){
    		final Context context = ActivityResultPage.this; // ������� ��������
    		AlertDialog.Builder build = new AlertDialog.Builder(context); // ������� ������ 
    		build.setTitle(getResources().getStringArray(R.array.rate_me)[0]); // ��������� �������
    		build.setMessage(getResources().getStringArray(R.array.rate_me)[1]); // ���������  
    		build.setPositiveButton(getResources().getStringArray(R.array.rate_me)[2], new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int which) {
    				Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.BBsRs.horoscopefree"));
    				marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
    				startActivity(marketIntent);
    				Toast.makeText(context, getResources().getStringArray(R.array.rate_me)[4], Toast.LENGTH_LONG).show();
    				Editor ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
    			    ed.putBoolean("itsFake", false);						//����, �� ������
    		        ed.commit();
    			}
    		});
    		build.setNegativeButton(getResources().getStringArray(R.array.rate_me)[3], new DialogInterface.OnClickListener() {	
    			public void onClick(DialogInterface dialog, int which) {
    				finish();
    			}
    		});
            
    	
    		build.show(); // ������� �����
    		} else {finish();}
	}*/

}
