
package com.BBsRs.horoscopeFullNew.GoAstroDeLoaderFragment;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;

import com.BBsRs.horoscopeFullNew.Base.BaseFragment;
import com.BBsRs.horoscopeFullNew.Fonts.CustomTypefaceSpan;
import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFont;
import com.BBsRs.horoscopeFullNew.Fonts.SFUIDisplayFontPath;
import com.BBsRs.horoscopeNewEdition.R;

public class GoAstroDePersonalLoaderFragment extends BaseFragment {
	
	int UNIVERSAL_ID = 5;
	
	//public for class views which will retrieve from fragment_content_show.xml layout
	PullToRefreshLayout mPullToRefreshLayout;
    TextView textContent;
    ScrollView scrollView;
    RelativeLayout relativeErrorLayout;
    TextView errorMessage;
    Button errorRetryButton;
    
    //custom refresh listener where in new thread will load job doing, need to customize for all kind of data
    CustomOnRefreshListener customOnRefreshListener = new CustomOnRefreshListener();
    
    //retrieved data
    String data = "";
    int dateLenght = 0;
    
    //LOG_TAG for log
    String LOG_TAG = "goastro.de"+UNIVERSAL_ID;
    
    //flag for error
    boolean error=false;
    
    //preferences 
    SharedPreferences sPref;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View contentView = inflater.inflate(R.layout.fragment_content_show);
    	
    	//enable menu
    	setHasOptionsMenu(true);
    	
    	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        
      //!----------------------------------AD-----------------------------------------------------!
    	showAd(contentView, sPref);
    	//!----------------------------------AD-----------------------------------------------------!
    	
    	//retrieving views from layout
    	textContent=(TextView)contentView.findViewById(R.id.textContent);
    	mPullToRefreshLayout = (PullToRefreshLayout) contentView.findViewById(R.id.ptr_layout);
    	scrollView = (ScrollView)contentView.findViewById(R.id.scrollview);
    	relativeErrorLayout = (RelativeLayout)contentView.findViewById(R.id.errorLayout);
    	errorMessage = (TextView)contentView.findViewById(R.id.errorMessage);
    	errorRetryButton = (Button)contentView.findViewById(R.id.errorRetryButton);
    	 
        //init pull to refresh module
        ActionBarPullToRefresh.from(getActivity())
          .allChildrenArePullable()
          .listener(customOnRefreshListener)
          .setup(mPullToRefreshLayout);
        
        //if we have saved info after screen rotating or pause/stop app
        if(savedInstanceState == null) {
        //refresh on open to load data when app first time started
        mPullToRefreshLayout.setRefreshing(true);
        customOnRefreshListener.onRefreshStarted(null);
        //we already load data, put false to checker
        Editor ed = sPref.edit();   
		ed.putBoolean("changed_"+UNIVERSAL_ID, false);	
		ed.commit();
        } else {
        	if (savedInstanceState.getBoolean("error")){
        		scrollView.setVisibility(View.GONE);
            	relativeErrorLayout.setVisibility(View.VISIBLE);
            } else {
            	data = savedInstanceState.getString("data");
            	dateLenght = savedInstanceState.getInt("dateLenght");
            	SpannableString sb = new SpannableString(Html.fromHtml("<br />"+data)+" ");
                sb.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIDisplayFontPath.MEDIUM)), 0, dateLenght, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                sb.setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_CENTER), 0, dateLenght, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                sb.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIDisplayFontPath.LIGHT)), dateLenght+1, sb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                sb.setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_NORMAL), dateLenght+1, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            	textContent.setText(sb);
            	
            	scrollView.setVisibility(View.VISIBLE);
            	relativeErrorLayout.setVisibility(View.GONE);
        	}
        }
        
        //programing error button
        errorRetryButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPullToRefreshLayout.setRefreshing(true);
		        customOnRefreshListener.onRefreshStarted(null);
		        errorRetryButton.setEnabled(false);
			}
		});
        
      //set fonts
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), errorMessage);
		SFUIDisplayFont.ULTRALIGHT.apply(getActivity(), errorRetryButton);
        
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //set subtitle for a current fragment with custom font
        setTitle(getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]);
        getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_action_bar_background));
        
        //check if settings changed
        if (sPref.getBoolean("changed_"+UNIVERSAL_ID, false)){
        	if (!customOnRefreshListener.isRefreshing){
	        	mPullToRefreshLayout.setRefreshing(true);
	        	customOnRefreshListener.onRefreshStarted(null);
        	}
        	Editor ed = sPref.edit();   
			ed.putBoolean("changed_"+UNIVERSAL_ID, false);	
			ed.commit();
        }
    }
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		 outState.putString("data", data);
		 outState.putInt("dateLenght", dateLenght);
		 outState.putBoolean("error", error);
	}
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_content_menu, menu);
    }
    
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
	      case android.R.id.home:
	    	  Intent i = new Intent("horo_open_menu_drawer");
	    	  getActivity().sendBroadcast(i);
	    	  break;
	      case R.id.menu_share_new:
	    	  if (error || data.length()<10)
	    		  break;
			  String shareBody = getResources().getStringArray(R.array.go_astro_de_horoscopes)[UNIVERSAL_ID]
					  	+" "+getResources().getString(R.string.share_personal_1)
	        			+"\n"
	        			+String.valueOf(textContent.getText()).replaceAll(getResources().getString(R.string.go_astro_de_copyright), "")
	        			+getResources().getString(R.string.share_send_from)
	        			+"\n"+getResources().getString(R.string.share_content_url);
			  Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
			  sharingIntent.setType("text/plain");
			  sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getResources().getString(R.string.app_name));
			  sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
			  startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share)));
	    	  break;
    	}
		return true;
    	
    }

    public class  CustomOnRefreshListener implements OnRefreshListener{
    	
    	public boolean isRefreshing = false;

		@Override
		public void onRefreshStarted(View view) {
			isRefreshing = true;
			// TODO Auto-generated method stub
			new AsyncTask<Void, Void, Void>() {
				 
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                    	// set no error, cuz we can reach it
                    	error=true;
                    	//load and retrieve data from http://www.goastro.de/numeroskope_1_1996-03-26.php
                    	Document doc = Jsoup.connect("http://www.goastro.de/" + getResources().getStringArray(R.array.nameOfHoroscopecForLoadGoAstroDe)[UNIVERSAL_ID] + "_1_" + String.valueOf(sPref.getInt("yearBorn", 1995))+"-"+String.valueOf(sPref.getInt("monthBorn", 4)+1)+"-"+String.valueOf(sPref.getInt("dayBorn", 10)) + ".php").userAgent(getResources().getString(R.string.user_agent)).timeout(getResources().getInteger(R.integer.user_timeout)).get();
                    	data = doc.getElementsByClass("text_orange_fett2").first().text()+"<br /><br />" + doc.getElementsByClass("text_orange").first().text() + "<br />" + doc.getElementsByClass("text_container_klein").html().replaceAll("<img src=\"(.*)\">", "").replaceAll("<br(.*)>", "")+"<br /><br />"+getResources().getString(R.string.go_astro_de_copyright)+"<br />";
                    	dateLenght = Html.fromHtml(doc.getElementsByClass("text_orange_fett2").first().text()).length()+1;
                    	if (!(doc.getElementsByClass("text_container_klein").text().length()<10))
                    		error=false;
                    } catch (NotFoundException e) {
                    	error=true;
    					Log.e(LOG_TAG, "data Error");
    					e.printStackTrace();
    				} catch (IOException e) {
    					error=true;
    					Log.e(LOG_TAG, "Load Error");
    					e.printStackTrace();
    				} catch (NullPointerException e) {
    					error=true;
    	        		Log.e(LOG_TAG, "null Load Error"); 
    					e.printStackTrace();
    				} catch (Exception e) {
    					error=true;
    	        		Log.e(LOG_TAG, "other Load Error");
    					e.printStackTrace();
    				}
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                	try{
	                    super.onPostExecute(result);
	                    
	                 // Notify PullToRefreshLayout that the refresh has finished
	                    mPullToRefreshLayout.setRefreshComplete();
	                    
	                    if (error){
	                    	scrollView.setVisibility(View.GONE);
	                    	relativeErrorLayout.setVisibility(View.VISIBLE);
	                    	errorRetryButton.setEnabled(true);
	                    } else {
	                    	scrollView.setVisibility(View.VISIBLE);
	                    	relativeErrorLayout.setVisibility(View.GONE);
	                    	
	                    	//download data is cancelled, setting up views 
	                    	SpannableString sb = new SpannableString(Html.fromHtml("<br />"+data)+" ");
	                        sb.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIDisplayFontPath.MEDIUM)), 0, dateLenght, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	                        sb.setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_CENTER), 0, dateLenght, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                        sb.setSpan(new CustomTypefaceSpan("", Typeface.createFromAsset(getActivity().getAssets(), SFUIDisplayFontPath.LIGHT)), dateLenght+1, sb.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
	                        sb.setSpan(new AlignmentSpan.Standard(Alignment.ALIGN_NORMAL), dateLenght+1, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
	                    	textContent.setText(sb);
	                    	
	                    
	                    	//with fly up animation
	                    	Animation flyUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim);
	                    	textContent.startAnimation(flyUpAnimation);
	                    }
                	} catch (Exception e){
                		e.printStackTrace();
                	}
                	isRefreshing = false;
                }
            }.execute();
		}
         
    }
    
	public String monthPlusZero(String arg1){
		if (arg1.length()==1)
		return "0"+arg1;
		else 
		return arg1;
	}
}


