package com.BBsRs.horoscopeNewEdition;

import org.holoeverywhere.preference.PreferenceManager;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.RelativeLayout;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;
import com.BBsRs.horoscopeNewEdition.Base.Constants;

public class LoaderActivity extends BaseActivity {
	
    RelativeLayout relativeContentLayout;
    ImageView stars, sign, lines;
 
    //handler
    Handler handler = new Handler();
    
    // preferences 
    SharedPreferences sPref; 


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        
        //set up preferences
        sPref = PreferenceManager.getDefaultSharedPreferences(this);

        //init views
    	relativeContentLayout = (RelativeLayout)this.findViewById(R.id.contentLayout);
        stars = (ImageView)findViewById(R.id.stars);
        sign = (ImageView)findViewById(R.id.sign);
        lines = (ImageView)findViewById(R.id.lines);

		//set fonts
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.title)));
		SFUIFonts.ULTRALIGHT.apply(this, ((TextView)this.findViewById(R.id.subTitle)));
	}
	
	public void onPause(){
		//stopp all delayed stuff
		handler.removeCallbacks(mainTask);
		handler.removeCallbacks(showStars);
		handler.removeCallbacks(showLines);
		handler.removeCallbacks(showSign);
		handler.removeCallbacks(startApp);
		
		super.onPause();
	}
	
	public void onResume(){
		super.onResume();
		
		//delete old image
		stars.setImageResource(android.R.color.transparent);
		sign.setImageResource(android.R.color.transparent);
		lines.setImageResource(android.R.color.transparent);
		
		//stopp all delayed stuff
		handler.postDelayed(mainTask, 750);
	}
	
	Runnable mainTask = new Runnable(){
		@Override
		public void run() {
			handler.postDelayed(showStars, 500);
		}
	};
	
	Runnable showStars = new Runnable(){
		@Override
		public void run() {
			try{
				//start stars animation
				//set icon
		        TypedArray imagesStars = getResources().obtainTypedArray(R.array.zodiac_stars_imgs);
		        stars.setImageResource(imagesStars.getResourceId(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 1), 1));
		        imagesStars.recycle();
            	Animation flyUpAnimationStars = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_stars);
            	stars.startAnimation(flyUpAnimationStars);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.postDelayed(showLines, 500);
		}
	};
	
	Runnable showLines = new Runnable(){
		@Override
		public void run() {
			try{
				//start lines animation
				//set icon
		        TypedArray imagesLines = getResources().obtainTypedArray(R.array.zodiac_lines_imgs);
		        lines.setImageResource(imagesLines.getResourceId(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 1), 1));
		        imagesLines.recycle();
            	Animation flyUpAnimationLines = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_stars);
            	lines.startAnimation(flyUpAnimationLines);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.postDelayed(showSign, 500);
		}
	};
	
	Runnable showSign = new Runnable(){
		@Override
		public void run() {
			try{
				//start sign animation
				//set icon
		        TypedArray images = getResources().obtainTypedArray(R.array.zodiac_signs_imgs);
		        sign.setImageResource(images.getResourceId(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 1), 1));
		        images.recycle();
            	Animation flyUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_sign);
            	sign.startAnimation(flyUpAnimation);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			handler.postDelayed(startApp, 1500);
		}
	};
	
	Runnable startApp = new Runnable(){
		@Override
		public void run() {
        	//create intent
        	Intent refresh = new Intent(getApplicationContext(), ContentActivity.class);
			//restart activity
		    startActivity(refresh);   
		    //set  animation
		    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
		    // stop curr activity
		    finish();
		}
	};

}
