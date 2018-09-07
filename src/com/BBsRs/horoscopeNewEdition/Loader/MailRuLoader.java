package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;
import java.util.Calendar;

import org.holoeverywhere.preference.SharedPreferences;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class MailRuLoader extends Loader{
	
	public MailRuLoader(int listType, SharedPreferences sPref, Context context) {
		super(listType, sPref, context);
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
				cal.add(Calendar.DATE, -1);
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/yesterday/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/31/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + " - " + cal.get(Calendar.DAY_OF_MONTH)+" "+context.getResources().getStringArray(R.array.moths_of_year)[cal.get(Calendar.MONTH)]+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TODAY:
				cal.setTimeInMillis(System.currentTimeMillis());
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/today/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);       	
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/31/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
            	res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + " - " + cal.get(Calendar.DAY_OF_MONTH)+" "+context.getResources().getStringArray(R.array.moths_of_year)[cal.get(Calendar.MONTH)]+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TOMORROW:
				cal.add(Calendar.DATE, +1);
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/tomorrow/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/31/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);    
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + " - " + cal.get(Calendar.DAY_OF_MONTH)+" "+context.getResources().getStringArray(R.array.moths_of_year)[cal.get(Calendar.MONTH)]+"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_WEEKLY:
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/week/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll(" \n<p><a href=.*</a></p>", "");
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				try {
					doc = Jsoup.connect(doc.getElementsByClass("article__text").first().child(0).select("a[href]").get(0).attr("abs:href"))
							.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
					res = doc.getElementsByClass("article__text").first().child(0).html();
					res = res.replaceAll(" \n<p><a href=.*</a></p>", "");
					res = res.replaceAll("<p>", "");
					res = res.replaceAll("</p>", "<br /><br />");
					if (res.substring(res.length()-12).equals("<br /><br />"))
						res = res.substring(0, res.length()-12); 
					horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				} catch(Exception e){
					
				}
				break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				cal.add(Calendar.DATE, +6);
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/month/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/32/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);    
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + " - " + context.getResources().getStringArray(R.array.moth_of_year)[cal.get(Calendar.MONTH)]+" "+String.valueOf(cal.get(Calendar.YEAR))+" года <br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY:
				cal.add(Calendar.DATE, +26);
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/year/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0] + " - " + doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", ""), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/33/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+String.valueOf(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);    
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + " - " +String.valueOf(cal.get(Calendar.YEAR))+" год <br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
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
