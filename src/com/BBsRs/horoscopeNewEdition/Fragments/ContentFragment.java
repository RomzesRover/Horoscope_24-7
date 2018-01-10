package com.BBsRs.horoscopeNewEdition.Fragments;

import org.holoeverywhere.LayoutInflater;

import uk.co.senab.actionbarpulltorefresh.extras.actionbarcompat.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.BaseFragment;

public class ContentFragment extends BaseFragment{
	
	//custom refresh listener where in new thread will load job doing, need to customize for all kind of data
    CustomOnRefreshListener customOnRefreshListener = new CustomOnRefreshListener();
    PullToRefreshLayout mPullToRefreshLayout;
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_content);
		
		mPullToRefreshLayout = (PullToRefreshLayout) contentView.findViewById(R.id.ptr_layout);
		
		//init pull to refresh
		ActionBarPullToRefresh.from(getActivity())
        .allChildrenArePullable()
        .listener(customOnRefreshListener)
        .setup(mPullToRefreshLayout);
		
		return contentView;
	}
	
	@Override
    public void onResume() {
        super.onResume();
        setTitle("Greka");
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
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                	mPullToRefreshLayout.setRefreshComplete();
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
