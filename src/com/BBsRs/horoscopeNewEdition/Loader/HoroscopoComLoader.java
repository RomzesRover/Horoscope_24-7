package com.BBsRs.horoscopeNewEdition.Loader;

import java.util.ArrayList;
import java.util.Calendar;

import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import android.content.Context;
import android.os.Handler;

import com.BBsRs.horoscopeNewEdition.R;
import com.BBsRs.horoscopeNewEdition.Base.Constants;
import com.BBsRs.horoscopeNewEdition.Base.HoroscopeCollection;

public class HoroscopoComLoader extends Loader{
	
	public HoroscopoComLoader(int listType, SharedPreferences sPref, Context context, Handler handler, int femaleIndex, int maleIndex) {
		super(listType, sPref, context, handler, femaleIndex, maleIndex);
	}

	@Override
	public ArrayList<HoroscopeCollection> loadCurrList(){
		try {
			Document doc;
			ArrayList<HoroscopeCollection> horoscopeCollection = new ArrayList<HoroscopeCollection>();
			Calendar cal = Calendar.getInstance();
			
			if (sPref.getBoolean(Constants.PREFERENCES_USE_PROXY_SERVER, false)){
				loadAndSetupProxyServer("https://www.horoscopo.com/");
			}
			
			String one = "";
			
			switch(listType){
			case Constants.BUNDLE_LIST_TYPE_TODAY:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-diaria-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-diaria-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-diaria-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TOMORROW:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		break;
			case Constants.BUNDLE_LIST_TYPE_WEEKLY:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], "<strong>" + one.substring(0, one.indexOf('-', one.indexOf('-')+1)) + "</strong>" + one.substring(one.indexOf('-', one.indexOf('-')+1), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(5).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - solteros", "<strong>" + one.substring(0, one.indexOf('-', one.indexOf('-')+1)) + "</strong>" + one.substring(one.indexOf('-', one.indexOf('-')+1), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-parejas-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(5).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - parejas", "<strong>" + one.substring(0, one.indexOf('-', one.indexOf('-')+1)) + "</strong>" + one.substring(one.indexOf('-', one.indexOf('-')+1), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], "<strong>" + one.substring(0, one.indexOf('-', one.indexOf('-')+1)) + "</strong>" + one.substring(one.indexOf('-', one.indexOf('-')+1), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/dinero-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[3], "<strong>" + one.substring(0, one.indexOf('-', one.indexOf('-')+1)) + "</strong>" + one.substring(one.indexOf('-', one.indexOf('-')+1), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>" + one.substring(0, one.indexOf('-', one.indexOf('-')+1)) + "</strong>" + one.substring(one.indexOf('-', one.indexOf('-')+1), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(5).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - solteros", "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-parejas-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(5).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - parejas", "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
				one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		one = doc.getElementsByClass("horoscope-box").get(0).child(4).html();
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>" + one.substring(0, one.indexOf('-')) + "</strong>" + one.substring(one.indexOf('-'), one.length()), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY:
				if (cal.get(Calendar.YEAR) == 2018){
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - solteros", "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-parejas-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - parejas", "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2] + " - " + context.getResources().getStringArray(R.array.horoscopo_com_kinds)[3], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				}
				try {
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - solteros", "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-parejas-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - parejas", "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2] + " - " + context.getResources().getStringArray(R.array.horoscopo_com_kinds)[3], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
		    		if (cal.get(Calendar.YEAR) == 2018)
			    		handler.post(new Runnable(){
							@Override
							public void run() {
								Toast.makeText(context, context.getResources().getString(R.string.next_year_predict_es), Toast.LENGTH_LONG).show();
							}
	            		});
				} catch(Exception e){
					e.printStackTrace();
				}
	    		break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY_CHINA:
				if (cal.get(Calendar.YEAR) == 2018){
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-prevision-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-amor-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-familia-amigos-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[6] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-profesion-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-dinero-anual-2018-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[3] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2018</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
		    	}
				try {
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-prevision-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[5] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-amor-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-familia-amigos-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[6] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-profesion-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
					doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-dinero-anual-2019-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
		    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[3] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], "<strong>"+context.getResources().getStringArray(R.array.horoscopo_com_horoscopes)[4]+" 2019</strong> - " + doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
		    		if (cal.get(Calendar.YEAR) == 2018)
			    		handler.post(new Runnable(){
							@Override
							public void run() {
								Toast.makeText(context, context.getResources().getString(R.string.next_year_predict_es), Toast.LENGTH_LONG).show();
							}
	            		});
				} catch(Exception e){
					e.printStackTrace();
				}
	    		break;
			case Constants.BUNDLE_LIST_TYPE_COMPATIBILITY:
				String result;
				switch(femaleIndex){
				case 11:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_11)[maleIndex];
					break;
				case 10:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_10)[maleIndex];
					break;
				case 9:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_9)[maleIndex];
					break;
				case 8:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_8)[maleIndex];
					break;
				case 7:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_7)[maleIndex];
					break;
				case 6:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_6)[maleIndex];
					break;
				case 5:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_5)[maleIndex];
					break;
				case 4:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_4)[maleIndex];
					break;
				case 3:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_3)[maleIndex];
					break;
				case 2:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_2)[maleIndex];
					break;
				case 1:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_1)[maleIndex];
					break;
				case 0: default:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_horoscopo_com_0)[maleIndex];
					break;
				}
				horoscopeCollection.add(new HoroscopeCollection("Hembra - "+context.getResources().getStringArray(R.array.zodiac_signs)[femaleIndex]+"<br />Masculino - "+context.getResources().getStringArray(R.array.zodiac_signs)[maleIndex], result, "<a href=\"https://www.horoscopo.com/compatibilidad/amor\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				break;
			}
			return horoscopeCollection.size() == 0 ? null : horoscopeCollection;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
