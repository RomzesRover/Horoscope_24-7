package com.BBsRs.horoscopeFullNew;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.preference.SharedPreferences.Editor;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;

import com.BBsRs.horoscopeFullNew.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.R;
import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class PurchaseDialogActivity extends BaseActivity {
	
	//preferences 
    SharedPreferences sPref;

    /*--------------------INIT IN APP BILLING-------------------------*/
    //inAppBillingData
    // PRODUCT & SUBSCRIPTION IDS
	private BillingProcessor bp;
	/*--------------------INIT IN APP BILLING-------------------------*/
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	  //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        //set app lang
        setLocale(sPref.getString("preference_locales", getResources().getString(R.string.default_locale)));

        /*--------------------INIT IN APP BILLING-------------------------*/
        bp = new BillingProcessor(this, Constants.LICENSE_KEY, new BillingProcessor.IBillingHandler() {
            @Override
            public void onProductPurchased(String productId, TransactionDetails details) {
            	//disable ad
            	if (bp.isPurchased(Constants.PRODUCT_ID_HIGH)) sPref.edit().putBoolean("agreeWithAd", false).commit();
            	activityRefresh();
            }
            @Override
            public void onBillingError(int errorCode, Throwable error) {
            	Toast.makeText(getApplicationContext(), "Billing not initialized.", Toast.LENGTH_LONG).show();
            }
            @Override
            public void onBillingInitialized() {
                bp.purchase(Constants.PRODUCT_ID_HIGH);
            }
            @Override
            public void onPurchaseHistoryRestored() {
            }
        });
        /*--------------------INIT IN APP BILLING-------------------------*/
	
	    // TODO Auto-generated method stub
	}
	
	@Override
    public void onDestroy() {
        if (bp != null)
            bp.release();
        super.onDestroy();
    }
	
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data))
            super.onActivityResult(requestCode, resultCode, data);
    }
    
	private void activityRefresh(){
		Editor ed = sPref.edit();  
		ed.putBoolean("preference_start", true); 	
		ed.commit();
		Intent refresh = new Intent(this, ContentShowActivity.class);
		//restart activity
	    startActivity(refresh);   
	    //set no animation
	    overridePendingTransition(0, 0);
	    // stop curr activity
	    finish();
	}

}
