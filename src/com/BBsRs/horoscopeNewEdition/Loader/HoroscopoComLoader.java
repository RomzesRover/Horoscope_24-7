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
			
			switch(listType){
			case Constants.BUNDLE_LIST_TYPE_TODAY:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-diaria-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-diaria-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-diaria-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_TOMORROW:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-diaria-manana-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		break;
			case Constants.BUNDLE_LIST_TYPE_WEEKLY:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/general-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - solteros", doc.getElementsByClass("horoscope-box").get(0).child(5).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-parejas-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - parejas", doc.getElementsByClass("horoscope-box").get(0).child(5).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/dinero-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[3], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-semanal-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
	    		break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[0], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - solteros", doc.getElementsByClass("horoscope-box").get(0).child(5).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/amor-parejas-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[1] + " - parejas", doc.getElementsByClass("horoscope-box").get(0).child(5).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
				doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/profesion-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[2], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscopo_com_copyright)+"</a>"));
	    		doc = Jsoup.connect("https://www.horoscopo.com/horoscopos/chinos-mensual-"+context.getResources().getStringArray(R.array.horoscopo_com_yearly_chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 0)]).userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).proxy(proxy).get(); if (cancelLoad) return null;
	    		horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.horoscopo_com_kinds)[4] + " - " + context.getResources().getStringArray(R.array.chinese_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_CHINESE_SIGN, 1)], doc.getElementsByClass("horoscope-box").get(0).child(4).html(), "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.horoscope_com_copyright)+"</a>"));
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
			}
			return horoscopeCollection.size() == 0 ? null : horoscopeCollection;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
