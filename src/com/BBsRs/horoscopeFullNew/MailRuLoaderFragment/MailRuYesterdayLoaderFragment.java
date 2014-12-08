
package com.BBsRs.horoscopeFullNew.MailRuLoaderFragment;

import java.io.IOException;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.res.Resources.NotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ScrollView;

import com.BBsRs.horoscopeFullNew.R;

public class MailRuYesterdayLoaderFragment extends Fragment {
	
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
    String LOG_TAG = "Mail.ru YESTERDAY";
    
    //flag for error
    boolean error=false;
    
    //preferences 
    SharedPreferences sPref;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	View contentView = inflater.inflate(R.layout.fragment_content_show);
    	
   	 	//set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
    	
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
		ed.putBoolean("changed_0", false);	
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
        getSupportActionBar().setSubtitle(getResources().getStringArray(R.array.zodiac_signs)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]+" - "+getResources().getStringArray(R.array.mail_ru_horoscopes)[0]);
        
        //check if settings changed
        if (sPref.getBoolean("changed_0", false)){
        	mPullToRefreshLayout.setRefreshing(true);
        	customOnRefreshListener.onRefreshStarted(null);
        	Editor ed = sPref.edit();   
			ed.putBoolean("changed_0", false);	
			ed.commit();
        }
    }
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		 outState.putString("data", data);
		 outState.putBoolean("error", error);
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
                    	
                        //load and retrieve data from horo.mail.ru
                    	Document doc = Jsoup.connect("http://horo.mail.ru/prediction/"+getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[Integer.parseInt(sPref.getString("preference_zodiac_sign", "0"))]+"/"+getResources().getStringArray(R.array.nameOfHoroscopeForLoadMailRu)[0]+"/").userAgent(getResources().getString(R.string.user_agent)).timeout(getResources().getInteger(R.integer.user_timeout)).get();
                    	data = doc.getElementsByClass("article__text").first().html()+"<br />"+getResources().getString(R.string.copyright_horo_mail_ru);
                    	if (!(doc.getElementsByClass("article__text").first().html().length()<10))
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
                    }
                    
                    // Notify PullToRefreshLayout that the refresh has finished
                    mPullToRefreshLayout.setRefreshComplete();
                }
            }.execute();
		}
         
    }
}



