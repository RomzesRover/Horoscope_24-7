package com.BBsRs.horoscopenew;

import java.io.IOException;
import java.util.Calendar;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.TextView;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BBsRs.horoscopenew.R;
import com.ebookfrenzy.inappbilling.util.IabHelper;
import com.ebookfrenzy.inappbilling.util.IabResult;
import com.ebookfrenzy.inappbilling.util.Inventory;
import com.ebookfrenzy.inappbilling.util.Purchase;

public class ActivityLoader extends Activity {
																						//data for in app billing !
	private static final String TAG = "<Horoscope 24/7>.inappbilling";
	IabHelper mHelper;
	static final String ITEM_SKU = "horoscope_full";
	boolean buyed = false;
	
	AlertDialog alert = null;															//alert dialog
    	
	
	SharedPreferences sPref;    // ��� ��� �������� ��
	 Editor ed;
	 boolean animation = false;
	 int currentVersion;
	private timer CountDownTimer;					// for timer
	  RelativeLayout  layoutErrorView ;
 	 	ImageView logo;
 	 	Button reconnect ;
 	 	private final Handler handler = new Handler();
	public class timer extends CountDownTimer{

		public timer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// extendedClass
		}

		@Override
		public void onFinish() {
			Thread thr=new Thread(new Runnable() {				//������ � ����� ������
		        public void run() {
		        	sPref = getSharedPreferences("T", 1);
		        	
		        	if (!sPref.getBoolean("agree", false)){
		        		Intent step1 = new Intent(getApplicationContext(), ActivityAgreement.class);
						startActivity(step1);
						finish();
		        	} else {
		        	
			if (isNetworkAvailable()){
				//
				 	ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
				    ed.putBoolean("canBack", false);						//����, �� ������
			        ed.commit();
				try {
					currentVersion=Integer.parseInt(Jsoup.connect("http://brothers-rovers.3dn.ru/currentInfo5.0.txt").get().text());
					sPref = getSharedPreferences("T", 1);
					if (currentVersion==sPref.getInt("currentVersion", 0000 )){
						// onFinish
				        String prpr = sPref.getString("name", "");
				        if (prpr.length()<1){
				        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
							startActivity(step1);
							finish();
				        } else {
				        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
							startActivity(step1);
							finish();
				        }
					} else {
						sPref = getSharedPreferences("T", 1);
						ed = sPref.edit();   // ���� ������ �� ���������, ������ �� ��� ���� :)
						if (currentVersion != 1120)
					    ed.putInt("currentVersion", currentVersion);						//����, �� ������
					    ed.putString("help or info", "info");
				        ed.commit();
				        Intent step1 = new Intent(getApplicationContext(), ActivityLoaderVersion.class);
						startActivity(step1);
						finish();
					}
				} catch (NumberFormatException e) {
					String prpr = sPref.getString("name", "");
			        if (prpr.length()<1){
			        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
						startActivity(step1);
						finish();
			        } else {
			        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
						startActivity(step1);
						finish();
			        }
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					String prpr = sPref.getString("name", "");
			        if (prpr.length()<1){
			        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
						startActivity(step1);
						finish();
			        } else {
			        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
						startActivity(step1);
						finish();
			        }
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NullPointerException e) {
					String prpr = sPref.getString("name", "");
			        if (prpr.length()<1){
			        	Intent step1 = new Intent(getApplicationContext(), ActivityHoroSettings1.class);
						startActivity(step1);
						finish();
			        } else {
			        	Intent step1 = new Intent(getApplicationContext(), ActivityResultPage.class);
						startActivity(step1);
						finish();
			        }
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				//���� ��� ���� !!!
				connectionError(layoutErrorView,logo,reconnect);
			}
		        	}
		        }
	        });
		thr.start();
		}

		@Override
		public void onTick(long arg0) {
			// on Tick
			
		}
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        
        layoutErrorView = (RelativeLayout)findViewById(R.id.errorLayout);
	 	logo = (ImageView)findViewById(R.id.logo);
	 	reconnect = (Button)findViewById(R.id.retry);
	 	
	 	/*---------INIT IN APP BILLING------------*/
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
    	      	    consumeItem();										//check if buyed
	      }
    	   }
    	});
    	
    	/*---------INIT IN APP BILLING END------------*/
    	
    	
         
         	 sPref = getSharedPreferences("T", 1);
         	 TextView days = (TextView)findViewById(R.id.days);
         	 Calendar c = Calendar.getInstance();  				//current date
         	 													
         	 if (!buyed){
         	 if (sPref.getInt("dayTo", -1)==-1){
         		 
	         Calendar cal=Calendar.getInstance();				//+8days
	         cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+8);
	         
	         Log.i("P", "day="+String.valueOf(c.get(Calendar.DAY_OF_MONTH))+" month="+String.valueOf(c.get(Calendar.MONTH)+1)+" year="+String.valueOf(c.get(Calendar.YEAR)));
	         Log.i("F", "day="+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+" month="+String.valueOf(cal.get(Calendar.MONTH)+1)+" year="+String.valueOf(cal.get(Calendar.YEAR)));
	         
	         days.setText(getResources().getString(R.string.trial_until)+": "+String.valueOf(cal.get(Calendar.DAY_OF_MONTH))+" "+getResources().getStringArray(R.array.moths_of_year)[c.get(Calendar.MONTH)]+" "+String.valueOf(cal.get(Calendar.YEAR)));
	         ed = sPref.edit();  
	         ed.putInt("dayTo", cal.get(Calendar.DAY_OF_MONTH));
	         ed.putInt("monthTo", cal.get(Calendar.MONTH)+1);
	         ed.putInt("yearTo", cal.get(Calendar.YEAR));
	         ed.putInt("dayFi", c.get(Calendar.DAY_OF_MONTH));
	         ed.putInt("monthFi", c.get(Calendar.MONTH)+1);
	         ed.putInt("yearFi", c.get(Calendar.YEAR));
		     ed.commit();
		     
      		 //trial in using we can start loading
      	   	 CountDownTimer = new timer (2000, 1000);   		//timer to 2 seconds (tick one second)
      	     CountDownTimer.start();							//start timer
         	 } else {
         		Log.i("Pa", "Past");
         		days.setText(getResources().getString(R.string.trial_until)+": "+String.valueOf(sPref.getInt("dayTo", -1))+" "+getResources().getStringArray(R.array.moths_of_year)[sPref.getInt("monthTo", -1)-1]+" "+String.valueOf(sPref.getInt("yearTo", -1)));	
         																		//check can we or no
         		if (sPref.getInt("dayTo", -1)==c.get(Calendar.DAY_OF_MONTH) && 
         				sPref.getInt("monthTo", -1)==c.get(Calendar.MONTH)+1 &&
         				sPref.getInt("yearTo", -1)==c.get(Calendar.YEAR)){
         			//end of trial try to buy it
         			final Context context = ActivityLoader.this; 								// create context
         			AlertDialog.Builder build = new AlertDialog.Builder(context); 				// create build for alert dialog
            		build.setTitle(getResources().getString(R.string.trial_title)); 			// set title

            		LayoutInflater inflater = (LayoutInflater)context.getSystemService
            			      (Context.LAYOUT_INFLATER_SERVICE);
            		
            		View content = inflater.inflate(R.layout.dialog_content, null);
            		
            		RelativeLayout paidRt = (RelativeLayout)content.findViewById(R.id.paidRt);
            		paidRt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							mHelper.launchPurchaseFlow(ActivityLoader.this, ITEM_SKU, 10001,   
					     			   mPurchaseFinishedListener, "");
						}
					});
            		
            		RelativeLayout freeRt = (RelativeLayout)content.findViewById(R.id.freeRt);
            		if (!sPref.getBoolean("canAdd30", true))  freeRt.setVisibility(View.GONE);
            		freeRt.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							// show intent market
							Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.BBsRs.horoscopefree"));
		    				marketIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
		    				startActivity(marketIntent);
		    				Toast.makeText(context, getResources().getStringArray(R.array.rate_me)[4], Toast.LENGTH_LONG).show();
		    				
		    				//add 30 days to trial
		    				Calendar calE = Calendar.getInstance();  				
		    				calE.set(sPref.getInt("yearFi", -1), sPref.getInt("monthFi", -1)-1, sPref.getInt("dayFi", -1));
		    				calE.set(Calendar.DAY_OF_MONTH, calE.get(Calendar.DAY_OF_MONTH)+31);
		    				
		    				ed = sPref.edit();  
		    		         ed.putInt("dayTo", calE.get(Calendar.DAY_OF_MONTH));
		    		         ed.putInt("monthTo", calE.get(Calendar.MONTH)+1);
		    		         ed.putInt("yearTo", calE.get(Calendar.YEAR));
		    		         ed.putBoolean("canAdd30", false);
		    			     ed.commit();
		    			     
		    			     Log.i("F_add", "day="+String.valueOf(calE.get(Calendar.DAY_OF_MONTH))+" month="+String.valueOf(calE.get(Calendar.MONTH)+1)+" year="+String.valueOf(calE.get(Calendar.YEAR)));
		    			     
		    			     finish();
						}
					});
            		
            		build.setView(content);
            		
            		build.setNegativeButton(getResources().getStringArray(R.array.rate_me)[3], new DialogInterface.OnClickListener() {	
            			public void onClick(DialogInterface dialog, int which) {
            				finish();
            			}
            		});
            		alert = build.create();															// show dialog
            		alert.setCanceledOnTouchOutside(false);
            		alert.show();
         		}else {
         			//trial in using we can start loading
         	   		 CountDownTimer = new timer (2000, 1000);   		//timer to 2 seconds (tick one second)
         	         CountDownTimer.start();							//start timer
         			
         			}
         	 	}
         	 }
         
       
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
	    //buyButton.setEnabled(false);
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
				 //full version in using we can start loading
				 if (alert!=null) alert.dismiss();
				 buyed = true;
		   		 CountDownTimer = new timer (2000, 1000);   		//timer to 2 seconds (tick one second)
		         CountDownTimer.start();
		         Log.i(TAG, "succes buyed");
			 } else {
			     Log.i(TAG, "not buyed");
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
    
    private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
    
    @Override
	public void onPause(){
		super.onPause();
		if (!animation)
	      	overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}
    
	private void connectionError(final RelativeLayout arg1, final ImageView arg2, final Button arg3){
		final Runnable updaterText = new Runnable() {
	        public void run() {

	    		arg1.setVisibility(View.VISIBLE);
	    		arg2.setVisibility(View.GONE);
	    		
	    		arg3.setOnClickListener(new View.OnClickListener() {
	    			public void onClick(View v) {
	    				animation=true;
	    				Intent step1 = new Intent(getApplicationContext(), ActivityLoader.class);
	    				startActivity(step1);
	    				overridePendingTransition(R.anim.nulling_animation, R.anim.nulling_animation);
	    				finish();
	    			}
	    		});
	        }
	    };
	    handler.post(updaterText);
	}
}
