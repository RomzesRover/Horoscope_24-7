package com.BBsRs.horoscopeNewEdition.Fragments;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.TextView;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.TargetApi;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.BBsRs.SFUIFontsEverywhere.CustomTypefaceSpan;
import com.BBsRs.SFUIFontsEverywhere.SFUIFontsPath;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BaseFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;
import com.BBsRs.horoscopeNewEdition.Loader.HoroscopeComLoader;

public class ContentFragment extends BaseFragment{
	
	ArrayList<HoroscopeCollection> horoscopeCollection = new ArrayList<HoroscopeCollection>();
	SpannableString[] finalString;
	
	//for retrieve data from activity
    Bundle bundle;
	
	//custom refresh listener where in new thread will load job doing, need to customize for all kind of data
    CustomOnRefreshListener customOnRefreshListener = new CustomOnRefreshListener();
    PullToRefreshLayout mPullToRefreshLayout;
    
    TextView textContent;
    ScrollView scrollView;
    
    //handler
    Handler handler = new Handler();
    
    // preferences 
    SharedPreferences sPref; 

    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_content);
		
		//retrieve bundle
      	bundle = this.getArguments();
      	
      	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
      	
      	//init views
      	textContent = (TextView)contentView.findViewById(R.id.textContent);
      	scrollView = (ScrollView)contentView.findViewById(R.id.scrollview);
		
		mPullToRefreshLayout = (PullToRefreshLayout) contentView.findViewById(R.id.ptr_layout);
		
		//init pull to refresh
		ActionBarPullToRefresh.from(getActivity())
        .allChildrenArePullable()
        .listener(customOnRefreshListener)
        .setup(mPullToRefreshLayout);
		
		 //if we have saved info after screen rotating or pause/stop app
        if(savedInstanceState == null) {
        	//refresh on open to load data when app first time started
    		updateList();
        } else{
        	if (sPref.getBoolean(Constants.PREFERENCES_FORCE_UPDATE_X+bundle.getInt(Constants.BUNDLE_LIST_TYPE), false)){
            	updateList();
            } else {
	        	horoscopeCollection = savedInstanceState.getParcelableArrayList(Constants.EXTRA_HOROSCOPE_COLLECTION);
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
	}
	
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
	
    AsyncTask<Void, Void, Void> loadM;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) 
    public class  CustomOnRefreshListener implements OnRefreshListener{
		@Override
		public void onRefreshStarted(View view) {
			loadM = new AsyncTask<Void, Void, Void>() {
				 
                @Override
                protected Void doInBackground(Void... params) {
                	try {
                		//hide prev state
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
                		
                		//sleep to prevent lags in animations
                		Thread.sleep(250);
                		
                		HoroscopeComLoader horoscopeComLoader = new HoroscopeComLoader(bundle.getInt(Constants.BUNDLE_LIST_TYPE), sPref, getActivity());
                		horoscopeCollection = horoscopeComLoader.loadCurrList();
                		
                    	handler.post(new Runnable(){
							@Override
							public void run() {
								mPullToRefreshLayout.setRefreshComplete();
							}
                    	});
                    	
                    	//sleep to prevent lags in animations
                    	Thread.sleep(200);
                    	
					} catch (Exception e) {
						e.printStackTrace();
					}
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
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
