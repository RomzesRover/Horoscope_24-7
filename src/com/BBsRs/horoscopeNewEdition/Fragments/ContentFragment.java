package com.BBsRs.horoscopeNewEdition.Fragments;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.TargetApi;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Layout.Alignment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.SFUIFontsEverywhere.CustomTypefaceSpan;
import com.BBsRs.SFUIFontsEverywhere.SFUIFontsPath;
import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BaseFragment;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class ContentFragment extends BaseFragment{
	
	ArrayList<HoroscopeCollection> horoscopeCollection = new ArrayList<HoroscopeCollection>();
	SpannableString[] finalString;
	
	//for retrieve data from activity
    Bundle bundle;
	
	//custom refresh listener where in new thread will load job doing, need to customize for all kind of data
    CustomOnRefreshListener customOnRefreshListener = new CustomOnRefreshListener();
    PullToRefreshLayout mPullToRefreshLayout;
    
    TextView textContent;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_content);
		
		//retrieve bundle
      	bundle = this.getArguments();
      	
      	//init views
      	textContent = (TextView)contentView.findViewById(R.id.textContent);
		
		mPullToRefreshLayout = (PullToRefreshLayout) contentView.findViewById(R.id.ptr_layout);
		
		//init pull to refresh
		ActionBarPullToRefresh.from(getActivity())
        .allChildrenArePullable()
        .listener(customOnRefreshListener)
        .setup(mPullToRefreshLayout);
		
		//refresh on open to load data when app first time started
        mPullToRefreshLayout.setRefreshing(true);
        customOnRefreshListener.onRefreshStarted(null);
		
		return contentView;
	}
	
	@Override
    public void onResume() {
        super.onResume();
        setTitle(bundle.getString(Constants.BUNDLE_LIST_TITLE_NAME));
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
                		//null prev
                		horoscopeCollection = new ArrayList<HoroscopeCollection>();
                		
                		Document doc;
                		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-today.aspx?sign=2").get();
                		horoscopeCollection.add(new HoroscopeCollection(getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
                		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-today.aspx?sign=2").get();
                		horoscopeCollection.add(new HoroscopeCollection(getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
                		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-today.aspx?sign=2").get();
                		horoscopeCollection.add(new HoroscopeCollection(getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
                		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-today.aspx?sign=2").get();
                		horoscopeCollection.add(new HoroscopeCollection(getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
                		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-today.aspx?sign=12").get();
                		horoscopeCollection.add(new HoroscopeCollection(getResources().getStringArray(R.array.horoscope_com_kinds)[5], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
                		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-today.aspx?sign=3").get();
                		horoscopeCollection.add(new HoroscopeCollection(getResources().getStringArray(R.array.horoscope_com_kinds)[6], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
                		
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
					} catch (Exception e) {
						e.printStackTrace();
					}
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                	mPullToRefreshLayout.setRefreshComplete();
                	
                	textContent.setText(TextUtils.concat(finalString));
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
