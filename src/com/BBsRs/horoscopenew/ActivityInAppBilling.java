package com.BBsRs.horoscopenew;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.BBsRs.horoscopenew.R;
import com.ebookfrenzy.inappbilling.util.IabHelper;
import com.ebookfrenzy.inappbilling.util.IabResult;
import com.ebookfrenzy.inappbilling.util.Inventory;
import com.ebookfrenzy.inappbilling.util.Purchase;



public class ActivityInAppBilling extends Activity {
	private static final String TAG = "<your domain>.inappbilling";
	IabHelper mHelper;
	static final String ITEM_SKU = "android.test.purchased";
	
	private Button clickButton;
	private Button buyButton;
	
	
	
	
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
	
	public void consumeItem() {
		mHelper.queryInventoryAsync(mReceivedInventoryListener);
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
	     consumeItem();
	    buyButton.setEnabled(false);
	 		}
	      
		}
	};
		
	IabHelper.QueryInventoryFinishedListener mReceivedInventoryListener 
	   = new IabHelper.QueryInventoryFinishedListener() {
		   public void onQueryInventoryFinished(IabResult result,
		      Inventory inventory) {
		      if (result.isFailure()) {
			  // Handle failure
		      } else {
		    	  Log.d(TAG, "Query inventory was successful.");
		    	  	
	                 
	                 Purchase TimePurchase = inventory.getPurchase(ITEM_SKU);
	                 if (TimePurchase != null ) {
	                     Log.d(TAG, "We have time. Consuming it.");
	                     mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU), mConsumeFinishedListener);
	                     return;
	                 }
		      }
	    }
	};
	
	IabHelper.OnConsumeFinishedListener mConsumeFinishedListener =
			  new IabHelper.OnConsumeFinishedListener() {
			   public void onConsumeFinished(Purchase purchase, 
		             IabResult result) {

			 if (result.isSuccess()) {		    	 
			   	  clickButton.setEnabled(true);
			 } else {
			         // handle error
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
	
}
