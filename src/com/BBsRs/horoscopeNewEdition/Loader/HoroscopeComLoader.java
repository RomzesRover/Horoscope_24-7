package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;

import org.holoeverywhere.preference.SharedPreferences;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.os.Handler;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class HoroscopeComLoader extends Loader{
	
	public HoroscopeComLoader(int listType, SharedPreferences sPref, Context context, Handler handler, int femaleIndex, int maleIndex) {
		super(listType, sPref, context, handler, femaleIndex, maleIndex);
	}

	@Override
	public ArrayList<HoroscopeCollection> loadCurrList(){
		try {
			Document doc;
			ArrayList<HoroscopeCollection> horoscopeCollection = new ArrayList<HoroscopeCollection>();
			
			if (sPref.getBoolean(Constants.PREFERENCES_USE_PROXY_SERVER, false)){
				loadAndSetupProxyServer("https://www.horoscope.com/");
			}
			
			switch(listType){
			case Constants.BUNDLE_LIST_TYPE_YESTERDAY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-yesterday.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-yesterday.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TODAY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-today.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-today.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TOMORROW:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-tomorrow.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-tomorrow.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_WEEKLY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-weekly-single.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " single", doc.getElementsByClass("main-horoscope").get(0).child(2).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-weekly-couple.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " couple", doc.getElementsByClass("main-horoscope").get(0).child(2).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/money/horoscope-money-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[3], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-weekly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-weekly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-monthly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-monthly-single.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " single", doc.getElementsByClass("main-horoscope").get(0).child(2).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-monthly-couple.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " couple", doc.getElementsByClass("main-horoscope").get(0).child(2).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-monthly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-monthly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-monthly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-monthly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("main-horoscope").get(0).child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=personal").proxy(proxy).get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("personal").child(0).html(), doc.getElementById("personal").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=career").proxy(proxy).get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("career").child(0).html(), doc.getElementById("career").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=love_single").proxy(proxy).get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("singles").child(0).html(), doc.getElementById("singles").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=love_couples").proxy(proxy).get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("couples").child(0).html(), doc.getElementById("couples").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
				break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY_CHINA:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-chinese-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs_chinese)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]+".aspx").proxy(proxy).get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("overview").child(0).html() + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementById("overview").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("love").child(0).html() + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementById("love").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("family").child(0).html() + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementById("family").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("work").child(0).html() + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementById("work").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("money").child(0).html() + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementById("money").child(1).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>")); if (cancelLoad) return null;
				break;
			case Constants.BUNDLE_LIST_TYPE_COMPATIBILITY:
				String result;
				switch(femaleIndex){
				case 11:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_11)[maleIndex];
					break;
				case 10:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_10)[maleIndex];
					break;
				case 9:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_9)[maleIndex];
					break;
				case 8:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_8)[maleIndex];
					break;
				case 7:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_7)[maleIndex];
					break;
				case 6:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_6)[maleIndex];
					break;
				case 5:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_5)[maleIndex];
					break;
				case 4:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_4)[maleIndex];
					break;
				case 3:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_3)[maleIndex];
					break;
				case 2:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_2)[maleIndex];
					break;
				case 1:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_1)[maleIndex];
					break;
				case 0: default:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscope_com_0)[maleIndex];
					break;
				}
				horoscopeCollection.add(new HoroscopeCollection("Female - "+context.getResources().getStringArray(R.array.zodiac_signs)[femaleIndex]+"<br />Male - "+context.getResources().getStringArray(R.array.zodiac_signs)[maleIndex], result, "<a href=\"https://www.horoscope.com/us/games/compatibility/game-love-compatibility.aspx\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			}
			return horoscopeCollection.size() == 0 ? null : horoscopeCollection;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
