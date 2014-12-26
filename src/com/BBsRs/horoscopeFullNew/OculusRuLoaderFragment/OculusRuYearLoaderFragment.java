
package com.BBsRs.horoscopeFullNew.OculusRuLoaderFragment;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;

import com.BBsRs.horoscopeFullNew.R;
import com.BBsRs.horoscopeFullNew.Base.BaseFragment;

public class OculusRuYearLoaderFragment extends BaseFragment {
	
	int UNIVERSAL_ID = 3;
	
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
    
    //LOG_TAG for log
    String LOG_TAG = "OculusRu"+UNIVERSAL_ID;
    
    //flag for error
    boolean error=false;
    
    //preferences 
    SharedPreferences sPref;
    
    //share action providers, use it to set current horoscope
    ShareActionProvider actionProvider;
	
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
            	textContent.setText(Html.fromHtml(data)+" ");
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
        
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //set titile for action bar
        getSupportActionBar().setTitle(sPref.getString("preference_name", getResources().getString(R.string.default_name)));
        //set subtitle for a current fragment
        getSupportActionBar().setSubtitle(getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]+" - "+getResources().getStringArray(R.array.oculus_ru_horoscopes)[UNIVERSAL_ID]);
        
        //check if settings changed
        if (sPref.getBoolean("changed_"+UNIVERSAL_ID, false)){
        	mPullToRefreshLayout.setRefreshing(true);
        	customOnRefreshListener.onRefreshStarted(null);
        	Editor ed = sPref.edit();   
			ed.putBoolean("changed_"+UNIVERSAL_ID, false);	
			ed.commit();
        }
    }
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		 outState.putString("data", data);
		 outState.putBoolean("error", error);
	}
    
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_content_menu, menu);
        
        MenuItem actionItem = menu.findItem(R.id.menu_share);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(actionItem);
        
        //fix share action
        if (!error && data.length()>10)
        	//set shareable content
        	actionProvider.setShareIntent(createShareIntent(
        			getResources().getString(R.string.share_content_horo_for)
        			+" "+getResources().getStringArray(R.array.oculus_ru_horoscopes)[UNIVERSAL_ID].toLowerCase()
        			+", "+getResources().getString(R.string.share_content_horo_for_2)
        			+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))].toLowerCase()
        			+"\n\n"
        			+String.valueOf(textContent.getText())
        			+"\n\n"+getResources().getString(R.string.share_send_from)
        			+"\n"+getResources().getString(R.string.share_content_url)));
        return;
    }
    
    
    public class  CustomOnRefreshListener implements OnRefreshListener{

		@Override
		public void onRefreshStarted(View view) {
			// TODO Auto-generated method stub
			new AsyncTask<Void, Void, Void>() {
				 
                @Override
                protected Void doInBackground(Void... params) {
                    try {
                    	// set no error, cuz we can reach it
                    	error=true;
                    	
                        //load and retrieve data from oculus.ru http://www.oculus.ru/goroskop_2015/goroskop_2015_Telec.html
                    	Document doc = Jsoup.connect("http://oculus.ru/"+getActivity().getResources().getStringArray(R.array.nameOfHoroscopecForLoadOculusRu)[UNIVERSAL_ID]+Character.toUpperCase((getActivity().getResources().getStringArray(R.array.nameOfzodiacForLoadOculusRu)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]).charAt(0)) + (getActivity().getResources().getStringArray(R.array.nameOfzodiacForLoadOculusRu)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]).substring(1)+".html").userAgent(getResources().getString(R.string.user_agent)).timeout(getResources().getInteger(R.integer.user_timeout)).get();
                    	
                    	String horo=doc.title();
                    	int i=0;
                    	for (Element item : doc.getElementById("centerCol").children()){
                    		if ((i!=doc.getElementById("centerCol").children().size()-1) && (i!=doc.getElementById("centerCol").children().size()-4)&& (i!=doc.getElementById("centerCol").children().size()-3) && (i!=doc.getElementById("centerCol").children().size()-2) && (i!=0) && (i!=1))
                    		horo += "<br /><br />"+item.html();
                    		i++;
                    	}
                    	
                    	data = horo+"<br /><br />"+getResources().getString(R.string.copyright_oculus_ru); 
                    	if (!(horo.length()<10))
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
                    super.onPostExecute(result);
                    
                    if (error){
                    	scrollView.setVisibility(View.GONE);
                    	relativeErrorLayout.setVisibility(View.VISIBLE);
                    	errorRetryButton.setEnabled(true);
                    } else {
                    	scrollView.setVisibility(View.VISIBLE);
                    	relativeErrorLayout.setVisibility(View.GONE);
                    	
                    	//download data is cancelled, setting up views 
                    	textContent.setText(Html.fromHtml(data)+" ");
                    	
                    
                    	//with fly up animation
                    	Animation flyUpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fly_up_anim);
                    	textContent.startAnimation(flyUpAnimation);
                    	
                    	//set shareable content
                    	actionProvider.setShareIntent(createShareIntent(
                    			getResources().getString(R.string.share_content_horo_for)
                    			+" "+getResources().getStringArray(R.array.oculus_ru_horoscopes)[UNIVERSAL_ID].toLowerCase()
                    			+", "+getResources().getString(R.string.share_content_horo_for_2)
                    			+" "+getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))].toLowerCase()
                    			+"\n\n"
                    			+String.valueOf(textContent.getText())
                    			+"\n\n"+getResources().getString(R.string.share_send_from)
                    			+"\n"+getResources().getString(R.string.share_content_url)));
                    }
                    
                    
                    // Notify PullToRefreshLayout that the refresh has finished
                    mPullToRefreshLayout.setRefreshComplete();
                }
            }.execute();
		}
         
    }
}



