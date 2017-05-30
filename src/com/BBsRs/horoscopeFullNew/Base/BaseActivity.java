package com.BBsRs.horoscopeFullNew.Base;

import java.util.Locale;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.TextView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import com.BBsRs.SFUIFontsEverywhere.SFUIFonts;
import com.BBsRs.horoscopeNewEdition.R;

public class BaseActivity extends Activity {
	
	public void setLocale(String lang) {
		 Locale myLocale;
	     myLocale = new Locale(lang);
	     Resources res = getResources();
	     DisplayMetrics dm = res.getDisplayMetrics();
	     Configuration conf = res.getConfiguration();
	     conf.locale = myLocale;
	     res.updateConfiguration(conf, dm);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (getSupportActionBar() != null) {
			getSupportActionBar().setSubtitle(null);
			getSupportActionBar().setTitle(null);
		}
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		actionBar = getSupportActionBar();
		
		if (actionBar==null) return;
		
		//init views
		actionTitle = getLayoutInflater().inflate(R.layout.action_bar, null);
		maintitle = ((TextView)actionTitle.findViewById(R.id.titleActionBar));
		subtitle = ((TextView)actionTitle.findViewById(R.id.subtitleActionBar));
		
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setCustomView(actionTitle,
		        new ActionBar.LayoutParams(
		                ActionBar.LayoutParams.WRAP_CONTENT,
		                ActionBar.LayoutParams.MATCH_PARENT,
		                Gravity.CENTER
		        )
		);
		//set font
		SFUIFonts.ULTRALIGHT.apply(BaseActivity.this, maintitle);
		SFUIFonts.ULTRALIGHT.apply(this, subtitle);
	}
	
	ActionBar actionBar;
	View actionTitle;
	TextView maintitle;
	TextView subtitle;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB) 
    public void setTitle(String title){
		
		if (actionBar==null) return;
		
		//separate text
		final String[] titles = title.split(";");
		
		if (titles.length==1){
			if (subtitle.getText().length() > 0){
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					maintitle.setText(titles[0]);
					AnimatorSet animSet = new AnimatorSet();
					//convertDpToPixel means that we move texview by 5dp converted to pixels
		            ObjectAnimator transAnim = ObjectAnimator.ofFloat(maintitle, "translationY", 0f, convertDpToPixel(5f, actionTitle.getContext()));
		            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(subtitle, "alpha", 1f, 0f);
		            animSet.playTogether(transAnim, alphaAnim);
		            animSet.setDuration(250);
		            animSet.addListener(new  AnimatorListenerAdapter(){
						@Override
						public void onAnimationEnd(Animator arg0) {
							subtitle.setText("");
						}
		            });
		            animSet.start();
		        } else {
		        	maintitle.setText(titles[0]);
		        	subtitle.setText("");
		        	subtitle.setVisibility(View.GONE);
		        }
			} else {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					AnimatorSet animSet = new AnimatorSet();
					//convertDpToPixel means that we move texview by 5dp converted to pixels
		            ObjectAnimator transAnim = ObjectAnimator.ofFloat(maintitle, "translationY", convertDpToPixel(5f, actionTitle.getContext()), convertDpToPixel(2f, actionTitle.getContext()));
		            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(maintitle, "alpha", 1f, 0f);
		            animSet.playTogether(transAnim, alphaAnim);
		            animSet.setDuration(250);
		            animSet.addListener(new  AnimatorListenerAdapter(){
						@Override
						public void onAnimationEnd(Animator arg0) {
							maintitle.setText(titles[0]);
							AnimatorSet animSet = new AnimatorSet();
				            ObjectAnimator transAnim = ObjectAnimator.ofFloat(maintitle, "translationY", convertDpToPixel(7f, actionTitle.getContext()), convertDpToPixel(5f, actionTitle.getContext()));
				            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(maintitle, "alpha", 0f, 1f);
				            animSet.playTogether(transAnim, alphaAnim);
				            animSet.setDuration(250);
				            animSet.start();
						}
		            });
		            animSet.start();
		        } else {
		        	maintitle.setText(titles[0]);
		        	subtitle.setText("");
		        	subtitle.setVisibility(View.GONE);
		        }
			}
		} else {
			if (titles[0].equals(maintitle.getText().toString()) && titles[1].equals(subtitle.getText().toString())) return;
			
			if (!titles[0].equals(maintitle.getText().toString())){
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					AnimatorSet animSet = new AnimatorSet();
					//convertDpToPixel means that we move texview by 5dp converted to pixels
		            ObjectAnimator transAnim = ObjectAnimator.ofFloat(maintitle, "translationY", 0f, -convertDpToPixel(2f, actionTitle.getContext()));
		            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(maintitle, "alpha", 1f, 0f);
		            animSet.playTogether(transAnim, alphaAnim);
		            animSet.setDuration(250);
		            animSet.addListener(new  AnimatorListenerAdapter(){
						@Override
						public void onAnimationEnd(Animator arg0) {
							maintitle.setText(titles[0]);
							AnimatorSet animSet = new AnimatorSet();
				            ObjectAnimator transAnim = ObjectAnimator.ofFloat(maintitle, "translationY", convertDpToPixel(2f, actionTitle.getContext()), 0f);
				            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(maintitle, "alpha", 0f, 1f);
				            animSet.playTogether(transAnim, alphaAnim);
				            animSet.setDuration(250);
				            animSet.start();
						}
		            });
		            animSet.start();
		        } else {
		        	maintitle.setText(titles[0]);
		        	maintitle.setVisibility(View.VISIBLE);
		        }
			} else {
				maintitle.setText(titles[0]);
			}
			
			if (subtitle.getText().length() == 0){
				subtitle.setText(titles[1]);
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					AnimatorSet animSet = new AnimatorSet();
					//convertDpToPixel means that we move texview by 5dp converted to pixels
		            ObjectAnimator transAnim = ObjectAnimator.ofFloat(maintitle, "translationY", convertDpToPixel(5f, actionTitle.getContext()), 0f);
		            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(subtitle, "alpha", 0f, 1f);
		            animSet.playTogether(transAnim, alphaAnim);
		            animSet.setDuration(250);
		            animSet.start();
		        } else {
		        	subtitle.setVisibility(View.VISIBLE);
		        }
			} else {
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
					AnimatorSet animSet = new AnimatorSet();
					//convertDpToPixel means that we move texview by 5dp converted to pixels
		            ObjectAnimator transAnim = ObjectAnimator.ofFloat(subtitle, "translationY", 0f, -convertDpToPixel(2f, actionTitle.getContext()));
		            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(subtitle, "alpha", 1f, 0f);
		            animSet.playTogether(transAnim, alphaAnim);
		            animSet.setDuration(250);
		            animSet.addListener(new  AnimatorListenerAdapter(){
						@Override
						public void onAnimationEnd(Animator arg0) {
							subtitle.setText(titles[1]);
							AnimatorSet animSet = new AnimatorSet();
				            ObjectAnimator transAnim = ObjectAnimator.ofFloat(subtitle, "translationY", convertDpToPixel(2f, actionTitle.getContext()), 0f);
				            ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(subtitle, "alpha", 0f, 1f);
				            animSet.playTogether(transAnim, alphaAnim);
				            animSet.setDuration(250);
				            animSet.start();
						}
		            });
		            animSet.start();
		        } else {
		        	subtitle.setText(titles[1]);
		        	subtitle.setVisibility(View.VISIBLE);
		        }
			}
		}
    }
	
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
}
