/*This is an sample code !!!! 
 * This module not used in final v of app
 * You can build an in app billing app based on this sample
 * thx to http://www.techotopia.com/index.php/Integrating_Google_Play_In-app_Billing_into_an_Android_Application_%E2%80%93_A_Tutorial
 * RomzesRover, 26 sen 2013
 * */
package com.BBsRs.horoscopenew;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ebookfrenzy.inappbilling.util.IabResult;
import com.inappbilling.util.IabHelper;
import com.inappbilling.util.Inventory;
import com.inappbilling.util.Purchase;



public class ActivityInAppBilling extends Activity {
	private static final String TAG = "<your domain>.inappbilling";
	IabHelper mHelper;
	static final String ITEM_SKU = "android.test.purchased";
	
	private Button clickButton;
	private Button buyButton;
	
	boolean mIsPremium = false;
	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_in_app_billing);

		buyButton = (Button)findViewById(R.id.buyButton);
		clickButton = (Button)findViewById(R.id.clickButton);
		
		
		clickButton.setEnabled(false);
		
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEArtgOpup2MUXEwcmJl+lNGY3PahRNUndNdDSNWHiYE6DTb9BcuuUx5BuPZikfrRCDqF2ZMbxt6lL7V1gGLR3sMqgfHGWQqt7DO9+78QkIWnmeU+phwn7RFsx9YRnZXb9Nfs9IwMMri42QK5C+cCk7lr2j6HWQHSaxABw2z4tgcx4TlE/b7A3lDAvfa56h2Scv7f6mf3Ob+Cwr8ugvf36rEfjfbsIWwQm5KaB9KbDdBAN/n3LolMjbyO4jHj/6BmN+qr4TaD9gDU8BvKwSxslh05Xs4b9wzyDmX1Ip5ICwLNr43CFQyn1qcYIJ7GqWymqEbAay6FKPjqRK7bmeGazRCQIDAQAB";
        
        	mHelper = new IabHelper(this, base64EncodedPublicKey);
        
        	mHelper.startSetup(new 
			IabHelper.OnIabSetupFinishedListener() {
        	   	  public void onIabSetupFinished(IabResult result) 
			  {
        	        if (!result.isSuccess()) {
        	           Log.d(TAG, "In-app Billing setup failed: " + 
					result);
        	      } else {             
        	      	    Log.d(TAG, "In-app Billing is set up OK");
        	      	    mHelper.queryInventoryAsync(mGotInventoryListener);	//check if we have premium
		      }
        	   }
        	});
        	
        	
        	
        buyButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mHelper.launchPurchaseFlow(ActivityInAppBilling.this, ITEM_SKU, 10001,   
		     			   mPurchaseFinishedListener, "");
			}
		});
	}
	
	IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener 
	= new IabHelper.OnIabPurchaseFinishedListener() {
	public void onIabPurchaseFinished(IabResult result, 
                    Purchase purchase) 
	{
	   if (result.isFailure()) {
	      // Handle error
	      return;
	 }      
	 else if (purchase.getSku().equals(ITEM_SKU)) {
		 mIsPremium = true;
         updateUI();
	 		}
	      
		}
	};
		
	IabHelper.QueryInventoryFinishedListener mGotInventoryListener
	   = new IabHelper.QueryInventoryFinishedListener() {
		   public void onQueryInventoryFinished(IabResult result,
		      Inventory inventory) {
		      if (result.isFailure()) {
			  // Handle failure
		      } else {
		    	  Log.d(TAG, "Query inventory was successful.");
		          Purchase premiumPurchase = inventory.getPurchase(ITEM_SKU);
		          mIsPremium = (premiumPurchase != null);
		          Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
		          updateUI();
		      }
	    }
	};
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mHelper != null) mHelper.dispose();
		mHelper = null;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, 
	     Intent data) 
	{
	      if (!mHelper.handleActivityResult(requestCode, 
	              resultCode, data)) {     
	    	super.onActivityResult(requestCode, resultCode, data);
	      }
	}
	
	public void updateUI(){
		buyButton.setEnabled(!mIsPremium);
		clickButton.setEnabled(mIsPremium);
	}
	
}
