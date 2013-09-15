package com.BBsRs.horoscopenew;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.BBsRs.horoscopenew.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.widget.RemoteViews;

public class appWidgetProvider extends AppWidgetProvider {
	SharedPreferences sPref; 
	int zodiacNumber,day,lg; String resDay="", title="";
	String tag = "WidgetToday";
	int providerNumber;
	Document  doc; // ��� �������� � ���������� �������� 
	public static String ACTION_WIDGET_RECEIVER = "ActionReceiverWidget";
	public static String ACTION_WIDGET_UPDATE = "ACTION_WIDGET_UPDATE";
	
	@Override
	public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds){
		//������� ����� RemoteViews
		
        final RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.a);
        
        Intent active = new Intent(context, appWidgetProvider.class);
        active.setAction(ACTION_WIDGET_RECEIVER);
        Intent activeUpdate = new Intent(context, appWidgetProvider.class);
        activeUpdate.setAction(ACTION_WIDGET_UPDATE);
        
        
        PendingIntent actionPendingIntent = PendingIntent.getBroadcast(context, 0, active, 0);
        remoteViews.setOnClickPendingIntent(R.id.openFull, actionPendingIntent);
        
        PendingIntent actionPendingIntentUpdate = PendingIntent.getBroadcast(context, 0, activeUpdate, 0);
        remoteViews.setOnClickPendingIntent(R.id.update, actionPendingIntentUpdate);
        
        sPref=context.getSharedPreferences("T", 1); 
        zodiacNumber=sPref.getInt("zodiacNumber", 13);		//����� �����
        providerNumber=sPref.getInt("providerNumber",7);	//����� ����������
        sPref = context.getSharedPreferences("T", 1);
        
        Thread thr=new Thread(new Runnable() {				//������ � ����� ������
	        public void run() {
        switch(providerNumber){
        case 4:
        	//horo.mail
        	try {							//�������� �������
				doc = Jsoup.connect("http://horo.mail.ru/prediction/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadMailRu)[1]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				resDay=doc.getElementById("tm_today").child(0).text()+"\n"+ifFf(doc.getElementById("tm_today").child(1).text()+"\n\n")+doc.getElementById("tm_today").child(2).text()+"\n\n"+doc.getElementById("tm_today").child(3).text();
        } catch (NotFoundException e) {
			Log.e(tag, "data Error");
			resDay = context.getResources().getString(R.string.error);
			e.printStackTrace();
		} catch (IOException e) {
			Log.e(tag, "Load Error"); 
			resDay = context.getResources().getString(R.string.error);
			e.printStackTrace();
		} catch (NullPointerException e) {
    		Log.e(tag, "null Load Error");
    		resDay = context.getResources().getString(R.string.error);
			e.printStackTrace();
		} catch (Exception e) {
    		Log.e(tag, "other Load Error");
    		resDay = context.getResources().getString(R.string.error);
			e.printStackTrace();
		}
        	break;
        case 0:
        	//horoscope.com
        	try {							//�������� �������
				doc = Jsoup.connect("http://my.horoscope.com/astrology/"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadHoroscopeCom)[1]+"-horoscope-"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				resDay=doc.getElementsByClass("col420").get(0).child(2).text().replaceAll("Share with friends:","")+"\n"+doc.getElementsByClass("fontdef1").get(1).text();
        	} catch (NotFoundException e) {
    			Log.e(tag, "data Error");
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (IOException e) {
    			Log.e(tag, "Load Error"); 
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (NullPointerException e) {
        		Log.e(tag, "null Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (Exception e) {
        		Log.e(tag, "other Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		}
        	break;
        case 1:
        	//en.yahoo
        	try {							//�������� �������
        		Calendar c = Calendar.getInstance();
        		doc = Jsoup.connect("http://shine.yahoo.com/horoscope/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadEnYahooCom)[0]+String.valueOf(c.get(Calendar.YEAR))+monthPlusZero(String.valueOf(c.get(Calendar.MONTH)+1))+String.valueOf(c.get(Calendar.DAY_OF_MONTH))+".html")
						.userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout))
						.timeout(context.getResources().getInteger(R.integer.user_timeout))
						.get();
        		resDay=doc.getElementById("tab-date").text()+"\n"+doc.getElementsByClass("astro-tab-body").first().text();
        	} catch (NotFoundException e) {
    			Log.e(tag, "data Error");
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (IOException e) {
    			Log.e(tag, "Load Error"); 
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (NullPointerException e) {
        		Log.e(tag, "null Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (Exception e) {
        		Log.e(tag, "other Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		}
        	break;
        case 2:
        	//fr.yahoo.com
        	try {							//�������� �������
        		Calendar c = Calendar.getInstance();
        		doc = Jsoup.connect("http://fr.astrology.yahoo.com/horoscope/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadFrYahooCom)[zodiacNumber-1].replace("*", "%C3%A9")+"/general"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadFrYahooCom)[0]+String.valueOf(c.get(Calendar.YEAR))+monthPlusZero(String.valueOf(c.get(Calendar.MONTH)+1))+String.valueOf(c.get(Calendar.DAY_OF_MONTH))+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				resDay=doc.getElementById("tab-date").text()+"\n"+doc.getElementsByClass("astro-tab-body").first().text();
        	} catch (NotFoundException e) {
    			Log.e(tag, "data Error");
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (IOException e) {
    			Log.e(tag, "Load Error"); 
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (NullPointerException e) {
        		Log.e(tag, "null Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (Exception e) {
        		Log.e(tag, "other Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		}
        	break;
        case 3:
        	//de.yahoo.com
        	try {							//�������� �������
        		Calendar c = Calendar.getInstance();
        		doc = Jsoup.connect("http://de.horoskop.yahoo.com/horoskop/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadDeYahooCom)[zodiacNumber-1].replace("*", "%C3%B6").replace("+", "%C3%BC")+"/astro"+context.getResources().getStringArray(R.array.nameOfHoroscopecForLoadDeYahooCom)[0]+String.valueOf(c.get(Calendar.YEAR))+monthPlusZero(String.valueOf(c.get(Calendar.MONTH)+1))+String.valueOf(c.get(Calendar.DAY_OF_MONTH))+".html").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				resDay=doc.getElementById("tab-date").text()+"\n"+doc.getElementsByClass("astro-tab-body").first().text();
        	} catch (NotFoundException e) {
    			Log.e(tag, "data Error");
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (IOException e) {
    			Log.e(tag, "Load Error"); 
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (NullPointerException e) {
        		Log.e(tag, "null Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (Exception e) {
        		Log.e(tag, "other Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		}
        	break;
        case 5:
        	//goroskop.ru
        	try {
				doc = Jsoup.connect("http://goroskop.ru/publish/open_article/29/"+context.getResources().getStringArray(R.array.nameOfzodiacForLoadMailRu)[zodiacNumber-1]+"/").userAgent(context.getResources().getString(R.string.user_agent)).timeout(context.getResources().getInteger(R.integer.user_timeout)).get();
				resDay=doc.getElementById("gContent").child(0).child(0).text()+	"\n"+doc.getElementById("article").child(1).text()+"\n"+doc.getElementById("article").child(2).text();
        	} catch (NotFoundException e) {
    			Log.e(tag, "data Error");
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (IOException e) {
    			Log.e(tag, "Load Error"); 
    			resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (NullPointerException e) {
        		Log.e(tag, "null Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		} catch (Exception e) {
        		Log.e(tag, "other Load Error");
        		resDay = context.getResources().getString(R.string.error);
    			e.printStackTrace();
    		}
        	
        	break;
        default:
        	resDay=context.getString(R.string.nosett);
        	break;
        }
        remoteViews.setTextViewText(R.id.name, sPref.getString("name",context.getResources().getString(R.string.noname)));		//������ ���
        remoteViews.setTextViewText(R.id.resDay, resDay);		//������ ����� ���������
        if (zodiacNumber!=13)
        remoteViews.setTextViewText(R.id.sign, context.getResources().getStringArray(R.array.zodiac_signs)[zodiacNumber-1]+" - "+context.getResources().getString(R.string.today));		//����� ����
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);
	        }
        });
	thr.start();
       
        //��������� ������
        
        
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
	    // TODO Auto-generated method stub
		//����� ��� Broadcast, ��������� � ������� ���������
        final String action = intent.getAction();
        if (ACTION_WIDGET_RECEIVER.equals(action)) {
        	sPref = context.getSharedPreferences("T", 1);
			String prpr = sPref.getString("name", "");
	        if (prpr.length()<1){
	        	Intent mailClient = new Intent(context , ActivityHoroSettings1.class);
	             mailClient.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             context.startActivity(mailClient);
	        } else {
	        	Intent mailClient = new Intent(context , ActivityLoader.class);
	             mailClient.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	             context.startActivity(mailClient);
	        }
        } else if (ACTION_WIDGET_UPDATE.equals(action)){
        	AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
    	    ComponentName thisAppWidget = new ComponentName(context.getPackageName(), appWidgetProvider.class.getName());
    	    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(thisAppWidget);

    	    this.onUpdate(context, appWidgetManager, appWidgetIds);
        } 
		super.onReceive(context, intent);
	}
	public String ifFf(final String arg1){
		if (arg1.length()<5){
			return "";}
			else {return arg1;}
	}
	
	public String monthPlusZero(String arg1){
		if (arg1.length()==1)
		return "0"+arg1;
		else 
		return arg1;
		
	}
}
