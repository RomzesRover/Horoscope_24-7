package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;
import java.util.Calendar;

import org.holoeverywhere.preference.SharedPreferences;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.os.Handler;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class GoAstroDeLoader extends Loader{
	
	public GoAstroDeLoader(int listType, SharedPreferences sPref, Context context, Handler handler) {
		super(listType, sPref, context, handler);
	}

	@Override
	public ArrayList<HoroscopeCollection> loadCurrList(){
		try {
			Document doc;
			ArrayList<HoroscopeCollection> horoscopeCollection = new ArrayList<HoroscopeCollection>();
			Calendar cal = Calendar.getInstance();
			String res;
			
			switch(listType){
			case Constants.BUNDLE_LIST_TYPE_YESTERDAY:
				doc = Jsoup.connect("https://www.goastro.de/horoskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"_0/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Sonnenschild<br>"+doc.getElementsByClass("current-day").first().text().replaceAll(".*, ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				
				doc = Jsoup.connect("https://www.goastro.de/tageshoroskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs_no_id)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"-"+sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH))+"-"+(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-0/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Ihr individuelles Horoskop"+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				
				doc = Jsoup.connect("https://www.goastro.de/numeroskope_0_"+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("china-result__item")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Numeroskop<br>"+doc.getElementsByClass("china-result__date--color").first().text().replaceAll(".*, ", "")+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TODAY:
				doc = Jsoup.connect("https://www.goastro.de/horoskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"_1/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Sonnenschild<br>"+doc.getElementsByClass("current-day").first().text().replaceAll(".*, ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				
				doc = Jsoup.connect("https://www.goastro.de/tageshoroskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs_no_id)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"-"+sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH))+"-"+(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-1/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Ihr individuelles Horoskop<br />"+doc.getElementsByClass("current-day").first().text().replaceAll(".*, ", "")+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				
				doc = Jsoup.connect("https://www.goastro.de/numeroskope_1_"+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("china-result__item")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Numeroskop<br>"+doc.getElementsByClass("china-result__date--color").first().text().replaceAll(".*, ", "")+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TOMORROW:
				doc = Jsoup.connect("https://www.goastro.de/horoskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"_2/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Sonnenschild<br>"+doc.getElementsByClass("current-day").first().text().replaceAll(".*, ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				
				doc = Jsoup.connect("https://www.goastro.de/tageshoroskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs_no_id)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"-"+sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH))+"-"+(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-2/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Ihr individuelles Horoskop"+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				
				doc = Jsoup.connect("https://www.goastro.de/numeroskope_2_"+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("china-result__item")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Numeroskop<br>"+doc.getElementsByClass("china-result__date--color").first().text().replaceAll(".*, ", "")+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_WEEKLY:
				doc = Jsoup.connect("https://www.goastro.de/wochenhoroskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Sonnenschild<br>"+doc.getElementsByClass("detail__title--week").first().text().replaceAll(".*, ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				doc = Jsoup.connect("https://www.goastro.de/monatshoroskop-"+context.getResources().getStringArray(R.array.goastro_de_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = "";
				for (Element one : doc.getElementsByClass("article")){
					res+=one.child(0).text()+"<br />"+one.child(1).text()+"<br /><br />";
				}
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);
				horoscopeCollection.add(new HoroscopeCollection("Sonnenschild", res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.goastro_de_copyright)+"</a>"));
				break;
			}
			return horoscopeCollection;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
    String intPlusZero(int s){
    	return s/10==0 ? "0"+s : ""+s;
    }

}
