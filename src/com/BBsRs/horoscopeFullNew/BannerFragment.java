
package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.preference.SharedPreferences;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.BBsRs.horoscopeNewEdition.R;

public class BannerFragment extends Fragment {
	
	//preferences 
    SharedPreferences sPref;
    
    private PullToRefreshLayout mPullToRefreshLayout;

    private WebView mWebView;
    
   //custom refresh listener where in new thread will load job doing, need to customize for all kind of data
    CustomOnRefreshListener customOnRefreshListener = new CustomOnRefreshListener();
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_banner_show);
		
		// Find WebView and get it ready to display pages
        mWebView = (WebView) contentView.findViewById(R.id.webBanner);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new SampleWebViewClient());

        // Now find the PullToRefreshLayout and set it up
        mPullToRefreshLayout = (PullToRefreshLayout) contentView.findViewById(R.id.ptr_layout);
        ActionBarPullToRefresh.from(getActivity())
                .allChildrenArePullable()
                .listener(customOnRefreshListener)
                .setup(mPullToRefreshLayout);

        // Finally make the WebView load something...
        mWebView.loadUrl(getResources().getString(R.string.notice_url));
		
        return contentView;
    }
    
    public class CustomOnRefreshListener implements OnRefreshListener {

		@Override
		public void onRefreshStarted(View view) {
			mWebView.reload();
		}
    }

    private class SampleWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
        	    mWebView.loadUrl(getResources().getString(R.string.notice_url));
                Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            // If the PullToRefreshAttacher is refreshing, make it as complete
            if (mPullToRefreshLayout.isRefreshing()) {
                mPullToRefreshLayout.setRefreshComplete();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getSupportActionBar().setTitle(R.string.app_name);
        getSupportActionBar().setSubtitle(getResources().getStringArray(R.array.application_titles)[2]);
    }
}
