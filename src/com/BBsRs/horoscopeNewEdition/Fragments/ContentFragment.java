package com.BBsRs.horoscopeNewEdition.Fragments;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.BBsRs.SFUIFontsEverywhere.CustomTypefaceSpan;
import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.SFUIFontsEverywhere.SFUIFontsPath;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BaseFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;
import com.BBsRs.horoscopeNewEdition.Loader.HoroscopeComLoader;

public class ContentFragment extends BaseFragment{
	
	ArrayList<HoroscopeCollection> horoscopeCollection = null;
	SpannableString[] finalString;
	
	//for retrieve data from activity
    Bundle bundle;
	
	//custom refresh listener where in new thread will load job doing, need to customize for all kind of data
    CustomOnRefreshListener customOnRefreshListener = new CustomOnRefreshListener();
    PullToRefreshLayout mPullToRefreshLayout;
    
    TextView textContent;
    ScrollView scrollView;
    RelativeLayout errorLayout;
    TextView errorMessage;
    Button errorRetryButton;
    
    //handler
    Handler handler = new Handler();
    
    // preferences 
    SharedPreferences sPref; 

    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_content);
		
		//enable menu
    	setHasOptionsMenu(true);
		
		//retrieve bundle
      	bundle = this.getArguments();
      	
      	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
      	
      	//init views
      	textContent = (TextView)contentView.findViewById(R.id.textContent);
      	scrollView = (ScrollView)contentView.findViewById(R.id.scrollview);
      	errorLayout = (RelativeLayout)contentView.findViewById(R.id.errorLayout);
    	errorMessage = (TextView)contentView.findViewById(R.id.errorMessage);
    	errorRetryButton = (Button)contentView.findViewById(R.id.errorRetryButton);
		
		mPullToRefreshLayout = (PullToRefreshLayout) contentView.findViewById(R.id.ptr_layout);
		
		//init pull to refresh
		ActionBarPullToRefresh.from(getActivity())
        .allChildrenArePullable()
        .listener(customOnRefreshListener)
        .setup(mPullToRefreshLayout);
		
		 //if we have saved info after screen rotating or pause/stop app
        if(savedInstanceState != null) {
        	horoscopeCollection = savedInstanceState.getParcelableArrayList(Constants.EXTRA_HOROSCOPE_COLLECTION);
        	if (horoscopeCollection != null){
        		//set up content text view
	        	finalString = new SpannableString[horoscopeCollection.size()];
	        	int index = 0;
	        	for (HoroscopeCollection one : horoscopeCollection){
	        		finalString[index] = new SpannableString(Html.fromHtml(one.title +"<br /><br />"+ one.content+"<br /><br />"));
	        		finalString[index].setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIFontsPath.MEDIUM)), 0, one.title.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	                finalString[index].setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_CENTER), 0, one.title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                finalString[index].setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIFontsPath.LIGHT)), one.title.length()+1, finalString[index].length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	                finalString[index].setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_NORMAL), one.title.length()+1, finalString[index].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	        		index++;
	        	}
	        	textContent.setText(TextUtils.concat(finalString));
	        	
	        	scrollView.setVisibility(View.VISIBLE);
	        	//with fly up animation
	        	Animation flyUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim);
	        	scrollView.startAnimation(flyUpAnimation);
        	}
        }
        
        //programing error button
        errorRetryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				updateList();
		        errorRetryButton.setEnabled(false);
			}
		});
		
        //set fonts
  		SFUIFonts.LIGHT.apply(getActivity(), errorMessage);
  		SFUIFonts.LIGHT.apply(getActivity(), errorRetryButton);
		
		return contentView;
	}
	
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (horoscopeCollection != null){
			 outState.putParcelableArrayList(Constants.EXTRA_HOROSCOPE_COLLECTION, horoscopeCollection);
		}
	}
	
	@Override
    public void onResume() {
        super.onResume();
        setTitle(getResources().getStringArray(R.array.zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+";"+bundle.getString(Constants.BUNDLE_LIST_TITLE_NAME));
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_bar_background));
        
        //register receiver
        try {
        	getActivity().registerReceiver(forceShowUpdateLine, new IntentFilter(Constants.INTENT_FORCE_SHOW_UPDATE_LINE));
            getActivity().registerReceiver(forceHideUpdateLine, new IntentFilter(Constants.INTENT_FORCE_HIDE_UPDATE_LINE));
        } catch (Exception e){
        	e.printStackTrace();
        }
        
        //load curr
        if (sPref.getBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+bundle.getInt(Constants.BUNDLE_LIST_TYPE), false) || horoscopeCollection == null || (System.currentTimeMillis() - sPref.getLong(Constants.PREFERENCES_HOROSCOPE_COLLECTION_TIME, System.currentTimeMillis()) > 7200000)){
        	updateList();
        }
	}
	
	@Override
	public void onPause(){
		//save time
		sPref.edit().putLong(Constants.PREFERENCES_HOROSCOPE_COLLECTION_TIME, System.currentTimeMillis()).commit();
		
		//stop load curr
		if (loadM != null && loadM.getStatus() == AsyncTask.Status.RUNNING){
			mPullToRefreshLayout.setRefreshing(false);
			mPullToRefreshLayout.setRefreshComplete();
			loadM.cancel(true);
		}
		
		//unregister receiver
        try {
        	getActivity().unregisterReceiver(forceShowUpdateLine);
    		getActivity().unregisterReceiver(forceHideUpdateLine);
        } catch (Exception e){
        	e.printStackTrace();
        }
		
		super.onPause();
	}
	
	private BroadcastReceiver forceShowUpdateLine = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			
			boolean active = loadM != null && loadM.getStatus() != AsyncTask.Status.FINISHED;
			
			if (active)
				mPullToRefreshLayout.setRefreshing(true);
		}
	};
	
	
	private BroadcastReceiver forceHideUpdateLine = new BroadcastReceiver(){
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			mPullToRefreshLayout.setRefreshComplete();
		}
	};
	
	public void updateList(){
		sPref.edit().putBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+bundle.getInt(Constants.BUNDLE_LIST_TYPE), false).commit();
		if (loadM == null || loadM.getStatus() != AsyncTask.Status.RUNNING){
			handler.postDelayed(new Runnable(){
				@Override
				public void run() {
					if (loadM == null || loadM.getStatus() != AsyncTask.Status.RUNNING){
						//refresh on open to load data when app first time started
				        mPullToRefreshLayout.setRefreshing(true);
				        customOnRefreshListener.onRefreshStarted(null);
					}
				}
	      	}, 100);
		}
	}
	
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_content_menu, menu);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	      case android.R.id.home:
	    	  Intent i = new Intent(Constants.INTENT_OPEN_DRAWER_MENU);
	    	  getActivity().sendBroadcast(i);
	    	  break;
	      case R.id.menu_share_new:
	    	  if (horoscopeCollection == null)
	    		  break;
			  String shareBody =  String.format(getResources().getString(R.string.share_text_comment), bundle.getString(Constants.BUNDLE_LIST_TITLE_NAME), getResources().getStringArray(R.array.zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]) + 
					  "\n\n" + textContent.getText().toString()+
					  "\n\n" + getResources().getString(R.string.share_comment) + " " + getResources().getString(R.string.share_link_google_play);
			  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			  sharingIntent.setType("text/plain");
			  sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
			  sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			  startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
	    	  break;
    	}
		return true;
    	
    }
	
    AsyncTask<Void, Void, Void> loadM;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
    public class  CustomOnRefreshListener implements OnRefreshListener{
		@Override
		public void onRefreshStarted(View view) {
			loadM = new AsyncTask<Void, Void, Void>() {
				
				HoroscopeComLoader horoscopeComLoader = new HoroscopeComLoader(bundle.getInt(Constants.BUNDLE_LIST_TYPE), sPref, getActivity());
				
			    @Override
			    protected void onCancelled() {
			    	horoscopeComLoader.abortLoad();
			    	super.onCancelled();
			    }
				 
                @Override
                protected Void doInBackground(Void... params) {
                	try {
                		//hide prev state
                		horoscopeCollection = null;
                		if (scrollView.getVisibility() == View.VISIBLE){
							handler.post(new Runnable(){
								@Override
								public void run() {
									Animation flyDownAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim_out);
									scrollView.startAnimation(flyDownAnimation);
			                    	flyDownAnimation.setAnimationListener(new AnimationListener(){
			        					@Override
			        					public void onAnimationEnd(Animation arg0) {
			        						scrollView.setVisibility(View.INVISIBLE);
			        						textContent.setText("");
			        					}
			        					@Override
			        					public void onAnimationRepeat(Animation arg0) { }
			        					@Override
			        					public void onAnimationStart(Animation arg0) { }
			                    	});
								}
							});
                		}
                		
                    	//hide error 
                		if (errorLayout.getVisibility() == View.VISIBLE){
							handler.post(new Runnable(){
								@Override
								public void run() {
			                    	Animation flyDownAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim_out);
			                    	errorLayout.startAnimation(flyDownAnimation);
			                    	flyDownAnimation.setAnimationListener(new AnimationListener(){
			        					@Override
			        					public void onAnimationEnd(Animation arg0) {
			        						errorLayout.setVisibility(View.INVISIBLE);
			        					}
			        					@Override
			        					public void onAnimationRepeat(Animation arg0) { }
			        					@Override
			        					public void onAnimationStart(Animation arg0) { }
			                    	});
								}
							});
                		}
                		
                		if (isCancelled()) return null;
                		
                		//sleep to prevent lags in animations
                		Thread.sleep(250);
                		
                		if (isCancelled()) return null;
                		
                		horoscopeCollection = horoscopeComLoader.loadCurrList();
                		
                		if (isCancelled()) return null;
                		
                    	handler.post(new Runnable(){
							@Override
							public void run() {
								mPullToRefreshLayout.setRefreshComplete();
							}
                    	});
                    	
                    	if (isCancelled()) return null;
                    	
                    	//sleep to prevent lags in animations
                    	Thread.sleep(200);
                    	
					} catch (Exception e) {
						horoscopeCollection = null;
						e.printStackTrace();
					}
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                	if (!isCancelled()){
	                	if (horoscopeCollection == null){
	                		scrollView.setVisibility(View.GONE);
	                    	errorLayout.setVisibility(View.VISIBLE);
	                    	errorRetryButton.setEnabled(true);
	                    	//with fly up animation
	                    	Animation flyUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim);
	                    	errorLayout.startAnimation(flyUpAnimation);
	                		return;
	                	}
	                	//hide error 
	    				errorLayout.setVisibility(View.INVISIBLE);
	                	//set up content text view
	                	finalString = new SpannableString[horoscopeCollection.size()];
	                	int index = 0;
	                	for (HoroscopeCollection one : horoscopeCollection){
	                		finalString[index] = new SpannableString(Html.fromHtml(one.title +"<br /><br />"+ one.content+"<br /><br />"));
	                		finalString[index].setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIFontsPath.MEDIUM)), 0, one.title.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	                        finalString[index].setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_CENTER), 0, one.title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                        finalString[index].setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIFontsPath.LIGHT)), one.title.length()+1, finalString[index].length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	                        finalString[index].setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_NORMAL), one.title.length()+1, finalString[index].length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                		index++;
	                	}
	                	textContent.setText(TextUtils.concat(finalString));
	                	
	                	scrollView.setVisibility(View.VISIBLE);
	                	//with fly up animation
	                	Animation flyUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim);
	                	scrollView.startAnimation(flyUpAnimation);
                	}
                }
            };
			
			//start async
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
	        	loadM.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	        } else {
	        	loadM.execute();
	        }
		}
    }

}
