package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;

import org.holoeverywhere.preference.SharedPreferences;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class HoroscopeComLoader {
	
	int listType;
    // preferences 
    SharedPreferences sPref;
    Context context;
    boolean cancelLoad = false;
	
	public HoroscopeComLoader(int _listType, SharedPreferences _sPref, Context _context){
		listType = _listType;
		sPref = _sPref;
		context = _context;
	}
	
	public void abortLoad(){
		cancelLoad = true;
	}
	
	public ArrayList<HoroscopeCollection> loadCurrList(){
		try {
			Document doc;
			ArrayList<HoroscopeCollection> horoscopeCollection = new ArrayList<HoroscopeCollection>();
			
			switch(listType){
			case Constants.BUNDLE_LIST_TYPE_YESTERDAY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-yesterday.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-yesterday.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-yesterday.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
				break;
			case Constants.BUNDLE_LIST_TYPE_TODAY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-today.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-today.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-today.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
				break;
			case Constants.BUNDLE_LIST_TYPE_TOMORROW:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-daily-tomorrow.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-daily-tomorrow.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-daily-tomorrow.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
				break;
			case Constants.BUNDLE_LIST_TYPE_WEEKLY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-weekly-single.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " single", doc.getElementsByClass("horoscope-content").get(0).child(1).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-weekly-couple.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " couple", doc.getElementsByClass("horoscope-content").get(0).child(1).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[3], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/money/horoscope-money-weekly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-weekly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-weekly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
				break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/general/horoscope-general-monthly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[0], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-monthly-single.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " single", doc.getElementsByClass("horoscope-content").get(0).child(1).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/love/horoscope-love-monthly-couple.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[1] + " couple", doc.getElementsByClass("horoscope-content").get(0).child(1).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/career/horoscope-career-monthly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[2], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/wellness/horoscope-wellness-monthly.aspx?sign="+(sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)+1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[4], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/chinese/horoscope-chinese-monthly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN_CORRECTED, 1)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/numerology/horoscope-numerology-monthly.aspx?sign="+sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3)).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscope_com_kinds)[6] + " - " + sPref.getInt(Constants.PREFERENCES_PERSONAL_NUMBER, 3), doc.getElementsByClass("horoscope-content").get(0).child(0).text()));
				break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY:
				doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=personal").get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("personal").child(0).html(), doc.getElementById("personal").child(1).html())); if (cancelLoad) return null;
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=career").get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("career").child(0).html(), doc.getElementById("career").child(1).html())); if (cancelLoad) return null;
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=love_single").get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("singles").child(0).html(), doc.getElementById("singles").child(1).html())); if (cancelLoad) return null;
	    		doc = Jsoup.connect("https://www.horoscope.com/us/horoscopes/yearly/2018-horoscope-"+context.getResources().getStringArray(R.array.horoscope_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+".aspx?type=love_couples").get();
	    		horoscopeCollection.add(new HoroscopeCollection(doc.getElementById("couples").child(0).html(), doc.getElementById("couples").child(1).html())); if (cancelLoad) return null;
				break;
			}
			return horoscopeCollection;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
