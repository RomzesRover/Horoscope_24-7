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

public class MailRuLoader extends Loader{
	
	public MailRuLoader(int listType, SharedPreferences sPref, Context context, Handler handler, int femaleIndex, int maleIndex) {
		super(listType, sPref, context, handler, femaleIndex, maleIndex);
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
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/31/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+intPlusZero(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] +"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), "<strong>"+cal.get(Calendar.DAY_OF_MONTH)+" "+context.getResources().getStringArray(R.array.moths_of_year)[cal.get(Calendar.MONTH)] + "</strong> - " + res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
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
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/31/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+intPlusZero(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
            	res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] +"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), "<strong>"+cal.get(Calendar.DAY_OF_MONTH)+" "+context.getResources().getStringArray(R.array.moths_of_year)[cal.get(Calendar.MONTH)] + "</strong> - " + res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
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
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/31/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+intPlusZero(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);    
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] +"<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), "<strong>"+cal.get(Calendar.DAY_OF_MONTH)+" "+context.getResources().getStringArray(R.array.moths_of_year)[cal.get(Calendar.MONTH)] + "</strong> - " + res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
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
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				try {
					doc = Jsoup.connect(doc.getElementsByClass("article__text").first().child(0).select("a[href]").get(0).attr("abs:href"))
							.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
					res = doc.getElementsByClass("article__text").first().child(0).html();
					res = res.replaceAll(" \n<p><a href=.*</a></p>", "");
					res = res.replaceAll("<p>", "");
					res = res.replaceAll("</p>", "<br /><br />");
					if (res.substring(res.length()-12).equals("<br /><br />"))
						res = res.substring(0, res.length()-12); 
					horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				} catch(Exception e){
					
				}
				break;
			case Constants.BUNDLE_LIST_TYPE_MONTHLY:
				cal.add(Calendar.DATE, +6);
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/month/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll(" \n<p><a href=.*</a></p>", "");
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				try {
					doc = Jsoup.connect(doc.getElementsByClass("article__text").first().child(0).select("a[href]").get(0).attr("abs:href"))
							.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
					res = doc.getElementsByClass("article__text").first().child(0).html();
					res = res.replaceAll(" \n<p><a href=.*</a></p>", "");
					res = res.replaceAll("<p>", "");
					res = res.replaceAll("</p>", "<br /><br />");
					if (res.substring(res.length()-12).equals("<br /><br />"))
						res = res.substring(0, res.length()-12); 
					horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				} catch(Exception e){
					
				}
				
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/32/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+intPlusZero(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);    
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + "<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)),"<strong>"+context.getResources().getStringArray(R.array.moth_of_year)[cal.get(Calendar.MONTH)]+" "+String.valueOf(cal.get(Calendar.YEAR))+" года</strong> - " + res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				break;
			case Constants.BUNDLE_LIST_TYPE_YEARLY:
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/year/")
				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12); 
				String dtt = doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "");
				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+dtt+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				if (!dtt.contains(String.valueOf(cal.get(Calendar.YEAR)))){
					cal.add(Calendar.DATE, +365);
				}
            	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/33/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+intPlusZero(cal.get(Calendar.DAY_OF_MONTH)))
            	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				res = doc.getElementsByClass("article__text").first().child(0).html();
				res = res.replaceAll("<p>", "");
				res = res.replaceAll("</p>", "<br /><br />");
				if (res.substring(res.length()-12).equals("<br /><br />"))
					res = res.substring(0, res.length()-12);    
            	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + "<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), "<strong>"+String.valueOf(cal.get(Calendar.YEAR))+" год</strong> - " + res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	
            	if (cal.get(Calendar.YEAR) == 2018){
            		handler.post(new Runnable(){
						@Override
						public void run() {
							Toast.makeText(context, context.getResources().getString(R.string.next_year_predict), Toast.LENGTH_LONG).show();
						}
            		});
    				cal.add(Calendar.DATE, +365);
    				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.mail_ru_to_load_zodiac_signs)[sPref.getInt(Constants.PREFERENCES_ZODIAC_SIGN, 0)]+"/2019/")
    				.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get(); if (cancelLoad) return null;
    				res = doc.getElementsByClass("article__text").first().child(0).html();
    				res = res.replaceAll("<p>", "");
    				res = res.replaceAll("</p>", "<br /><br />");
    				if (res.substring(res.length()-12).equals("<br /><br />"))
    					res = res.substring(0, res.length()-12); 
    				horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[0], "<strong>"+doc.getElementsByClass("p-prediction__right").first().child(1).child(0).text().replaceFirst("Прогноз на ", "")+"</strong> - "+ res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
                	doc = Jsoup.connect("https://horo.mail.ru/numerology/calc/33/?v1="+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20))+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"-"+intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"&v2="+String.valueOf(cal.get(Calendar.YEAR))+"-"+intPlusZero(cal.get(Calendar.MONTH)+1)+"-"+intPlusZero(cal.get(Calendar.DAY_OF_MONTH)))
                	.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
    				res = doc.getElementsByClass("article__text").first().child(0).html();
    				res = res.replaceAll("<p>", "");
    				res = res.replaceAll("</p>", "<br /><br />");
    				if (res.substring(res.length()-12).equals("<br /><br />"))
    					res = res.substring(0, res.length()-12);    
                	horoscopeCollection.add(new HoroscopeCollection(context.getResources().getStringArray(R.array.mail_ru_kinds)[1] + "<br />" + intPlusZero(sPref.getInt(Constants.PREFERENCES_DAY_BORN, cal.get(Calendar.DAY_OF_MONTH)))+"."+intPlusZero(sPref.getInt(Constants.PREFERENCES_MONTH_BORN, cal.get(Calendar.MONTH))+1)+"."+String.valueOf(sPref.getInt(Constants.PREFERENCES_YEAR_BORN, cal.get(Calendar.YEAR)-20)), "<strong>"+String.valueOf(cal.get(Calendar.YEAR))+" год</strong> - " + res, "<a href=\""+doc.location()+"\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
            	}
				break;
			case Constants.BUNDLE_LIST_TYPE_COMPATIBILITY:
				String[] result;
				switch(femaleIndex){
				case 11:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_11)[maleIndex].split("#");
					break;
				case 10:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_10)[maleIndex].split("#");
					break;
				case 9:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_9)[maleIndex].split("#");
					break;
				case 8:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_8)[maleIndex].split("#");
					break;
				case 7:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_7)[maleIndex].split("#");
					break;
				case 6:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_6)[maleIndex].split("#");
					break;
				case 5:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_5)[maleIndex].split("#");
					break;
				case 4:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_4)[maleIndex].split("#");
					break;
				case 3:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_3)[maleIndex].split("#");
					break;
				case 2:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_2)[maleIndex].split("#");
					break;
				case 1:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_1)[maleIndex].split("#");
					break;
				case 0: default:
					result = context.getResources().getStringArray(R.array.compatibility_lookup_table_mail_ru_0)[maleIndex].split("#");
					break;
				}
				horoscopeCollection.add(new HoroscopeCollection("<i>Результат: "+result[0]+"%</i><br />Женщина - "+context.getResources().getStringArray(R.array.zodiac_signs)[femaleIndex]+"<br />Мужчина - "+context.getResources().getStringArray(R.array.zodiac_signs)[maleIndex], result[1], "<a href=\"https://horo.mail.ru/compatibility/zodiac/\">"+context.getResources().getString(R.string.mail_ru_copyright)+"</a>"));
				break;
			}
			return horoscopeCollection.size() == 0 ? null : horoscopeCollection;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
    String intPlusZero(int s){
    	return s/10==0 ? "0"+s : ""+s;
    }

}
